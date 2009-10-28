import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.io.IOException;

public class BattleshipUI {
	private List<UIListener> listeners = new ArrayList<UIListener>();
	private JFrame frame;

	public BattleshipUI() {
		frame = new JFrame("Battleship");
		/*
			Build GUI here
		*/

		/* Add action listeners to buttons with method calls
		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get x and y from GUI
				attackSquare(x,y);
			}
		});
		*/

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				exitClient();
				System.exit (0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public synchronized void addListener(UIListener listener) {
		listeners.add(listener);
	}
	
	private synchronized void attackSquare(int x, int y) {
		try {
			for(UIListener listener : listeners) {
				listener.attackSquare(x,y);
			}
		} catch (IOException exc) {}
	}

	private synchronized void exitClient() {
		try {
			for (UIListener listener : listeners) {
				/*
					Handle client exit
				*/
			}
		} catch (IOException exc) {}
	}

}
