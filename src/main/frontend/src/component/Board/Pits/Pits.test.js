import {render} from '@testing-library/react';
import Pits from "./Pits";
import {UserEnum} from "../../User/constant";

test('renders pits', () => {
  const handleClick = jest.fn();
  const pits = [6, 6, 6, 6, 6, 6];
  const {container} = render(
    <Pits player={UserEnum.FIRST} activePlayer={UserEnum.FIRST} pits={pits} moveStones={handleClick}/>
  );
  const pit = container.getElementsByClassName("row");
  expect(pit.length).toBe(1);
  expect(pit.item(0).children.length).toBe(6);
});

test('renders wrapper div all the time', () => {
  const handleClick = jest.fn();
  const {container} = render(
    <Pits player={UserEnum.FIRST} activePlayer={UserEnum.FIRST} pits={[]} moveStones={handleClick}/>
  );
  const pits = container.getElementsByClassName("row");
  expect(pits.length).toBe(1);
  expect(pits.item(0)).toHaveClass("player-0");
  expect(pits.item(0)).toBeInTheDocument();
});