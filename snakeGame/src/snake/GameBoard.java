package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	protected boolean distract;

	public GameBoard() {
		setBackground(new Color(0x00, 0xFF, 0xFF));
		setForeground(new Color(0xFF, 0xFF, 0xFF));
		distract = false;
	}

	public void drawRect(int x, int y, Color color) {
        int size = 20;
        int padding = 2;
        Graphics ctx = getGraphics();
        ctx.setColor(color);
        ctx.fillRect((size + padding) * x, (size + padding) * y, size, size);
        ctx.dispose();
      } 

	public void drawCirc(int x, int y, Color color) {
		int radius = 10;
		int padding = 2;
        Graphics ctx = getGraphics();
		ctx.setColor(color);
		ctx.fillOval(x * (2 * radius + padding), y * (2 * radius + padding), 2*radius, 2*radius);
		ctx.dispose();
	}

	public void drawScore(int moves, int tableSize, int tailListLength) {
		int score = 100 - (moves / 2 - tableSize); // expect avg 2 moves per feed
        Graphics ctx = getGraphics();
		ctx.setFont(new Font("Arial Bold", Font.ITALIC, 30));
		if (moves == -1 || moves == -2) {
			if (moves == -1)
				ctx.drawString("Oops - Out of Bounds", 10, 45);
			else
				ctx.drawString("Ouch - Bitten", 10, 45);
			int progress = Math.round(100 * tailListLength / tableSize);  // why multiply by 100 first, not last
			ctx.drawString(" you made it " + progress + "%", 40, 80);
			ctx.drawString(" of the way.", 55, 115);
		} else {
			ctx.drawString("Your score " + score, 10, 50);
			if (moves / 2 > tableSize) {
				int extra = (moves / 2 - tableSize) * 2;
				String only = " ";
				if (extra < 10)
					only = " only ";
				if (extra > 20)
					only = " ugh ";
				ctx.drawString(" used" + only + extra + " extra move" + (extra == 1 ? "" : "s"), 40, 80);
			}
		}
		ctx.dispose();
	} // drawScore
	
	public void toggleDistract() {
		distract = !distract;
	}
}
