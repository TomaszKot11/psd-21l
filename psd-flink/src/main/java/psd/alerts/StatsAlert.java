package psd.alerts;

public abstract class StatsAlert {
  private final int assetId;
  private final double threshold;

  protected StatsAlert(int assetId, double threshold) {
    this.assetId = assetId;
    this.threshold = threshold;
  }

  public int getAssetId() {
    return assetId;
  }

  public double getThreshold() {
    return threshold;
  }

  protected String getBasicInfo() {
    return assetId + ", threshold: " + threshold + ", ";
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof StatsAlert) {
      StatsAlert statsAlert = (StatsAlert) obj;
      return statsAlert.canEquals(this) && assetId == statsAlert.assetId;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return assetId;
  }

  public boolean canEquals(Object obj) {
    return obj instanceof StatsAlert;
  }
}
