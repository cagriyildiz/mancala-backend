package com.bol.mancala.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

import static com.bol.mancala.domain.Board.BOARD_SIZE_X;
import static com.bol.mancala.domain.Board.BOARD_SIZE_Y;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {

  public enum PlayerOrder {
    FIRST, SECOND
  }

  public enum Winner {
    TIE, FIRST, SECOND
  }

  @Transient
  public static final int DEFAULT_INITIAL_STONE_COUNT = 6;

  @Transient
  public static final int MIN_INITIAL_STONE_COUNT = 4;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
  private UUID id; // UUID is used because game ids must be unique for every game session in the central database

  @Version
  private Integer version;

  @CreationTimestamp
  @Column(updatable = false)
  private OffsetDateTime createdDate;

  @UpdateTimestamp
  private OffsetDateTime lastModifiedDate;

  private Long firstPlayerId;

  private Long secondPlayerId;

  @Enumerated(EnumType.ORDINAL)
  private PlayerOrder activePlayer;

  @Enumerated
  private Winner winner;

  private Integer initialStoneCount;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "board_id")
  private Board board;

  @PrePersist
  private void setDefaults() {
    setDefaultActivePlayer();
    setDefaultInitialStoneCount();
    initializeBoard();
  }

  private void setDefaultActivePlayer() {
    // set default value for the user who starts the game first
    if (activePlayer == null) {
      activePlayer = PlayerOrder.FIRST;
    }
  }

  private void setDefaultInitialStoneCount() {
    // set default initial stone count
    if (initialStoneCount == null) {
      initialStoneCount = DEFAULT_INITIAL_STONE_COUNT;
    }
  }

  private void initializeBoard() {
    if (board == null) {
      int[][] pits = new int[BOARD_SIZE_Y][BOARD_SIZE_X];
      for (int[] row : pits) {
        Arrays.fill(row, 0, BOARD_SIZE_X - 1, initialStoneCount);
      }
      board = Board.builder()
          .pits(pits)
          .build();
    }
  }

}
