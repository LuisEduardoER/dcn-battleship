import java.net.InetSocketAddress;
import java.net.Socket;

public class BattleshipClient {

	public static void main (String[] args) throws Exception {
		if (args.length != 2) usage();
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		Socket socket = new Socket();
		socket.connect(new InetSocketAddress (host, port));

		final ServerProxy proxy = new ServerProxy(socket);
		final BattleshipUI ui = new BattleshipUI();
		final BattleshipModel model = new BattleshipModel();

		ui.addListener(proxy);
		proxy.setListener(new ModelListener() {
			public void processAttack(int x, int y) {
				model.processAttack(x,y);
			}
			public void processResult(boolean hit) {
				model.processResult(hit);
			}
		});
		proxy.start();

	}

	private static void usage() {
		System.err.println("Usage: java BattleshipClient <host> <port>");
		System.err.println("<host> = Server host name");
		System.err.println("<port> = Server port number");
		System.exit (1);
	}
}
