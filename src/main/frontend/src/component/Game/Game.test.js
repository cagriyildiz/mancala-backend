import {act, fireEvent, render, screen} from '@testing-library/react';
import axios from "axios";
import Game from "./Game";

const postResponseBody = Object.freeze({
  id: "10852890-a89d-4ee7-bc5b-853f60a7fbec",
  version: 0,
  createdDate: "2021-12-19T08:22:32.559651Z",
  lastModifiedDate: "2021-12-19T08:22:32.559686Z",
  firstPlayerId: 123,
  secondPlayerId: 456,
  initialStoneCount: 6,
  activePlayer: 0,
  winner: -1,
  finished: false,
  board: {
    id: 1,
    pits: [
      [6, 6, 6, 6, 6, 6, 0],
      [6, 6, 6, 6, 6, 6, 0]
    ]
  }
});

const getResponseBody = Object.freeze({
  id: "10852890-a89d-4ee7-bc5b-853f60a7fbec",
  version: 0,
  createdDate: "2021-12-19T08:22:32.559651Z",
  lastModifiedDate: "2021-12-19T08:22:32.559686Z",
  firstPlayerId: 123,
  secondPlayerId: 456,
  initialStoneCount: 6,
  activePlayer: 0,
  winner: -1,
  finished: false,
  board: {
    id: 1,
    pits: [
      [0, 7, 7, 7, 7, 7, 1],
      [6, 6, 6, 6, 6, 6, 0]
    ]
  }
});

const getResponseBodyGameFinished = Object.freeze({
  id: "10852890-a89d-4ee7-bc5b-853f60a7fbec",
  version: 0,
  createdDate: "2021-12-19T08:22:32.559651Z",
  lastModifiedDate: "2021-12-19T08:22:32.559686Z",
  firstPlayerId: 123,
  secondPlayerId: 456,
  initialStoneCount: 6,
  activePlayer: 1,
  winner: 0,
  finished: true,
  board: {
    id: 1,
    pits: [
      [0, 0, 0, 0, 0, 0, 41],
      [0, 0, 0, 0, 0, 0, 31]
    ]
  }
});

jest.mock("axios");

test('renders play mancala button on the first page', () => {
  render(<Game/>);
  const playButton = screen.getByText(/Play Mancala/i);
  expect(playButton).toBeInTheDocument();
});

test('renders board on play button click', async () => {
  const container = await startGame();
  const users = screen.getAllByText(/User .*/i);
  const board = container.getElementsByClassName("board");
  expect(users.length).toBe(2);
  expect(board.length).toBe(1);
});

test('renders new state of the game on the board', async () => {
  const container = await startGame();

  axios.get.mockResolvedValue({data: getResponseBody});
  const board = container.getElementsByClassName("board")[0];
  const pits = board.getElementsByClassName("row")[1];
  const pit = pits.getElementsByClassName("pit")[0];

  await act(async () => {
    fireEvent.click(pit);
  });

  const bigPit = await container.getElementsByClassName("big-pit")[1];
  expect(bigPit.textContent).toBe("1");
});

test('renders finish state of the game on the board', async () => {
  const container = await startGame();

  axios.get.mockResolvedValue({data: getResponseBodyGameFinished});
  const board = container.getElementsByClassName("board")[0];
  const pits = board.getElementsByClassName("row")[1];
  const pit = pits.getElementsByClassName("pit")[5];

  await act(async () => {
    fireEvent.click(pit);
  });

  const bigPit = await container.getElementsByClassName("big-pit")[1];
  expect(bigPit.textContent).toBe("41");
});

const startGame = async () => {
  axios.post.mockResolvedValue({data: postResponseBody});
  const {container} = render(<Game/>);
  const playButton = screen.getByText(/Play Mancala/i);

  await act(async () => {
    fireEvent.click(playButton);
  });

  await screen.findAllByText(/User .*/i);
  return container;
}
