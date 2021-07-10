package geoviz;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class LoadPointsOnClickEvent implements EventHandler<MouseEvent> {

	private GUI gui;
	private boolean load;

	/**
	 * @param gui
	 * @param load
	 */
	public LoadPointsOnClickEvent(GUI gui, boolean load) {
		this.gui = gui;
		this.load = load;
	}

	@Override
	public void handle(MouseEvent event) {
		
		File file;
		gui.setFileBrowser(new FileChooser());
		gui.getFileBrowser().setInitialDirectory(new File(System.getProperty("user.dir") + "/src/data"));
		gui.getFileBrowser().getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Text Files", "*.txt")
			);
		gui.getFileBrowser().setInitialFileName("MyPoints.txt");
		
		if (load)
		{
			//load
			gui.getFileBrowser().setTitle("Import Points From File");
			if ((file = gui.getFileBrowser().showOpenDialog(gui.getPrimaryStage())) != null)
				gui.loadPointsFromFile(file.getPath());
		}
		else
		{
			//save
			gui.getFileBrowser().setTitle("Export Points To File");
			if ((file = gui.getFileBrowser().showSaveDialog(gui.getPrimaryStage())) != null)
				savePointsToFile(file);
		}
	}
	
	private void savePointsToFile(File file) {
        try {
        	String text = "";
        	for (MyPoint point : gui.getPoints()) {
				text += point.toCSV() + "\n";
			}
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(text);
            writer.close();
        } catch (IOException ex) {
        }
    }

}
