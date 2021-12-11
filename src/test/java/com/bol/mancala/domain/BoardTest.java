package com.bol.mancala.domain;

import com.bol.mancala.repository.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.bol.mancala.domain.GameTest.FIRST_PLAYER_ID;
import static com.bol.mancala.domain.GameTest.SECOND_PLAYER_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Test Board JPA Entity")
@DataJpaTest
class BoardTest {

  @Autowired
  private GameRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @DisplayName("Create Board Entity While Game Insert")
  @Test
  void testEntityCreation() {
    Game game = Game.builder()
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .build();

    Game newGame = repository.save(game);
    entityManager.flush();

    Board board = newGame.getBoard();
    assertNotNull(board);
    assertNotNull(board.getId());
    assertNotNull(board.getPits());
    assertNull(board.getGame()); // since game field in Board class is needed only for reference
  }

}