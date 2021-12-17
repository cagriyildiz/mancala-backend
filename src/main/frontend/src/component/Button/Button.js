import React from "react";

const Button = (props) => {
  return (
    <p className="App-start" onClick={props.clicked}>
      {props.text}
    </p>
  );

};

export default Button;