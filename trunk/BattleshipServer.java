import java.io.IOException;
import java.lang.NumberFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class BattleshipServer {

	private ServerSocket serversocket;

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
		Stack<ServerListener> waiting = new Stack<ServerListener>();
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error creating server socket");
		}
		for (;;) {
			Socket socket = serversocket.accept();
			if (waiting.empty())
				waiting.push(new ClientProxy(socket));
			else {
				ClientProxy newGuy = new ClientProxy(socket);
				waiting.peek().setListener(newGuy);
				newGuy.setListener(waiting.pop());
			}
		}
	}
}
