package paquin.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Graph {
	private final boolean isTileMode;

	private int graphXPos;
	private int graphYPos;

	private int graphWidth;
	private int graphHeight;

	private Color backgroundColor;
	private Color foregroundColor;

	private int columns;
	private int rows;

	private ArrayList<Node> nodes;
	private int[][] graph;

	private int scrollOffsetX = 0;
	private int scrollOffsetY = 0;

	/**
	 * Graph constructor that starts a blank graph without a predefined graph
	 * integer array.
	 * 
	 * @param x        Screen position for the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param y        Screen position for the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param w        Displayed width of the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param h        Displayed height of the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param columns  Denotes number of columns in the graph.
	 * @param rows     Denotes number of rows in the graph.
	 * @param tileMode Determines if math is calculated between the intersections of
	 *                 grid lines or the center points of space between grid lines.
	 */
	public Graph(int x, int y, int w, int h, int columns, int rows, boolean tileMode) {
		this.columns = columns;
		this.rows = rows;
		isTileMode = tileMode;
		graphXPos = x;
		graphYPos = y;
		graphWidth = w;
		graphHeight = h;

		graph = new int[columns][rows];

		// Create nodes
		initNodes(graph);
	}

	/**
	 * Graph constructor that takes a predefined graph via an integer array with 0
	 * indicating passable nodes and 1 indicating blocked nodes.
	 * 
	 * @param graph    2D integer array. 0 indicates passable nodes and 1 indicates
	 *                 a blocked node.
	 * @param x        Screen position for the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param y        Screen position for the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param w        Displayed width of the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param h        Displayed height of the graph. This can be 0 if nothing will
	 *                 be displayed.
	 * @param tileMode Determines if math is calculated between the intersections of
	 *                 grid lines or the center points of space between grid lines.
	 */
	public Graph(int[][] graph, int x, int y, int w, int h, boolean tileMode) {
		this.graph = graph;
		isTileMode = tileMode;
		columns = graph.length;
		rows = graph[0].length;
		graphXPos = x;
		graphYPos = y;
		graphWidth = w;
		graphHeight = h;

		// Create nodes
		initNodes(graph);
	}

	/**
	 * Initializes nodes for the designed graph. If a pre-constructed graph was
	 * provided then 1s indicate non passable tiles.
	 * 
	 * @param theGraph The graph that was provided or a blank one if none was
	 *                 provided to the constructor.
	 */
	private void initNodes(int[][] theGraph) {
		nodes = new ArrayList<>();
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				nodes.add(new Node(new Point(x, y)));
				if (theGraph[x][y] == 1) {
					nodes.get(nodes.size() - 1).setPassable(false);
					int nodeHeight = graphHeight / rows;
					int nodeWidth = graphWidth / columns;
					nodes.get(nodes.size() - 1).addDisplayData(null, nodeHeight, nodeWidth);
					nodes.get(nodes.size() - 1).setColor(foregroundColor);
				}
			}
		}
	}

	/**
	 * This method sets the foreground and background colors of the displayed graph.
	 * 
	 * @param backColor is the color rendered behind the entire graph
	 * @param foreColor is the color of the grid lines.
	 */
	public void setColors(Color backColor, Color foreColor) {
		backgroundColor = backColor;
		foregroundColor = foreColor;
	}

	/**
	 * Draws the background for the graph and all of the nodes.
	 * 
	 * @param g Graphics object for rendering the graph on a frame/panel.
	 */
	public void drawGraph(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Draw Background Color (Default is black)
		if (backgroundColor != null) {
			g2.setColor(backgroundColor);
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.drawRect(graphXPos, graphYPos, graphWidth, graphHeight);

		// Draw Nodes
		for (int i = 0; i < nodes.size(); i++) {
			// Translated X / Y supports offscreen graphs and scrolling.
			int translatedX = (int) Math.round(nodes.get(i).getLocation().getX() + scrollOffsetX);
			int translatedY = (int) Math.round(nodes.get(i).getLocation().getY() + scrollOffsetY);
			nodes.get(i).drawNode(g, translatedX, translatedY);
		}

	}

	/**
	 * Returns the distance as a double from the start node to the end node. Tile
	 * mode determines if the distance is calculated from the node x,y or from the
	 * tile center point.
	 * 
	 * @param start
	 * @param end
	 * @return double representing the distance between the start and end node
	 *         (pixels, not tiles).
	 */
	private double getPixelDistance(Node start, Node end) {
		double xDistance = 0, yDistance = 0;

		if (!isTileMode) {
			xDistance = Math.abs(start.getLocation().getX() - end.getLocation().getX());
			yDistance = Math.abs(start.getLocation().getY() - end.getLocation().getY());
		} else {
			// All nodes are always the same size so start and end will be the same size.
			int xOffset = start.getWidth() / 2;
			int yOffset = start.getHeight() / 2;

			xDistance = Math.abs(start.getLocation().getX() + xOffset - end.getLocation().getX() + xOffset);
			yDistance = Math.abs(start.getLocation().getY() + yOffset - end.getLocation().getY() + yOffset);
		}
		return Math.sqrt(((xDistance * xDistance) + (yDistance * yDistance)));
	}

	/**
	 * Returns the distance as a int from the start node to the end node.
	 * 
	 * @param start Node to measure from.
	 * @param end   Node to measure to.
	 * @return integer representing the distance between the start and end node in tiles
	 *         (not pixels).
	 */
	private int getTileDistance(Node start, Node end) {
		int xDistance = (int) Math.round(Math.abs(start.getLocation().getX() - end.getLocation().getX()));
		int yDistance = (int) Math.round(Math.abs(start.getLocation().getY() - end.getLocation().getY()));
		return xDistance + yDistance;
	}
}
