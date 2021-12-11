package com.bol.mancala.web.controller;

import com.bol.mancala.service.GameService;
import com.bol.mancala.web.model.GameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test Game Controller")
@WebMvcTest(GameController.class)
class GameControllerTest {

  protected static final String ENDPOINT_CREATE = "/api/v1/game/create/";

  protected static final long FIRST_PLAYER_ID = 1L;
  protected static final long SECOND_PLAYER_ID = 2L;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private GameService gameService;

  @DisplayName("Create New Game")
  @Test
  void createNewGame() throws Exception {
    GameDto gameDto = GameDto.builder()
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .build();

    given(gameService.createNewGame(any())).willReturn(gameDto);

    String gameDtoJson = objectMapper.writeValueAsString(gameDto);
    mockMvc.perform(
        post(ENDPOINT_CREATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameDtoJson))
        .andExpect(status().isCreated());
  }

}
