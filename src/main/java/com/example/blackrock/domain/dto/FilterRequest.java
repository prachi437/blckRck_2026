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
public class FilterRequest {
  @NotNull
  private List<PeriodQ> q;
  @NotNull
  private List<PeriodP> p;
  @NotNull
  private List<PeriodK> k;
  @NotNull
  private List<Transaction> transactions;
}
