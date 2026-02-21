package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Expense;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParseTransactionsRequest {
  @NotNull
  private List<Expense> expenses;
}
