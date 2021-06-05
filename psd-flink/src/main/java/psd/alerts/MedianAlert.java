package psd.alerts;

public class MedianAlert extends StatsAlert {
  private final double median;

  public MedianAlert(int windowId, double threshold, double median, int assetId) {
    super(windowId, assetId, threshold);
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
    return "MedianAlert(" + getBasicInfo() + median + ")";
  }
}
