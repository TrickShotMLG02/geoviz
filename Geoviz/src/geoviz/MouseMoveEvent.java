package geoviz;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseMoveEvent implements EventHandler<MouseEvent> {

	private GUI gui;

	public MouseMoveEvent(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void handle(MouseEvent event) {
		gui.getCursorPositionLbl().setText(event.getX() + " | " + event.getY());
	}

}
