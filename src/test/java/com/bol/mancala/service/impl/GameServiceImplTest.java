package com.bol.mancala.service.impl;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.repository.GameRepository;
import com.bol.mancala.service.GameService;
import com.bol.mancala.web.mapper.GameMapper;
import com.bol.mancala.web.mapper.impl.ActivePlayerMapper;
import com.bol.mancala.web.model.BoardDto;
import com.bol.mancala.web.model.GameDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.bol.mancala.domain.Game.MIN_INITIAL_STONE_COUNT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Test Game Service")
@SpringBootTest
class GameServiceImplTest {

  protected static final long FIRST_PLAYER_ID = 1L;
  protected static final long SECOND_PLAYER_ID = 2L;

  @Autowired
  private GameService gameService;

  @Autowired
  private GameMapper gameMapper;

  @Autowired
  private ActivePlayerMapper activePlayerMapper;

  @MockBean
  private GameRepository gameRepository;

  @DisplayName("Create New Game")
  @Test
  void createNewGame() {
    GameDto gameDto = GameDto.builder()
        .id(UUID.randomUUID())
        .createdDate(OffsetDateTime.now())
        .lastModifiedDate(OffsetDateTime.now())
        .version(0)
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .initialStoneCount(MIN_INITIAL_STONE_COUNT)
        .activePlayer(0)
        .build();

    given(gameRepository.save(any())).willReturn(gameMapper.gameDtoToGame(gameDto));
    GameDto newGame = gameService.createNewGame(gameDto);

    verifyAutoGeneratedFields(newGame);

    assertEquals(FIRST_PLAYER_ID, newGame.getFirstPlayerId());
    assertEquals(SECOND_PLAYER_ID, newGame.getSecondPlayerId());
    assertEquals(MIN_INITIAL_STONE_COUNT, newGame.getInitialStoneCount());
    assertEquals(0, newGame.getActivePlayer());
  }

  @DisplayName("Play Game - Last Stone Goes to the Big Pit")
  @Test
  void playGameLastStoneGoesToBigPit() {
    final int startingPit = 0;
    final int[][] expectedPits = new int[][]{
        {0, 7, 7, 7, 7, 7, 1},
        {6, 6, 6, 6, 6, 6, 0}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(createDefaultBoard())
        .build();

    verifyPlayGame(true, startingPit, expectedPits, game);
  }

  @DisplayName("Play Game - Start From Arbitrarily Chosen Stone")
  @Test
  void playGame() {
    final int startingPit = 2;
    final int[][] expectedPits = new int[][]{
        {6, 6, 0, 7, 7, 7, 1},
        {7, 7, 6, 6, 6, 6, 0}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(createDefaultBoard())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);
  }

  @DisplayName("Play Game - Last Stone Goes To The Same Side Of The Board")
  @Test
  void playGameLastStoneOnSameSide() {
    final int startingPit = 1;
    final int[][] expectedPits = new int[][]{
        {7, 0, 1, 8, 8, 8, 2},
        {8, 0, 8, 7, 7, 7, 1}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.SECOND)
        .board(createSpecialBoard())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);
  }

  @DisplayName("Play Game - Start From Empty Pit")
  @Test
  void playGameStartFromEmptyPit() {
    final int startingPit = 1;
    final int[][] expectedPits = new int[][]{
        {7, 0, 1, 8, 8, 8, 2},
        {8, 1, 7, 7, 7, 7, 1}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(createSpecialBoard())
        .build();

    verifyPlayGame(true, startingPit, expectedPits, game);
  }

  @DisplayName("Play Game - Capture Stones")
  @Test
  void playGameCaptureStones() {
    final int startingPit = 1;
    final int[][] actualPits = new int[][]{
        {3, 0, 12, 9, 9, 0, 12},
        {1, 1, 0, 2, 10, 10, 3}
    };
    final int[][] expectedPits = new int[][]{
        {3, 0, 12, 0, 9, 0, 12},
        {1, 0, 0, 2, 10, 10, 13}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.SECOND)
        .board(Board.builder().pits(actualPits).build())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);
  }

