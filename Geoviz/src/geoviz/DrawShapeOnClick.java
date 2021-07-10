package geoviz;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawShapeOnClick implements EventHandler<MouseEvent> {

	private GUI gui;

	public DrawShapeOnClick(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void handle(MouseEvent event) {

		if (gui.getLastSelectedPoint() == null) {
			gui.setLastSelectedPoint((MyPoint) event.getTarget());
		} else if (gui.getLastSelectedPoint() != null && gui.getCurrentSelectedPoint() == null) {
			gui.setCurrentSelectedPoint((MyPoint) event.getTarget());
		} else {
			gui.setCurrentSelectedPoint(gui.getLastSelectedPoint());
			gui.setLastSelectedPoint((MyPoint) event.getTarget());
		}

		if (gui.getCurrentSelectedPoint() != null && gui.getLastSelectedPoint() != null) {

			if (gui.getLineRdBtn().isSelected()) {

				MyLine line = new MyLine(gui.getLastSelectedPoint(), gui.getCurrentSelectedPoint());
				double m = line.getSlope();
				double b = line.getIntercept();
				double multiplier = 5;
				MyPoint p1 = new MyPoint(0, b, GUI.POINT_RADIUS);
				MyPoint p2 = new MyPoint(gui.getScene().getWidth() * multiplier,
						m * gui.getScene().getWidth() * multiplier + b, GUI.POINT_RADIUS);

				MyLine lineInfinite = new MyLine(p1, p2);
				lineInfinite.setStroke(gui.getColorPicker().getValue());
				gui.getLines().add(lineInfinite);

				Pane grid = ((Pane) gui.getRoot().getCenter());
				grid.getChildren().add(lineInfinite);
			} else if (gui.getRectangleRdBtn().isSelected()) {
				MyRectangle rectangle = new MyRectangle(gui.getLastSelectedPoint(), gui.getCurrentSelectedPoint());
				if (gui.getFillCheckBox().isSelected())
					rectangle.setFill(gui.getColorPicker().getValue());
				else
					rectangle.setFill(null);
				rectangle.setStroke(gui.getColorPicker().getValue());
				gui.getRectangles().add(rectangle);

				Pane grid = ((Pane) gui.getRoot().getCenter());
				grid.getChildren().add(rectangle);
			} else if (gui.getCircleRdBtn().isSelected()) {
				MyCircle circle = new MyCircle(gui.getLastSelectedPoint(), gui.getCurrentSelectedPoint());
				if (gui.getFillCheckBox().isSelected())
					circle.setFill(gui.getColorPicker().getValue());
				else
					circle.setFill(null);
				circle.setStroke(gui.getColorPicker().getValue());
				gui.getCircles().add(circle);

				Pane grid = ((Pane) gui.getRoot().getCenter());
				grid.getChildren().add(circle);
			}

			gui.setLastSelectedPoint(null);
			gui.setCurrentSelectedPoint(null);
		}
	}

	public GUI getGui() {
		return gui;
	}

}
