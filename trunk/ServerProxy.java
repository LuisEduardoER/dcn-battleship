import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerProxy implements UIListener {

	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ModelListener listener;

	private int lastX;
	private int lastY;

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

	public void start() {
		new Reader().start();
	}

	public void stop() {
		try { socket.shutdownInput(); } catch (IOException exc) {}
	}

	// called if you calculate a loss only
	public void endGame() {
		stop();
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
