import React from "react";

const Pit = (props) => {

  const moveStones = (event) => {
    const stoneCount = event.target.innerText;
    let disabled = event.target.dataset.disabled;
    if (!disabled && stoneCount) {
      props.clicked(event.target.dataset.index);
    }
  };

  return (
    <div className="pit"
         data-index={props.index}
         data-disabled={props.isActive ? undefined : true}
         onClick={moveStones}>
      {props.stones > 0 ? props.stones : null}
    </div>
  );
};

export default Pit;