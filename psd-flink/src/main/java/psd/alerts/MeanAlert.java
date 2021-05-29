package psd.alerts;

public class MeanAlert extends StatsAlert {
  private double mean;

  public MeanAlert(int assetId, double mean) {
    super(assetId);
    this.mean = mean;
  }

  public void setMean(double mean) {
    this.mean = mean;
  }

  public double getMean() {
    return mean;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MeanAlert) {
      MeanAlert event = (MeanAlert) obj;
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
    return obj instanceof MeanAlert;
  }

  @Override
  public String toString() {
    return "MeanAlert(" + getAssetId() + ", " + mean + ")";
  }
}