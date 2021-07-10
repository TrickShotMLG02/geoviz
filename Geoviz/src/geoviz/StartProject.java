package geoviz;

import javafx.application.Application;

public class StartProject {

	public static void main(String[] args) {
		
		/*
		MyPoint[] points = { new MyPoint(1, 1), new MyPoint(2, 2), new MyPoint(1, 1), new MyPoint(1, 2) };
		MyLine line1 = new MyLine(points[0], points[1]);
		MyCircle circle = new MyCircle(points[2], points[3]);
		MyCircle circle2 = new MyCircle(points[3], points[1]);
		
		System.out.println("Line-Circle");
		System.out.println(Utilities.getPointOfIntersection(line1, circle));
		
		System.out.println("\n\nCircle-Circle");
		System.out.println(Utilities.getPointOfIntersection(circle2, circle));
		
		System.out.println("\n\nLine-Circle");
		MyLine line3 = new MyLine(new MyPoint(-3.25, 7.12), new MyPoint(-0.49, 6.5));
		MyCircle circle3 = new MyCircle(new MyPoint(-7.54, -4.0), new MyPoint(-7.54, 8.85));
		System.out.println(Utilities.getPointOfIntersection(line3, circle3));
		
		System.out.println("\n\nCircle-Circle");
		MyCircle circle4 = new MyCircle(new MyPoint(2.41, -5.49), new MyPoint(1.67+2.41, -5.49));
		MyCircle circle5 = new MyCircle(new MyPoint(-1.92, -4.95) , new MyPoint(8.54-1.92, -4.95));
		System.out.println(Utilities.getPointOfIntersection(circle4, circle5));
		
		System.out.println("\n\nDataLoader");
		System.out.println(DataLoader.loadPoints("src/data/points.txt"));	
		*/
		
		
		Application.launch(GUI.class);
	}

}
