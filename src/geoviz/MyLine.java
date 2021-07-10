package geoviz;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;

public class MyLine extends Line {

	private MyPoint p1;
	private MyPoint p2;

	private double slope;
	private double intercept;

	
	  /////////////////////////////////////////////////////////
	 // 				Constructor							//
	/////////////////////////////////////////////////////////
	
	public MyLine(MyPoint p1, MyPoint p2) {
		super(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		this.p1 = p1;
		this.p2 = p2;
		
		slope = Utilities.getSlope(p1, p2);
		intercept = Utilities.getIntercept(p1, p2);
		
		//Tooltip
		Tooltip t = new Tooltip("Slope: " + Utilities.round2(this.getSlope()) + "\nIntercept: " + Utilities.round2(this.getIntercept()));
		this.setStrokeWidth(GUI.STROKE_WIDTH);
		Tooltip.install(this, t);
	}

	
	  /////////////////////////////////////////////////////////
	 // 					Getter 							//
	/////////////////////////////////////////////////////////
	
	public MyPoint getP1() {
		return p1;
	}

	public MyPoint getP2() {
		return p2;
	}

	public double getSlope() {
		return slope;
	}

	public double getIntercept() {
		return intercept;
	}

}
