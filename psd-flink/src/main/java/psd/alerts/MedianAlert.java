package psd.alerts;

import static psd.InvestmentWalletJob.IS_CSV_OUTPUT;

public class MedianAlert extends StatsAlert {
  private final double median;

  public MedianAlert(int windowId, int assetId, double percentage, double threshold, double median) {
    super(windowId, assetId, percentage, threshold);
    this.median = median;
  }

  @Override
  public double getValue() {
    return median;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MedianAlert) {
      MedianAlert other = (MedianAlert) obj;
      return other.canEquals(this) && super.equals(other) && median == other.median;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 41 * super.hashCode() + Double.hashCode(median);
  }

  @Override
  public boolean canEquals(Object obj){
    return obj instanceof MedianAlert;
  }

  @Override
  public String toString() {
    if (IS_CSV_OUTPUT) {
      return "3," + getBasicInfo() + getValue();
    } else {
      return "MedianAlert(" + getBasicInfo() + getValue() + ")";
    }
  }
}
