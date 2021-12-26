package com.jurengis.mancala.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
  private UUID id;

  @OneToOne(mappedBy = "board")
  private Game game;

  @OneToMany(cascade = CascadeType.ALL)
  @CollectionTable(name = "board_state", joinColumns = @JoinColumn(name = "side_id"))
  private List<State> state;

}