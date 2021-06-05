package psd.utils;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import static psd.InvestmentWalletJob.IS_DEBUG;

public final class Tokenizer implements FlatMapFunction<String, Tuple3<Integer, Integer, Double> > {
  private static final String DELIMITER = ",";

  @Override
  public void flatMap(String value, Collector<Tuple3<Integer, Integer, Double> > out) {
    String[] tokens = value.split(DELIMITER);

    // Read sample id
    Integer sampleId = -1;
    String sampleIdToken = tokens[0];
    if (sampleIdToken.length() > 0) {
      sampleId = Integer.valueOf(sampleIdToken);
    }

    for (int i = 1; i < 7; i++) {
      String token = tokens[i];
      if (token.length() > 0) {
        Double sample = Double.valueOf(token);

        // Index of column as a key and sample as value
        final Tuple3<Integer, Integer, Double> tuple = new Tuple3<>(sampleId, i, sample);

        if (IS_DEBUG) {
          System.out.println(tuple.toString());
        }

        out.collect(tuple);
      }
    }
  }
}
