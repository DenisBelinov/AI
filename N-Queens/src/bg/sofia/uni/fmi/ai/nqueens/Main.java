package bg.sofia.uni.fmi.ai.nqueens;

import javax.management.MXBean;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // get input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter row count: ");
        int n = scan.nextInt();

        if (n > Board.MAX_N) {
            System.out.println("The size of the board is larger than 10k. Aborting...");
            return;
        }

        Board board = new Board();

        long nano_startTime = System.nanoTime();
        board.calculate(n);
        long nano_endTime = System.nanoTime();
        long elapsedTime = nano_endTime - nano_startTime;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        System.out.println("Time taken in seconds: "
                + seconds);

    }
}
