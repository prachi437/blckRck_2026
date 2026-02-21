package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReturnsRequest {
  @NotNull
  private int age;
  @NotNull
  private double wage;       // monthly salary
  @NotNull
  private double inflation;  // annual (e.g., 0.055)
  @NotNull
  private List<PeriodQ> q;
  @NotNull
  private List<PeriodP> p;
  @NotNull
  private List<PeriodK> k;
  @NotNull
  private List<Transaction> transactions;
}
