import java.io.IOException;

public interface UIListener {

	public void sendAttack(int x, int y) throws IOException;
	
	public void sendResult(boolean hit) throws IOException;

	public void updateGUI(char[][] myBoard, char[][] enemyBoard);

	public void setTurn(boolean myTurn);

	public void endGame();

	public void opponentQuit();
}
