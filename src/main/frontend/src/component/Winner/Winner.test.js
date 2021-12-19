import {render, screen} from '@testing-library/react';
import {WinnerEnum} from "./constant";
import Winner from "./Winner";

test('renders winner when game is finished', () => {
  render(<Winner finished={true} winner={WinnerEnum.FIRST}/>);
  const winner = screen.getByText(/User 1 Wins/i);
  expect(winner).toBeInTheDocument();
  expect(winner).toHaveClass("winner");
  expect(winner).toHaveAttribute("data-player", `${WinnerEnum.FIRST}`);
});

test('renders tie game when game is finished', () => {
  render(<Winner finished={true} winner={WinnerEnum.TIE}/>);
  const winner = screen.getByText(/Tie Game/i);
  expect(winner).toBeInTheDocument();
  expect(winner).toHaveClass("winner");
  expect(winner).toHaveAttribute("data-player", `${WinnerEnum.TIE}`);
});

test('renders nothing if game is not finished yet', () => {
  render(<Winner finished={false} winner={WinnerEnum.FIRST}/>);
  const winner = screen.queryByText(/User .* Wins/i);
  expect(winner).not.toBeInTheDocument();
});