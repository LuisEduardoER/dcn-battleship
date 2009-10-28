import java.io.IOException;

public interface ServerListener {

	public void attackSquare(int x, int y) throws IOException;

	public void sendResult(boolean result) throws IOException;

}
