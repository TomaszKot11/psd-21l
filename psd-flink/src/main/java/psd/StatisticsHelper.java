package psd;

import org.apache.flink.shaded.curator4.com.google.common.math.Stats;
import org.apache.flink.shaded.curator4.com.google.common.math.Quantiles;

public class StatisticsHelper {

  public static double mean(double[] samples) {
    return Stats.meanOf(samples);
  }

  public static double median(double[] samples) {
    return Quantiles.median().compute(samples);
  }

  // kwantyl rzędu 0,1,
  public static double quantile(double[] samples) {
    return 0;
    //return Quantiles.scale(10).indexes(1).compute(samples);
  }

  // średnia z 10% najmniejszych stóp zwrotu,
  public static double meanFromMinRates(double[] samples) {
    return 0;
  }

  // miara bezpieczeństwa oparta na odchyleniu przeciętnym
  public static double safeRateAverageDeviation(double[] samples) {
    return 0;
  }

  // miara bezpieczeństwa oparta na średniej różnicy Giniego
  public static double safeRateGini(double[] samples) {
    return 0;
  }
}
