package psd.stats;

import org.apache.flink.api.java.tuple.Tuple2;
import psd.alerts.*;

import java.util.ArrayList;
import java.util.List;

public class StatsChecker {

  /**
   * // For testing (first 100 samples):
   * private static final double[] MEAN_THRESHOLD = {-0.006894507, 0.004927153, 0.00134661, -0.001135192, 0.003586044, -0.01379235};
   * private static final double[] MEDIAN_THRESHOLD = {-0.002762632, 0.01151195, -0.002524657, -0.003271306, 0.006812286, -0.02584299};
   * private static final double[] QUANTILE_THRESHOLD = {-0.07934302, -0.07264282, -0.07145597, -0.07667505, -0.07903422, -0.08632244};
   * private static final double[] MEAN_FROM_MIN_RATES_THRESHOLD = {-0.08980066, -0.08159521, -0.086443, -0.09122514, -0.08923056, -0.09611306};
   * private static final double[] SAFETY_RATE_AVG_DEV_THRESHOLD = {0.02552551, 0.0251733, 0.02181392, 0.02429839, 0.02354965, 0.02586854};
   * private static final double[] SAFETY_RATE_GINI_THRESHOLD = {0.06781909, 0.06622779, 0.06090565, 0.06642242, 0.06432812, 0.06894819};
   **/

  private static final double[] MEAN_THRESHOLD = {2.741122e-05, 9.557385e-06, -7.064e-06, 8.467337e-05, 7.915884e-05, 3.187289e-05};
  private static final double[] MEDIAN_THRESHOLD = {8.58e-05, 1.625e-05, 4.005e-06, 0.000182586, 4.775e-05, 2.05e-05};
  private static final double[] QUANTILE_THRESHOLD = {-0.07981682, -0.08011171, -0.0797318, -0.08006085, -0.07988979, -0.07994182};
  private static final double[] MEAN_FROM_MIN_RATES_THRESHOLD = {-0.08989526, -0.09005075, -0.08987406, -0.09004942, -0.08990006, -0.08999679};
  private static final double[] SAFETY_RATE_AVG_DEV_THRESHOLD = {0.02492365, 0.02500448, 0.0249166, 0.02502468, 0.02497435, 0.02498649};
  private static final double[] SAFETY_RATE_GINI_THRESHOLD = {0.06651252, 0.06668754, 0.06647875, 0.06671993, 0.06659843, 0.06664281};

  private StatsChecker() {
  }

  // Produces alerts on events that values are 10% lower than constant threshold
  public static List<StatsAlert> produceAlertsFor(Tuple2<Integer, StatsAggregationResult> tuple) {
    final List<StatsAlert> alertsProduced = new ArrayList<>();
    final int assetId = tuple.f0;
    final int index = assetId - 1;
    final StatsAggregationResult result = tuple.f1;
    final int windowId = result.getWindowId();

    if (result.getMean() < MEAN_THRESHOLD[index]) {
      double value = result.getMean();
      double threshold = MEAN_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new MeanAlert(windowId, assetId, percentage, threshold, value));
      }
    }

    if (result.getMedian() < MEDIAN_THRESHOLD[index]) {
      double value = result.getMedian();
      double threshold = MEDIAN_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new MedianAlert(windowId, assetId, percentage, threshold, value));
      }
    }

    if (result.getQuantile() < QUANTILE_THRESHOLD[index]) {
      double value = result.getQuantile();
      double threshold = QUANTILE_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new QuanitleAlert(windowId, assetId, percentage, threshold, value));
      }
    }

    if (result.getMeanFromMinRates() < MEAN_FROM_MIN_RATES_THRESHOLD[index]) {
      double value = result.getMeanFromMinRates();
      double threshold = MEAN_FROM_MIN_RATES_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new MeanFromMinRates(windowId, assetId, percentage, threshold, value));
      }
    }

    if (result.getSafetyRateAverageDeviation() < SAFETY_RATE_AVG_DEV_THRESHOLD[index]) {
      double value = result.getSafetyRateAverageDeviation();
      double threshold = SAFETY_RATE_AVG_DEV_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new SafetyRateAlert(windowId, assetId, percentage, threshold, value));
      }
    }

    if (result.getSafetyRateGini() < SAFETY_RATE_GINI_THRESHOLD[index]) {
      double value = result.getSafetyRateGini();
      double threshold = SAFETY_RATE_GINI_THRESHOLD[index];
      double percentage = StatsHelper.calculatePercentage(value, threshold);
      if (StatsHelper.higherThanMaxTolerance(percentage)) {
        alertsProduced.add(new SafetyRateGiniAlert(windowId, assetId, percentage, threshold, value));
      }
    }

    return alertsProduced;
  }
}
