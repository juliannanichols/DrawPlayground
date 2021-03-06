import java.awt.*;
import java.io.*;

public class MyStar implements DrawingObject, Serializable {

	private static final long serialVersionUID = 1L;
	static int points = 5; //5 points is default
	int lastX, lastY;
	double offset = Math.PI / points; // offset the inner circle
	double angle = 2 * Math.PI / points;
	int[] xPoints = new int[2 * points]; // x points for star
	int[] yPoints = new int[2 * points]; // y points for star
	int sizeX, sizeY, originX, originY;
	Rectangle bounds = new Rectangle();
	Polygon polygon = new Polygon();
	Color color;

	public MyStar() {
		sizeX = sizeY = originX = originY = 0;
		setBounds(bounds);
	}

	/**
	 * Bounds for rectangle surrounding star
	 * 
	 * @param oX
	 * @param oY
	 * @param sX
	 * @param sY
	 */
	public MyStar(int oX, int oY, int sX, int sY) {
		setBounds(bounds);
	}

	/**
	 * Actually drawing the shape
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getColor());
		g2d.drawPolygon(xPoints, yPoints, xPoints.length);
	}

	public void start(Point p) {
		originX = p.x;
		originY = p.y;
		lastX = p.x;
		lastY = p.y;
	}

	/**
	 * When user selects and moves star
	 */
	public void drag(Point p) {
		sizeX = p.x - originX;
		sizeY = p.y - originY;
		doMath();
		setBounds(bounds);
	}

	/**
	 * Translation of star
	 */
	public void move(Point p) {
		doMath();
		originX = p.x;
		originY = p.y;
		setBounds(bounds);
	}

	/**
	 * Does math to determine number/position of points of star
	 */
	public void doMath() {
		for (int i = 0, j = 0; j < xPoints.length; i++, j += 2) {

			// Outer points
			xPoints[j] = (int) (originX + sizeX * Math.cos(angle * i));
			yPoints[j] = (int) (originY + sizeY * Math.sin(angle * i));

			// Inner points
			xPoints[j + 1] = (int) (originX + (sizeX / 2) * Math.cos(offset + angle * i));
			yPoints[j + 1] = (int) (originY + (sizeY / 2) * Math.sin(offset + angle * i));
		}
		// to flush any data already in xPoints and yPoints
		polygon.invalidate();
		// so the polygon includes all the points
		polygon.xpoints = xPoints;
		polygon.ypoints = yPoints;
		polygon.npoints = points * 2;
	}

	/**
	 * Bounding box for star
	 */
	public void setBounds(Rectangle b) {
		b.setBounds(polygon.getBounds());
	}

	/**
	 * If point is contained then it will be selected
	 */
	public boolean contains(Point p) {
		return bounds.contains(p);
	}

	/**
	 * Called to set the color of a shape
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Called to get the color of a shape
	 */
	public Color getColor() {
		return color;
	}
}
