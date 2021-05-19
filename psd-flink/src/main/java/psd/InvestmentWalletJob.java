package psd;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;


public class InvestmentWalletJob {

	public static void main(String[] args) throws Exception {
		final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().setGlobalJobParameters(params);

		DataStream<String> text = null;
		if (params.has("input")) {
			for (String input : params.getMultiParameterRequired("input")) {
				if (text == null) {
					text = env.readTextFile(input);
				} else {
					text = text.union(env.readTextFile(input));
				}
			}
			Preconditions.checkNotNull(text, "Input DataStream should not be null.");
		}

		if (text != null) {
			DataStream<Tuple2<Integer, Double>> stream =
							text.flatMap(new Tokenizer())
											.keyBy(value -> value.f0)
											.countWindow(30, 1)
											.sum(1);

			// TODO: apply statistics on it
			// TODO: apply alerts based on statistics (https://flink.apache.org/news/2020/01/15/demo-fraud-detection.html)

			if (params.has("output")) {
				stream.writeAsText(params.get("output"));
			}

			env.execute("Streaming InvestmentWalletJob");
		}
	}

	public static final class Tokenizer implements FlatMapFunction<String, Tuple2<Integer, Double>> {
		@Override
		public void flatMap(String value, Collector<Tuple2<Integer, Double>> out) {
			String[] tokens = value.split(",");
			for (int i = 1; i < 7; i++) {
				String token = tokens[i];
				if (token.length() > 0) {
					Double sample = Double.valueOf(token);
					out.collect(new Tuple2<>(i, sample));
				}
			}
		}
	}
}