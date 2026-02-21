package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ValidateRequest {
  @NotNull
  private double wage;
  @NotNull
  private List<Transaction> transactions;
}
