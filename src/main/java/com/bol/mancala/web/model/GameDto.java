package com.bol.mancala.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.bol.mancala.domain.Game.MIN_INITIAL_STONE_COUNT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {

  @Null
  private UUID id;

  @Null
  private Integer version;

  @Null
  private OffsetDateTime createdDate;

  @Null
  private OffsetDateTime lastModifiedDate;

  @NotNull
  @Positive
  private Long firstPlayerId;

  @NotNull
  @Positive
  private Long secondPlayerId;

  @Min(MIN_INITIAL_STONE_COUNT)
  private Integer initialStoneCount;

  @PositiveOrZero
  private Integer activePlayer;

  @Null
  private Integer winner;

  @Null
  private Boolean finished;

  @Null
  private BoardDto board;

}
