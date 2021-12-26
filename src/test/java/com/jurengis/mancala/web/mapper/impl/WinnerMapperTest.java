package com.jurengis.mancala.web.mapper.impl;

import com.jurengis.mancala.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Winner Mapper")
class WinnerMapperTest {

  protected static final int FIRST_PLAYER = 0;
  protected static final int SECOND_PLAYER = 1;
  protected static final int TIE_GAME = -1;

  private WinnerMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new WinnerMapper();
  }

  @DisplayName("Test Integer to Enum")
  @Test
  void testAsEnum() {
    Game.Winner winnerIsFirstPlayer = mapper.asEnum(FIRST_PLAYER);
    Game.Winner winnerIsSecondPlayer = mapper.asEnum(SECOND_PLAYER);
    Game.Winner tieGame = mapper.asEnum(TIE_GAME);
    assertEquals(Game.Winner.FIRST, winnerIsFirstPlayer);
    assertEquals(Game.Winner.SECOND, winnerIsSecondPlayer);
    assertEquals(Game.Winner.TIE, tieGame);
  }

  @DisplayName("Test Enum to Integer")
  @Test
  void testAsInteger() {
    int winnerIsFirstPlayer = mapper.asInteger(Game.Winner.FIRST);
    int winnerIsSecondPlayer = mapper.asInteger(Game.Winner.SECOND);
    int tieGame = mapper.asInteger(Game.Winner.TIE);
    int invalidWinner = mapper.asInteger(null);
    assertEquals(FIRST_PLAYER, winnerIsFirstPlayer);
    assertEquals(SECOND_PLAYER, winnerIsSecondPlayer);
    assertEquals(TIE_GAME, tieGame);
    assertEquals(TIE_GAME, invalidWinner);
  }

}
