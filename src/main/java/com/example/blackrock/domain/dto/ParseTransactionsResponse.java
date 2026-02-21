
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import java.util.List;

public class ParseTransactionsResponse {
  private List<Transaction> transactions;
  private double transactionsTotalAmount;
  private double transactionsTotalCeiling;

  public ParseTransactionsResponse(List<Transaction> txs, double totalAmt, double totalCeil) {
    this.transactions = txs; this.transactionsTotalAmount = totalAmt; this.transactionsTotalCeiling = totalCeil;
  }
  public List<Transaction> getTransactions() { return transactions; }
  public double getTransactionsTotalAmount() { return transactionsTotalAmount; }
  public double getTransactionsTotalCeiling() { return transactionsTotalCeiling; }
}
