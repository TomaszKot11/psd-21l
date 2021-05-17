package psd;

import org.apache.flink.shaded.curator4.com.google.common.math.Stats;
import org.apache.flink.shaded.curator4.com.google.common.math.Quantiles;

import java.util.Arrays;

public class StatsHelper {

  public static double mean(double[] samples) {
    checkArray(samples);
    return Stats.meanOf(samples);
  }

  public static double median(double[] samples) {
    checkArray(samples);
    return Quantiles.median().compute(samples);
  }

  // kwantyl rzędu 0,1,
  public static double quantile(double[] samples) {
    checkArray(samples);
    return Quantiles.scale(10).index(1).compute(samples);
  }

  // średnia z 10% najmniejszych stóp zwrotu,
  public static double meanFromMinRates(double[] samples) {
    checkArray(samples);
    Arrays.sort(samples);
    int length = samples.length;
    int tenPercentIndex = (int) Math.floor(length * 0.1);
    double[] dividedSamples = Arrays.copyOfRange(samples, 1, tenPercentIndex);
    return Stats.meanOf(dividedSamples);
  }

  // miara bezpieczeństwa oparta na odchyleniu przeciętnym
  public static double safeRateAverageDeviation(double[] samples) {
    checkArray(samples);

    double mean = Stats.meanOf(samples);
    int t = samples.length;
    double sum = 0;
    for (double sample : samples) {
      sum += Math.abs(mean - sample);
    }
    return 1 / (2D*t) * sum;
  }

  // miara bezpieczeństwa oparta na średniej różnicy Giniego
  public static double safeRateGini(double[] samples) {
    checkArray(samples);

    int t = samples.length;
    double sumOfSums = 0;
    for (int i = 0; i < t; i++) {
      double sum = 0;
      for (int j = 0; j < t; j++) {
        sum += Math.abs(samples[i] - samples[j]);
      }
      sumOfSums += sum;
    }
    return 1 / (2D * t * t) * sumOfSums;
  }

  private static void checkArray(double[] samples) {
    if (samples.length == 0) {
      throw new IllegalArgumentException("Samples array can't be empty!");
    }
  }
}
