import {render} from '@testing-library/react';
import Board from "./Board";
import {UserEnum} from "../User/constant";

test('renders nothing if it should be hidden', () => {
  const handleClick = jest.fn();
  const pits = [[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]];
  const {container} = render(<Board show={false} activePlayer={UserEnum.FIRST} pits={pits} moveStones={handleClick}/>);
  const board = container.getElementsByClassName("board");
  expect(board.length).toBe(0);
});

test('renders board correctly', () => {
  const handleClick = jest.fn();
  const pits = [[6, 6, 6, 6, 6, 6, 0], [6, 6, 6, 6, 6, 6, 0]];
  const {container} = render(<Board show={true} activePlayer={UserEnum.FIRST} pits={pits} moveStones={handleClick}/>);
  const board = container.getElementsByClassName("board");
  expect(board.length).toBe(1);

  const children = board.item(0).children;
  expect(children.length).toBe(3);
  expect(children.item(0)).toHaveClass("big-pit", "player-1");
  expect(children.item(1)).toHaveClass("rows");
  expect(children.item(1).children.length).toBe(2);
  expect(children.item(2)).toHaveClass("big-pit", "player-0");
});