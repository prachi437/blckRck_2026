package com.example.blackrock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodQ {
  private double fixed; // replacement
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime start;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime end;
  private int idx; // original order for tie-break on same start
}
