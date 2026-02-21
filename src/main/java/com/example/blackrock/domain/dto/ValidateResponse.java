
package com.example.blackrock.domain.dto;

import com.example.blackrock.domain.Transaction;
import java.util.ArrayList;
import java.util.List;

public class ValidateResponse {
  public static class InvalidTransaction extends Transaction {
    private String message;
    public InvalidTransaction() {}
    public InvalidTransaction(Transaction t, String msg) {
      super(t.getDate(), t.getAmount(), t.getCeiling(), t.getRemanent());
      this.message = msg;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
  }
  private List<Transaction> valid = new ArrayList<>();
  private List<InvalidTransaction> invalid = new ArrayList<>();
  private List<Transaction> duplicates = new ArrayList<>();
  public List<Transaction> getValid() { return valid; }
  public void setValid(List<Transaction> v) { this.valid = v; }
  public List<InvalidTransaction> getInvalid() { return invalid; }
  public void setInvalid(List<InvalidTransaction> i) { this.invalid = i; }
  public List<Transaction> getDuplicates() { return duplicates; }
  public void setDuplicates(List<Transaction> d) { this.duplicates = d; }
}
