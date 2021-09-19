package rushhour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RushHour
{
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	public final static int[] DIRECTIONS = {UP, DOWN, LEFT, RIGHT};

	public final static int SIZE = 6;
	public final static int NOTFOUND = -1;
	public final static int INITIAL = 0;
	public final static int FINAL = 1;
	public final static int XPOS = 1;
	public final static int YPOS = 0;

	private char[][] board = new char[SIZE][SIZE];
	private int cost = 100000000;

	/**
	 * @param fileName
	 * Reads a board from file and creates the board
	 * @throws Exception if the file not found or the board is bad
	 */
	public RushHour(String fileName) throws Exception {

		//initialize board arrays
		for(int i = 0; i < SIZE; i++){
			board[i] = new char[SIZE];
		}

		String temp;

		File file = new File(fileName);
		Scanner reader = new Scanner(file);

		//read to string and convert to char array
		for(int i = 0; i < SIZE; i++){
			if(reader.hasNextLine()){
				temp = reader.nextLine();
				//toCharArray method found @ - https://www.geeksforgeeks.org/convert-a-string-to-character-array-in-java/
				board[i] = temp.toCharArray();
			}
		}

		reader.close();

		//check if bad board
		if(board.length != SIZE){
			throw new Exception("Bad Board - not enough rows");
		}

		int i = 1;
		for(char[] row : board){
			if(row.length != SIZE){
				throw new Exception("Bad Board not enough cols in row " + i);
			}
			i++;
		}

	}

	public RushHour(char[][] inputBoard) throws Exception{
		this.board = inputBoard;
		for(int i = 0; i < inputBoard.length; i++)
			board[i] = Arrays.copyOf(inputBoard[i], inputBoard.length);

		if(board.length != SIZE){
			throw new Exception("Bad Board - not enough rows");
		}
		int i = 1;
		for(char[] row : board){
			if(row.length != SIZE){
				throw new Exception("Bad Board not enough cols in row " + i);
			}
			i++;
		}
	}

	public char[][] getBoard(){
		return this.board;
	}

	public void printBoard(){
		System.out.println("Printing Board");

		for(int i = 0; i < SIZE; i++){
			for(int k = 0; k < SIZE; k++){
				System.out.print(board[i][k]);
			}
			System.out.println();
		}
	}

	/**
	 * @param carName
	 * locates the position of the given carName
	 */
	public int[][] locateCar(char carName){

		int[][] carPos = {{-1,-1},{-1,-1}};

		//search for specified car's first and last pos
		for(int i = 0; i < SIZE; i++){
			for(int k = 0; k < SIZE; k++){
				if(board[i][k] == carName && carPos[INITIAL][XPOS] == NOTFOUND){
					carPos[INITIAL][XPOS] = k;
					carPos[INITIAL][YPOS] = i;
//					System.out.println("carPos 1 found for car " + carName);
				}
				else if(board[i][k] == carName && carPos[INITIAL][XPOS] != NOTFOUND) {
					carPos[FINAL][XPOS] = k;
					carPos[FINAL][YPOS] = i;
//					System.out.println("carPos 2 found for car " + carName);
				}
			}
		}

//		System.out.println("Car '" + carName + "' pos is: " + carPos[0][0] + " " + carPos[0][1]);

		//if car was not found then car doesnt exist
		if(carPos[INITIAL][XPOS] == NOTFOUND){
			System.out.println("Car does not exist on board - try again");
			return null;
		}
		return carPos;
	}

	public boolean isCar(int x, int y){
		return (board[y][x] != '.');
	}

	private boolean isExtended(int x, int y, int direction){

		switch(direction){
			case UP:
				if(y == 0)
					return false;
				return (board[y--][x] == board[y][x]);
			case DOWN:
				if(y == SIZE-1)
					return false;
				return (board[y++][x] == board[y][x]);
			case LEFT:
				if(x == 0)
					return false;
				return (board[y][x--] == board[y][x]);
			case RIGHT:
				if(x == SIZE-1)
					return false;
				return (board[y][x++] == board[y][x]);
		}
		return false;
	}

	public int[][] wholeCar(int x, int y){
		int[][] carPos = {{y,x},{-1,-1}};
		int i = y, k = x;
		for(int direction : DIRECTIONS){
				while(isExtended(k, i, direction)){
					switch (direction){
						case UP:
							i++;
							break;
						case DOWN:
							i--;
							break;
						case LEFT:
							k--;
							break;
						case RIGHT:
							k++;
							break;
					}
				}
		}

		carPos[FINAL][XPOS] = k;
		carPos[FINAL][YPOS] = i;

		if(i < y || k < x){
			carPos[INITIAL][XPOS] = k;
			carPos[INITIAL][YPOS] = i;
			carPos[FINAL][XPOS] = x;
			carPos[FINAL][YPOS] = y;
		}
		return carPos;
	}

	//checks if the specified move can be made by checking if the move consists of blank spaces within the board and checks if the direction is valid
	public boolean moveOkay(int[][] carPos, int dir, int length){

		for(int i = 1; i <= length; i++){

			if(dir == UP){
				if(carPos[INITIAL][YPOS] - i < 0)
					return false;
				if(carPos[INITIAL][XPOS] != carPos[FINAL][XPOS])
					return false;
				//compare method found @ - https://www.javatpoint.com/post/java-character-compare-method
				else if(board[carPos[INITIAL][YPOS] - i][carPos[INITIAL][XPOS]] != '.' || carPos[INITIAL][YPOS] - length < 0)
					 return false;
			}

			else if(dir == DOWN){
				if(carPos[FINAL][YPOS] + i > SIZE-1)
					return false;
				if(carPos[INITIAL][XPOS] != carPos[FINAL][XPOS])
					return false;
				else if(board[carPos[FINAL][YPOS] + i][carPos[FINAL][XPOS]] != '.' || carPos[FINAL][YPOS] + length > SIZE - 1)
					return false;
			}

			else if(dir == LEFT){
				if(carPos[INITIAL][XPOS] - i < 0)
					return false;
				if(carPos[INITIAL][YPOS] != carPos[FINAL][YPOS])
					return false;
				else if(board[carPos[INITIAL][YPOS]][carPos[INITIAL][XPOS] - i] != '.' || carPos[INITIAL][XPOS] - length < 0)
					return false;
			}

			else if(dir == RIGHT){
				if(carPos[FINAL][XPOS] + i > SIZE-1)
					return false;
				if(carPos[INITIAL][YPOS] != carPos[FINAL][YPOS])
					return false;
				else if(board[carPos[FINAL][YPOS]][carPos[FINAL][XPOS] + i] != '.' || (carPos[FINAL][XPOS] + length) > SIZE - 1)
					return false;
			}
		}
		return true;
	}

	//moves the specified car up by given length
	public void moveUp(int[][] carPos, int length, char carName){
		for(int i = 1; i <= length; i++){
			board[carPos[INITIAL][YPOS] - 1][carPos[INITIAL][XPOS]] = carName;
			carPos[INITIAL][YPOS] -= 1;
			board[carPos[FINAL][YPOS]][carPos[FINAL][XPOS]] = '.';
			carPos[FINAL][YPOS] -= 1;
		}
	}
	//moves the specified car down by given length
	public void moveDown(int[][] carPos, int length, char carName){
		for(int i = 1; i <= length; i++){
			board[carPos[FINAL][YPOS] + 1][carPos[FINAL][XPOS]] = carName;
			carPos[FINAL][YPOS]++;
			board[carPos[INITIAL][YPOS]][carPos[INITIAL][XPOS]] = '.';
			carPos[INITIAL][YPOS]++;
		}
	}
	//moves the specified car left by given length
	public void moveLeft(int[][] carPos, int length, char carName){
		for(int i = 1; i <= length; i++){
			board[carPos[INITIAL][YPOS]][carPos[INITIAL][XPOS] - 1] = carName;
			carPos[INITIAL][XPOS] -= 1;
			board[carPos[FINAL][YPOS]][carPos[FINAL][XPOS]] = '.';
			carPos[FINAL][XPOS] -= 1;
		}
	}

	//moves the specified car right by given length
	public void moveRight(int[][] carPos, int length, char carName){
		for(int i = 1; i <= length; i++){
			board[carPos[FINAL][YPOS]][carPos[FINAL][XPOS] + 1] = carName;
			carPos[FINAL][XPOS]++;
			board[carPos[INITIAL][YPOS]][carPos[INITIAL][XPOS]] = '.';
			carPos[INITIAL][XPOS]++;
		}
	}

	/**
	 * Moves car with the given name for length steps in the given direction  
	 * @throws IllegalMoveException if the move is illegal
	 */
	public void makeMove(char carName, int dir, int length) throws IllegalMoveException {
		carName = Character.toUpperCase(carName);

		//stores first and last pos of car
		int[][] carPos = locateCar(carName);

		if(!moveOkay(carPos, dir, length)){
			return;
			//throw new IllegalMoveException("Illegal Move, cannot move '" + carName + "' in this way");
		}

		switch (dir) {
			case UP -> moveUp(carPos, length, carName);
			case DOWN -> moveDown(carPos, length, carName);
			case RIGHT -> moveRight(carPos, length, carName);
			case LEFT -> moveLeft(carPos, length, carName);
		}

//		printBoard();
	}

	/**
	 * @return true if and only if the board is solved,
	 * i.e., the XX car is touching the right edge of the board
	 */
	public boolean isSolved() {

		if(board[2][5] == 'X' ||
				(board[2][4] == 'X' && board[2][5] == '.') ||
				(board[2][3] == 'X' && board[2][4] == '.' && board[2][5] == '.') ||
				(board[2][2] == 'X' && board[2][3] == '.' && board[2][4] == '.' && board[2][5] == '.') ||
				(board[2][1] == 'X' && board[2][2] == '.' && board[2][3] == '.' && board[2][4] == '.' && board[2][5] == '.'))
			return true;

		return false;
	}

	public LinkedList<RushHour> getStates() throws Exception {
		LinkedList<RushHour> states = new LinkedList<RushHour>();

		LinkedList visited = new LinkedList();

		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(isCar(x,y) && !visited.contains(board[y][x])){
					visited.add(board[y][x]);

					int[][] carPos = locateCar(board[y][x]);

				//	System.out.println("Car: " + board[y][x]);

					for(int direction : DIRECTIONS){
						if(moveOkay(carPos, direction, 1)){
							char[][] temp = new char[SIZE][SIZE];

							this.makeMove(board[y][x], direction, 1);

							for(int i = 0; i < board.length; i++)
								temp[i] = Arrays.copyOf(board[i], board.length);

							switch (direction){
								case UP:
									this.makeMove(board[y-1][x], DOWN, 1);
									break;
								case DOWN:
									this.makeMove(board[y+1][x], UP, 1);
									break;
								case LEFT:
									this.makeMove(board[y][x-1], RIGHT, 1);
									break;
								case RIGHT:
									this.makeMove(board[y][x+1], LEFT, 1);
									break;
							}
							states.add(new RushHour(temp));
						}
					}
				}
			}
		}

	//	System.out.println("STATES SIZE " + states.size());
/**
		for(char[][] state : states){
			for (int i = 0; i < SIZE; i++) {
				for (int k = 0; k < SIZE; k++) {
					System.out.print(state[i][k]);
				}
				System.out.println();
			}
			System.out.println();
		}
*/
	//		System.out.println(Arrays.deepHashCode(states.get(j)));

		return states;
	}

	public void setCost(int c){cost = c;}

	public int getCost(){return cost;}

	public int heuristic(){return 0;}

	public RushHour clone(){
		try {
			return (new RushHour(this.board));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer hashBoard(){
		return Arrays.deepHashCode(this.board);
	}
}
