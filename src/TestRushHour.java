import java.io.FileNotFoundException;  // Import this class to handle errors

import rushhour.IllegalMoveException;
import rushhour.RushHour;
 
public class TestRushHour
{
	public static void testReadFromFile1() {
		try {
			RushHour game = new RushHour("board1.txt");
			System.out.println("testReadFromFile1 OK");
		}
		catch (Exception e) {
			System.out.println("testReadFromFile1 Failed" + e);
			e.printStackTrace();
		}
	}

	public static void testReadFromFile2() {
		try {
			RushHour game = new RushHour("nofile.txt");
			System.out.println("testReadFromFile2 Failed - nofile.txt does not exist");
		}
		catch (FileNotFoundException e) {
			System.out.println("testReadFromFile2 OK: " + e);
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("testReadFromFile2 unexpected exception: " + e);
			e.printStackTrace();
		}
	}

	public static void testReadFromFile3() {
		try {
			RushHour game = new RushHour("badboard.txt");
			System.out.println("testReadFromFile3 Failed - badboard.txt is bad");
		}
		catch (FileNotFoundException e) {
			System.out.println("testReadFromFile3 Failed: " + e);
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("testReadFromFile3 OK: " + e);
			e.printStackTrace();
		}
	}


	public static void testMoves1() {
		try {
			RushHour game = new RushHour("board1.txt");
			//	..AA..
			//	.XX.B.
			//	....B.
			//	.DCCC.
			//	.D....
			//	.EE...
			game.makeMove('A', RushHour.RIGHT, 2);
			game.makeMove('X', RushHour.RIGHT, 1);
			game.makeMove('E', RushHour.LEFT, 1);
			game.makeMove('D', RushHour.UP, 3);
			game.makeMove('C', RushHour.LEFT, 2);
			game.makeMove('c', RushHour.RIGHT, 3);
			//	expected board
			//	.D..AA
			//	.DXXB.
			//	....B.
			//	...CCC
			//	......
			//	EE....
			System.out.println("testMoves1 OK");
		}
		catch (Exception e) {
			System.out.println("testMoves1 Failed with exception: " + e);
			e.printStackTrace();
		}
		
	}

	public static void testMoves2() {
		try {
			RushHour game = new RushHour("board1.txt");
			//	..AA..
			//	.XX.B.
			//	....B.
			//	.DCCC.
			//	.D....
			//	.EE...
			game.makeMove('X', RushHour.RIGHT, 2);
			System.out.println("testMoves2 Failed: illegal move not caught");
		}
		catch (IllegalMoveException ime) {
			System.out.println("testMoves2 Ok: illegal move exception: " + ime);
			ime.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("testMoves2 Failed with an unexpected exception: " + e);
			e.printStackTrace();
		}
		
	}

	public static void testIsSolved1() {
		try {
			RushHour game = new RushHour("board1.txt");
			game.makeMove('X', RushHour.RIGHT, 1);
			game.makeMove('D', RushHour.UP, 2);
			game.makeMove('C', RushHour.LEFT, 2);
			game.makeMove('B', RushHour.DOWN, 3);
			game.makeMove('X', RushHour.RIGHT, 2);
			// 	..AA..
			// 	.D..XX
			//	.D....
			//	CCC...
			//	....B.
			//	.EE.B.

			game.printBoard();

			if (game.isSolved())
				System.out.println("testIsSolved1 OK");
			else
				System.out.println("testIsSolved1 Failed");
		}
		catch (Exception e) { // catching all exception
			System.out.println("testIsSolved1 Failed with exception: " + e);
			e.printStackTrace();
		}
	}

	public static void testIsSolved2() {
		try {
			RushHour game = new RushHour("A00.txt");
			//	..AA..
			//	.XX.B.
			//	....B.
			//	.DCCC.
			//	.D....
			//	.EE...
			game.makeMove('Q', RushHour.LEFT, 1);
			game.makeMove('C', RushHour.LEFT, 1);
			game.makeMove('O', RushHour.DOWN, 3);
			//	..AAB.
			//	..XXB.
			//	......
			//	.DCCC.
			//	.D....
			//	.EE...
			if (game.isSolved())
				System.out.println("testIsSolved2 Ok");
			else
				System.out.println("testIsSolved2 Failed");
		}
		catch (Exception e) { // catching all exception
			System.out.println("testIsSolved2 Failed with exception: " + e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		testReadFromFile1();
		testReadFromFile2();
		testReadFromFile3();
		testMoves1();
		testMoves2();
		testIsSolved1();
		testIsSolved2();

		RushHour game = new RushHour("board1.txt");

		game.getStates();
	}
}