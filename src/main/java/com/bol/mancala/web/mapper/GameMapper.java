package com.bol.mancala.web.mapper;

import com.bol.mancala.domain.Game;
import com.bol.mancala.web.mapper.impl.ActivePlayerMapper;
import com.bol.mancala.web.model.GameDto;
import org.mapstruct.Mapper;

@Mapper(uses = ActivePlayerMapper.class)
public interface GameMapper {

  GameDto gameToGameDto(Game game);

  Game gameDtoToGame(GameDto gameDto);
}
