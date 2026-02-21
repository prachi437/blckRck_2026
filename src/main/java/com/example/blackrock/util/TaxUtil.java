
package com.example.blackrock.util;

public final class TaxUtil {
  private TaxUtil() {}

  // simplified slabs; income is annual pre-tax
  public static double tax(double income) {
    double tax = 0.0;
    if (income <= 700000) return 0.0;
    if (income <= 1000000) return 0.10 * (income - 700000);
    tax = 0.10 * 300000; // 7L-10L
    if (income <= 1200000) return tax + 0.15 * (income - 1000000);
    tax += 0.15 * 200000; // 10L-12L
    if (income <= 1500000) return tax + 0.20 * (income - 1200000);
    tax += 0.20 * 300000; // 12L-15L
    return tax + 0.30 * (income - 1500000);
  }
}
