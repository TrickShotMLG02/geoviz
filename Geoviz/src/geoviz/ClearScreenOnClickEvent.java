package geoviz;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ClearScreenOnClickEvent implements EventHandler<MouseEvent> {

	private GUI gui;

	public ClearScreenOnClickEvent(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void handle(MouseEvent event) {
		Pane grid = ((Pane) gui.getRoot().getCenter());

		grid.getChildren().removeAll(gui.getPoints());
		grid.getChildren().removeAll(gui.getLines());
		grid.getChildren().removeAll(gui.getRectangles());
		grid.getChildren().removeAll(gui.getCircles());
		grid.getChildren().removeAll(gui.getIntersections());

		gui.getIntersections().clear();
		gui.getPoints().clear();
		gui.getLines().clear();
		gui.getRectangles().clear();
		gui.getCircles().clear();
		gui.getIntersections().clear();

		gui.getShowIntersectionsBtn().setText("Show Intersections");
	}

}
