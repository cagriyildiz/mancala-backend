package com.bol.mancala.service.impl;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Pit;
import com.bol.mancala.domain.State;
import com.bol.mancala.repository.GameRepository;
import com.bol.mancala.service.GameService;
import com.bol.mancala.web.mapper.GameMapper;
import com.bol.mancala.web.mapper.impl.ActivePlayerMapper;
import com.bol.mancala.web.mapper.impl.WinnerMapper;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.bol.mancala.domain.Board.BOARD_SIZE_X;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;

  private final GameMapper gameMapper;

  private final ActivePlayerMapper activePlayerMapper;

  private final WinnerMapper winnerMapper;

  @Override
  public GameDto createNewGame(GameDto gameDto) {
    Game newGame = gameMapper.gameDtoToGame(gameDto);
    Game savedGame = gameRepository.save(newGame);
    log.info("New game session created: {}", savedGame.getId());
    log.debug("Initial game state: {}", savedGame);
    return gameMapper.gameToGameDto(savedGame);
  }

  @Override
  public GameDto playGame(final UUID gameId, final int startingPit) {
    Game game = gameRepository.getById(gameId);
    log.debug("Current game state: {}", game);
    int activePlayer = activePlayerMapper.asInteger(game.getActivePlayer());
    Board board = game.getBoard();
    List<State> gameState = board.getState();

    if (getStonesInPit(gameState, startingPit, activePlayer) != 0) { // prevent player to start from an empty pit
      int nextActivePlayer = moveStones(gameState, activePlayer, startingPit);

      boolean isFinished = gameIsFinished(gameState, activePlayer);
      if (isFinished) {
        Game.Winner winner = getWinner(gameState);
        game.setWinner(winner);
        game.setFinished(true);
        log.info("Game '{}' finished. Winner is the {} player.", gameId, winner);
      }

      game.setActivePlayer(activePlayerMapper.asEnum(nextActivePlayer));
      gameRepository.save(game);
    }

    log.debug("Next game state: {}", game);
    return gameMapper.gameToGameDto(game);
  }

  private int moveStones(List<State> state, int activePlayer, int pit) {
    int side = activePlayer;
    int stonesInHand = getStonesInPit(state, pit, side);
    setStonesInPit(state, pit, side, 0); // empty starting pit

    boolean nextPlayerIsTheSame = false;
    int i = pit; // counter for current pit
    while (stonesInHand != 0) {
      i++;
      if (!(i == BOARD_SIZE_X - 1 && activePlayer != side)) { // skip opponent's big pit
        setStonesInPit(state, i, side, getStonesInPit(state, i, side) + 1); // put one stone on each pit
        stonesInHand--; // remove one stone from hand
      }
      if (i == BOARD_SIZE_X - 1) { // if the current pit is active player's own big pit
        if (stonesInHand == 0) {
          nextPlayerIsTheSame = true; // last stone ends up in the big pit
          break;
        }
        side = nextSide(side); // move to the next side of the board
        i = -1;
      }
    }

    if (isLastStoneEndsUpInEmptyPit(state, activePlayer, side, nextPlayerIsTheSame, i)) {
      captureStones(state, side, i);
    }

    return nextPlayerIsTheSame ? activePlayer : nextSide(activePlayer);
  }

  private void captureStones(List<State> state, int currentSide, int currentPit) {
    int oppositeSide = nextSide(currentSide);
    int oppositePit = BOARD_SIZE_X - currentPit - 2; // minus 2 because; 1 for big pit, 1 for 0 indexed array
    int stonesInOppositePit = getStonesInPit(state, oppositePit, oppositeSide);
    setStonesInPit(state, oppositePit, oppositeSide, 0); // empty opposite pit

    int stonesInCurrentPit = getStonesInPit(state, currentPit, currentSide);
    setStonesInPit(state, currentPit, currentSide, 0); // empty current pit

    int totalPoints = stonesInOppositePit + stonesInCurrentPit;
    // put all points to the big pit
    state.get(currentSide).getBigPit().setStoneCount(state.get(currentSide).getBigPit().getStoneCount() + totalPoints);
  }

  private Game.Winner getWinner(List<State> state) {
    int winner = -1;
    if (state.get(0).getBigPit().getStoneCount() > state.get(1).getBigPit().getStoneCount()) {
      winner = 0;
    } else if (state.get(1).getBigPit().getStoneCount() > state.get(0).getBigPit().getStoneCount()) {
      winner = 1;
    }
    return winnerMapper.asEnum(winner);
  }

  private boolean gameIsFinished(List<State> state, int activePlayer) {
    int playerWithEmptyPits = -1;
    if (pitsAreEmpty(state.get(activePlayer).getPits())) { // current player has no stones in his side
      playerWithEmptyPits = activePlayer;
    } else if (pitsAreEmpty(state.get(nextSide(activePlayer)).getPits())) { // opponent has no stones in his side
      playerWithEmptyPits = nextSide(activePlayer);
    }
    if (playerWithEmptyPits > -1) { // game is finished
      int oppositeSide = nextSide(playerWithEmptyPits);
      collectRemainderStones(state.get(oppositeSide).getPits());
      return true;
    }
    return false;
  }

  private boolean isLastStoneEndsUpInEmptyPit(List<State> state, int activePlayer, int side, boolean nextPlayerIsTheSame, int i) {
    // if its own side and not big pit and current pit has only 1 stone
    return side == activePlayer && !nextPlayerIsTheSame && getStonesInPit(state, i, side) == 1;
  }

  private boolean pitsAreEmpty(List<Pit> pits) {
    for (Pit pit : pits) {
      if (pit.getType().equals(Pit.Type.LITTLE) && pit.getStoneCount() != 0) {
        return false;
      }
    }
    return true;
  }

  private void collectRemainderStones(List<Pit> pits) {
    int total = 0;
    for (Pit pit : pits) {
      if (pit.getType().equals(Pit.Type.LITTLE)) {
        total += pit.getStoneCount();
        pit.setStoneCount(0);
      } else {
        pit.setStoneCount(pit.getStoneCount() + total);
      }
    }
  }

  private void setStonesInPit(List<State> state, int pit, int side, int stones) {
    state.get(side).getPits().get(pit).setStoneCount(stones);
  }

  private int getStonesInPit(List<State> state, int pit, int side) {
    return state.get(side).getPits().get(pit).getStoneCount();
  }

  private int getTotalPlayerCount() {
    return Game.PlayerOrder.values().length;
  }

  private int nextSide(int currentSide) {
    return (currentSide + 1) % getTotalPlayerCount();
  }

}
