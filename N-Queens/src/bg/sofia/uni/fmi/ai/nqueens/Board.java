package bg.sofia.uni.fmi.ai.nqueens;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    public static final int MAX_N = 20000;

    private static int retryCount = 0;

    private Random randomGenerator = new Random();
    private int[] queenPossitions = new int[MAX_N];
    private int n = 0;
    ArrayList<Integer> candidates = new ArrayList<Integer>();

    private int[] countQueensInRow = new int[MAX_N];
    private int[] countQueensInMainDiag = new int[2 * MAX_N - 1];
    private int[] countQueensInSecDiag = new int[2 * MAX_N - 1];

    public void calculate(int number) {
        n = number;

        // get random possitions
        for (int i = 0; i < n; i++) {
            queenPossitions[i] = i;
        }

        for (int i = 0; i < n; i++) {
            int j = randomGenerator.nextInt(n);
            int rowToSwap = queenPossitions[i];
            queenPossitions[i] = queenPossitions[j];
            queenPossitions[j] = rowToSwap;
        }

        fillConflictArrays();

        // minConflicts
        int MAX_ITER = MAX_N * 3;

        for (int i = 0; i < MAX_ITER; i++) {
            i += 1;

            int max_col = getColWithMaxConf();
            // check if we have no conflicting queens
            if (max_col == -1) {
                printPossitions();
                return;
            }

            // move queen to best possition
            int min_row = getRowWithMinConf(max_col);

            // fix the cache
            countQueensInRow[queenPossitions[max_col]] -= 1;
            countQueensInMainDiag[MAX_N - 1 + max_col - queenPossitions[max_col]] -= 1;
            countQueensInSecDiag[max_col + queenPossitions[max_col]] -= 1;

            queenPossitions[max_col] = min_row;

            countQueensInRow[queenPossitions[max_col]] += 1;
            countQueensInMainDiag[MAX_N - 1 + max_col - queenPossitions[max_col]] += 1;
            countQueensInSecDiag[max_col + queenPossitions[max_col]] += 1;
        }

        if (retryCount < 3) {
            retryCount += 1;
            calculate(n);
        } else {
            System.out.println("Couldn't find solution");
        }
    }

    private void fillConflictArrays() {
        for (int i = 0; i < n; i++) {
            countQueensInRow[queenPossitions[i]] += 1;
            countQueensInMainDiag[MAX_N - 1 + i - queenPossitions[i]] += 1;
            countQueensInSecDiag[i + queenPossitions[i]] += 1;
        }
    }

    private void printPossitions() {
        if (n <= 50) {
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (queenPossitions[col] == row) {
                        System.out.print("* ");
                    } else {
                        System.out.print("_ ");
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("Found solution, N is too large to print the board.");
        }
    }

    private int getRowWithMinConf(int col) {
        int minConflicts = n;
        candidates.clear();

        for (int r = 0; r < n; r++) {
            if (r == queenPossitions[col]) continue;

            int conflicts = getAttackingQueensCount(r, col);

            if (conflicts == minConflicts) {
                candidates.add(r);
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                candidates.clear();
                candidates.add(r);
            }
        }
        if (!candidates.isEmpty())
            return candidates.get(randomGenerator.nextInt(candidates.size()));

        return queenPossitions[col];
    }

    private int getAttackingQueensCount(int row, int col) {
        // NOTE: This will include the queen on [row, col] three times if there is one
        return countQueensInRow[row] +
                countQueensInMainDiag[MAX_N - 1 + col - row] +
                countQueensInSecDiag[col + row];
    }

    private int getColWithMaxConf() {
        candidates.clear();

        while (true) {
            int maxConflicts = 0;

            for (int i = 0; i < n; i++) {
                int conflicts = getAttackingQueensCount(queenPossitions[i], i) - 3;

                if (conflicts == maxConflicts) {
                    candidates.add(i);
                } else if (conflicts > maxConflicts) {
                    maxConflicts = conflicts;
                    candidates.clear();
                    candidates.add(i);
                }
            }

            if (maxConflicts == 0) {
                // we have no conflicts
                return -1;
            }

            // return a random queen from the candidates
            return candidates.get(randomGenerator.nextInt(candidates.size()));
        }
    }
}
