package com.bol.mancala.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {

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

  private Long activePlayerId;

  private Integer initialStoneCount;

}
