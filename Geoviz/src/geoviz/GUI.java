package geoviz;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

	public static final double STROKE_WIDTH = 2.5;
	public static final int POINT_RADIUS = 5;
	public static final Color INTERSECTION_POINT_COLOR = Color.GREEN;
	public static String FILE_PATH = "src/data/points.txt";

	private List<MyPoint> points = new ArrayList<MyPoint>();
	private List<MyLine> lines = new ArrayList<MyLine>();
	private List<MyRectangle> rectangles = new ArrayList<MyRectangle>();
	private List<MyCircle> circles = new ArrayList<MyCircle>();
	private List<MyPoint> intersections = new ArrayList<MyPoint>();

	private Stage primaryStage;
	private BorderPane root;
	
	//Top bar
	private ToolBar topBar;
	private RadioButton lineRdBtn;
	private RadioButton rectangleRdBtn;
	private RadioButton circleRdBtn;
	private CheckBox fillCheckBox;
	private Button showIntersectionsBtn;
	private ColorPicker colorPicker;
	
	//Bottom bar
	private ToolBar bottomBar;
	private Button saveDataBtn;
	private Button loadDataBtn;
	private Button clearScreenBtn;
	private Label cursorPositionLbl;

	private Scene scene;
	private FileChooser fileBrowser;
	
	private MyPoint lastSelectedPoint;
	private MyPoint currentSelectedPoint;

	public void init() {
		System.out.println("init");
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		setupGUI(primaryStage, 750, 750, "GeoViz");
		addEvents(scene);
	}

	/**
	 * @param primaryStage
	 * @param width
	 * @param height
	 * @param title
	 * 				<br><br>
	 * 				create the GUI
	 */
	public void setupGUI(Stage primaryStage, int width, int height, String title) {
		root = new BorderPane();
		scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
		
		topBar = createTopMenu(root);
		bottomBar = createBottomMenu(root);
		
		root.setCenter(createGrid(root, 10, 1, Color.LIGHTGRAY));
		root.setTop(topBar);
		root.setBottom(bottomBar);
		
		primaryStage.setResizable(false);
		
		primaryStage.show();
	}

	/**
	 * @param root
	 * @param spacing
	 * @param lineThickness
	 * @param strokeColor
	 * @return a Pane with a newly created coordinate grid
	 */
	public Pane createGrid(BorderPane root, int spacing, int lineThickness, Color strokeColor) {

		Pane grid = new Pane();

		int columnCount = (int) (scene.getWidth() / spacing);
		int rowCount = (int) (scene.getHeight() / spacing);

		for (int i = 0; i < columnCount; i++) {
			Line line = new Line(i * spacing, 0, i * spacing, scene.getHeight());
			line.setStrokeWidth(lineThickness);
			line.setStroke(strokeColor);
			grid.getChildren().add(line);
		}

		for (int i = 0; i < rowCount; i++) {
			Line line = new Line(0, i * spacing, scene.getWidth(), i * spacing);
			line.setStrokeWidth(lineThickness);
			line.setStroke(strokeColor);
			grid.getChildren().add(line);
		}

		return grid;
	}

	/**
	 * @param borderPane
	 * @return TopMenu with all buttons etc
	 */
	public ToolBar createTopMenu(BorderPane borderPane) {
		ToolBar topBar;
		// Radiobuttons
		lineRdBtn = new RadioButton("Line");
		rectangleRdBtn = new RadioButton("Rectangle");
		circleRdBtn = new RadioButton("Circle");
		lineRdBtn.setSelected(true);

		// Togglegroup
		ToggleGroup shapeGroup = new ToggleGroup();
		lineRdBtn.setToggleGroup(shapeGroup);
		rectangleRdBtn.setToggleGroup(shapeGroup);
		circleRdBtn.setToggleGroup(shapeGroup);

		// Checkboxes
		fillCheckBox = new CheckBox("Fill");

		// Buttons
		showIntersectionsBtn = new Button("Show Intersections");
		showIntersectionsBtn.setOnMouseClicked(new IntersectionsOnClickEvent(this));

		// Colorpickers
		colorPicker = new ColorPicker(Color.BLACK);

		// Toolbar
		topBar = new ToolBar(lineRdBtn, rectangleRdBtn, circleRdBtn, fillCheckBox, showIntersectionsBtn, colorPicker);
		
		return topBar;
	}

	/**
	 * 
	 * @param borderPane
	 * @return BottomMenu with all buttons etc
	 */
	@SuppressWarnings("static-access")
	public ToolBar createBottomMenu(BorderPane borderPane) {
		ToolBar bottomBar;
		saveDataBtn = new Button("Save Data");
		saveDataBtn.setOnMouseClicked(new LoadPointsOnClickEvent(this, false));
		loadDataBtn = new Button("Load Data");
		loadDataBtn.setOnMouseClicked(new LoadPointsOnClickEvent(this, true));
		clearScreenBtn = new Button("Clear Screen");
		HBox spacer = new HBox();
		spacer.setHgrow(spacer, Priority.ALWAYS);
		cursorPositionLbl = new Label("");

		clearScreenBtn.setOnMouseClicked(new ClearScreenOnClickEvent(this));

		bottomBar = new ToolBar(saveDataBtn, loadDataBtn, clearScreenBtn, spacer, cursorPositionLbl);
		return bottomBar;
	}

	/**
	 * @param scene <br>
	 *              <br>
	 *              Add events to buttons etc.
	 */
	public void addEvents(Scene scene) {
		root.getCenter().setOnMouseClicked(new DrawPointOnClickEvent(this));
		root.getCenter().setOnMouseMoved(new MouseMoveEvent(this));
	}
	
	/**
	 * @param path	<br><br>
	 * 				Loads and draws all points from a the file located at 'FILE_PATH'
	 */
	public void loadPointsFromFile(String path)
	{
		//remove all points and shapes ect
		Pane grid = ((Pane)root.getCenter());
		grid.getChildren().removeAll(getPoints());
		grid.getChildren().removeAll(getLines());
		grid.getChildren().removeAll(getRectangles());
		grid.getChildren().removeAll(getCircles());
		grid.getChildren().removeAll(getIntersections());
		points.clear();
		lines.clear();
		rectangles.clear();
		circles.clear();
		intersections.clear();

		//load points and add them to the file
		List<MyPoint> pointsLoaded = new ArrayList<MyPoint>();
		pointsLoaded.addAll(DataLoader.loadPoints(path));
		
		for (MyPoint p : pointsLoaded) {
			MyPoint point = new MyPoint(p.getX(), p.getY(), POINT_RADIUS);
			point.setOnMouseClicked(new DrawShapeOnClick(this));
			points.add(point);
		}		
		grid.getChildren().addAll(points);
	}

	// GETTER
	public List<MyPoint> getPoints() {
		return points;
	}

	public BorderPane getRoot() {
		return root;
	}

	public ToolBar getTopBar() {
		return topBar;
	}

	public ToolBar getBottomBar() {
		return bottomBar;
	}

	public Scene getScene() {
		return scene;
	}

	public RadioButton getLineRdBtn() {
		return lineRdBtn;
	}

	public RadioButton getRectangleRdBtn() {
		return rectangleRdBtn;
	}

	public RadioButton getCircleRdBtn() {
		return circleRdBtn;
	}

	public CheckBox getFillCheckBox() {
		return fillCheckBox;
	}

	public Button getShowIntersectionsBtn() {
		return showIntersectionsBtn;
	}

	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	public Button getSaveDataBtn() {
		return saveDataBtn;
	}

	public Button getLoadDataBtn() {
		return loadDataBtn;
	}

	public Button getClearScreenBtn() {
		return clearScreenBtn;
	}

	public Label getCursorPositionLbl() {
		return cursorPositionLbl;
	}

	public MyPoint getLastSelectedPoint() {
		return lastSelectedPoint;
	}

	public void setLastSelectedPoint(MyPoint lastSelectedPoint) {
		this.lastSelectedPoint = lastSelectedPoint;
	}

	public MyPoint getCurrentSelectedPoint() {
		return currentSelectedPoint;
	}

	public void setCurrentSelectedPoint(MyPoint currentSelectedPoint) {
		this.currentSelectedPoint = currentSelectedPoint;
	}

	public List<MyLine> getLines() {
		return lines;
	}

	public List<MyCircle> getCircles() {
		return circles;
	}

	public List<MyRectangle> getRectangles() {
		return rectangles;
	}

	public List<MyPoint> getIntersections() {
		return intersections;
	}

	
	public FileChooser getFileBrowser() {
		return fileBrowser;
	}

	public void setFileBrowser(FileChooser fileBrowser) {
		this.fileBrowser = fileBrowser;
	}

	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
