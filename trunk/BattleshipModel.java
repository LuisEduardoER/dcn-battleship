import java.io.IOException;

public class BattleshipModel implements ModelListener {
	private UIListener listener;

	private char[][] myBoard;
	private char[][] enemyBoard;
	
	// Board Pieces
	private final char _SHIP = 'O';    // unhit ship
	private final char _HIT = 'X';     // hit ship
	private final char _WATER = '.';   // unhit water
	private final char _SPLASH = '+';  // hit water
	
	public BattleshipModel() {
		myBoard = new char[10][10];
		enemyBoard = new char[10][10];
		// initially fill boards with empty water
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				enemyBoard[x][y] = _WATER;
				myBoard[x][y] = _WATER;
			}
		}

		/*
			fill myBoard with pieces (randomly or user-generated)
		*/
		myBoard[1][1] = _SHIP;
		myBoard[1][2] = _SHIP;

	}

	public void setListener (UIListener listener) {
		if (listener == null) {
			throw new NullPointerException
				("BattleshipModel.setListener(): listener is null");
		}
		this.listener = listener;
	}


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
		updateGUI();
	}

	public void processResult(boolean hit) {
		/*
			update enemyBoard with result from last attack
		*/
		updateGUI();
	}

	public void updateGUI() {
		/*
			Update GUI with new board
		*/
	}

}
