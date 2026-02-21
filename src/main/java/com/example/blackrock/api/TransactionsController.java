
package com.example.blackrock.api;

import com.example.blackrock.domain.dto.*;
import com.example.blackrock.service.TemporalService;
import com.example.blackrock.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class TransactionsController {

  private final TransactionService txService;
  private final TemporalService temporal;

  public TransactionsController(TransactionService txService, TemporalService temporal) {
    this.txService = txService; this.temporal = temporal;
  }

  @PostMapping(value = "/transactions:parse", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ParseTransactionsResponse parse(@RequestBody @Valid ParseTransactionsRequest req) {
    return txService.parse(req.getExpenses());
  }

  @PostMapping(value = "/transactions:validator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ValidateResponse validate(@RequestBody @Valid ValidateRequest req) {
    return txService.validate(req.getWage(), req.getTransactions());
  }

  @PostMapping(value = "/transactions:filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public FilterResponse filter(@RequestBody @Valid FilterRequest req) {
    return temporal.applyPeriods(req.getQ(), req.getP(), req.getK(), req.getTransactions());
  }
}
