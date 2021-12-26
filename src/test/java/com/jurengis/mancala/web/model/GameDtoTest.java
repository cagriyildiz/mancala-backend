package com.jurengis.mancala.web.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test Game DTO")
class GameDtoTest {

  private static final long VALID_FIRST_PLAYER_ID = 1L;
  private static final long VALID_SECOND_PLAYER_ID = 2L;
  private static final long INVALID_FIRST_PLAYER_ID = -1L;
  private static final long INVALID_SECOND_PLAYER_ID = -2L;
  private static final int INVALID_ACTIVE_PLAYER = -1;

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @DisplayName("Validate Object - Without Required Fields")
  @Test
  void testValidateWithoutRequiredFields() {
    GameDto dto = GameDto.builder().build();
    Set<ConstraintViolation<GameDto>> violations = validator.validate(dto);
    assertEquals(2, violations.size());
  }

  @DisplayName("Validate Object - Required Fields")
  @Test
  void testValidateRequiredFields() {
    GameDto dto = GameDto.builder()
        .firstPlayerId(VALID_FIRST_PLAYER_ID)
        .secondPlayerId(VALID_SECOND_PLAYER_ID)
        .build();
    Set<ConstraintViolation<GameDto>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @DisplayName("Validate Object - Required Fields Invalid")
  @Test
  void testValidateRequiredFieldsInvalid() {
    GameDto dto = GameDto.builder()
        .firstPlayerId(INVALID_FIRST_PLAYER_ID)
        .secondPlayerId(INVALID_SECOND_PLAYER_ID)
        .build();
    Set<ConstraintViolation<GameDto>> violations = validator.validate(dto);
    assertEquals(2, violations.size());
  }

  @DisplayName("Validate Object - Active Player Value Invalid")
  @Test
  void testActivePlayerInvalid() {
    GameDto dto = GameDto.builder()
        .firstPlayerId(VALID_FIRST_PLAYER_ID)
        .secondPlayerId(VALID_SECOND_PLAYER_ID)
        .activePlayer(INVALID_ACTIVE_PLAYER)
        .build();
    Set<ConstraintViolation<GameDto>> violations = validator.validate(dto);
    assertEquals(1, violations.size());
  }

  @DisplayName("Validate Object - Null Fields Are Not Empty")
  @Test
  void testNullFieldsNotEmpty() {
    GameDto dto = GameDto.builder()
        .id(UUID.randomUUID())
        .version(0)
        .createdDate(OffsetDateTime.now())
        .lastModifiedDate(OffsetDateTime.now())
        .firstPlayerId(VALID_FIRST_PLAYER_ID)
        .secondPlayerId(VALID_SECOND_PLAYER_ID)
        .winner(0)
        .finished(true)
        .board(BoardDto.builder().build())
        .build();
    Set<ConstraintViolation<GameDto>> violations = validator.validate(dto);
    assertEquals(7, violations.size());
  }

}