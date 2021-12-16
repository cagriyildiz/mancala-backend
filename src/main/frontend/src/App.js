import {useRef, useState} from "react";
import axios from "axios";
import './App.css';
import Board from "./component/Board/Board";

function App() {

  const gameId = useRef();
  const createGameEndpoint = 'http://localhost:8080/api/v1/game/create/';
  const playGameEndpoint = 'http://localhost:8080/api/v1/game/play/';

  const [playing, setPlaying] = useState(false);
  const [activePlayer, setActivePlayer] = useState(0);
  const [gameState, setGameState] = useState([[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]]);

  const getDefaultRequestPayload = () => {
    return {
      "firstPlayerId": "123",
      "secondPlayerId": "456"
    };
  };

  function setNextGameState(response) {
    setActivePlayer(response.data.activePlayer);
    setGameState(response.data.board.pits);
  }

  const startGame = () => {
    axios.post(createGameEndpoint, getDefaultRequestPayload())
      .then(response => {
        gameId.current = response.data.id;
        setNextGameState(response);
      }).catch(error => {
      console.error(error);
    });
    setPlaying(true);
  };

  const moveStonesHandler = (pit) => {
    axios.get(`${playGameEndpoint}${gameId.current}?pit=${pit}`)
      .then(response => {
        setNextGameState(response);
      }).catch(error => {
        console.error(error);
    });
  };

  return (
    <div data-player={activePlayer} className="App">
      <header className="App-header">
        <p className="App-start" onClick={startGame}>
          Play Mancala
        </p>
        <Board show={playing} pits={gameState} moveStones={moveStonesHandler}/>
      </header>
    </div>
  );
}

export default App;
