import React from "react";
import {WinnerEnum} from "./constant";

const Winner = (props) => {

  const getWinnerText = () => {
    switch (props.winner) {
      case WinnerEnum.FIRST:
      case WinnerEnum.SECOND:
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