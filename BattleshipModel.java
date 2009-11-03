import java.io.IOException;
import java.util.Random;

public class BattleshipModel implements ModelListener {
	private UIListener listener;

	private char[][] myBoard;
	private char[][] enemyBoard;
	
	// Board Constants
	private final int BOARD_W = 10;    // board width
	private final int BOARD_H = 10;    // board height
	private final int[] ships = {5, 4, 3, 3, 2}; // ship lengths

	private final char _SHIP = 'O';    // unhit ship
	private final char _HIT = 'X';     // hit ship
	private final char _WATER = '.';   // unhit water
	private final char _SPLASH = '+';  // hit water
	
	public BattleshipModel() {
		myBoard = new char[BOARD_W][BOARD_H];
		enemyBoard = new char[BOARD_W][BOARD_H];
		// initially fill boards with empty water
		for (int x=0; x<BOARD_W; x++) {
			for (int y=0; y<BOARD_H; y++) {
				enemyBoard[x][y] = _WATER;
				myBoard[x][y] = _WATER;
			}
		}
		// fill myBoard with ships placed randomly
		generateRandomBoard();
	}

	public void setListener (UIListener listener) {
		if (listener == null) {
			throw new NullPointerException
				("BattleshipModel.setListener(): listener is null");
		}
		this.listener = listener;
		updateGUI(myBoard, enemyBoard);
	}


	/************************************
	* Functions called from ServerProxy *
	*************************************/
	public void processAttack(int x, int y) {
		boolean hit = false;
		// check myBoard for hit at x,y
		// update board with results
		if (myBoard[x][y] == _SHIP) {
			myBoard[x][y] = _HIT;
			hit = true;
		} else {
			myBoard[x][y] = _SPLASH;
		}
		System.out.println("Model computes hit: " + hit);
		try { listener.sendResult(hit); } catch (IOException E) {}
		updateGUI(myBoard, enemyBoard);
		setTurn(true);
	}

	public void processResult(boolean hit, int x, int y) {
		System.out.println("MODEL x = " + x + ", y = " + y);
		if (hit) {
			enemyBoard[x][y] = _HIT;
		} else {
			enemyBoard[x][y] = _SPLASH;
		}
		updateGUI(myBoard, enemyBoard);
	}

	public void updateGUI(char[][] myBoard, char[][] enemyBoard) {
		listener.updateGUI(myBoard, enemyBoard);
	}
	public void setTurn(boolean myTurn) {
		listener.setTurn(myTurn);
	}

	/*************************************
	* Private board management functions *
	*************************************/
	private void generateRandomBoard() {
		Random rand = new Random();
		for (int i=0; i<ships.length; i++) {
			int shipLength = ships[i];
			int xZero;
			int yZero;
			boolean vertical;
			do {
				xZero = rand.nextInt(BOARD_W);
				yZero = rand.nextInt(BOARD_H);
				vertical = rand.nextBoolean();
			} while ( !setShip(shipLength, xZero, yZero, vertical) );
		}
	}

	private boolean setShip(int shipLength, int xZero,
							int yZero, boolean vertical) {
		int x = xZero;
		int y = yZero;

		// check if placement is out of bounds
		if ( vertical && ((yZero+shipLength)>(BOARD_H-1)) ) {
			return false;
		} else if (!vertical && ((xZero+shipLength)>(BOARD_W-1)) ) {
			return false;
		}
		// check if placement overlaps another ship
		for (int i=0; i<shipLength; i++) {
			if (myBoard[x][y] != _WATER) {
				return false;
			}
			if (vertical) {
				y++;
			} else {
				x++;
			}
		}

		// place ship
		x = xZero;
		y = yZero;
		for (int i=0; i<shipLength; i++) {
			myBoard[x][y] = _SHIP;
			if (vertical) {
				y++;
			} else {
				x++;
			}
		}
		// ship successfully placed
		return true;
	}

	// test function to print a board to System.out
	private void printBoard(char[][] board) {
		System.out.println();
		for (int i=0; i<BOARD_W; i++) {
			for (int j=0; j<BOARD_H; j++) {
				System.out.print(board[j][i] + " ");
			}
			System.out.println("");
		}
	}

}
