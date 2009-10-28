//import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.io.IOException;

public class BattleshipUI {
	private List<UIListener> listeners = new ArrayList<UIListener>();
	private JFrame frame;
	private JTextField field1;
	private JTextField field2;
	public BattleshipUI() {
		frame = new JFrame("Battleship");
		/*
			Build GUI here
		*/
		
		JPanel panel = new JPanel();
		frame.add(panel);
		field1 = new JTextField(20);
		field2 = new JTextField(20);
		panel.add(field1);
		panel.add(field2);
		JButton attackButton = new JButton("Attack");
		panel.add(attackButton);


		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get x and y from GUI
				int x = Integer.parseInt(field1.getText());
				int y = Integer.parseInt(field2.getText());
				attackSquare(x,y);
			}
		});

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
				listener.sendAttack(x,y);
			}
		} catch (IOException exc) {}
	}

	private synchronized void exitClient() {
		//try {
			for (UIListener listener : listeners) {
				/*
					Handle client exit
				*/
			}
		//} catch (IOException exc) {}
	}

}
