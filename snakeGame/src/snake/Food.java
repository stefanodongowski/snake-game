package snake;

import java.awt.Color;

public class Food {
	private int tableSize;
	private Color color;
	private static int x;
	private static int y;
	
	public Food(int tableSize) {
        this.tableSize = tableSize;
		randomizePosition();
        color = new Color(0,255,0,170);  // green color with 1/3 transparency;
    }

    public void randomizePosition() {
      x = (int)(Math.random() * tableSize);
      y = (int)(Math.random() * tableSize);
    }
    
    public Color getColor() {
    	return color;
    }
    
    public static int getX() {
    	return x;
    }
    
    public static int getY() {
    	return y;
    }
}
