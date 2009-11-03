//import java.awt.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.IOException;

public class BattleshipUI {
	private List<UIListener> listeners = new ArrayList<UIListener>();
	private JFrame frame;
	private JButton[][] bottomBoard;
	private JButton[][] topBoard;
	public BattleshipUI() {
		bottomBoard = new JButton[10][10];
		topBoard = new JButton[10][10];
		frame = new JFrame("Battleship");
		/*
			Build GUI here
		*/
		
		GridLayout mainLayout = new GridLayout(2,1);
		mainLayout.setVgap(20);
		JPanel panel = new JPanel(mainLayout);
		JPanel topPanel = new JPanel(new GridLayout(10,10));
		JPanel bottomPanel = new JPanel(new GridLayout(10,10));
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < 10; i++)
							for (int j = 0; j < 10; j++) {
								if (e.getSource() == bottomBoard[i][j]) {
									System.out.println("Clicked Bottom ("+ j + ","+ i +")");
								}
							}
					}
				});
				bottomPanel.add(button);
				bottomBoard[i][j] = button;
			}
		
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < 10; i++)
							for (int j = 0; j < 10; j++) {
								if (e.getSource() == topBoard[i][j]) {
									System.out.println("Clicked top ("+ j + ","+ i +")");
									attackSquare(i, j);
								}
							}
					}
				});
				topPanel.add(button);
				topBoard[i][j] = button;
			}

		panel.add(topPanel);
		panel.add(bottomPanel);

		frame.add(panel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				exitClient();
				System.exit (0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public void updateGUI(char[][] myboard, char[][] enemyboard) {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				switch (myboard[i][j]) {
					case 'O':
						bottomBoard[i][j].setBackground(Color.BLACK);
						break;
					case 'X':
						bottomBoard[i][j].setBackground(Color.GREEN);
						break;
					case '.':
						bottomBoard[i][j].setBackground(Color.BLUE);
						break;
					case '+':
						bottomBoard[i][j].setBackground(Color.RED);
						break;
				}
				switch (enemyboard[i][j]) {
					case 'O':
						bottomBoard[i][j].setBackground(Color.BLACK);
						break;
					case 'X':
						bottomBoard[i][j].setBackground(Color.GREEN);
						break;
					case '.':
						bottomBoard[i][j].setBackground(Color.BLUE);
						break;
					case '+':
						bottomBoard[i][j].setBackground(Color.RED);
						break;
				}
			}
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
				//listener.stop();
			}
		//} catch (IOException exc) {}
	}

}
