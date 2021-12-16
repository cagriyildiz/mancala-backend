import React from "react";

const User = (props) => {

  const activeClass = props.isActive ? "active" : "";

  return (
    props.playing ? <p className={["App-user", activeClass].join(" ")}>User {props.user + 1}</p> : null
  );
};

export default User;