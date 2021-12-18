package com.bol.mancala.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test CORS Configuration")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CorsConfigurationTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    DefaultMockMvcBuilder builder = MockMvcBuilders
        .webAppContextSetup(this.context)
        .dispatchOptions(true);
    this.mockMvc = builder.build();
  }

  @DisplayName("Request From Allowed Origin")
  @Test
  void testCorsWithAllowedOrigin() throws Exception {
    this.mockMvc
        .perform(options("/api/v1/game/create")
        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.POST)
        .header(HttpHeaders.ORIGIN, "http://localhost:3000"))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST"));
  }

  @DisplayName("Request From Not Allowed Origin")
  @Test
  void testCorsWithNotAllowedOrigin() throws Exception {
    this.mockMvc
        .perform(options("/api/v1/game/play")
            .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET)
            .header(HttpHeaders.ORIGIN, "http://localhost:3001"))
        .andExpect(status().isForbidden());
  }

}
