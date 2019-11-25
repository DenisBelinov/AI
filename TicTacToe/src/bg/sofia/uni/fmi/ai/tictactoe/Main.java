package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
		// create the initial board
		Board currentBoard = new Board();
		MiniMaxAlgorithm bot = new MiniMaxAlgorithm();


		System.out.println("Welcome to TIC-TAC-TOE.");
		System.out.println("Enter 1 if you want to go 1st. Enter 2 if you want to go 2nd:");

		Scanner in = new Scanner(System.in);
		int choice = 0;

		// get player's choice
		while (true) {
			choice = in.nextInt();
			if (choice == 1 || choice ==2) {
				break;
			}
			System.out.println("You must choose between 1 and 2:");
		}

		// play one PC turn, if user choose to go second
		if (choice == 2) {
			currentBoard = bot.playMove(currentBoard);
			printBoard(currentBoard);
		}

		// game flow
		while (currentBoard.getWinner() == Integer.MIN_VALUE) {
			// player turn
			System.out.println("Enter your move(two numbers in [0, 2]:");
			int x = in.nextInt();
			int y = in.nextInt();

			try {
				currentBoard.makeMove(x, y, BoardOption.HUMAN_PLAYER);
			} catch (IllegalArgumentException e) {
				System.out.println("You cannot make that move. Try again:");
				continue;
			}

			printBoard(currentBoard);
			int winner = currentBoard.getWinner();
			if (winner != Integer.MIN_VALUE) {
				break;
			}

			// PC turn
			System.out.println("Bot is making a move:");
			currentBoard = bot.playMove(currentBoard);
			printBoard(currentBoard);
		}

		int winner = currentBoard.getWinner();

		if (winner == 0) {
			System.out.println("We have a draw!");
		} else if (winner < 0) {
			System.out.println("Bot wins!");
		} else {
			System.out.println("Congrats! You win!");
		}
	}

	private static void printBoard(Board b) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(b.getBoard()[i][j].getValue() + " ");
			}
			System.out.println();
		}
	}
}
