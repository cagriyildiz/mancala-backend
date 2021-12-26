package com.jurengis.mancala.web.mapper.impl;

import com.jurengis.mancala.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.jurengis.mancala.domain.Game.PlayerOrder.FIRST;
import static com.jurengis.mancala.domain.Game.PlayerOrder.SECOND;
import static com.jurengis.mancala.web.mapper.impl.WinnerMapperTest.FIRST_PLAYER;
import static com.jurengis.mancala.web.mapper.impl.WinnerMapperTest.SECOND_PLAYER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Active Player Mapper")
class ActivePlayerMapperTest {

  protected static final int INVALID_PLAYER = -1;

  private ActivePlayerMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new ActivePlayerMapper();
  }

  @DisplayName("Test Integer to Enum")
  @Test
  void testAsEnum() {
    Game.PlayerOrder firstActivePlayer = mapper.asEnum(FIRST_PLAYER);
    Game.PlayerOrder secondActivePlayer = mapper.asEnum(SECOND_PLAYER);
    Game.PlayerOrder defaultActivePlayer = mapper.asEnum(INVALID_PLAYER);
    assertEquals(FIRST, firstActivePlayer);
    assertEquals(SECOND, secondActivePlayer);
    assertEquals(FIRST, defaultActivePlayer);
  }

  @DisplayName("Test Enum to Integer")
  @Test
  void testAsInteger() {
    int firstActivePlayer = mapper.asInteger(FIRST);
    int secondActivePlayer = mapper.asInteger(SECOND);
    int defaultActivePlayer = mapper.asInteger(null);
    assertEquals(FIRST_PLAYER, firstActivePlayer);
    assertEquals(SECOND_PLAYER, secondActivePlayer);
    assertEquals(INVALID_PLAYER, defaultActivePlayer);
  }

}
