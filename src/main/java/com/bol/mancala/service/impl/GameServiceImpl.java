package com.bol.mancala.service.impl;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.repository.GameRepository;
import com.bol.mancala.service.GameService;
import com.bol.mancala.web.model.BoardDto;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;

  @Override
  public GameDto createNewGame(GameDto gameDto) {
    // TODO: use mapper to do a conversion between dto and entity
    Game newGame = mapGameDtoToGame(gameDto);
    Game savedGame = gameRepository.save(newGame);
    return mapGameToGameDto(savedGame);
  }

  private Game mapGameDtoToGame(GameDto gameDto) {
    return Game.builder()
        .firstPlayerId(gameDto.getFirstPlayerId())
        .secondPlayerId(gameDto.getSecondPlayerId())
        .initialStoneCount(gameDto.getInitialStoneCount())
        .build();
  }

  private GameDto mapGameToGameDto(Game savedGame) {
    return GameDto.builder()
        .id(savedGame.getId())
        .version(savedGame.getVersion())
        .createdDate(savedGame.getCreatedDate())
        .lastModifiedDate(savedGame.getLastModifiedDate())
        .firstPlayerId(savedGame.getFirstPlayerId())
        .secondPlayerId(savedGame.getSecondPlayerId())
        .initialStoneCount(savedGame.getInitialStoneCount())
        .activePlayerId(savedGame.getActivePlayerId())
        .board(mapBoardToBoardDto(savedGame.getBoard()))
        .build();
  }

  private BoardDto mapBoardToBoardDto(Board board) {
    return BoardDto.builder()
        .id(board.getId())
        .pits(board.getPits())
        .build();
  }

}
