import React, {useRef, useState} from "react";
import axios from "axios";
import Button from "../Button/Button";
import User from "../User/User";
import Board from "../Board/Board";
import Winner from "../Winner/Winner";
import {ENDPOINT_CREATE_GAME, ENDPOINT_PLAY_GAME} from "./constant";
import {UserEnum} from "../User/constant";

const Game = () => {

  const gameId = useRef();

  const [playing, setPlaying] = useState(false);
  const [finished, setFinished] = useState(false);
  const [winner, setWinner] = useState(Winner.TIE);
  const [activePlayer, setActivePlayer] = useState(UserEnum.FIRST);
  const [gameState, setGameState] = useState();

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
    setWinner(Winner.TIE);
    axios.post(ENDPOINT_CREATE_GAME, getDefaultRequestPayload())
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
    axios.get(`${ENDPOINT_PLAY_GAME}${gameId.current}?pit=${pit}`)
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
        <User playing={playing} user={UserEnum.SECOND} isActive={activePlayer === UserEnum.SECOND}/>
        <Board show={playing}
               pits={gameState}
               activePlayer={activePlayer}
               moveStones={moveStonesHandler}/>
        <User playing={playing} user={UserEnum.FIRST} isActive={activePlayer === UserEnum.FIRST}/>
        <Winner finished={finished} winner={winner}/>
      </header>
    </div>
  );
};

export default Game;