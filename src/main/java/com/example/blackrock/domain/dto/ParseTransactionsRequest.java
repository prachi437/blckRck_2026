
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Expense;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ParseTransactionsRequest {
  @NotNull
  private List<Expense> expenses;
  public List<Expense> getExpenses() { return expenses; }
  public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }
}
