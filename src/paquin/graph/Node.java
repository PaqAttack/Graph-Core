package paquin.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Node {
	// location represents the Point on the graph that this node resides at.
	private Point location;
	
	// Costs for A* pathfinding
	private int fCost = 0, hCost = 0, gCost = 0;
	
	// Parent node for Pathfinding traceback
	private Node parent;
	
	// Booleans for A* pathfinding
	private boolean isPassable = true;
	private boolean isChecked = false;
	private boolean isOpen = false;
	private boolean isStart = false;
	private boolean isEnd = false;
	
	// image and size in case node will display an image;
	private BufferedImage image;
	private int height, width;
	private Color color;
	
	
	
	// Basic constructor with bare minimum information
	public Node(Point location) {
		this.location = location;
	}
	
	/**
	 * Adds visual data if this node will be displayed on screen.
	 * @param img This will be the image displayed.
	 * @param height This will be the displayed height of the image (set by Graph configuration)
	 * @param width This will be the displayed width of the image (set by Graph configuration)
	 */
	public void addDisplayData(BufferedImage img, int height, int width) {
		this.image = img;
		this.height = height;
		this.width = width;
	}

	/**
	 * Draws node using Graphics onto a frame/panel.
	 * This should be called after background colors and before any 
	 * graph elements are drawn that should be above the graph.
	 * 
	 * If an image isn't present then a rectangle is drawn.
	 * 
	 * @param g Graphics or Graphics 2D object must be passed in.
	 * @param x x-axis position to display this node at.
	 * @param y y-axis position to display this node at.
	 * (precondition: x and y must be translated prior to this call.
	 */
	public void drawNode(Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		// Consider adding a check here to verify this tile is even on screen.
		// There is no reason to draw anything off screen.
		if (image != null) {
			g2.drawImage(image, x, y, null);
		} else {
			if (getColor() != null) {
				g2.setColor(color);
				g2.drawRect(x, y, width, height);
			} else {
				g2.setColor(Color.WHITE);
				g2.drawRect(x, y, width, height);
			}
		}
	}
	
	public int getfCost() {
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isPassable() {
		return isPassable;
	}

	public void setPassable(boolean isPassable) {
		this.isPassable = isPassable;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public Point getLocation() {
		return location;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	
}
