
package com.example.blackrock.api;

import com.example.blackrock.domain.dto.ReturnsRequest;
import com.example.blackrock.domain.dto.ReturnsResponse;
import com.example.blackrock.service.ReturnsService;
import com.example.blackrock.service.TemporalService;
import com.example.blackrock.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class ReturnsController {

  private final ReturnsService returnsService;
  private final TemporalService temporalService;
  private final TransactionService txService;

  public ReturnsController(ReturnsService returnsService, TemporalService temporalService, TransactionService txService) {
    this.returnsService = returnsService;
    this.temporalService = temporalService;
    this.txService = txService;
  }

  @PostMapping(value = "/returns:nps", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ReturnsResponse nps(@RequestBody @Valid ReturnsRequest req) {
    return returnsService.computeNps(req, temporalService, txService);
  }

  @PostMapping(value = "/returns:index", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ReturnsResponse index(@RequestBody @Valid ReturnsRequest req) {
    return returnsService.computeIndex(req, temporalService, txService);
  }
}
