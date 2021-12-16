import {useState} from "react";
import axios from "axios";
import './App.css';
import Board from "./component/Board/Board";

function App() {

  const createGameEndpoint = 'http://localhost:8080/api/v1/game/create/';

  const [playing, setPlaying] = useState(false);
  const [gameState, setGameState] = useState([[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]]);

  const getDefaultRequestPayload = () => {
    return {
      "firstPlayerId": "123",
      "secondPlayerId": "456"
    };
  };

  const startGame = () => {
    axios.post(createGameEndpoint, getDefaultRequestPayload())
      .then(response => {
        setGameState(response.data.board.pits);
      }).catch(error => {
      console.error(error);
    });
    setPlaying(true);
  };

  return (
    <div className="App">
      <header className="App-header">
        <p className="App-start" onClick={startGame}>
          Play Mancala
        </p>
        <Board show={playing} pits={gameState}/>
      </header>
    </div>
  );
}

export default App;
