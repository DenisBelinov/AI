package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class MiniMaxAlgorithm {
    public Board playMove(Board currentBoard) {
        List<Board> possibleNextBoards = new ArrayList<>();
        for (Integer[] move : currentBoard.getPossibleMoves()) {
            Board b = new Board(currentBoard);
            b.makeMove(move[0], move[1], BoardOption.PC_PLAYER);

            possibleNextBoards.add(b);
        }

        Board bestMove = null;
        int bestResult = Integer.MAX_VALUE;

        for (Board b : possibleNextBoards) {
            // start the alg
            int result = maxValue(b, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (result < bestResult) {
                bestMove = b;
                bestResult = result;
            }
        }

        return bestMove;
    }

    private int maxValue (Board currentBoard, int alpha, int beta) {
        int boardResult = currentBoard.getWinner();
        if (boardResult != Integer.MIN_VALUE) {
            return boardResult;
        }

        List<Board> possibleNextBoards = new ArrayList<>();
        for (Integer[] move : currentBoard.getPossibleMoves()) {
            Board b = new Board(currentBoard);
            b.makeMove(move[0], move[1], BoardOption.HUMAN_PLAYER);

            possibleNextBoards.add(b);
        }

        // With pruning
        int bestResult = Integer.MIN_VALUE;
        for (Board move : possibleNextBoards) {
            bestResult = Integer.max(bestResult, minValue(move, alpha, beta));

            if (bestResult >= beta) {
                return bestResult;
            }

            alpha = Integer.max(alpha, bestResult);
        }

        return bestResult;

        // Without pruning:
        // return possibleNextBoards.stream().map(b -> minValue(b)).max(Comparator.naturalOrder()).get();
    }

    private int minValue (Board currentBoard, int alpha, int beta) {
        int boardResult = currentBoard.getWinner();
        if (boardResult != Integer.MIN_VALUE) {
            return boardResult;
        }

        List<Board> possibleNextBoards = new ArrayList<>();
        for (Integer[] move : currentBoard.getPossibleMoves()) {
            Board b = new Board(currentBoard);
            b.makeMove(move[0], move[1], BoardOption.PC_PLAYER);

            possibleNextBoards.add(b);
        }

        // With pruning:
        int bestResult = Integer.MAX_VALUE;
        for (Board move : possibleNextBoards) {
            bestResult = Integer.min(bestResult, maxValue(move, alpha, beta));

            if (bestResult <= alpha) {
                return bestResult;
            }

            beta = Integer.min(beta, bestResult);
        }

        return bestResult;

        // w/o pruning
        //return possibleNextBoards.stream().map(b -> maxValue(b)).min(Comparator.naturalOrder()).get();
    }
}
