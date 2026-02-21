
package com.example.blackrock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PeriodP {
  private double extra; // additive
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime start;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime end;

  public PeriodP() {}

  public PeriodP(double extra, LocalDateTime start, LocalDateTime end) {
    this.extra = extra; this.start = start; this.end = end;
  }
  public double getExtra() { return extra; }
  public void setExtra(double extra) { this.extra = extra; }
  public LocalDateTime getStart() { return start; }
  public void setStart(LocalDateTime start) { this.start = start; }
  public LocalDateTime getEnd() { return end; }
  public void setEnd(LocalDateTime end) { this.end = end; }
}
