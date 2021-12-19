import {fireEvent, screen, render} from '@testing-library/react';
import Pit from "./Pit";

test('renders stone count in the pit', () => {
  const handleClick = jest.fn();
  const {container} = render(<Pit stones={6} isActive={true} index={0} clicked={handleClick}/>);
  const pit = container.getElementsByClassName("pit");
  expect(pit.length).toBe(1);
  expect(pit.item(0)).toHaveAttribute("data-index", "0");
  expect(pit.item(0)).not.toHaveAttribute("data-disabled");
});

test('do not show stone count if the pit is empty', () => {
  const handleClick = jest.fn();
  const {container} = render(<Pit stones={0} isActive={true} index={0} clicked={handleClick}/>);
  const pit = container.getElementsByClassName("pit");
  expect(pit.length).toBe(1);
  expect(pit.item(0)).toBeEmptyDOMElement();
});

test('renders disabled pit', () => {
  const handleClick = jest.fn();
  const {container} = render(<Pit stones={6} isActive={false} index={1} clicked={handleClick}/>);
  const pit = container.getElementsByClassName("pit");
  expect(pit.length).toBe(1);
  expect(pit.item(0)).toHaveAttribute("data-index", "1");
  expect(pit.item(0)).toHaveAttribute("data-disabled");
});

test('renders disabled pit', () => {
  const handleClick = jest.fn();
  const {container} = render(<Pit stones={6} isActive={false} index={0} clicked={handleClick}/>);
  const pit = container.getElementsByClassName("pit");
  expect(pit.length).toBe(1);
  expect(pit.item(0)).toHaveAttribute("data-index", "0");
  expect(pit.item(0)).toHaveAttribute("data-disabled");
});

test('calls clicked prop when the pit is clicked', () => {
  const handleClick = jest.fn();
  render(<Pit stones={6} isActive={true} index={0} clicked={handleClick}/>);
  const pit = screen.getByText(/6/i);
  fireEvent.click(pit);
  expect(handleClick).toHaveBeenCalledTimes(1);
});