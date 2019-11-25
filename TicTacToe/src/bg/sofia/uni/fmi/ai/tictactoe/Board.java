package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final int BOARD_SIZE = 3;

    private BoardOption[][] board = new BoardOption[BOARD_SIZE][BOARD_SIZE];

    public int getEmptySpaces() {
        return emptySpaces;
    }

    private int emptySpaces = BOARD_SIZE * BOARD_SIZE;

    public BoardOption[][] getBoard() {
        return board;
    }

    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = BoardOption.EMPTY;
            }
        }
    }

    public Board(Board b) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = b.getBoard()[i][j];
            }
        }

        emptySpaces = b.getEmptySpaces();
    }

    public void makeMove(int x, int y, BoardOption player) {
        if (board[x][y] != BoardOption.EMPTY) {
            throw new IllegalArgumentException("Attempt to make an impossible move.");
        }

        board[x][y] = player;
        emptySpaces--;
    }

    public List<Integer[]> getPossibleMoves() {
        List<Integer[]> possibleMoves = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BoardOption.EMPTY) {
                    Integer[] pair = new Integer[2];
                    pair[0] = i;
                    pair[1] = j;
                    possibleMoves.add(pair);
                }
            }
        }

        return possibleMoves;
    }

    /**
     * MIN_INT -> no winner yet
     * 0 -> tie
     * >0 -> Human win
     * <0 -> PC win
     */
    public int getWinner() {
        BoardOption w = getHorizontalWin();
        if (w != BoardOption.EMPTY) {
            return getWinningValue(w);
        }

        w = getVerticalWin();
        if (w != BoardOption.EMPTY) {
            return getWinningValue(w);
        }

        w = getDiagonalWin();
        if (w != BoardOption.EMPTY) {
            return getWinningValue(w);
        }

        if (getPossibleMoves().isEmpty()) {
            return 0;
        }

        return Integer.MIN_VALUE;
    }

    private BoardOption getDiagonalWin() {
        if (board[0][0] != BoardOption.EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }

        if (board[0][2] != BoardOption.EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }

        return BoardOption.EMPTY;
    }

    private BoardOption getVerticalWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != BoardOption.EMPTY) {
                if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                    return board[i][0];
                }
            }
        }

        return BoardOption.EMPTY;
    }

    private BoardOption getHorizontalWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] != BoardOption.EMPTY) {
                if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                    return board[0][i];
                }
            }
        }

        return BoardOption.EMPTY;
    }

    /**
     * Assuming the board has a winner, this function returns a bigger(respectively smaller) number the more free
     * spaces there are on the board.
     *
     * In other words, if we have a win with less free spaces, that means it is faster and more valuable.
     * @param w The player.
     * @return the value of the win
     */
    private int getWinningValue(BoardOption w) {
        int value = w.getValue();
        if (value < 0) {
            // we have PC win
            return value - emptySpaces;
        } else {
            return value + emptySpaces;
        }
    }
}
