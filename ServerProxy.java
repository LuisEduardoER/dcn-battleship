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

	public ServerProxy (Socket socket) throws IOException {
		this.socket = socket;
		out = new DataOutputStream (socket.getOutputStream());
		in = new DataInputStream (socket.getInputStream());
	}

	public void setListener (ModelListener listener) {
		if (listener == null) {
			throw new NullPointerException
				("ServerProxy.setListener(): listener is null");
		}
		this.listener = listener;
	}

	public void start() {
		new Reader() .start();
	}

	public void stop() {
		try { socket.shutdownInput(); } catch (IOException exc) {}
	}


	private class Reader extends Thread {
		public void run() {
			try {
				for (;;) {
					byte opcode = dis.readByte();
					// handle incoming messages here
					// call ModelListener functions
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
