package psd.utils;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public final class Tokenizer implements FlatMapFunction<String, Tuple2<Integer, Double>> {
  private static final String DELIMITER = ",";

  @Override
  public void flatMap(String value, Collector<Tuple2<Integer, Double>> out) {
    String[] tokens = value.split(DELIMITER);
    for (int i = 1; i < 7; i++) {
      String token = tokens[i];
      if (token.length() > 0) {
        Double sample = Double.valueOf(token);

        // Index of column as a key and sample as value
        out.collect(new Tuple2<>(i, sample));
      }
    }
  }
}
