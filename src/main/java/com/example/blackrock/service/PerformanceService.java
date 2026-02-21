package com.example.blackrock.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class PerformanceService {
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Perf {
    private String time;
    private String memory;
    private int threads;
  }

  public Perf snapshot(long startNanos) {
    long nanos = System.nanoTime() - startNanos;
    double ms = nanos / 1_000_000.0;
    Runtime rt = Runtime.getRuntime();
    long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
    int threads = Thread.activeCount();
    return new Perf(String.format("%.3f ms", ms), String.format("%d MB", used), threads);
  }
}
