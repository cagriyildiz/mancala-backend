package com.bol.mancala.service.impl;

import com.bol.mancala.domain.Game;
import com.bol.mancala.repository.GameRepository;
import com.bol.mancala.service.GameService;
import com.bol.mancala.web.mapper.GameMapper;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;

  private final GameMapper gameMapper;

  @Override
  public GameDto createNewGame(GameDto gameDto) {
    Game newGame = gameMapper.gameDtoToGame(gameDto);
    Game savedGame = gameRepository.save(newGame);
    return gameMapper.gameToGameDto(savedGame);
  }

}
