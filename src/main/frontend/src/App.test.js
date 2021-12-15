import { render, screen } from '@testing-library/react';
import App from './App';

test('renders play mancala text', () => {
  render(<App />);
  const linkElement = screen.getByText(/play mancala/i);
  expect(linkElement).toBeInTheDocument();
});
