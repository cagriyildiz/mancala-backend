package com.bol.mancala.web.controller;

import com.bol.mancala.service.GameService;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/game/")
@RestController
public class GameController {

  private final GameService gameService;

  @PostMapping(value = "create")
  public ResponseEntity<GameDto> createNewGame(@RequestBody @Validated GameDto gameDto) {
    GameDto newGame = gameService.createNewGame(gameDto);
    return new ResponseEntity<>(newGame, HttpStatus.CREATED);
  }

}
