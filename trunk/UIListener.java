import java.io.IOException;
public interface UIListener {

	public void sendAttack(int x, int y) throws IOException;
	
	public void sendResult(boolean hit) throws IOException;

}
