import {useState} from "react";
import './App.css';
import Board from "./component/Board/Board";

function App() {

  const [playing, setPlaying] = useState(false);
  const [gameState] = useState([[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]]);

  const startGame = () => {
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
