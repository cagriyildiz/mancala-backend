import React from "react";

const Pit = (props) => {

  const moveStones = (event) => {
    props.clicked(event.target.dataset.index);
  };

  return (
    <div className="pit"
         data-index={props.index}
         onClick={moveStones}>
      {props.stones > 0 ? props.stones : null}
    </div>
  );
};

export default Pit;