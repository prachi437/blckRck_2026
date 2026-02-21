
package com.example.blackrock.service;

import org.springframework.stereotype.Service;

@Service
public class PerformanceService {
  public static class Perf {
    public String time;
    public String memory;
    public int threads;
    public Perf(String time, String memory, int threads) {
      this.time = time; this.memory = memory; this.threads = threads;
    }
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
