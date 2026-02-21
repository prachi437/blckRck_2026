
package com.example.blackrock.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
  private DateUtil() {}
  // Challenge format: "YYYY-MM-DD HH:mm:ss"
  public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static LocalDateTime parse(String s) { return LocalDateTime.parse(s, FMT); }
  public static String format(LocalDateTime dt) { return dt.format(FMT); }
  public static boolean inRange(LocalDateTime t, LocalDateTime s, LocalDateTime e) {
    return !t.isBefore(s) && !t.isAfter(e); // inclusive bounds
  }
}
