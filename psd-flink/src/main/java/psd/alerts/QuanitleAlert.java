package psd.alerts;

import static psd.InvestmentWalletJob.IS_CSV_OUTPUT;

public class QuanitleAlert extends StatsAlert {
  private final double quantile;

  public QuanitleAlert(int windowId, int assetId, double percentage, double threshold, double quantile) {
    super(windowId, assetId, percentage, threshold);
    this.quantile = quantile;
  }

  @Override
  public double getValue() {
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
    if (IS_CSV_OUTPUT) {
      return "4," + getBasicInfo() + getValue();
    } else {
      return "QuanitleAlert(" + getBasicInfo() + getValue() + ")";
    }
  }
}