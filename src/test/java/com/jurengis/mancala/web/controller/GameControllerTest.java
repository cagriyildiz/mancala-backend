package com.jurengis.mancala.web.controller;

import com.jurengis.mancala.service.GameService;
import com.jurengis.mancala.web.model.GameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.jurengis.mancala.domain.Board.BOARD_SIZE_X;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test Game Controller")
@WebMvcTest(GameController.class)
class GameControllerTest {

  protected static final String ENDPOINT_CREATE = "/api/v1/game/create/";
  protected static final String ENDPOINT_PLAY = "/api/v1/game/play/";

  protected static final int PIT_INDEX_BIG = BOARD_SIZE_X - 1;
  protected static final int PIT_INDEX_VALID = ThreadLocalRandom.current().nextInt(0, PIT_INDEX_BIG);

  protected static final String PARAM_PIT = "pit";
  protected static final String PARAM_PIT_VAL = String.valueOf(PIT_INDEX_VALID);
  protected static final String PARAM_PIT_INVALID_MIN_VAL = "-1"; // any negative value can be accepted as invalid input
  protected static final String PARAM_PIT_INVALID_MAX_VAL = String.valueOf(PIT_INDEX_BIG);

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

  @DisplayName("Play Game")
  @Test
  void playGame() throws Exception {
    GameDto gameDto = GameDto.builder()
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .build();

    given(gameService.playGame(any(), anyInt())).willReturn(gameDto);

    mockMvc.perform(
            get(ENDPOINT_PLAY + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .param(PARAM_PIT, PARAM_PIT_VAL))
        .andExpect(status().isOk());
  }

  @DisplayName("Play Game Without Providing a Pit")
  @Test
  void playGameWithoutProvidingPit() throws Exception {
    mockMvc.perform(
            get(ENDPOINT_PLAY + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("Play Game With Invalid Min Pit")
  @Test
  void playGameWithInvalidMinPit() throws Exception {
    mockMvc.perform(
            get(ENDPOINT_PLAY + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .param(PARAM_PIT, PARAM_PIT_INVALID_MIN_VAL))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("Play Game With Invalid Max Pit")
  @Test
  void playGameWithInvalidMaxPit() throws Exception {
    mockMvc.perform(
            get(ENDPOINT_PLAY + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .param(PARAM_PIT, PARAM_PIT_INVALID_MAX_VAL))
        .andExpect(status().isBadRequest());
  }

}
