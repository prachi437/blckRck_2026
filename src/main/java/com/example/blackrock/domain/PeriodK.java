
package com.example.blackrock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class PeriodK {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime start;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime end;

  public PeriodK() {}

  public PeriodK(LocalDateTime start, LocalDateTime end) {
    this.start = start; this.end = end;
  }
  public LocalDateTime getStart() { return start; }
  public void setStart(LocalDateTime start) { this.start = start; }
  public LocalDateTime getEnd() { return end; }
  public void setEnd(LocalDateTime end) { this.end = end; }
}
