
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ValidateRequest {
  @NotNull private double wage;
  @NotNull private List<Transaction> transactions;
  public double getWage() { return wage; }
  public void setWage(double wage) { this.wage = wage; }
  public List<Transaction> getTransactions() { return transactions; }
  public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
