import java.io.FileNotFoundException;  // Import this class to handle errors

import rushhour.*;


public class EvaluationRushHour
{
	public static void testReadFromFile1() {
		try {
			RushHour game = new RushHour("board1.txt");
			//System.out.println("testReadFromFile1 OK");
			System.out.println("AC");
		}
		catch (Exception e) {
			//System.out.println("testReadFromFile1 Failed" + e);
			//e.printStackTrace();
			System.out.println("WA");
		}
	}

	public static void testReadFromFile2() {
		try {
			RushHour game = new RushHour("second_nofile.txt");
			//System.out.println("testReadFromFile2 Failed - nofile.txt does not exist");
			System.out.println("WA");
		}
		catch (FileNotFoundException e) {
			//System.out.println("testReadFromFile2 OK: " + e);
			//e.printStackTrace();
			System.out.println("AC");
		}
		catch (Exception e) {
			//System.out.println("testReadFromFile2 unexpected exception: " + e);
			//e.printStackTrace();
			System.out.println("WA");
		}
	}

	public static void testReadFromFile3() {
		try {
			RushHour game = new RushHour("badboard2.txt");
			//System.out.println("testReadFromFile3 Failed - badboard.txt is bad");
			System.out.println("WA");
		}
		catch (FileNotFoundException e) {
			//System.out.println("testReadFromFile3 Failed: " + e);
			//e.printStackTrace();
			System.out.println("WA");
		}
		catch (Exception e) {
			//System.out.println("testReadFromFile3 OK: " + e);
			//e.printStackTrace();
			System.out.println("AC");
		}
	}

	public static void testMoves1() {
		try {
			RushHour game = new RushHour("board3.txt");
			// .....B
			// D...AB
			// DXXXAB
			// D.....
			// D..CCC
			// D.....
			game.makeMove('A', RushHour.UP, 1);
			game.makeMove('A', RushHour.DOWN, 2);
			game.makeMove('C', RushHour.LEFT, 2);
			game.makeMove('B', RushHour.DOWN, 3);
			//	expected board
			// ......
			// D.....
			// DXXXA.
			// D...AB
			// DCCC.B
			// D....B
			System.out.println("AC");
		}
		catch (Exception e) {
//			System.out.println("testMoves1 Failed with exception: " + e);
//			e.printStackTrace();
			System.out.println("WA");
		}
		
	}

	public static void testMoves2() {
		try {
			RushHour game = new RushHour("board3.txt");
			// .....B
			// D...AB
			// DXXXAB
			// D.....
			// D..CCC
			// D.....
			game.makeMove('A', RushHour.UP, 1);
			game.makeMove('X', RushHour.RIGHT, 2);
//			System.out.println("testMoves2 Failed: illigal move not caught");
			System.out.println("WA");
		}
		catch (IllegalMoveException e) {
			System.out.println("AC");
		}
		catch (Exception e){
			System.out.println("WA");
		}
		
	}

	public static void testIsSolved1() {
		try {
			RushHour game = new RushHour("board3.txt");
			// .....B
			// D...AB
			// DXXXAB
			// D.....
			// D..CCC
			// D.....
			game.makeMove('C', RushHour.LEFT, 1);
			game.makeMove('B', RushHour.DOWN, 3);
			game.makeMove('A', RushHour.UP, 1);
			game.makeMove('X', RushHour.RIGHT, 2);
			// ....A.
			// D...A.
			// D..XXX
			// D....B
			// D.CCCB
			// D....B
			if (game.isSolved())
				//System.out.println("testIsSolved1 OK");
				System.out.println("AC");
			else
				//System.out.println("testIsSolved1 Failed");
				System.out.println("WA");
		}
		catch (Exception e) { // catching all exception
			//System.out.println("testIsSolved1 Failed with exception: " + e);
			//e.printStackTrace();
			System.out.println("WA");
		}
	}

	public static void testIsSolved2() {
		try {
			RushHour game = new RushHour("board3.txt");
			game.makeMove('B', RushHour.DOWN, 1);
			game.makeMove('A', RushHour.UP, 1);
			game.makeMove('X', RushHour.RIGHT, 1);
			if (!game.isSolved())
				//System.out.println("testIsSolved2 Ok");
				System.out.println("AC");
			else
				//System.out.println("testIsSolved2 Failed");
				System.out.println("WA");
		}
		catch (Exception e) { // catching all exception
			//System.out.println("testIsSolved2 Failed with exception: " + e);
			//e.printStackTrace();
			System.out.println("WA");
		}
	}

	public static void main(String[] args) {
		String testID = args[0];
		switch (testID){
			case "testReadFromFile1":
				testReadFromFile1();
				break;
			case "testReadFromFile2":
				testReadFromFile2();
				break;
			case "testReadFromFile3":
				testReadFromFile3();
				break;
			case "testMoves1":
				testMoves1();
				break;
			case "testMoves2":
				testMoves2();
				break;
			case "testIsSolved1":
				testIsSolved1();
				break;
			case "testIsSolved2":
				testIsSolved2();
				break;
		}
		System.exit(0);
	}
}