package com.bol.mancala.web.mapper.impl;

import com.bol.mancala.domain.Game;
import org.springframework.stereotype.Component;

@Component
public class ActivePlayerMapper {

  public Game.PlayerOrder asEnum(int activePlayer) {
    Game.PlayerOrder[] players = Game.PlayerOrder.values();
    for (int i = 0; i < players.length; i++) {
      if (activePlayer == i) {
        break;
      }
    }
    return players[activePlayer];
  }

  public int asInteger(Game.PlayerOrder activePlayer) {
    if (activePlayer != null) {
      Game.PlayerOrder[] players = Game.PlayerOrder.values();
      for (int i = 0; i < players.length; i++) {
        if (players[i] == activePlayer) {
          return i;
        }
      }
    }
    return -1;
  }

}
