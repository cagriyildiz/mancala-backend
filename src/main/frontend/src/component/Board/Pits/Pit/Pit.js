import React from "react";

const Pit = (props) => {
  return (
    <div className="pit">
      <p>{props.stones > 0 ? props.stones : null}</p>
    </div>
  );
};

export default Pit;