  @DisplayName("Play Game - First Player Wins")
  @Test
  void playGameFirstPlayerWins() {
    final int startingPit = 5;
    final int[][] actualPits = new int[][]{
        {0, 0, 0, 0, 0, 1, 40},
        {1, 5, 4, 2, 6, 10, 3}
    };
    final int[][] expectedPits = new int[][]{
        {0, 0, 0, 0, 0, 0, 41},
        {0, 0, 0, 0, 0, 0, 31}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(Board.builder().pits(actualPits).build())
        .build();

    verifyPlayGame(true, startingPit, expectedPits, game);

    assertTrue(game.isFinished());
    assertEquals(Game.Winner.FIRST, game.getWinner());
  }

  @DisplayName("Play Game - Second Player Wins")
  @Test
  void playGameSecondPlayerWins() {
    final int startingPit = 5;
    final int[][] actualPits = new int[][]{
        {0, 0, 0, 0, 0, 5, 32},
        {1, 5, 4, 2, 10, 10, 3}
    };
    final int[][] expectedPits = new int[][]{
        {0, 0, 0, 0, 0, 0, 33},
        {0, 0, 0, 0, 0, 0, 39}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(Board.builder().pits(actualPits).build())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);

    assertTrue(game.isFinished());
    assertEquals(Game.Winner.SECOND, game.getWinner());
  }

  @DisplayName("Play Game - Second Player Wins")
  @Test
  void playGameOppositeSideIsEmptyWhenCurrentPlayerMoves() {
    final int startingPit = 0;
    final int[][] actualPits = new int[][]{
        {1, 0, 0, 0, 0, 0, 32},
        {5, 0, 0, 0, 0, 0, 34}
    };
    final int[][] expectedPits = new int[][]{
        {0, 0, 0, 0, 0, 0, 32},
        {0, 0, 0, 0, 0, 0, 40}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.SECOND)
        .board(Board.builder().pits(actualPits).build())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);

    assertTrue(game.isFinished());
    assertEquals(Game.Winner.SECOND, game.getWinner());
  }

  @DisplayName("Play Game - The End Tie Game")
  @Test
  void playGameTheEndTieGame() {
    final int startingPit = 5;
    final int[][] actualPits = new int[][]{
        {0, 0, 0, 0, 0, 5, 35},
        {1, 5, 4, 2, 4, 3, 13}
    };
    final int[][] expectedPits = new int[][]{
        {0, 0, 0, 0, 0, 0, 36},
        {0, 0, 0, 0, 0, 0, 36}
    };

    Game game = Game.builder()
        .activePlayer(Game.PlayerOrder.FIRST)
        .board(Board.builder().pits(actualPits).build())
        .build();

    verifyPlayGame(false, startingPit, expectedPits, game);

    assertTrue(game.isFinished());
    assertEquals(Game.Winner.TIE, game.getWinner());
  }

  private void verifyPlayGame(boolean nextPlayerIsSame, int startingPit, int[][] expectedPits, Game game) {
    given(gameRepository.getById(any())).willReturn(game);

    int activePlayer = activePlayerMapper.asInteger(game.getActivePlayer());

    GameDto newGameState = gameService.playGame(UUID.randomUUID(), startingPit);

    Integer nextActivePlayer = newGameState.getActivePlayer();

    if (nextPlayerIsSame) {
      assertEquals(activePlayer, nextActivePlayer);
    } else {
      assertNotEquals(activePlayer, nextActivePlayer);
    }

    verifyBoard(newGameState.getBoard(), expectedPits);
  }

  private void verifyBoard(BoardDto board, int[][] expectedPits) {
    int[][] pits = board.getPits();
    IntStream expectedPitsStream = Arrays.stream(expectedPits).flatMapToInt(Arrays::stream);
    IntStream actualPitsStream = Arrays.stream(pits).flatMapToInt(Arrays::stream);
    isStreamsEqual(expectedPitsStream, actualPitsStream);
  }

  private void isStreamsEqual(IntStream stream1, IntStream stream2) {
    Iterator<Integer> iterator1 = stream1.iterator();
    Iterator<Integer> iterator2 = stream2.iterator();

    // check every value is equal in both collection
    while (iterator1.hasNext() && iterator2.hasNext()) {
      assertEquals(iterator1.next(), iterator2.next());
    }

    // check if no other value is left in both collection
    assertFalse(iterator1.hasNext());
    assertFalse(iterator2.hasNext());
  }

  private void verifyAutoGeneratedFields(GameDto newGame) {
    assertNotNull(newGame.getId());
    assertNotNull(newGame.getVersion());
    assertNotNull(newGame.getCreatedDate());
    assertNotNull(newGame.getLastModifiedDate());
  }

  private Board createDefaultBoard() {
    int[][] pits = new int[][]{
        {6, 6, 6, 6, 6, 6, 0},
        {6, 6, 6, 6, 6, 6, 0}
    };
    return Board.builder()
        .pits(pits)
        .build();
  }

  private Board createSpecialBoard() {
    int[][] pits = new int[][]{
        {7, 0, 1, 8, 8, 8, 2},
        {8, 1, 7, 7, 7, 7, 1}
    };
    return Board.builder()
        .pits(pits)
        .build();
  }

}
