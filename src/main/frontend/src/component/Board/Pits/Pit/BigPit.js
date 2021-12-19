import React from "react";

const BigPit = (props) => {
  return (
    <div className={["big-pit", `player-${props.player}`].join(" ")}>
      {props.store > 0 ? props.store : null}
    </div>
  );
};

export default BigPit;