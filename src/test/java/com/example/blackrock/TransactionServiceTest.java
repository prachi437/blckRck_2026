package com.example.blackrock;

import com.example.blackrock.domain.Expense;
import com.example.blackrock.domain.Transaction;
import com.example.blackrock.domain.dto.ParseTransactionsResponse;
import com.example.blackrock.domain.dto.ValidateResponse;
import com.example.blackrock.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

  private TransactionService service;

  @BeforeEach
  void setUp() {
    service = new TransactionService();
  }

  @Test
  void parse_ceil_to_next_hundred_and_sorts_by_date() {
    List<Expense> expenses = List.of(
        expense("2025-02-01 10:00:00", 250),
        expense("2025-01-01 10:00:00", 150)
    );
    ParseTransactionsResponse resp = service.parse(expenses);
    assertEquals(2, resp.getTransactions().size());
    assertEquals(400.0, resp.getTransactionsTotalAmount());
    assertEquals(500.0, resp.getTransactionsTotalCeiling());
    // Sorted by date: Jan first
    assertEquals(150.0, resp.getTransactions().get(0).getAmount());
    assertEquals(200.0, resp.getTransactions().get(0).getCeiling());
    assertEquals(50.0, resp.getTransactions().get(0).getRemanent());
    assertEquals(250.0, resp.getTransactions().get(1).getAmount());
    assertEquals(300.0, resp.getTransactions().get(1).getCeiling());
    assertEquals(50.0, resp.getTransactions().get(1).getRemanent());
  }

  @Test
  void parse_exact_hundred_remanent_zero() {
    ParseTransactionsResponse resp = service.parse(List.of(expense("2025-01-01 00:00:00", 100)));
    assertEquals(1, resp.getTransactions().size());
    assertEquals(100.0, resp.getTransactions().get(0).getCeiling());
    assertEquals(0.0, resp.getTransactions().get(0).getRemanent());
  }

  @Test
  void validate_accepts_valid_transaction() {
    List<Transaction> txs = List.of(
        new Transaction(null, 100, 100, 0)
    );
    txs.get(0).setDate(java.time.LocalDateTime.parse("2025-01-15 09:30:00", java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    ValidateResponse resp = service.validate(5000, txs);
    assertEquals(1, resp.getValid().size());
    assertTrue(resp.getInvalid().isEmpty());
    assertTrue(resp.getDuplicates().isEmpty());
  }

  @Test
  void validate_rejects_amount_ge_500000() {
    List<Transaction> txs = List.of(transaction("2025-01-01 00:00:00", 500000, 500000, 0));
    ValidateResponse resp = service.validate(5000, txs);
    assertEquals(0, resp.getValid().size());
    assertEquals(1, resp.getInvalid().size());
    assertTrue(resp.getInvalid().get(0).getMessage().contains("5e5"));
  }

  @Test
  void validate_rejects_negative_remanent() {
    List<Transaction> txs = List.of(transaction("2025-01-01 00:00:00", 100, 100, -1));
    ValidateResponse resp = service.validate(5000, txs);
    assertEquals(0, resp.getValid().size());
    assertEquals(1, resp.getInvalid().size());
    assertTrue(resp.getInvalid().get(0).getMessage().contains("remanent"));
  }

  @Test
  void validate_marks_duplicate_dates() {
    List<Transaction> txs = List.of(
        transaction("2025-01-15 09:30:00", 50, 100, 50),
        transaction("2025-01-15 09:30:00", 50, 100, 50)
    );
    ValidateResponse resp = service.validate(5000, txs);
    assertEquals(1, resp.getValid().size());
    assertEquals(1, resp.getDuplicates().size());
  }

  private static Expense expense(String timestamp, double amount) {
    Expense e = new Expense();
    e.setTimestamp(timestamp);
    e.setAmount(amount);
    return e;
  }

  private static Transaction transaction(String dateStr, double amount, double ceiling, double remanent) {
    var dt = java.time.LocalDateTime.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    return new Transaction(dt, amount, ceiling, remanent);
  }
}
