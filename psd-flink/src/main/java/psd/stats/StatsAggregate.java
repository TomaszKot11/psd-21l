package psd.stats;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class StatsAggregate implements AggregateFunction<Tuple2<Integer, Double>, Tuple2<Integer, List<Double>>, Tuple2<Integer, StatsAggregationResult>> {
  @Override
  public Tuple2<Integer, List<Double>> createAccumulator() {
    return new Tuple2<>(1, new ArrayList<>());
  }

  @Override
  public Tuple2<Integer, List<Double>> add(Tuple2<Integer, Double> value, Tuple2<Integer, List<Double>> accumulator) {
    accumulator.f1.add(value.f1);
    return new Tuple2<>(value.f0, accumulator.f1);
  }

  @Override
  public Tuple2<Integer, StatsAggregationResult> getResult(Tuple2<Integer, List<Double>> accumulator) {
    Double[] d = accumulator.f1.toArray(new Double[0]);
    double[] samples = ArrayUtils.toPrimitive(d);
    StatsAggregationResult result = StatsHelper.calculateStats(samples);
    return new Tuple2<>(accumulator.f0, result);
  }

  @Override
  public Tuple2<Integer, List<Double>> merge(Tuple2<Integer, List<Double>> a, Tuple2<Integer, List<Double>> b) {
    a.f1.addAll(b.f1);
    return new Tuple2<>(a.f0, a.f1);
  }
}
