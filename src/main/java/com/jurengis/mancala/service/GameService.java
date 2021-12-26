package com.jurengis.mancala.service;

import com.jurengis.mancala.web.model.GameDto;

import java.util.UUID;

public interface GameService {

  GameDto createNewGame(GameDto gameDto);

  GameDto playGame(UUID gameId, int pit);
}
