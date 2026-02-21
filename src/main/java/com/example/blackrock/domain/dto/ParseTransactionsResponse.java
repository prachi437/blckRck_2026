package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParseTransactionsResponse {
  private List<Transaction> transactions;
  private double transactionsTotalAmount;
  private double transactionsTotalCeiling;
}
