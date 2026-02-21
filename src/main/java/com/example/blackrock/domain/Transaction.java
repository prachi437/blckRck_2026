
package com.example.blackrock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Transaction {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;
  private double amount;
  private double ceiling;
  private double remanent;

  public Transaction() {}

  public Transaction(LocalDateTime date, double amount, double ceiling, double remanent) {
    this.date = date; this.amount = amount; this.ceiling = ceiling; this.remanent = remanent;
  }
  public LocalDateTime getDate() { return date; }
  public void setDate(LocalDateTime date) { this.date = date; }
  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }
  public double getCeiling() { return ceiling; }
  public void setCeiling(double ceiling) { this.ceiling = ceiling; }
  public double getRemanent() { return remanent; }
  public void setRemanent(double r) { this.remanent = r; }
}
