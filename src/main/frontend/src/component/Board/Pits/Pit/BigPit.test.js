import {render} from '@testing-library/react';
import BigPit from "./BigPit";
import {UserEnum} from "../../../User/constant";

test('renders stone count in the big pit', () => {
  const {container} = render(<BigPit store={10} player={UserEnum.FIRST}/>);
  const bigPit = container.getElementsByClassName("big-pit");
  expect(bigPit.length).toBe(1);
  expect(bigPit.item(0).classList.contains("player-0")).toBe(true);
  expect(bigPit.item(0).textContent).toBe("10");
});

test('do not show stone count if the big pit is empty', () => {
  const {container} = render(<BigPit store={0} player={UserEnum.SECOND}/>);
  const bigPit = container.getElementsByClassName("big-pit");
  expect(bigPit.length).toBe(1);
  expect(bigPit.item(0).classList.contains("player-1")).toBe(true);
  expect(bigPit.item(0)).toBeEmptyDOMElement();
});

test('renders wrapper div all the time', () => {
  const {container} = render(<BigPit/>);
  const bigPit = container.getElementsByClassName("big-pit");
  expect(bigPit.item(0)).toBeInTheDocument();
});