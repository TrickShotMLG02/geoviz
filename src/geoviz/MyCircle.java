package geoviz;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.Circle;

public class MyCircle extends Circle {

	private MyPoint centerPoint;
	private MyPoint radiusPoint;

	public MyCircle(MyPoint centerPoint, MyPoint radiusPoint) {
		super(centerPoint.getX(), centerPoint.getY(), Utilities.getDistance(centerPoint, radiusPoint));
		this.centerPoint = centerPoint;
		this.radiusPoint = radiusPoint;
		
		//Tooltip
		Tooltip t = new Tooltip("Center X: " + Utilities.round2(this.getCenterX()) + "\nCenter Y: " + Utilities.round2(this.getCenterY()) + "\nRadius: " + Utilities.round2(this.getRadius()));
		this.setStrokeWidth(GUI.STROKE_WIDTH);
        Tooltip.install(this, t);
	}

	public MyPoint getCenterPoint() {
		return centerPoint;
	}

	public MyPoint getRadiusPoint() {
		return radiusPoint;
	}

}
