package psd.stats;

import org.apache.flink.api.java.tuple.Tuple2;
import psd.alerts.*;

import java.util.ArrayList;
import java.util.List;

public class StatsChecker {
  private static final double MEAN_THRESHOLD = 1;
  private static final double MEDIAN_THRESHOLD = 1;
  private static final double QUANTILE_THRESHOLD = 1;
  private static final double MEAN_FROM_MIN_RATES_THRESHOLD = 1;
  private static final double SAFETY_RATE_AVG_DEV_THRESHOLD = 1;
  private static final double SAFETY_RATE_GINI_THRESHOLD = 1;

  private StatsChecker() {
  }

  // Produces alerts on events that values are 10% lower than constant threshold
  public static List<StatsAlert> produceAlertsFor(Tuple2<Integer, StatsAggregationResult> tuple) {
    final List<StatsAlert> alertsProduced = new ArrayList<>();
    final int assetId = tuple.f0;
    final StatsAggregationResult result = tuple.f1;

    if (result.getMean() < MEAN_THRESHOLD) {
      double value = result.getMean();
      if (StatsHelper.lowerThanThreshold(value, MEAN_THRESHOLD)) {
        alertsProduced.add(new MeanAlert(assetId, value));
      }
    }

    if (result.getMedian() < MEDIAN_THRESHOLD) {
      double value = result.getMedian();
      if (StatsHelper.lowerThanThreshold(value, MEDIAN_THRESHOLD)) {
        alertsProduced.add(new MedianAlert(assetId, value));
      }
    }

    if (result.getQuantile() < QUANTILE_THRESHOLD) {
      double value = result.getQuantile();
      if (StatsHelper.lowerThanThreshold(value, QUANTILE_THRESHOLD)) {
        alertsProduced.add(new QuanitleAlert(assetId, value));
      }
    }

    if (result.getMeanFromMinRates() < MEAN_FROM_MIN_RATES_THRESHOLD) {
      double value = result.getMeanFromMinRates();
      if (StatsHelper.lowerThanThreshold(value, MEAN_FROM_MIN_RATES_THRESHOLD)) {
        alertsProduced.add(new MeanFromMinRates(assetId, value));
      }
    }

    if (result.getSafetyRateAverageDeviation() < SAFETY_RATE_AVG_DEV_THRESHOLD) {
      double value = result.getSafetyRateAverageDeviation();
      if (StatsHelper.lowerThanThreshold(value, SAFETY_RATE_AVG_DEV_THRESHOLD)) {
        alertsProduced.add(new SafetyRateAlert(assetId, value));
      }
    }

    if (result.getSafetyRateGini() < SAFETY_RATE_GINI_THRESHOLD) {
      double value = result.getSafetyRateGini();
      if (StatsHelper.lowerThanThreshold(value, SAFETY_RATE_GINI_THRESHOLD)) {
        alertsProduced.add(new SafetyRateGiniAlert(assetId, value));
      }
    }

    return alertsProduced;
  }
}
