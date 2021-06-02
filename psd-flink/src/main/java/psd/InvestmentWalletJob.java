package psd;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;
import psd.alerts.StatsAlert;
import psd.stats.StatsAggregate;
import psd.stats.StatsAggregationResult;
import psd.stats.StatsChecker;
import psd.utils.Tokenizer;

import java.util.List;
import java.util.Map;

public class InvestmentWalletJob {
  private static final String JOB_NAME = "Streaming InvestmentWalletJob";
  private static final String INPUT_KEY = "input";
  private static final String OUTPUT_KEY = "output";
  private static final String PATTERN_NAME = "statsChecker";

  public static void main(String[] args) throws Exception {

    // Checking input parameters
    final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);

    // Set up the execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // Make parameters available in the web interface
    env.getConfig().setGlobalJobParameters(params);

    // Get input data
    DataStream<String> text = null;
    if (params.has(INPUT_KEY)) {
      // Union all the inputs from text files
      for (String input : params.getMultiParameterRequired(INPUT_KEY)) {
        if (text == null) {
          text = env.readTextFile(input);
        } else {
          text = text.union(env.readTextFile(input));
        }
      }
      Preconditions.checkNotNull(text, "Input DataStream should not be null.");
    }

    if (text != null) {
      // Assign data stream with sliding window of 30 samples, moved by 1 sample, keyed by asset id
      DataStream<Tuple2<Integer, StatsAggregationResult>> stream =
              text.flatMap(new Tokenizer())
                      .keyBy(value -> value.f0)
                      .countWindow(30, 1)
                      .aggregate(new StatsAggregate());

      // Print stream output to file if output file arg is provided
      if (params.has(OUTPUT_KEY)) {
        stream.writeAsText(params.get(OUTPUT_KEY));
      }

      // Print stream to stdout
      stream.print();

      // Simple alert pattern, filter "suspicious" statistics
      Pattern<Tuple2<Integer, StatsAggregationResult>, ?> alertPattern = Pattern.begin(PATTERN_NAME);

      // Create a pattern stream from our alert pattern
      PatternStream<Tuple2<Integer, StatsAggregationResult>> alertPatternStream =
              CEP.pattern(stream.keyBy(value -> value.f0), alertPattern);

      // Generate stats alerts and collect them
      DataStream<StatsAlert> alerts = alertPatternStream.flatSelect(
              (Map<String, List<Tuple2<Integer, StatsAggregationResult>>> pattern, Collector<StatsAlert> out) -> {
                Tuple2<Integer, StatsAggregationResult> tuple = pattern.get(PATTERN_NAME).get(0);
                List<StatsAlert> producedAlerts = StatsChecker.produceAlertsFor(tuple);
                if (!producedAlerts.isEmpty()) {
                  producedAlerts.forEach(out::collect);
                }
              },
              TypeInformation.of(StatsAlert.class)
      );

      // Print alerts to stdout
      alerts.print();

      // Execute job
      env.execute(JOB_NAME);
    }
  }
}