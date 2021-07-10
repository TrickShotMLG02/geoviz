package geoviz;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

public class MyRectangle extends Rectangle {

	private MyPoint startPoint;
	private MyPoint endPoint;

	public MyRectangle(MyPoint startPoint, MyPoint endPoint) {
		
		//MyPoint topLeft = new MyPoint(Math.min(startPoint.getX(), endPoint.getX()), Math.min(startPoint.getY(), endPoint.getY()));
		//MyPoint bottomRight = new MyPoint(Math.max(startPoint.getX(), endPoint.getX()), Math.max(startPoint.getY(), endPoint.getY()));
		super(Math.min(startPoint.getX(), endPoint.getX()), Math.min(startPoint.getY(), endPoint.getY()), Math.max(startPoint.getX(), endPoint.getX()) - Math.min(startPoint.getX(), endPoint.getX()), Math.max(startPoint.getY(), endPoint.getY()) - Math.min(startPoint.getY(), endPoint.getY()));
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		
		//Tooltip
		Tooltip t = new Tooltip("Width: " + Utilities.round2(this.getWidth()) + "\nHeight: " + Utilities.round2(this.getHeight()));
		this.setStrokeWidth(GUI.STROKE_WIDTH);
        Tooltip.install(this, t);
	}

	/**
	 * 
	 * @return MyPoint of top-left corner
	 */
	public MyPoint getPosition() {
		double x = startPoint.getX() < endPoint.getX() ? startPoint.getX() : endPoint.getX();
		double Y = startPoint.getY() < endPoint.getY() ? startPoint.getY() : endPoint.getY();

		MyPoint pos = new MyPoint(x, Y);
		return pos;
	}

	public MyPoint getStartPoint() {
		return startPoint;
	}

	public MyPoint getEndPoint() {
		return endPoint;
	}

}
