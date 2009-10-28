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
		out.writeByte('a'); //Attack Opcode 'a'
		out.writeInt(x);
		out.writeInt(y);
		out.flush();
	}

	public void sendResult(boolean hit) throws IOException {
		out.writeByte('r'); //Result Opcode 'r'
		if (hit) {
			out.writeInt(1);
		} else {
			out.writeInt(0);
		}
		out.flush();
	}

	public void start() {
		new Reader().start();
	}

	public void stop() {
		try { socket.shutdownInput(); } catch (IOException exc) {}
	}

	private class Reader extends Thread {
		public void run() {
			try {
				for (;;) {
					String msg = in.readLine();
					System.out.println("Reader received message: " + msg);

					// handle incoming messages
					char code = msg.charAt(0);
					switch (code) {
						case ('a'):
							int x=0;
							int y=0;
							/*
								extract x and y from msg
							*/
							listener.processAttack(x,y);
							break;
						case ('r'):
							boolean hit=false;
							/*
								extract hit result from msg
							*/
							listener.processResult(hit);
							break;
						default:
							System.err.println("Reader received bad code.");
							break;
					}


				}
			} catch (EOFException exc) {
			} catch (IOException exc) {
				System.err.println ("ServerProxy.Reader.run(): I/O error");
				exc.printStackTrace (System.err);
			} finally {
				try { socket.close(); } catch (IOException exc) {}
			}
		}
	}

}
