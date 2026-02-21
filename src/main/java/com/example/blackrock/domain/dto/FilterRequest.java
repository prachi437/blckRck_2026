
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class FilterRequest {
  @NotNull private List<PeriodQ> q;
  @NotNull private List<PeriodP> p;
  @NotNull private List<PeriodK> k;
  @NotNull private List<Transaction> transactions;

  public List<PeriodQ> getQ() { return q; }
  public void setQ(List<PeriodQ> q) { this.q = q; }
  public List<PeriodP> getP() { return p; }
  public void setP(List<PeriodP> p) { this.p = p; }
  public List<PeriodK> getK() { return k; }
  public void setK(List<PeriodK> k) { this.k = k; }
  public List<Transaction> getTransactions() { return transactions; }
  public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
