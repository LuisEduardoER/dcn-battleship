import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientProxy extends Thread implements ServerListener {

	private DataInputStream input;
	private DataOutputStream output;
	private ServerListener listener;
	private Socket socket;

	public ClientProxy(Socket socket) {
		this.socket = socket;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
	}

	public void setListener(ServerListener listener) {
		this.listener = listener;
		start();
	}

	public void attackSquare(int x, int y) throws IOException {
		// my partner wants to attack my square at (x,y)

	}

	public void sendResult(boolean result) throws IOException {

	}

	public void run() {
		try {
			for (;;) {
			// when you recieve an attack command, call attackSquare on the listener
			// your partner executes attackSquare, and calls sendResult on us.

			}
		} catch (EOFException e) {
		} catch (IOException e) {
		} finally { try { socket.close(); } catch (IOException e) {}}
	}
}
