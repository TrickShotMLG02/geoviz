package geoviz;

import java.util.ArrayList;

public class Utilities {

	//////////////
	// Methods //
	////////////

	/**
	 * @param a
	 * @param b
	 * @return true if two double values have a difference which is less than
	 *         0.000001, otherwhise false
	 */
	public static boolean doubleComparison(double a, double b) {
		double delta = 0.000001;
		// Compare if values are the same with a max delta
		return (a <= b + delta && a >= b - delta);
	}

	/**
	 * @param p1
	 * @param p2
	 * @return slope of a line which is specified by two points
	 */
	public static double getSlope(MyPoint p1, MyPoint p2) {
		return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
	}

	/**
	 * @param line
	 * @return slope of a given line
	 */
	public static double getSlope(MyLine line) {
		return getSlope(line.getP1(), line.getP2());
	}

	/**
	 * @param value
	 * @return a double value with a maximum of two digits (cutted after 2. decimal
	 *         place)
	 */
	public static double round2(double value) {
		int tmpInt = (int) (value * Math.pow(10, 2));
		double cuttedValue = tmpInt / Math.pow(10, 2);
		return cuttedValue;
	}

	/**
	 * @param p1
	 * @param p2
	 * @return y-position of intercept of a line which is specified by two points
	 *         and the y-axis
	 */
	public static double getIntercept(MyPoint p1, MyPoint p2) {
		return p1.getY() - (getSlope(p1, p2) * p1.getX());
	}

	/**
	 * @param line
	 * @return y-position of intercept of a line and the y-axis
	 */
	public static double getIntercept(MyLine line) {
		return getIntercept(line.getP1(), line.getP2());
	}

	/**
	 * @param slope1
	 * @param slope2
	 * @return true if slopes of two given lines are the same (e.g. lines are
	 *         parallel)
	 */
	public static boolean isParallel(double slope1, double slope2) {
		return doubleComparison(slope1, slope2);
	}

	/**
	 * @param slope1
	 * @param slope2
	 * @return true if two given lines are orthogonal, otherwhise false
	 */
	public static boolean isOrthogonal(double slope1, double slope2) {
		return slope1 * slope2 == -1 ? true : false;
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return Arraylist which contains the Points of Intersection of two lines
	 *         which are specified by 4 points (p1 - p4)
	 */
	public static MyPoint getPointOfIntersection(MyPoint p1, MyPoint p2, MyPoint p3, MyPoint p4) {
		double m1 = getSlope(p1, p2);
		double b1 = getIntercept(p1, p2);

		double m2 = getSlope(p3, p4);
		double b2 = getIntercept(p3, p4);

		// calculating x coordinate
		double div = m1;
		double x = b2;

		div -= m2;
		x -= b1;

		x = x / div;

		// calculating y coordinate
		double y = m1 * x + b1;

		MyPoint point = new MyPoint(x, y);

		return point;
	}

	/**
	 * @param l1
	 * @param l2
	 * @return Arraylist which contains the Points of Intersection of two given
	 *         lines
	 */
	public static MyPoint getPointOfIntersection(MyLine l1, MyLine l2) {
		return getPointOfIntersection(l1.getP1(), l1.getP2(), l2.getP1(), l2.getP2());
	}
	
	/**
	 * @param line
	 * @param circle
	 * @return Arraylist which contains the Points of Intersection of a given line
	 *         and a circle
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyLine line, MyCircle circle) {

		// Source of formulas:
		// https://mathworld.wolfram.com/Circle-LineIntersection.html

		double xtransform = circle.getCenterPoint().getX();
		double ytransform = circle.getCenterPoint().getY();
		double r = circle.getRadius();

		// Move line up/down by the vertical shift of the circle center from P(0|0) and
		// calculate intersections
		// Then move these points back by the given shift to their original position
		double x1 = line.getP1().getX() - xtransform;
		double x2 = line.getP2().getX() - xtransform;
		double y1 = line.getP1().getY() - ytransform;
		double y2 = line.getP2().getY() - ytransform;

		double dx = x2 - x1;
		double dy = y2 - y1;
		double dr = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

		double D = x1 * y2 - x2 * y1;
		double discriminant = Math.pow(r, 2) * Math.pow(dr, 2) - Math.pow(D, 2);

		ArrayList<MyPoint> points = new ArrayList<>();

		if (discriminant > 0) {
			double x = ((D * dy - sgn(dy) * dx * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + xtransform;
			double y = ((-D * dx - Math.abs(dy) * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + ytransform;

			points.add(new MyPoint(x, y));

			x = ((D * dy + sgn(dy) * dx * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + xtransform;
			y = ((-D * dx + Math.abs(dy) * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + ytransform;
			points.add(new MyPoint(x, y));

		} else if (discriminant == 0) {
			double x = ((D * dy + sgn(dy) * dx * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + xtransform;
			double y = ((-D * dx + Math.abs(dy) * Math.sqrt(discriminant)) / Math.pow(dr, 2)) + ytransform;
			points.add(new MyPoint(x, y));
		}
		return points;
	}
	
	/**
	 * 
	 * @param circle1
	 * @param circle2
	 * @return Arraylist which contains the Points of Intersection of two given circles
	 */
	public static ArrayList<MyPoint> getPointOfIntersection(MyCircle circle1, MyCircle circle2) {
		// Source of formulas:
		// https://bytes.com/topic/java/answers/645269-circle-circle-intersection-more
		
		double x1 = circle1.getCenterPoint().getX();
		double x2 = circle2.getCenterPoint().getX();
		double y1 = circle1.getCenterPoint().getY();
		double y2 = circle2.getCenterPoint().getY();
		
		double r1 = circle1.getRadius();
		double r2 = circle2.getRadius();
		double d = getDistance(circle1.getCenterPoint(), circle2.getCenterPoint());
		
		double d1 = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);
		
		double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(d1, 2));
		
		double x3 = x1 + (d1 * (x2 - x1)) / d;
		double y3 = y1 + (d1 * (y2 - y1)) / d;
		
		double x4_1 = x3 + (h * (y2 - y1)) / d;
		double y4_1 = y3 - (h * (x2 - x1)) / d;
		
		double x4_2 = x3 - (h * (y2 - y1)) / d;
		double y4_2 = y3 + (h * (x2 - x1)) / d;
		
		ArrayList<MyPoint> points = new ArrayList<>();
		
		points.add(new MyPoint(x4_1, y4_1));
		points.add(new MyPoint(x4_2, y4_2));
		return points;
	}

	
	/**
	 * @param point1
	 * @param point2
	 * @return distance between two given points
	 */
	public static double getDistance(MyPoint point1, MyPoint point2) {
		// d = sqrt((x2-x1)^2 + (y2-y1)^2)
		return Math.sqrt(Math.pow(Math.abs(point2.getX() - point1.getX()), 2)
				+ Math.pow(Math.abs(point2.getY() - point1.getY()), 2));
	}

	/**
	 * @param d
	 * @return -1 for d<0<br>
	 *         1 otherwhise
	 */
	public static int sgn(double d) {
		return d < 0 ? -1 : 1;
	}
}
