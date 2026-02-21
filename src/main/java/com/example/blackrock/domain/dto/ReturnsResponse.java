
package com.example.blackrock.domain.dto;

import java.util.List;

public class ReturnsResponse {

  public static class SavingByDates {
    private String start;
    private String end;
    private double amount;
    public SavingByDates() {}
    public SavingByDates(String start, String end, double amount) {
      this.start = start; this.end = end; this.amount = amount;
    }
    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }
    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
  }

  private double transactionsTotalAmount;
  private double transactionsTotalCeiling;
  private List<SavingByDates> savingsByDates;
  private double profits;     // gross profit (A - P)
  private double taxBenefit;  // 0 in case of index

  public ReturnsResponse() {}

  public ReturnsResponse(double tta, double ttc, List<SavingByDates> sbd, double profits, double taxBenefit) {
    this.transactionsTotalAmount = tta; this.transactionsTotalCeiling = ttc;
    this.savingsByDates = sbd; this.profits = profits; this.taxBenefit = taxBenefit;
  }

  public double getTransactionsTotalAmount() { return transactionsTotalAmount; }
  public void setTransactionsTotalAmount(double v) { this.transactionsTotalAmount = v; }
  public double getTransactionsTotalCeiling() { return transactionsTotalCeiling; }
  public void setTransactionsTotalCeiling(double v) { this.transactionsTotalCeiling = v; }
  public List<SavingByDates> getSavingsByDates() { return savingsByDates; }
  public void setSavingsByDates(List<SavingByDates> s) { this.savingsByDates = s; }
  public double getProfits() { return profits; }
  public void setProfits(double p) { this.profits = p; }
  public double getTaxBenefit() { return taxBenefit; }
  public void setTaxBenefit(double t) { this.taxBenefit = t; }
}
