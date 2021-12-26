package com.jurengis.mancala.web.model;

import com.jurengis.mancala.domain.Pit;
import com.jurengis.mancala.web.mapper.impl.PitTypeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jurengis.mancala.domain.Board.BOARD_SIZE_X;
import static com.jurengis.mancala.domain.Board.BOARD_SIZE_Y;
import static com.jurengis.mancala.domain.Game.DEFAULT_INITIAL_STONE_COUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Board DTO")
class BoardDtoTest {

  @DisplayName("Create Board DTO With Builder")
  @Test
  void testBoardDtoWithBuilder() {
    UUID id = UUID.randomUUID();
    BoardDto boardDto = BoardDto.builder()
        .id(id)
        .state(getExpectedGameState())
        .build();

    int[][] pits = new int[2][7];
    assertEquals(id, boardDto.getId());
    assertEquals(BOARD_SIZE_Y, pits.length);
    assertEquals(BOARD_SIZE_X, pits[0].length);
  }

  private List<StateDto> getExpectedGameState() {
    List<StateDto> state = new ArrayList<>();
    for (int i = 0; i < BOARD_SIZE_Y; i++) {
      state.add(createNewGameState());
    }
    return state;
  }

  private StateDto createNewGameState() {
    List<PitDto> pits = new ArrayList<>(BOARD_SIZE_X);
    for (int i = 0; i < BOARD_SIZE_X - 1; i++) {
      PitDto pit = createPit(Pit.Type.LITTLE, DEFAULT_INITIAL_STONE_COUNT);
      pits.add(pit);
    }
    PitDto pit = createPit(Pit.Type.BIG, 0);
    pits.add(pit);
    return StateDto.builder().pits(pits).build();
  }

  private PitDto createPit(Pit.Type type, int stoneCount) {
    PitTypeMapper mapper = new PitTypeMapper();
    return PitDto.builder()
        .type(mapper.asInteger(type))
        .stoneCount(stoneCount)
        .build();
  }

}