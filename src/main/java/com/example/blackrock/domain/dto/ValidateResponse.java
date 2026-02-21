package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidateResponse {
  @Getter
  @Setter
  @NoArgsConstructor
  public static class InvalidTransaction extends Transaction {
    private String message;

    public InvalidTransaction(Transaction t, String msg) {
      super(t.getDate(), t.getAmount(), t.getCeiling(), t.getRemanent());
      this.message = msg;
    }
  }

  private List<Transaction> valid = new ArrayList<>();
  private List<InvalidTransaction> invalid = new ArrayList<>();
  private List<Transaction> duplicates = new ArrayList<>();
}
