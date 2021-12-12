package com.bol.mancala.service;

import com.bol.mancala.web.model.GameDto;

import java.util.UUID;

public interface GameService {

  GameDto createNewGame(GameDto gameDto);

  GameDto playGame(UUID gameId, int pit);
}
