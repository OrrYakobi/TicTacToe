package ticTacToe;

import java.util.*;

public class TicTacToe{
  
	private final static int MAX_MOVES_TO_WIN = 5;
	private final static int MAX_VALID_MOVES = 9;
	private final static int NUM_POSITIONS = 9;
	private final static int WINNING_SCORE = 3;
	
	private static char [][] board = new char [5][5];
	private static HashMap<Integer, int[]> positions =  new HashMap<Integer, int[] >();
	private static HashMap<Integer, Integer> scores = new HashMap<Integer, Integer >();
	private static HashMap<Integer, int[]> combos = new HashMap<Integer, int[] >();
	private static int turn = 0;
	private static Scanner scanner = new Scanner ( System.in );
	private static int numValidMoves = 0;
	private static int lastPosition = 0;
	
	
	public static void main(String [] args){
		initBoard();
		initScores();
		initPositions();
		initCombos();
		System.out.println("Welcome to Tic-Tac-Toe!");
		System.out.println();
		printDirections();
		while(numValidMoves < MAX_VALID_MOVES && !gameWon() ){
			System.out.println();
			System.out.print("Player " + (turn+1) + "'s turn. Enter position: ");
			String input = scanner.nextLine();
			if (!validInput(input)){ continue; }
			int position = parseInput(input);
			if (!setPosition(position)){ continue; };
			System.out.println();
			printBoard();
			changeTurn();
			numValidMoves++;
			lastPosition = position;
			updateScores();
			System.out.println();
			if (numValidMoves == MAX_VALID_MOVES || gameWon()){
				promptAndReset();
			}
		}
		System.out.println("Goodbye!");
		
	}
	
	/*
	 * Prompt the user to either stop playing or start a new game
	 * once the current game has ended
	 */
	private static void promptAndReset(){
		changeTurn();
		if(gameWon()){
			System.out.print("Player " + (turn+1) + " won!\n");
		}
		else {
			System.out.print("It's a draw!\n");
		}
		
		System.out.print("Play again? (y/n): ");
		String input = scanner.nextLine();
		input = input.toLowerCase();
		while( !input.matches("y") && !input.matches("n")){
			System.out.println("You must enter either y or n");
			System.out.print("Play again? (y/n): ");
			input = scanner.nextLine();
			input = input.toLowerCase();
		}
		if (input.matches("y")){
			turn = 0;
			numValidMoves = 0;
			lastPosition = 0;
			
			initBoard();
			initScores();
			initPositions();
			printDirections();
		}
		
	}
	
	/*
	 * Changes the players' turn
	 */
	private static void changeTurn(){
		if(turn == 0){
			turn = 1;
		}
		else {
			turn = 0;
		}
	}
	
	/*
	 * Validates that the user has inputted a valid position
	 */
	private static boolean validInput(String input){
		char [] charArr = input.toCharArray();
		char pos = charArr[0];
		int value = Character.getNumericValue(pos);
		
		if( charArr.length != 1 || !Character.isDigit(pos) || (value < 1 || value > 9)){
			System.out.println("Your position input was not a number 1 through 9");
			return false;
		}
		return true;
		
	}
	
	/*
	 * Return the numeric value of the user's input
	 */
	private static int parseInput(String input){
		char [] charArr = input.toCharArray();
		char pos = charArr[0];
		return Character.getNumericValue(pos);
	}
	
	/*
	 * Initialize all positions to their corresponding 
	 * in the board.
	 */
	private static void initPositions(){
		int count = 0;
		for(int i = 0 ; i < NUM_POSITIONS  ; i=i+3 ){
			positions.put(i+1, new int[]{i-count,0});
			positions.put(i+2, new int[]{i-count,2});
			positions.put(i+3, new int[]{i-count,4});
			count++;
		}
		
	}
	
	/*
	 * Increments or decrement score associated to a certain
	 * combination.
	 */
	private static void updateScores(){
		int increment = 1;
		if (turn == 0){
			increment = -1;
		}
		int [] combinations = combos.get(lastPosition);
		for(int combination:combinations){
			int value = scores.get(combination) + increment;
			scores.put(combination,value);
		}
		
	}
	
	/*
	 * Maps a position to its associated combination.
	 * The column combinations are labeled 1, 2, and 3.
	 * The row combinations are labeled  4, 5, and 6.
	 * The diagonal containing positions 1, 5, and 9 is labeled 7.
	 * The diagonal containing positions 3, 5, and 7 is labeled 8.
	 * 
	 * 			  |	1 2 3 | 7
	 * 			--|-----------
	 *  		4 |	1|2|3 | 
	 *  		  |	----- | 
	 *  		5 |	4|5|6 | 
	 *  		  |	----- | 
	 * 	  	6 |	7|8|9 |
	 *			--------------
	 *			  |		    | 8
	 */
	public static void initCombos(){
		combos.put(1, new int[]{1,4,7});
		combos.put(2, new int[]{2,4});
		combos.put(3, new int[]{3,4,8});
		
		combos.put(4, new int[]{1,5});
		combos.put(5, new int[]{2,5,7,8});
		combos.put(6, new int[]{3,5});
		
		combos.put(7, new int[]{1,6,8});
		combos.put(8, new int[]{2,6});
		combos.put(9, new int[]{3,6,7});
	}
	
	/*
	 * Initialize all scores to 0
	 */
	private static void initScores(){
		for(int i = 1 ; i < NUM_POSITIONS  ; i++ ){
			scores.put(i, 0);
		}
	}
	
	/*
	 * Check that position isn't taken and set it
	 */
	private static boolean setPosition(int position){		
		if (getPosition(position) != ' '){
			System.out.println("Position already taken!");
			return false;
		}
		
		int i = positions.get(position)[0];
		int j = positions.get(position)[1];
		
		if(turn==0){
			board[i][j] = 'o';
		} 
		else {
			board[i][j] = 'x';
		}

		return true;
	}
	
	/*
	 * Get value at position
	 */
	private static char getPosition(int position){
		int i = positions.get(position)[0];
		int j = positions.get(position)[1];
		return board[i][j];
	}
	
	/*
	 * Create a blank board
	 */
 	private static void initBoard(){
		for(int i = 0 ; i < board.length ; i = i+2){
			for(int j = 0 ; j < board.length ; j++){
				if( j%2 == 0){
					board[i][j] = ' ';
				}
				else {
					board[i][j] = '|';
				}
			}
		}
		for(int i = 1 ; i < board.length ; i = i+2){
			for(int j = 0 ; j < board.length ; j++){
				board[i][j] = '-';
			}
		}
	}
	
 	/*
 	 * Print the current board
 	 */
	private static void printBoard(){
		for(int i = 0 ; i < board.length ; i++ ){
			for(int j = 0 ; j < board.length ; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	/*
	 * Check if game is won. This can only happen after
	 * the 5th valid move and when a combinations score 
	 * is 3 or -3.
	 */
	private static boolean gameWon(){
		if(numValidMoves < MAX_MOVES_TO_WIN){ return false; }
		int [] combinations = combos.get(lastPosition);
		for(int combination: combinations){
			if ( Math.abs(scores.get(combination)) == WINNING_SCORE){
				return true;
			}
		}
		return false;
	}
	
	private static void printDirections(){
		System.out.println("The board positions are as follows:");
		System.out.println();
		System.out.println("1|2|3");
		System.out.println("-----");
		System.out.println("4|5|6");
		System.out.println("-----");
		System.out.println("7|8|9");
	}
	
}
