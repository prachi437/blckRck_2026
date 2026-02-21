
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ReturnsRequest {
  @NotNull private int age;
  @NotNull private double wage;       // monthly salary
  @NotNull private double inflation;  // annual (e.g., 0.055)
  @NotNull private List<PeriodQ> q;
  @NotNull private List<PeriodP> p;
  @NotNull private List<PeriodK> k;
  @NotNull private List<Transaction> transactions;

  public int getAge() { return age; }
  public void setAge(int age) { this.age = age; }
  public double getWage() { return wage; }
  public void setWage(double wage) { this.wage = wage; }
  public double getInflation() { return inflation; }
  public void setInflation(double inflation) { this.inflation = inflation; }
  public List<PeriodQ> getQ() { return q; }
  public void setQ(List<PeriodQ> q) { this.q = q; }
  public List<PeriodP> getP() { return p; }
  public void setP(List<PeriodP> p) { this.p = p; }
  public List<PeriodK> getK() { return k; }
  public void setK(List<PeriodK> k) { this.k = k; }
  public List<Transaction> getTransactions() { return transactions; }
  public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
