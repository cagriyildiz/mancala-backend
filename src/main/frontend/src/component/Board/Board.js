import React from "react";
import Pits from "./Pits/Pits";
import BigPit from "./Pits/Pit/BigPit";

const Board = (props) => {

  const reverseArray = arr => arr.slice(0).reverse();
  const stonesInBigPit = player => props.pits[player].at(-1);

  return (
    props.show ?
      <div className="board">
        <BigPit store={stonesInBigPit(1)} player={1}/>

        <div className="rows">
          {
            reverseArray(props.pits)
              .map((state, idx) =>
                <Pits key={idx}
                      pits={state.slice(0, -1)}
                      player={props.pits.length - 1 - idx}
                      moveStones={props.moveStones}/>)
          }
        </div>

        <BigPit store={stonesInBigPit(0)} player={0}/>
      </div> : null
  );
};

export default Board;