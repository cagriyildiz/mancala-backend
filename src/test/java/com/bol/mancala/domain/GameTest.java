package com.bol.mancala.domain;

import com.bol.mancala.repository.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.bol.mancala.domain.Game.DEFAULT_INITIAL_STONE_COUNT;
import static com.bol.mancala.domain.Game.MIN_INITIAL_STONE_COUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test Game JPA Entity")
@DataJpaTest
class GameTest {

  protected static final long FIRST_PLAYER_ID = 1L;
  protected static final long SECOND_PLAYER_ID = 2L;

  @Autowired
  private GameRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @DisplayName("Create Game Entity With Complete Input")
  @Test
  void testEntityCreationWithCompleteInput() {
    Game game = Game.builder()
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .activePlayerId(SECOND_PLAYER_ID)
        .initialStoneCount(MIN_INITIAL_STONE_COUNT)
        .build();

    Game newGame = repository.save(game);
    entityManager.flush();

    // check auto generated fields are filled
    verifyAutoGeneratedFields(newGame);

    // check values are assigned correctly
    assertEquals(FIRST_PLAYER_ID, newGame.getFirstPlayerId());
    assertEquals(SECOND_PLAYER_ID, newGame.getSecondPlayerId());
    assertEquals(SECOND_PLAYER_ID, newGame.getActivePlayerId());
    assertEquals(MIN_INITIAL_STONE_COUNT, newGame.getInitialStoneCount());
  }

  @DisplayName("Create Game Entity With Only Required Input")
  @Test
  void testEntityCreationWithOnlyRequiredInput() {
    Game game = Game.builder()
        .firstPlayerId(FIRST_PLAYER_ID)
        .secondPlayerId(SECOND_PLAYER_ID)
        .build();

    Game newGame = repository.save(game);
    entityManager.flush();

    // check auto generated fields are filled
    verifyAutoGeneratedFields(newGame);

    // check values are assigned correctly
    assertEquals(FIRST_PLAYER_ID, newGame.getFirstPlayerId());
    assertEquals(SECOND_PLAYER_ID, newGame.getSecondPlayerId());

    // check values are initialized with defaults
    verifyDefaults(newGame);
  }

  private void verifyAutoGeneratedFields(Game newGame) {
    assertNotNull(newGame.getId());
    assertNotNull(newGame.getVersion());
    assertNotNull(newGame.getCreatedDate());
    assertNotNull(newGame.getLastModifiedDate());
  }

  private void verifyDefaults(Game newGame) {
    assertEquals(FIRST_PLAYER_ID, newGame.getActivePlayerId());
    assertEquals(DEFAULT_INITIAL_STONE_COUNT, newGame.getInitialStoneCount());
  }

}
