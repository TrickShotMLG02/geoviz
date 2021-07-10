package geoviz;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class IntersectionsOnClickEvent implements EventHandler<MouseEvent> {

	private GUI gui;

	private List<MyPoint> points;
	private List<MyPoint> intersectionPointsLinesCircles;
	private List<MyPoint> intersectionPointsCirclesCircles;

	public IntersectionsOnClickEvent(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void handle(MouseEvent event) {
		if (gui.getIntersections().size() == 0) {
			gui.getShowIntersectionsBtn().setText("Hide Intersections");

			points = new ArrayList<MyPoint>();
			// Line-Line intersections
			{
				for (MyLine line1 : gui.getLines()) {
					for (MyLine line2 : gui.getLines()) {
						if (!Double.isNaN(Utilities.getPointOfIntersection(line1, line2).getX())
								&& !Double.isNaN(Utilities.getPointOfIntersection(line1, line2).getY()))
							points.add(new MyPoint(Utilities.getPointOfIntersection(line1, line2).getX(),
									Utilities.getPointOfIntersection(line1, line2).getY(), GUI.POINT_RADIUS));
						for (MyPoint point : points) {
							point.setStroke(GUI.INTERSECTION_POINT_COLOR);
							point.setFill(GUI.INTERSECTION_POINT_COLOR);
						}
					}
				}
			}
			// Line-Circle intersections
			{
				intersectionPointsLinesCircles = new ArrayList<MyPoint>();
				for (MyLine line1 : gui.getLines()) {
					for (MyCircle circle1 : gui.getCircles()) {
						if (Utilities.getPointOfIntersection(line1, circle1).size() != 0) {
							intersectionPointsLinesCircles.addAll(Utilities.getPointOfIntersection(line1, circle1));
						}
					}
				}
				for (MyPoint p : intersectionPointsLinesCircles) {

					MyPoint tmp = new MyPoint(p.getX(), p.getY(), GUI.POINT_RADIUS);
					tmp.setStroke(GUI.INTERSECTION_POINT_COLOR);
					tmp.setFill(GUI.INTERSECTION_POINT_COLOR);

					points.add(tmp);
				}
			}
			// Circle-Circle intersections
			{
				intersectionPointsCirclesCircles = new ArrayList<MyPoint>();
				for (MyCircle circle1 : gui.getCircles()) {
					for (MyCircle circle2 : gui.getCircles()) {
						if (Utilities.getPointOfIntersection(circle1, circle2).size() != 0) {
							intersectionPointsCirclesCircles.addAll(Utilities.getPointOfIntersection(circle1, circle2));
						}
					}
				}
				for (MyPoint p : intersectionPointsCirclesCircles) {

					MyPoint tmp = new MyPoint(p.getX(), p.getY(), GUI.POINT_RADIUS);
					tmp.setStroke(GUI.INTERSECTION_POINT_COLOR);
					tmp.setFill(GUI.INTERSECTION_POINT_COLOR);

					points.add(tmp);
				}
			}

			// add all points to list
			gui.getIntersections().addAll(points);
			// draw all points
			for (MyPoint myPoint : gui.getIntersections()) {

				Pane grid = ((Pane) gui.getRoot().getCenter());
				grid.getChildren().add(myPoint);
			}

			points.clear();
			intersectionPointsLinesCircles.clear();
			intersectionPointsCirclesCircles.clear();
		} else {
			// remove all points

			Pane grid = ((Pane) gui.getRoot().getCenter());
			grid.getChildren().removeAll(gui.getIntersections());
			gui.getIntersections().clear();
			gui.getShowIntersectionsBtn().setText("Show Intersections");
		}

	}

}
