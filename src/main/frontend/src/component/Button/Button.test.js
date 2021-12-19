import {render, screen} from '@testing-library/react';
import Button from "./Button";

test('renders play mancala text', () => {
  render(<Button text={"Play Mancala"}/>);
  const button = screen.getByText(/Play Mancala/i);
  expect(button).toBeInTheDocument();
});