import java.io.IOException;

/*
 *	Inferface for the server-side controller of the Battleship program.
 *
 *	@author Timothy Ransome (twr9948@rit.edu)
 *	@author	Gabriel Smith (ges7506@rit.edu)
 */
public interface ServerListener {

	public void attackSquare(int x, int y) throws IOException;

	public void sendResult(boolean result) throws IOException;

}
