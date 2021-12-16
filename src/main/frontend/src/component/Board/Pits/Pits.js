import React from "react";
import Pit from "./Pit/Pit";

const Pits = (props) => {
  return (
    <div className={["row", "player-" + props.player].join(' ')}>
      {
        props.pits.map((stones, idx) =>
          <Pit key={`${props.player}-${idx}`}
               index={idx}
               stones={stones}
               clicked={props.moveStones}/>)
      }
    </div>
  );
};

export default Pits;