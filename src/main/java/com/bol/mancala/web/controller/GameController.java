package com.bol.mancala.web.controller;

import com.bol.mancala.service.GameService;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

import static com.bol.mancala.domain.Board.BOARD_SIZE_X;

@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/")
@RestController
public class GameController {

  private final GameService gameService;

  @PostMapping(value = "create")
  public ResponseEntity<GameDto> createNewGame(@RequestBody @Validated GameDto gameDto) {
    log.debug("Requested new game session: {}", gameDto);
    GameDto newGame = gameService.createNewGame(gameDto);
    return new ResponseEntity<>(newGame, HttpStatus.CREATED);
  }

  @GetMapping(value = "play/{gameId}")
  public ResponseEntity<GameDto> playGame(@PathVariable UUID gameId,
                                          // only small pits are accepted as a parameter
                                          @RequestParam(value = "pit") @PositiveOrZero @Max(BOARD_SIZE_X - 2) int pit) {
    log.debug("Requested to make a move in game '{}'. Starting pit: {}", gameId, pit);
    GameDto newGameState = gameService.playGame(gameId, pit);
    return new ResponseEntity<>(newGameState, HttpStatus.OK);
  }

}
