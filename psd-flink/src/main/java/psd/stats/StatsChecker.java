package psd.stats;

import org.apache.flink.api.java.tuple.Tuple2;
import psd.alerts.*;

import java.util.ArrayList;
import java.util.List;

public class StatsChecker {
  // TODO: replace with real values
  private static final double MEAN_THRESHOLD = -0.014043304766666662;
  private static final double MEDIAN_THRESHOLD = -0.010948185;
  private static final double QUANTILE_THRESHOLD = -0.08794572989999999;
  private static final double MEAN_FROM_MIN_RATES_THRESHOLD = -0.09732010833333334;
  private static final double SAFETY_RATE_AVG_DEV_THRESHOLD = 0.020040304575555556;
  private static final double SAFETY_RATE_GINI_THRESHOLD = 0.027843420183333332;

  private StatsChecker() {}

  // Produces alerts on events that values are 10% lower than constant threshold
  public static List<StatsAlert> produceAlertsFor(Tuple2<Integer, StatsAggregationResult> tuple) {
    final List<StatsAlert> alertsProduced = new ArrayList<>();
    final int assetId = tuple.f0;
    final StatsAggregationResult result = tuple.f1;
    final int windowId = result.getWindowId();

    if (result.getMean() < MEAN_THRESHOLD) {
      double value = result.getMean();
      if (StatsHelper.lowerThanThreshold(value, MEAN_THRESHOLD)) {
        alertsProduced.add(new MeanAlert(windowId, MEAN_THRESHOLD, value, assetId));
      }
    }

    if (result.getMedian() < MEDIAN_THRESHOLD) {
      double value = result.getMedian();
      if (StatsHelper.lowerThanThreshold(value, MEDIAN_THRESHOLD)) {
        alertsProduced.add(new MedianAlert(windowId, MEDIAN_THRESHOLD, value, assetId));
      }
    }

    if (result.getQuantile() < QUANTILE_THRESHOLD) {
      double value = result.getQuantile();
      if (StatsHelper.lowerThanThreshold(value, QUANTILE_THRESHOLD)) {
        alertsProduced.add(new QuanitleAlert(windowId, QUANTILE_THRESHOLD, value, assetId));
      }
    }

    if (result.getMeanFromMinRates() < MEAN_FROM_MIN_RATES_THRESHOLD) {
      double value = result.getMeanFromMinRates();
      if (StatsHelper.lowerThanThreshold(value, MEAN_FROM_MIN_RATES_THRESHOLD)) {
        alertsProduced.add(new MeanFromMinRates(windowId, MEAN_FROM_MIN_RATES_THRESHOLD, value, assetId));
      }
    }

    if (result.getSafetyRateAverageDeviation() < SAFETY_RATE_AVG_DEV_THRESHOLD) {
      double value = result.getSafetyRateAverageDeviation();
      if (StatsHelper.lowerThanThreshold(value, SAFETY_RATE_AVG_DEV_THRESHOLD)) {
        alertsProduced.add(new SafetyRateAlert(windowId, SAFETY_RATE_AVG_DEV_THRESHOLD, value, assetId));
      }
    }

    if (result.getSafetyRateGini() < SAFETY_RATE_GINI_THRESHOLD) {
      double value = result.getSafetyRateGini();
      if (StatsHelper.lowerThanThreshold(value, SAFETY_RATE_GINI_THRESHOLD)) {
        alertsProduced.add(new SafetyRateGiniAlert(windowId, SAFETY_RATE_GINI_THRESHOLD, value, assetId));
      }
    }

    return alertsProduced;
  }
}
