
package com.example.blackrock.service;

import com.example.blackrock.domain.Expense;
import com.example.blackrock.domain.Transaction;
import com.example.blackrock.domain.dto.ParseTransactionsResponse;
import com.example.blackrock.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

  private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

  public ParseTransactionsResponse parse(java.util.List<Expense> expenses) {
    log.debug("parse: input count={}", expenses != null ? expenses.size() : 0);
    java.util.List<Transaction> txs = new java.util.ArrayList<>(expenses.size());
    double sumAmount = 0, sumCeil = 0;
    for (Expense e : expenses) {
      LocalDateTime dt = DateUtil.parse(e.getTimestamp());
      double amount = e.getAmount();
      double ceiling = Math.ceil(amount / 100.0) * 100.0; // next multiple of 100
      double rem = ceiling - amount;
      Transaction t = new Transaction(dt, amount, ceiling, rem);
      txs.add(t);
      sumAmount += amount; sumCeil += ceiling;
    }
    txs.sort(java.util.Comparator.comparing(Transaction::getDate));
    log.info("parse: completed, transactionsCount={}", txs.size());
    return new ParseTransactionsResponse(txs, sumAmount, sumCeil);
  }

  public com.example.blackrock.domain.dto.ValidateResponse validate(double wageMonthly, java.util.List<Transaction> txs) {
    log.debug("validate: input transactionsCount={}", txs != null ? txs.size() : 0);
    com.example.blackrock.domain.dto.ValidateResponse resp = new com.example.blackrock.domain.dto.ValidateResponse();
    java.util.Set<LocalDateTime> seen = new java.util.HashSet<>();
    for (Transaction t : txs) {
      boolean dup = !seen.add(t.getDate());
      if (dup) {
        resp.getDuplicates().add(t);
        continue;
      }
      if (t.getAmount() >= 500000) {
        resp.getInvalid().add(new com.example.blackrock.domain.dto.ValidateResponse.InvalidTransaction(t, "amount >= 5e5"));
      } else if (t.getRemanent() < 0) {
        resp.getInvalid().add(new com.example.blackrock.domain.dto.ValidateResponse.InvalidTransaction(t, "remanent < 0"));
      } else {
        resp.getValid().add(t);
      }
    }
    log.info("validate: completed, valid={}, invalid={}, duplicates={}",
        resp.getValid().size(), resp.getInvalid().size(), resp.getDuplicates().size());
    return resp;
  }
}
