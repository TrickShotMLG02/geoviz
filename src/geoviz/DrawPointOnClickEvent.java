package geoviz;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawPointOnClickEvent implements EventHandler<MouseEvent> {

	private GUI gui;

	public DrawPointOnClickEvent(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getTarget().getClass() != MyPoint.class) {
			MyPoint point = new MyPoint(event.getX(), event.getY(), GUI.POINT_RADIUS);
			point.setOnMouseClicked(new DrawShapeOnClick(gui));
			gui.getPoints().add(point);

			Pane grid = ((Pane) gui.getRoot().getCenter());
			grid.getChildren().add(point);
		}
	}

}
