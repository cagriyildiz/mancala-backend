package com.bol.mancala.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Pit {

  public enum Type {
    LITTLE, BIG
  }

  private Type type = Type.LITTLE;

  private int stoneCount;

}