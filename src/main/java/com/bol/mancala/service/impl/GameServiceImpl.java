package com.bol.mancala.service.impl;

import com.bol.mancala.domain.Board;
import com.bol.mancala.domain.Game;
import com.bol.mancala.repository.GameRepository;
import com.bol.mancala.service.GameService;
import com.bol.mancala.web.mapper.GameMapper;
import com.bol.mancala.web.mapper.impl.ActivePlayerMapper;
import com.bol.mancala.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;

  private final GameMapper gameMapper;

  private final ActivePlayerMapper activePlayerMapper;

  @Override
  public GameDto createNewGame(GameDto gameDto) {
    Game newGame = gameMapper.gameDtoToGame(gameDto);
    Game savedGame = gameRepository.save(newGame);
    return gameMapper.gameToGameDto(savedGame);
  }

  @Override
  public GameDto playGame(final UUID gameId, final int startingPit) {
    Game game = gameRepository.getById(gameId);
    int activePlayer = activePlayerMapper.asInteger(game.getActivePlayer());
    Board board = game.getBoard();
    int[][] gameState = board.getPits();

    if (gameState[activePlayer][startingPit] != 0) { // prevent player to start from an empty pit
      int nextActivePlayer = movePits(gameState, activePlayer, startingPit);

      game.setActivePlayer(activePlayerMapper.asEnum(nextActivePlayer));
      gameRepository.save(game);
    }
    return gameMapper.gameToGameDto(game);
  }

  private int movePits(int[][] state, final int activePlayer, final int pit) {
    int side = activePlayer;
    final int totalPlayerCount = Game.PlayerOrder.values().length;
    final int boardSize = state[side].length;
    int stonesInHand = state[side][pit];
    state[side][pit] = 0; // empty starting pit

    boolean nextPlayerIsTheSame = false;
    int i = pit; // counter for current pit
    while (stonesInHand != 0) {
      i++;
      if (!(i == boardSize - 1 && activePlayer != side)) { // skip opponent's big pit
        state[side][i] = state[side][i] + 1; // put one stone on each pit
        stonesInHand--; // remove one stone from hand
      }
      if (i == boardSize - 1) { // if the current pit is active player's own big pit
        if (stonesInHand == 0) {
          nextPlayerIsTheSame = true; // last stone ends up in the big pit
          break;
        }
        side = (side + 1) % totalPlayerCount; // move to the next side of the board
        i = -1;
      }
    }

    // if its own side and not big pit and current pit has only 1 stone
    if (side == activePlayer && !nextPlayerIsTheSame && state[side][i] == 1) {
      captureStones(state, side, totalPlayerCount, boardSize, i);
    }

    return nextPlayerIsTheSame ? activePlayer : (activePlayer + 1) % totalPlayerCount;
  }

  private void captureStones(int[][] state, int currentSide, int totalPlayerCount, int boardSize, int currentPit) {
    int oppositeSide = (currentSide + 1) % totalPlayerCount;
    int oppositePit = boardSize - currentPit - 2; // minus 2 because; 1 for big pit, 1 for 0 indexed array
    int stonesInOppositePit = state[oppositeSide][oppositePit];
    state[oppositeSide][oppositePit] = 0; // empty opposite pit

    int stonesInCurrentPit = state[currentSide][currentPit];
    state[currentSide][currentPit] = 0; // empty current pit

    int totalPoints = stonesInOppositePit + stonesInCurrentPit;
    state[currentSide][boardSize - 1] += totalPoints; // put all points to the big pit
  }

}
