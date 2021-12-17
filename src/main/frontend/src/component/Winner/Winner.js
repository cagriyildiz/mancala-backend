import React from "react";

const Winner = (props) => {

  const getWinnerText = () => {
    switch (props.winner) {
      case 0:
      case 1:
        return `User ${props.winner + 1} Wins`;
      default:
        return "Tie Game";
    }
  };

  return (
    props.finished ? <p className="winner" data-player={props.winner}>{getWinnerText()}</p> : null
  );

};

export default Winner;