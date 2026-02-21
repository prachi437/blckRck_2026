
package com.example.blackrock.domain;

import jakarta.validation.constraints.NotNull;

public class Expense {
  @NotNull
  private String timestamp;   // "YYYY-MM-DD HH:mm:ss"
  @NotNull
  private double amount;

  public Expense() {}

  public String getTimestamp() { return timestamp; }
  public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }
}
