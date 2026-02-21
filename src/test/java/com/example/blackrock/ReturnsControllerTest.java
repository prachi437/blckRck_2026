package com.example.blackrock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReturnsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String BASE = "/blackrock/challenge/v1";

  private static final String MINIMAL_RETURNS_BODY = """
      {
        "age": 35,
        "wage": 5000,
        "inflation": 0.055,
        "q": [],
        "p": [],
        "k": [{"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59"}],
        "transactions": [
          {"date":"2025-01-15 12:00:00","amount":100,"ceiling":100,"remanent":100}
        ]
      }
      """;

  @Test
  void nps_returns_structured_response() throws Exception {
    mockMvc.perform(post(BASE + "/returns:nps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(MINIMAL_RETURNS_BODY))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactionsTotalAmount").exists())
        .andExpect(jsonPath("$.transactionsTotalCeiling").exists())
        .andExpect(jsonPath("$.savingsByDates", hasSize(1)))
        .andExpect(jsonPath("$.savingsByDates[0].amount").exists())
        .andExpect(jsonPath("$.profits").exists())
        .andExpect(jsonPath("$.taxBenefit").exists());
  }

  @Test
  void index_returns_structured_response() throws Exception {
    mockMvc.perform(post(BASE + "/returns:index")
            .contentType(MediaType.APPLICATION_JSON)
            .content(MINIMAL_RETURNS_BODY))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactionsTotalAmount").exists())
        .andExpect(jsonPath("$.transactionsTotalCeiling").exists())
        .andExpect(jsonPath("$.savingsByDates", hasSize(1)))
        .andExpect(jsonPath("$.profits").exists())
        .andExpect(jsonPath("$.taxBenefit").value(0.0));
  }

  @Test
  void nps_with_multiple_k_periods() throws Exception {
    String body = """
        {
          "age": 40,
          "wage": 10000,
          "inflation": 0.05,
          "q": [],
          "p": [],
          "k": [
            {"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59"},
            {"start":"2025-02-01 00:00:00","end":"2025-02-28 23:59:59"}
          ],
          "transactions": [
            {"date":"2025-01-15 12:00:00","amount":200,"ceiling":200,"remanent":200},
            {"date":"2025-02-10 12:00:00","amount":300,"ceiling":300,"remanent":300}
          ]
        }
        """;
    mockMvc.perform(post(BASE + "/returns:nps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.savingsByDates", hasSize(2)))
        .andExpect(jsonPath("$.transactionsTotalAmount").value(500.0));
  }

  @Test
  void returns_rejects_missing_required_fields_with_400() throws Exception {
    mockMvc.perform(post(BASE + "/returns:nps")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
  }
}
