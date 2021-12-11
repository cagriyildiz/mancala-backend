package com.bol.mancala.service;

import com.bol.mancala.web.model.GameDto;

public interface GameService {

  GameDto createNewGame(GameDto gameDto);
}
