package com.jurengis.mancala.web.controller.exception;

import com.jurengis.mancala.service.GameService;
import com.jurengis.mancala.web.controller.GameController;
import com.jurengis.mancala.web.model.exception.ExceptionInfo;
import com.jurengis.mancala.web.model.exception.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test Controller Advice")
@WebMvcTest(GameController.class)
class ControllerExceptionHandlerTest {

  protected static final String ENDPOINT_PLAY = "/api/v1/game/play/";
  protected static final String PARAM_PIT = "pit";
  protected static final String PARAM_PIT_INVALID_MIN_VAL = "-1"; // any negative value can be accepted as invalid input

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private GameService gameService;

  @DisplayName("Show Custom Error Message")
  @Test
  void validationErrorHandler() throws Exception {
    MvcResult result = mockMvc.perform(
            get(ENDPOINT_PLAY + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .param(PARAM_PIT, PARAM_PIT_INVALID_MIN_VAL))
        .andExpect(status().isBadRequest())
        .andReturn();

    ExceptionResponse response = getResponse(result);
    verifyResponse(response);
  }

  private ExceptionResponse getResponse(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
    String response = result.getResponse().getContentAsString();
    return objectMapper.readValue(response, ExceptionResponse.class);
  }

  private void verifyResponse(ExceptionResponse response) {
    assertNotNull(response);
    List<ExceptionInfo> info = response.getInfo();
    assertEquals(1, info.size());
    assertEquals(HttpStatus.BAD_REQUEST.value(), info.get(0).getStatus());
  }

}
