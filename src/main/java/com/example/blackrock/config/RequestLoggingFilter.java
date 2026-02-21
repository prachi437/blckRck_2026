package com.example.blackrock.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Logs HTTP method, path, response status and duration. Does not log request/response
 * bodies, headers or query params to avoid exposing sensitive data (wages, amounts, etc.).
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestLoggingFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    long startNanos = System.nanoTime();
    String method = request.getMethod();
    String path = request.getRequestURI();

    try {
      filterChain.doFilter(request, response);
    } finally {
      int status = response.getStatus();
      long durationMs = (System.nanoTime() - startNanos) / 1_000_000;
      log.info("{} {} -> {} ({} ms)", method, path, status, durationMs);
    }
  }
}
