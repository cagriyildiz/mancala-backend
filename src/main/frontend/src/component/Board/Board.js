import React from "react";
import Pits from "./Pits/Pits";
import BigPit from "./Pits/Pit/BigPit";

const Board = (props) => {

  const playerOneState = props.pits[0];
  const playerTwoState = props.pits[1];

  const playerOnePits = playerOneState.slice(0, -1);
  const playerTwoPits = playerTwoState.slice(0, -1);

  return (
    props.show ?
      <div className="board">
        <BigPit store={playerTwoState.at(-1)} player={1}/>

        <div className="rows">
          <Pits pits={playerTwoPits} player={1} moveStones={props.moveStones}/>
          <Pits pits={playerOnePits} player={0} moveStones={props.moveStones}/>
        </div>

        <BigPit store={playerOneState.at(-1)} player={0}/>
      </div> : null
  );
};

export default Board;