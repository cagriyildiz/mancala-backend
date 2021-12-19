import {render, screen} from '@testing-library/react';
import User from "./User";
import {UserEnum} from "./constant";

test('renders active user', () => {
  render(<User playing={true} isActive={true} user={UserEnum.FIRST}/>);
  const user = screen.getByText(/User 1/i);
  expect(user).toBeInTheDocument();
  expect(user).toHaveClass("App-user", "active");
});

test('renders not active user', () => {
  render(<User playing={true} isActive={false} user={UserEnum.SECOND}/>);
  const user = screen.getByText(/User 2/i);
  expect(user).toBeInTheDocument();
  expect(user).toHaveClass("App-user");
  expect(user).not.toHaveClass("active");
});

test('renders nothing if the game is not started yet', () => {
  render(<User playing={false} isActive={true} user={UserEnum.SECOND}/>);
  const user = screen.queryByText(/User 2/i);
  expect(user).not.toBeInTheDocument();
});