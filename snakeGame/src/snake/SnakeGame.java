package snake;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class SnakeGame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private int tableSize = 20;
	private GameBoard gameBoard;
	private Snake snake;
	private Food food;
	boolean gameOver, paused;

	public SnakeGame() {
		super("Snake");
		setSize(460, 505);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		snake = new Snake(tableSize);
		food = new Food(tableSize);
		gameOver = false;
		paused = false;
		gameBoard = new GameBoard();
		addKeyListener(this);
		add(gameBoard);
		setVisible(true);
		gameLoop();
	}

	private void gameLoop() {
		while (!gameOver) {
			for (int x = 0; x < tableSize; x++) { // draw table
				for (int y = 0; y < tableSize; y++) {
					gameBoard.drawRect(x, y, new Color(160, 160, 160));
				}
			}
			if (snake.checkOutOfBounds()) {
				gameBoard.drawScore(-1, tableSize, snake.getTailListSize()); // out of bounds
				snake.setBodyColor(new Color(0, 128, 0, 65));
				gameOver = true;
			} else if (snake.getTailListSize() > tableSize) { // winner
				gameBoard.drawScore(snake.getMoves(), tableSize, snake.getTailListSize());
				snake.setBodyColor(new Color(128, 0, 128, 65));
				gameOver = true;
			} else if (!paused){ // updates
				snake.update(); // move snake with body, tally moves
				if (snake.checkForBites()) {
					gameBoard.drawScore(-2, tableSize, snake.getTailListSize()); // bitten
					snake.setBodyColor(new Color(128, 0, 0, 65));
					gameOver = true;
				}
				if (snake.getX() == food.getX() && snake.getY() == food.getY()) { // eat and replace food
					snake.addTail();
					food.randomizePosition();
				}
			}
			gameBoard.drawCirc(snake.getX(), snake.getY(), snake.getHeadColor()); // draw snake head
			for (Point p : snake.getTailList()) { // draw snake body
				gameBoard.drawRect(p.x, p.y, snake.getBodyColor());
			}
			gameBoard.drawCirc(food.getX(), food.getY(), food.getColor()); // draw food
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameLoop();
		}
	} // gameLoop

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menu.setMnemonic('O');

		JMenuItem menuItem = new JMenuItem("Pause Game Toggle", KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = !paused;  // snake.setDire(-1);
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Save Data");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snake.saveData();
			}
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load Data");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
				snake.loadData();
			}
		});
		menu.add(menuItem);

		menuBar.add(menu);
		return menuBar;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case 10: // enter - pause
		case 80: // p - pause
			paused = !paused;
			break;
		case 101: // 5
		case 87: // w
		case 38: // up arrow
			snake.setDire(1);
//			gameBoard.toggleDistract();
			break;
		case 97: // 1
		case 65: // a
		case 37: // left arrow
			snake.setDire(0);
			break;
		case 98: // 2
		case 83: // s
		case 40: // down arrow
			snake.setDire(3);
			break;
		case 99: // 3
		case 68: // d
		case 39: // right arrow
			snake.setDire(2);
			break;

		}
		System.out.println(snake.getDire() + " keyCode: " + e.getKeyCode() + " code: " + e.getKeyChar() + " moves: "
				+ snake.getMoves());
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		new SnakeGame();

	}
}
