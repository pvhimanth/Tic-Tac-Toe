package com.tap.game;

import java.util.Random;
import java.util.Scanner;

class TicTacToe {

	static char[][] board;

	public TicTacToe() {
		board = new char[3][3];
		initBoard();
	}

	void initBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = ' ';
			}
		}
	}

	void dispBoard() {
		System.out.println("-------------");
		for (int i = 0; i < board.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");
		}
	}

	static boolean placeMark(int row, int col, char mark) {
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			if (board[row][col] == ' ') {
				board[row][col] = mark;
				return true;
			} else {
				System.out.println("Position already occupied. Try again.");
			}
		} else {
			System.out.println("Invalid position. Enter values between 0 and 2.");
		}
		return false;
	}

	static boolean checkColWin() {
		for (int i = 0; i < board.length; i++) {
			if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				return true;
			}
		}
		return false;
	}

	static boolean checkRowWin() {
		for (int i = 0; i < board.length; i++) {
			if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				return true;
			}
		}
		return false;
	}

	static boolean checkDiagWin() {
		return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
				|| (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
	}

	static boolean checkDraw() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
}

abstract class Player {
	String name;
	char mark;

	abstract void makeMove();

	boolean isValidMove(int row, int col) {
		return row >= 0 && row < 3 && col >= 0 && col < 3 && TicTacToe.board[row][col] == ' ';
	}
}

class HumanPlayer extends Player {
	private Scanner scan;

	public HumanPlayer(String name, char mark, Scanner scan) {
		this.name = name;
		this.mark = mark;
		this.scan = scan;
	}

	@Override
	void makeMove() {
		int row, col;
		System.out.println(name + ", enter the row and column (0-2):");

		while (true) {
			row = scan.nextInt();
			col = scan.nextInt();
			if (TicTacToe.placeMark(row, col, mark)) {
				break;
			}
		}
	}
}

class AIPlayer extends Player {
	private Random rand;

	public AIPlayer(String name, char mark) {
		this.name = name;
		this.mark = mark;
		this.rand = new Random();
	}

	@Override
	void makeMove() {
		int row, col;
		do {
			row = rand.nextInt(3);
			col = rand.nextInt(3);
		} while (!TicTacToe.placeMark(row, col, mark));

		System.out.println(name + " placed at (" + row + ", " + col + ")");
	}
}

public class LaunchGame {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		TicTacToe t = new TicTacToe();
		HumanPlayer p1 = new HumanPlayer("Alex", 'X', scan);
		AIPlayer p2 = new AIPlayer("AI", 'O');

		Player cp = p1;

		while (true) {
			System.out.println(cp.name + "'s turn");
			cp.makeMove();
			t.dispBoard();

			if (TicTacToe.checkColWin() || TicTacToe.checkRowWin() || TicTacToe.checkDiagWin()) {
				System.out.println(cp.name + " has won!");
				break;
			}

			if (TicTacToe.checkDraw()) {
				System.out.println("Game is a Draw!");
				break;
			}

			cp = (cp == p1) ? p2 : p1;
		}

		scan.close();
	}
}
