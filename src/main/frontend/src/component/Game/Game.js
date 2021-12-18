import React, {useRef, useState} from "react";
import axios from "axios";
import Button from "../Button/Button";
import User from "../User/User";
import Board from "../Board/Board";
import Winner from "../Winner/Winner";

const Game = () => {

  const gameId = useRef();
  const createGameEndpoint = 'http://localhost:8080/api/v1/game/create/';
  const playGameEndpoint = 'http://localhost:8080/api/v1/game/play/';

  const [playing, setPlaying] = useState(false);
  const [finished, setFinished] = useState(false);
  const [winner, setWinner] = useState(-1);
  const [activePlayer, setActivePlayer] = useState(0);
  const [gameState, setGameState] = useState([[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]]);

  const getDefaultRequestPayload = () => {
    return {
      "firstPlayerId": "123",
      "secondPlayerId": "456"
    };
  };

  const setNextGameState = (response) => {
    setActivePlayer(response.data.activePlayer);
    setGameState(response.data.board.pits);
  };

  const startGame = () => {
    setFinished(false);
    setWinner(-1);
    axios.post(createGameEndpoint, getDefaultRequestPayload())
      .then(response => {
        gameId.current = response.data.id;
        setNextGameState(response);
        setPlaying(true);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const gameIsFinished = (response) => {
    if (response.data.finished) {
      setFinished(response.data.finished);
      setWinner(response.data.winner);
    }
  };

  const moveStonesHandler = (pit) => {
    axios.get(`${playGameEndpoint}${gameId.current}?pit=${pit}`)
      .then(response => {
        setNextGameState(response);
        gameIsFinished(response);
      })
      .catch(error => {
        console.error(error);
      });
  };

  return (
    <div data-player={activePlayer} className="App">
      <header className="App-header">
        <Button text="Play Mancala" clicked={startGame}/>
        <User playing={playing} user={1} isActive={activePlayer === 1}/>
        <Board show={playing}
               pits={gameState}
               activePlayer={activePlayer}
               moveStones={moveStonesHandler}/>
        <User playing={playing} user={0} isActive={activePlayer === 0}/>
        <Winner finished={finished} winner={winner}/>
      </header>
    </div>
  );
};

export default Game;