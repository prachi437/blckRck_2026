package com.example.blackrock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PerformanceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void performance_returns_time_memory_threads() throws Exception {
    mockMvc.perform(get("/blackrock/challenge/v1/performance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.time").exists())
        .andExpect(jsonPath("$.memory").exists())
        .andExpect(jsonPath("$.threads").isNumber())
        .andExpect(jsonPath("$.time", containsString("ms")))
        .andExpect(jsonPath("$.memory", containsString("MB")));
  }
}
