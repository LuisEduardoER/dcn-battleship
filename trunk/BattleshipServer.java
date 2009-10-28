import java.lang.NumberFormatException;
import java.net.ServerSocket;
import java.net.Socket;

public class BattleshipServer {

	public static void main(String[] args) {
		if (args.length == 1) {
			try {
				new BattleshipServer(Integer.parseInt(args[0]));
			} catch (NumberFormatException e) {
				System.err.println("Incorrectly formatted port number");
			}
		} else {
			System.err.println("Usage: java BattleshipServer <port>");
		}
	}

	public BattleshipServer(int port) {
		try {
			ServerSocket serversocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error creating server socket");
		}
		for (;;) {
			Socket socket = serversocket.accept();
			new ClientProxy(socket).start();
		}
	}
}
