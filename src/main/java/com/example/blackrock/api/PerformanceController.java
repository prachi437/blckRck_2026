
package com.example.blackrock.api;

import com.example.blackrock.service.PerformanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PerformanceController {
  private final PerformanceService perf;

  public PerformanceController(PerformanceService perf) { this.perf = perf; }

  @GetMapping("/blackrock/challenge/v1/performance")
  public PerformanceService.Perf performance() {
    long start = System.nanoTime();
    return perf.snapshot(start);
  }
}
