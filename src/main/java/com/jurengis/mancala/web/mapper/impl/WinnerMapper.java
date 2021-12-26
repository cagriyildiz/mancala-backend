package com.jurengis.mancala.web.mapper.impl;

import com.jurengis.mancala.domain.Game;
import org.springframework.stereotype.Component;

import static com.jurengis.mancala.domain.Game.Winner.*;

@Component
public class WinnerMapper {

  public Game.Winner asEnum(int winner) {
    switch (winner) {
      case 0: return FIRST;
      case 1: return SECOND;
      default: return TIE;
    }
  }

  public int asInteger(Game.Winner winner) {
    if (winner != null) {
      switch (winner) {
        case FIRST: return 0;
        case SECOND: return 1;
        default: return -1;
      }
    }
    return -1;
  }

}
