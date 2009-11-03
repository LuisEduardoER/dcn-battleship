import java.io.IOException;

public interface ModelListener {

	public void processAttack(int x, int y) throws IOException;
	
	public void processResult(boolean hit, int lastX, int lastY) throws IOException;

	public void updateGUI(char[][] myBoard, char[][] enemyBoard);

	public void setTurn(boolean myTurn);

	public void displayResult(boolean win);

}
