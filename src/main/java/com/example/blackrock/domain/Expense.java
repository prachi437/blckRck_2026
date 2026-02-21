package com.example.blackrock.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Expense {
  @NotNull
  private String timestamp;   // "YYYY-MM-DD HH:mm:ss"
  @NotNull
  private double amount;
}
