package psd.alerts;

public class MedianAlert extends StatsAlert {
  private double median;

  public MedianAlert(int assetId, double median) {
    super(assetId);
    this.median = median;
  }

  public double getMedian() {
    return median;
  }

  public void setMedian(double median) {
    this.median = median;
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
    return "MedianAlert(" + getAssetId() + ", " + median + ")";
  }
}
