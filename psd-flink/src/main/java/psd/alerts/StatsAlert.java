package psd.alerts;

import static psd.InvestmentWalletJob.IS_CSV_OUTPUT;

public abstract class StatsAlert {
  private final int windowId;
  private final int assetId;
  private final double percentage;
  private final double threshold;

  protected StatsAlert(int windowId, int assetId, double percentage, double threshold) {
    this.windowId = windowId;
    this.assetId = assetId;
    this.percentage = percentage;
    this.threshold = threshold;
  }

  public abstract double getValue();

  public int getWindowId() {
    return windowId;
  }

  public int getAssetId() {
    return assetId;
  }

  public double getPercentage() {
    return percentage;
  }

  public double getThreshold() {
    return threshold;
  }

  protected String getBasicInfo() {
    if (IS_CSV_OUTPUT) {
      return windowId + "," +
              assetId + "," +
              percentage + "," +
              threshold + ",";
    } else {
      return "window: " + windowId +
              ", asset: " + assetId +
              ", percentage: " + percentage +
              ", threshold: " + threshold +
              ", value: ";
    }
  }

  public static String getCsvInfo() {
    if (IS_CSV_OUTPUT) {
      return "CSV order: alertId, windowId, assetId, percentage, threshold, statValue";
    } else {
      return "CSV output mode is disabled...";
    }
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
