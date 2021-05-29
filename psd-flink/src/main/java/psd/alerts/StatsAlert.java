package psd.alerts;

public abstract class StatsAlert {
  private int assetId;

  protected StatsAlert(int assetId) {
    this.assetId = assetId;
  }

  public int getAssetId() {
    return assetId;
  }

  public void setAssetId(int assetId) {
    this.assetId = assetId;
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
