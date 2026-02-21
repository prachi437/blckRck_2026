
package com.example.blackrock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PeriodQ {
  private double fixed; // replacement
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime start;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime end;
  private int idx; // original order for tie-break on same start

  public PeriodQ() {}

  public PeriodQ(double fixed, LocalDateTime start, LocalDateTime end, int idx) {
    this.fixed = fixed; this.start = start; this.end = end; this.idx = idx;
  }
  public double getFixed() { return fixed; }
  public void setFixed(double fixed) { this.fixed = fixed; }
  public LocalDateTime getStart() { return start; }
  public void setStart(LocalDateTime start) { this.start = start; }
  public LocalDateTime getEnd() { return end; }
  public void setEnd(LocalDateTime end) { this.end = end; }
  public int getIdx() { return idx; }
  public void setIdx(int idx) { this.idx = idx; }
}
