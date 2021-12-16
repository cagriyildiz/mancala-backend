import './App.css';
import Board from "./component/Board/Board";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p className="App-start">
          Play Mancala
        </p>
        <Board pits={[[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]]}/>
      </header>
    </div>
  );
}

export default App;
