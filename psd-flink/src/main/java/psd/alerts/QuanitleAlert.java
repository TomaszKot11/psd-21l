package psd.alerts;

public class QuanitleAlert extends StatsAlert {
  private final double quantile;

  public QuanitleAlert(int assetId, double threshold, double quantile) {
    super(assetId, threshold);
    this.quantile = quantile;
  }

  public double getQuantile() {
    return quantile;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof QuanitleAlert) {
      QuanitleAlert event = (QuanitleAlert) obj;
      return event.canEquals(this) && super.equals(event) && quantile == event.quantile;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 41 * super.hashCode() + Double.hashCode(quantile);
  }

  @Override
  public boolean canEquals(Object obj) {
    return obj instanceof MeanAlert;
  }

  @Override
  public String toString() {
    return "QuanitleAlert(" + getBasicInfo() + quantile + ")";
  }
}