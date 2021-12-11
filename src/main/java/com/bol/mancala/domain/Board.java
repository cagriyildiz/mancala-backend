package com.bol.mancala.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

  @Transient
  public static final int BOARD_SIZE_X = 7;

  @Transient
  public static final int BOARD_SIZE_Y = 2;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @OneToOne(mappedBy = "board")
  private Game game;

  @Lob // store pits as bytes in the DB
  private int[][] pits; // multi-dimensional representation of board pits, last indexes represent big pits.

}