package com.jurengis.mancala.web.mapper.impl;

import com.jurengis.mancala.domain.Game;
import org.springframework.stereotype.Component;

import static com.jurengis.mancala.domain.Game.PlayerOrder.FIRST;
import static com.jurengis.mancala.domain.Game.PlayerOrder.SECOND;

@Component
public class ActivePlayerMapper {

  public Game.PlayerOrder asEnum(int activePlayer) {
    if (activePlayer == 1) {
      return SECOND;
    }
    return FIRST;
  }

  public int asInteger(Game.PlayerOrder activePlayer) {
    if (activePlayer != null) {
      if (activePlayer == FIRST) {
        return 0;
      } else if (activePlayer == SECOND) {
        return 1;
      }
    }
    return -1;
  }

}
