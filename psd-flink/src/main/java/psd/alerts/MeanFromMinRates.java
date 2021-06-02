package psd.alerts;

public class MeanFromMinRates extends StatsAlert {
  private final double mean;

  public MeanFromMinRates(int assetId, double threshold, double mean) {
    super(assetId, threshold);
    this.mean = mean;
  }

  public double getMean() {
    return mean;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MeanFromMinRates) {
      MeanFromMinRates event = (MeanFromMinRates) obj;
      return event.canEquals(this) && super.equals(event) && mean == event.mean;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 41 * super.hashCode() + Double.hashCode(mean);
  }

  @Override
  public boolean canEquals(Object obj) {
    return obj instanceof MeanFromMinRates;
  }

  @Override
  public String toString() {
    return "MeanFromMinRates(" + getBasicInfo() + mean + ")";
  }
}
