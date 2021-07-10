package geoviz;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;

public class MyPoint extends Circle {

	private double x;
	private double y;

	/////////////////////////////////////////////////////////
	// Constructor //
	/////////////////////////////////////////////////////////

	public MyPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public MyPoint(double x, double y, int radius) {
		super(x, y, radius);
		this.x = x;
		this.y = y;
		
		//Tooltip
		Tooltip t = new Tooltip("X: " + Utilities.round2(this.getX()) + "\nY: " + Utilities.round2(this.getY()));
		this.setStrokeWidth(GUI.STROKE_WIDTH);
		Tooltip.install(this, t);
	}

	public boolean isEqualTo(MyPoint p2) {
		if (Utilities.doubleComparison(x, p2.x) && Utilities.doubleComparison(y, p2.y))
			return true;
		else
			return false;
	}

	/////////////////////////////////////////////////////////
	// Getter //
	/////////////////////////////////////////////////////////

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "MyPoint [x=" + x + ", y=" + y + "]";
	}
	
	public String toCSV()
	{
		return x + "," + y;
	}

}
