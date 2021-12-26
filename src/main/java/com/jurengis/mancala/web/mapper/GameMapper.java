package com.jurengis.mancala.web.mapper;

import com.jurengis.mancala.domain.Game;
import com.jurengis.mancala.web.mapper.impl.ActivePlayerMapper;
import com.jurengis.mancala.web.mapper.impl.PitTypeMapper;
import com.jurengis.mancala.web.mapper.impl.WinnerMapper;
import com.jurengis.mancala.web.model.GameDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ActivePlayerMapper.class, WinnerMapper.class, PitTypeMapper.class})
public interface GameMapper {

  GameDto gameToGameDto(Game game);

  Game gameDtoToGame(GameDto gameDto);
}
