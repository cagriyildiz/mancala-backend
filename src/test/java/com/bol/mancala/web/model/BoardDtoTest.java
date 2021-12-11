package com.bol.mancala.web.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bol.mancala.domain.Board.BOARD_SIZE_X;
import static com.bol.mancala.domain.Board.BOARD_SIZE_Y;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Board DTO")
class BoardDtoTest {

  private static final long BOARD_ID = 1L;

  @DisplayName("Create Board DTO With Builder")
  @Test
  void testBoardDtoWithBuilder() {
    BoardDto boardDto = BoardDto.builder()
        .id(BOARD_ID)
        .pits(new int[BOARD_SIZE_Y][BOARD_SIZE_X])
        .build();

    int[][] pits = boardDto.getPits();
    assertEquals(BOARD_ID, boardDto.getId());
    assertEquals(BOARD_SIZE_Y, pits.length);
    assertEquals(BOARD_SIZE_X, pits[0].length);
  }

}