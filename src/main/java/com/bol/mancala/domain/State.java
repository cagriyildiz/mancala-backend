package com.bol.mancala.domain;

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
public class State {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
  private UUID id; // UUID is used because game ids must be unique for every game session in the central database

  @ElementCollection
  private List<Pit> pits;

  @Transient
  public Pit getBigPit() {
    return pits.get(pits.size() - 1);
  }

}
