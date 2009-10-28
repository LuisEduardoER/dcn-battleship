import java.io.IOException;
public class BattleshipModel implements ModelListener {
	private UIListener listener;

	//Board standards:
	// 'O' = unhit ship
	// 'X' = hit ship
	// '.' = empty water
	// '+' = Hit water

	private char[][] myBoard;
	private char[][] enemyBoard;
	
	public BattleshipModel() {
		myBoard = new char[10][10];

		/*
			fill myBoard with pieces (randomly or user-generated)
		*/

		
		enemyBoard = new char[10][10];
		//fill enemyBoard with empty water
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				enemyBoard[x][y] = '.';
			}
		}
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
		if (myBoard[x][y] == 'O') {
			myBoard[x][y] = 'X';
			hit = true;
		} else {
			myBoard[x][y] = '+';
		}
		try {
			listener.sendResult(hit);
		} catch (IOException E) {}
		/*
			Update GUI with new board
		*/
	}

	public void processResult(boolean hit) {
		/*
			update enemyBoard with result from last attack
			update GUI with new board
		*/
	}

}
