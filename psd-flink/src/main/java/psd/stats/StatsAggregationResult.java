package psd.stats;

public class StatsAggregationResult {
  private final double mean;
  private final double median;
  private final double quantile;
  private final double meanFromMinRates;
  private final double safetyRateAverageDeviation;
  private final double safetyRateGini;

  public StatsAggregationResult(double mean, double median, double quantile,
                                double meanFromMinRates, double safetyRateAverageDeviation,
                                double safetyRateGini) {
    this.mean = mean;
    this.median = median;
    this.quantile = quantile;
    this.meanFromMinRates = meanFromMinRates;
    this.safetyRateAverageDeviation = safetyRateAverageDeviation;
    this.safetyRateGini = safetyRateGini;
  }

  public double getMean() {
    return mean;
  }

  public double getMedian() {
    return median;
  }

  public double getQuantile() {
    return quantile;
  }

  public double getMeanFromMinRates() {
    return meanFromMinRates;
  }

  public double getSafetyRateAverageDeviation() {
    return safetyRateAverageDeviation;
  }

  public double getSafetyRateGini() {
    return safetyRateGini;
  }

  @Override
  public String toString() {
    return "StatsAggregationResult{" +
            "m=" + mean +
            ", md=" + median +
            ", q=" + quantile +
            ", mmr=" + meanFromMinRates +
            ", sr=" + safetyRateAverageDeviation +
            ", gini=" + safetyRateGini +
            '}';
  }
}
