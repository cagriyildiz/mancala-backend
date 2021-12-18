import React from "react";
import Pits from "./Pits/Pits";
import BigPit from "./Pits/Pit/BigPit";
import {UserEnum} from "../User/constant";

const Board = (props) => {

  const reverseArray = arr => arr.slice(0).reverse();
  const stonesInBigPit = player => props.pits[player].at(-1);

  return (
    props.show ?
      <div className="board">
        <BigPit store={stonesInBigPit(UserEnum.SECOND)} player={UserEnum.SECOND}/>

        <div className="rows">
          {
            reverseArray(props.pits)
              .map((state, idx) =>
                <Pits key={idx}
                      pits={state.slice(0, -1)}
                      player={props.pits.length - 1 - idx}
                      activePlayer={props.activePlayer}
                      moveStones={props.moveStones}/>)
          }
        </div>

        <BigPit store={stonesInBigPit(UserEnum.FIRST)} player={UserEnum.FIRST}/>
      </div> : null
  );
};

export default Board;