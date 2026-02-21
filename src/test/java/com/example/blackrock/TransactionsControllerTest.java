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
class TransactionsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String BASE = "/blackrock/challenge/v1";

  @Test
  void parse_transactions_returns_ceiling_and_remanent() throws Exception {
    String body = """
        {"expenses":[
          {"timestamp":"2023-10-12 20:15:00","amount":250},
          {"timestamp":"2023-10-11 09:00:00","amount":150.50}
        ]}
        """;
    mockMvc.perform(post(BASE + "/transactions:parse")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactions", hasSize(2)))
        .andExpect(jsonPath("$.transactionsTotalAmount").value(400.5))
        .andExpect(jsonPath("$.transactionsTotalCeiling").value(500.0))
        .andExpect(jsonPath("$.transactions[0].amount").value(150.5))
        .andExpect(jsonPath("$.transactions[0].ceiling").value(200.0))
        .andExpect(jsonPath("$.transactions[0].remanent").value(49.5))
        .andExpect(jsonPath("$.transactions[1].amount").value(250.0))
        .andExpect(jsonPath("$.transactions[1].ceiling").value(300.0))
        .andExpect(jsonPath("$.transactions[1].remanent").value(50.0));
  }

  @Test
  void parse_single_expense() throws Exception {
    String body = "{\"expenses\":[{\"timestamp\":\"2025-01-01 00:00:00\",\"amount\":100}]}";
    mockMvc.perform(post(BASE + "/transactions:parse")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactions", hasSize(1)))
        .andExpect(jsonPath("$.transactionsTotalAmount").value(100.0))
        .andExpect(jsonPath("$.transactionsTotalCeiling").value(100.0))
        .andExpect(jsonPath("$.transactions[0].remanent").value(0.0));
  }

  @Test
  void parse_empty_expenses() throws Exception {
    String body = "{\"expenses\":[]}";
    mockMvc.perform(post(BASE + "/transactions:parse")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactions", hasSize(0)))
        .andExpect(jsonPath("$.transactionsTotalAmount").value(0.0))
        .andExpect(jsonPath("$.transactionsTotalCeiling").value(0.0));
  }

  @Test
  void validator_accepts_valid_transactions() throws Exception {
    String body = """
        {"wage":5000,"transactions":[
          {"date":"2025-01-15 09:30:00","amount":150.5,"ceiling":200,"remanent":49.5}
        ]}
        """;
    mockMvc.perform(post(BASE + "/transactions:validator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(1)))
        .andExpect(jsonPath("$.invalid", hasSize(0)))
        .andExpect(jsonPath("$.duplicates", hasSize(0)));
  }

  @Test
  void validator_rejects_amount_above_500000() throws Exception {
    String body = """
        {"wage":5000,"transactions":[
          {"date":"2025-01-15 09:30:00","amount":500000,"ceiling":500000,"remanent":0}
        ]}
        """;
    mockMvc.perform(post(BASE + "/transactions:validator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(0)))
        .andExpect(jsonPath("$.invalid", hasSize(1)))
        .andExpect(jsonPath("$.invalid[0].message", containsString("5e5")));
  }

  @Test
  void validator_rejects_negative_remanent() throws Exception {
    String body = """
        {"wage":5000,"transactions":[
          {"date":"2025-01-15 09:30:00","amount":100,"ceiling":100,"remanent":-1}
        ]}
        """;
    mockMvc.perform(post(BASE + "/transactions:validator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(0)))
        .andExpect(jsonPath("$.invalid", hasSize(1)))
        .andExpect(jsonPath("$.invalid[0].message", containsString("remanent")));
  }

  @Test
  void validator_marks_duplicates() throws Exception {
    String body = """
        {"wage":5000,"transactions":[
          {"date":"2025-01-15 09:30:00","amount":50,"ceiling":100,"remanent":50},
          {"date":"2025-01-15 09:30:00","amount":50,"ceiling":100,"remanent":50}
        ]}
        """;
    mockMvc.perform(post(BASE + "/transactions:validator")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(1)))
        .andExpect(jsonPath("$.duplicates", hasSize(1)));
  }

  @Test
  void filter_applies_periods_and_returns_valid() throws Exception {
    String body = """
        {
          "q":[],
          "p":[{"extra":10,"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59"}],
          "k":[{"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59"}],
          "transactions":[
            {"date":"2025-01-15 12:00:00","amount":100,"ceiling":100,"remanent":0}
          ]
        }
        """;
    mockMvc.perform(post(BASE + "/transactions:filter")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(1)))
        .andExpect(jsonPath("$.valid[0].remanent").value(10.0))
        .andExpect(jsonPath("$.invalid", hasSize(0)));
  }

  @Test
  void filter_with_q_replacement() throws Exception {
    String body = """
        {
          "q":[{"fixed":500,"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59","idx":0}],
          "p":[],
          "k":[{"start":"2025-01-01 00:00:00","end":"2025-01-31 23:59:59"}],
          "transactions":[
            {"date":"2025-01-15 12:00:00","amount":100,"ceiling":100,"remanent":0}
          ]
        }
        """;
    mockMvc.perform(post(BASE + "/transactions:filter")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid", hasSize(1)))
        .andExpect(jsonPath("$.valid[0].remanent").value(500.0));
  }

  @Test
  void parse_rejects_missing_expenses_with_400() throws Exception {
    mockMvc.perform(post(BASE + "/transactions:parse")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
  }
}
