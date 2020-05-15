package snake;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Snake {

	private int tableSize;
	private int x;
	private int y;
	private int moves;
	private int dire;
	private int prevDire;
	private Color headColor;
	private Color bodyColor;
	private ArrayList<Point> tailList;
	
	public Snake(int size) {
		tableSize = size;
		x = 0;
		y = 0;
		moves = 0;
		dire = -1;
		prevDire = -1;
		headColor = new Color (255,0,0);
		tailList = new ArrayList<Point>();
	}

	public void update() {
		Point prev = new Point (x,y);
		for (int i = 0; i < getTailListSize(); i++) {
			Point temp = tailList.get(i);
			tailList.set(i, prev);
			prev = temp;
		}
		
			switch (dire) {
			case -1:
				break;
			case 1:
				y--;
				break;
			case 2:
				x++;
				break;
			case 3:
				y++;
				break;
			case 0:
				x--;
				break;
			}
		if (dire != prevDire) {
			prevDire = dire;
			moves++;
		}
	}	
	
	public void addTail() {
		Point newPoint = new Point (x,y);
		tailList.add(newPoint);
	}
	
	public boolean checkForBites() {
		for (int i = 0; i < tailList.size(); i++) {
			Point seg = tailList.get(i);
			if (x==seg.getX() && y==seg.getY()) {
				return true;
			}
		}
		return false;
	}
	
	public void clearTailList() {
		tailList.clear();
	}
	
	public boolean checkOutOfBounds() {
		if (x < 0 || y < 0 || x > tableSize - 1 || y > tableSize - 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Color getBodyColor() {
		return bodyColor;
	}
	
	public int getDire() {
		return dire;
	}
	
	public Color getHeadColor() {
		return headColor;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public int getTailListSize() {
		return tailList.size();
	}
	
	public ArrayList<Point> getTailList() {
		return tailList;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setDire(int newDire) {
		dire = newDire;
	}
	
	public void setHeadColor(Color newHeadColor) {
		headColor = newHeadColor;
	}
	
	public void setBodyColor(Color newBodyColor) {
		bodyColor = newBodyColor;
	}
	
	public void saveData() {
		try {
			Writer fw = new FileWriter("data.txt"); 
			fw.write(x + ", " + y + " head\n");
			for(Point seg : tailList) {
				fw.write(seg.x + ", " + seg.y + " body\n");
			}
			fw.write(moves + " moves");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadData() {
		Scanner sc;
		try {
			sc = new Scanner(new File("data.txt"));
			tailList.clear();
			while(sc.hasNextLine()) {
				String[] line = sc.nextLine().split(",?\\s");
				if(line.length == 3)
					if(line[2].equals("head")) {
						x = Integer.parseInt(line[0]);
						y = Integer.parseInt(line[1]);
					} else {
						tailList.add(new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
					}
				if(line.length == 2)
					moves = Integer.parseInt(line[0]);
				System.out.println(line[0] + " " + line[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
