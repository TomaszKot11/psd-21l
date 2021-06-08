package psd.stats;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.ArrayList;
import java.util.List;

import static psd.InvestmentWalletJob.IS_DEBUG;

/**
* IN (sampleId, assetId, value)
* ACC (assetId, samples)
* OUT (assetId, aggregation result)
**/
public class StatsAggregate implements AggregateFunction<
        Tuple3<Integer, Integer, Double>,
        Tuple2<Integer, List<Double>>,
        Tuple2<Integer, StatsAggregationResult>> {
  private static final int DEFAULT_WINDOW_ID = -1;
  private int currentWindowId;
  private int amountOfSamples;

  @Override
  public Tuple2<Integer, List<Double>> createAccumulator() {
    amountOfSamples = 0;
    currentWindowId = DEFAULT_WINDOW_ID;
    return new Tuple2<>(1, new ArrayList<>());
  }

  @Override
  public Tuple2<Integer, List<Double>> add(Tuple3<Integer, Integer, Double> value,
                                           Tuple2<Integer, List<Double>> accumulator) {
    currentWindowId = value.f0;
    amountOfSamples++;
    accumulator.f1.add(value.f2);
    return new Tuple2<>(value.f1, accumulator.f1);
  }

  @Override
  public Tuple2<Integer, StatsAggregationResult> getResult(Tuple2<Integer, List<Double>> accumulator) {
    Double[] d = accumulator.f1.toArray(new Double[0]);
    double[] samples = ArrayUtils.toPrimitive(d);
    int assetId = accumulator.f0;

    // Calculate results
    StatsAggregationResult result = StatsHelper.calculateStats(currentWindowId, samples);

    if (IS_DEBUG) {
      // Print result for current window
      System.out.println("StatsAggregate: samples: " + amountOfSamples
              + ", asset: " + assetId
              + ", " + result.toString());
    }

    // Reset window id after processing the result
    currentWindowId = DEFAULT_WINDOW_ID;

    return new Tuple2<>(accumulator.f0, result);
  }

  @Override
  public Tuple2<Integer, List<Double>> merge(Tuple2<Integer, List<Double>> a, Tuple2<Integer, List<Double>> b) {
    a.f1.addAll(b.f1);
    return new Tuple2<>(a.f0, a.f1);
  }
}
