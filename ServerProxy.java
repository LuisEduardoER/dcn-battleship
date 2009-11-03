import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerProxy implements UIListener {
	// Private data members
	private Socket socket;
	private DataOutputStream out;	// write to socket
	private DataInputStream in;		// read from socket
	private ModelListener listener;

	private int lastX;	// stores x coord from last attack
	private int lastY;	// stores y coord from last attack

	public ServerProxy(Socket socket) throws IOException {
		this.socket = socket;
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}

	public void setListener (ModelListener listener) {
		if (listener == null) {
			throw new NullPointerException
				("ServerProxy.setListener(): listener is null");
		}
		this.listener = listener;
	}

	/**************************************
	* Functions inherited from UIListener *
	**************************************/
	public void sendAttack(int x, int y) throws IOException {
		lastX = x;
		lastY = y;
		out.writeChar('a'); //Attack Opcode 'a'
		out.writeInt(x);
		out.writeInt(y);
		out.flush();
	}

	public void sendResult(boolean hit) throws IOException {
		System.out.println("ServerProxy sending result: "+hit);
		out.writeChar('r'); //Result Opcode 'r'
		out.writeBoolean(hit);
		out.flush();
	}

	public void updateGUI(char[][] myBoard, char[][] enemyBoard) {
		listener.updateGUI(myBoard, enemyBoard);
	}

	public void setTurn(boolean myTurn) {
		listener.setTurn(myTurn);
	}

	public void opponentQuit() {
		listener.opponentQuit();
	}

	// called if you lose
	public void endGame() {
		stop();
	}

	/*******************
	* Reader functions *
	*******************/
	public void start() {
		new Reader().start();
	}

	public void stop() {
		System.out.println("CLOSING SOCKET");
		try { socket.shutdownInput(); } catch (IOException exc) {}
	}

	private class Reader extends Thread {
		public void run() {
			try {
				for (;;) {
					char code = in.readChar();
					switch (code) {
						case ('a'):
							int x = in.readInt();
							int y = in.readInt();
							listener.processAttack(x,y);
							break;
						case ('r'):
							boolean hit=in.readBoolean();
							listener.processResult(hit, lastX, lastY);
							break;
						default:
							System.err.println("Reader received bad code.");
							break;
					}


				}
			} catch (EOFException exc) {
				listener.opponentQuit();
				System.err.println("Opponent closed the socket.");
			} catch (IOException exc) {
				System.err.println ("ServerProxy.Reader.run(): I/O error");
				exc.printStackTrace (System.err);
			} finally {
				try { socket.close(); } catch (IOException exc) {}
			}
		}
	}

}
