package com.jurengis.mancala.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jurengis.mancala.domain.Board.BOARD_SIZE_X;
import static com.jurengis.mancala.domain.Board.BOARD_SIZE_Y;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

  private boolean finished = false;

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
      board = Board.builder()
          .state(initializeGameState())
          .build();
    }
  }

  private List<State> initializeGameState() {
    List<State> state = new ArrayList<>();
    for (int i = 0; i < BOARD_SIZE_Y; i++) {
      state.add(createNewGameState());
    }
    return state;
  }

  private State createNewGameState() {
    List<Pit> pits = new ArrayList<>(BOARD_SIZE_X);
    for (int i = 0; i < BOARD_SIZE_X - 1; i++) {
      Pit pit = createPit(Pit.Type.LITTLE, initialStoneCount);
      pits.add(pit);
    }
    Pit pit = createPit(Pit.Type.BIG, 0);
    pits.add(pit);
    return State.builder().pits(pits).build();
  }

  private Pit createPit(Pit.Type type, int stoneCount) {
    return Pit.builder()
        .type(type)
        .stoneCount(stoneCount)
        .build();
  }

}
