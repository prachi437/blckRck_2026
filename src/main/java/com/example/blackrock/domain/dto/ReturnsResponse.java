package com.example.blackrock.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnsResponse {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SavingByDates {
    private String start;
    private String end;
    private double amount;
  }

  private double transactionsTotalAmount;
  private double transactionsTotalCeiling;
  private List<SavingByDates> savingsByDates;
  private double profits;     // gross profit (A - P)
  private double taxBenefit;  // 0 in case of index
}
