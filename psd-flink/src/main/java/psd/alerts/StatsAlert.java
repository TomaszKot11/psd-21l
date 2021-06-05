package psd.alerts;

public abstract class StatsAlert {
  private final int windowId;
  private final int assetId;
  private final double threshold;

  protected StatsAlert(int windowId, int assetId, double threshold) {
    this.windowId = windowId;
    this.assetId = assetId;
    this.threshold = threshold;
  }

  public abstract double getValue();

  public int getWindowId() {
    return windowId;
  }

  public int getAssetId() {
    return assetId;
  }

  public double getThreshold() {
    return threshold;
  }

  protected String getBasicInfo() {
    return "window: " + windowId + ", asset: " + assetId + ", threshold: " + threshold + ", " + "value: ";
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
