import React from "react";

const BigPit = (props) => {
  return (
    <div className={["big-pit", `player-${props.player}`].join(" ")}>
      <p>{props.store > 0 ? props.store : null}</p>
    </div>
  );
};

export default BigPit;