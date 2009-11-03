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
	private Boolean myturn = true;
	private JLabel label;
	public BattleshipUI() {
		bottomBoard = new JButton[10][10];
		topBoard = new JButton[10][10];
		frame = new JFrame("Battleship");
		label = new JLabel("BATTLESHIP");
		/*
			Build GUI here
		*/
		
		GridLayout mainLayout = new GridLayout(3,1);
		mainLayout.setVgap(10);
		JPanel panel = new JPanel(mainLayout);
		JPanel topPanel = new JPanel(new GridLayout(10,10));
		JPanel bottomPanel = new JPanel(new GridLayout(10,10));
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
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
									if ((topBoard[i][j].getBackground() == Color.BLUE) && myturn)
										attackSquare(i, j);
								}
							}
					}
				});
				topPanel.add(button);
				topBoard[i][j] = button;
			}

		panel.add(topPanel);
		panel.add(label);
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

	public void setTurn(boolean turn) {
		myturn = turn;
	}

	public void updateGUI(char[][] myboard, char[][] enemyboard) {
		int count = 0;
		int enemyCount = 0;
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				switch (myboard[i][j]) {
					case 'O':
						count++;
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
						topBoard[i][j].setBackground(Color.BLACK);
						break;
					case 'X':
						enemyCount++;
						topBoard[i][j].setBackground(Color.GREEN);
						break;
					case '.':
						topBoard[i][j].setBackground(Color.BLUE);
						break;
					case '+':
						topBoard[i][j].setBackground(Color.RED);
						break;
				}
			}
		if (count == 0) {
			for(UIListener listener : listeners) {
				listener.endGame();
				label.setText("You Lose!");
			}
		}
		if (enemyCount == 17)
			label.setText("You Win!");
	}
	public synchronized void addListener(UIListener listener) {
		listeners.add(listener);
	}
	
	private synchronized void attackSquare(int x, int y) {
		myturn = false;
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