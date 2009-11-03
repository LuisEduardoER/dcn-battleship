import java.io.IOException;
public interface ModelListener {

	public void processAttack(int x, int y) throws IOException;
	
	public void processResult(boolean hit) throws IOException;

	public void updateGUI(char[][] myBoard, char[][] enemyBoard);

}
