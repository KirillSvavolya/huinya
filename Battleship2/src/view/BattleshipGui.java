package view;

import controller.BattleshipGuiDelegate;
import controller.GuiController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BattleshipGui extends Application {
	// instance of a controller.
	private BattleshipGuiDelegate controller;
	// Gui properties:
	// Settings for the first screen.
	private static final int HORIZONTAL_SIZE = 700;
	private static final int VERTICAL_SIZE = 500;
	private static final int SPACING_BETWEEN_BUTTONS = 30;
	private static final int BUTTONS_WIDTH = 150;
	private static final Background FIRST_SCENE_BACKGROUND = new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY));
	private static final Background BUTTON_BACKGROUND = new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(5.0), Insets.EMPTY));
	// Pointers to help communication with the controller.
	public Stage primaryStage;
	private Scene firstScene;
	private Scene secondScene;
	private Scene thirdScene;
	// Settings for the grid.
	private int rows = 10;
	private int columns = 15;
	// Second scene setting.
	private static final int HORIZONTAL_GRID_SIZE = 400;
	private static final int VERTICAL_GRID_SIZE = 300;
	// Pointers to set visibility of the play buttons.
	private Button playOffline;
	private Button playOnline;
	// Pointers to the grids.
	private Grid playerGrid;
	private Grid opponentGrid;
	// Second screen settings
	private static final int GRID_BOX_WIDTH = 590;
	private static final Background PREP_VBOX_BACKGROUND = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background EMPTY_CELL_BACKGROUND = new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background TAKEN_CELL_BACKGROUND = new Background(new BackgroundFill(Color.VIOLET, CornerRadii.EMPTY, Insets.EMPTY));
	private static final Background GRID_PANE_BACKGROUND = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
	public static final String TAKEN_CELL_BORDER = "-fx-border-width: 1.5 0.5 1.5 0.5; -fx-border-color: black black black black;";
	public static final String EMPTY_CELL_BORDER = "-fx-border-width: 1;-fx-border-color: black;";
	// Third Screen settings.
	private static final int GAME_BOARD_WIDTH = 300;
	private static final int GAME_BOARD_HEIGHT = 200;
	private static final Background GAME_VBOX_BACKGROUND = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
	public static final Background SHOT_CELL_BACKGROUND = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
	public static final String DOT_HEX_COLOR = "#000000";
	
	@Override
	public void init() throws Exception {
		super.init();
		// get the controller object.
		this.controller = GuiController.sharedInstance;
		this.controller.setView(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		// Set title of the state.
		primaryStage.setTitle("Battleship");
		// Create the first scene.
		setFirstScene();
		// Create the second scene.
		setSecondScene();
		// Pass the scene to the stage.
		primaryStage.setScene(this.firstScene);
		// Forbid resizing.
		primaryStage.setResizable(false);
		// show the stage.
		primaryStage.show();
		
		
	}
	public Scene getFirstScene() {
		return this.firstScene;
	}
	public Scene getSecondScene() {
		return this.secondScene;
	}
	public Scene getThirdScene() {
		return this.thirdScene;
	}
	public Grid getPlayerGrid() {
		return this.playerGrid;
	}
	public Grid getOpponentGrid() {
		return this.opponentGrid;
	}
	
/**
 * Method to create the first scene which should be displayed to the user.
 * @ensures the first scene is created.
 */
	private void setFirstScene() {
		// Create play offline button.
		Button play = new Button("Play");
		// Set the background of the button.
		play.setBackground(BUTTON_BACKGROUND);
		// Set the width.
		play.setPrefWidth(BUTTONS_WIDTH);
		// Set the actions. (call the controller).
		play.setOnAction(actionEvent -> controller.playClicked());
		// Add buttons to vbox.
		VBox vbox = new VBox(SPACING_BETWEEN_BUTTONS, play);
		// Set background.
		vbox.setBackground(FIRST_SCENE_BACKGROUND);
		// Set buttons Alignment for the VBox.
		vbox.setAlignment(Pos.CENTER);
		// Create new scene.
		Scene scene =  new Scene(vbox, HORIZONTAL_SIZE, VERTICAL_SIZE);
		this.firstScene = scene;
	}
	
/**
 * Creates the second scene (game preparation). The scene contains four buttons and an empty grid.
 * @ensures the second scene is created.
 */
	public void setSecondScene() {
		HBox root = new HBox();
		// Get both vertical boxes.
		VBox gridBox = createGridBox();
		VBox buttonBox = createButtonBox();
		// Add both vertical boxes to the horizontal pane.
		root.getChildren().add(gridBox);
		root.getChildren().add(buttonBox);
		// Set the scene.
		this.secondScene = new Scene(root, HORIZONTAL_SIZE, VERTICAL_SIZE);
		
	}
	
	
	// Creates the third scene.
	public void setThirdScene() {
		VBox playerGridBox = createPlayerGridBox();
		VBox opponentGridBox = createOpponentGridBox();
		// Add boxes to the root node.
		HBox root = new HBox();
		root.getChildren().add(playerGridBox);
		root.getChildren().add(opponentGridBox);
		this.thirdScene = new Scene(root, HORIZONTAL_SIZE, VERTICAL_SIZE);
	}
	
	// Create vertical box to hold player board during the game.
	private VBox createPlayerGridBox() {
		VBox vbox = new VBox();
		vbox.setBackground(GAME_VBOX_BACKGROUND);
		vbox.setPrefHeight(VERTICAL_SIZE);
		vbox.setPrefWidth(HORIZONTAL_SIZE / 2);
		GridPane playerBoard = new GridPane();
		playerBoard.setPrefHeight(GAME_BOARD_HEIGHT);
		playerBoard.setPrefWidth(GAME_BOARD_WIDTH);
		playerBoard.setBackground(GAME_VBOX_BACKGROUND);
		playerBoard.setAlignment(Pos.CENTER);
		// fill the board with empty panes and get array pointers
		Grid gr = fillGrid(playerBoard, GAME_BOARD_HEIGHT, GAME_BOARD_WIDTH, false);
		this.playerGrid = gr;
		// pass the grid to the box.
		vbox.getChildren().add(playerBoard);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
	}
	
	// Create vertical box to hold opponent board during the game.
	private VBox createOpponentGridBox() {
		// Create a vertical box.
		VBox vbox = new VBox();
		vbox.setPrefHeight(VERTICAL_SIZE);
		vbox.setPrefWidth(HORIZONTAL_SIZE / 2);
		// Set it's background.
		vbox.setBackground(GAME_VBOX_BACKGROUND);
		// Create a grid, fill it and set the pointer to a "quick access array" returned.
		GridPane pane = new GridPane();
		pane.setPrefHeight(GAME_BOARD_HEIGHT);
		pane.setPrefWidth(GAME_BOARD_WIDTH);
		pane.setBackground(GAME_VBOX_BACKGROUND);
		pane.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		this.opponentGrid = fillGrid(pane, GAME_BOARD_HEIGHT, GAME_BOARD_WIDTH, true);
		vbox.getChildren().add(pane);
		return vbox;
	}

/**
 * Creates a box to hold a grid on the second scene, adds grid with 150 panes to it and returns the result.
 * @return vertical box, which holds an empty grid.
 */
	private VBox createGridBox() {
		VBox gridBox = new VBox();
		// Set properties for the first vertical box.
		gridBox.setPrefHeight(VERTICAL_SIZE);
		gridBox.setPrefWidth(GRID_BOX_WIDTH);
		gridBox.setBackground(PREP_VBOX_BACKGROUND);
		// Create new grid pane.
		GridPane grid = new GridPane();
		// Set properties of the grid.
		grid.setBackground(GRID_PANE_BACKGROUND);
		grid.setAlignment(Pos.CENTER);
		grid.setPrefWidth(HORIZONTAL_GRID_SIZE);
		grid.setPrefHeight(VERTICAL_GRID_SIZE);
		// Fill the grid pane and get pointer to the array object.
		Grid gr = fillGrid(grid, VERTICAL_GRID_SIZE, HORIZONTAL_GRID_SIZE, false);
		// assign pointer to the array.
		this.playerGrid = gr;
		// Add grid to the grid box.
		gridBox.getChildren().add(grid);
		gridBox.setAlignment(Pos.CENTER_RIGHT);
		return gridBox;
	}

/**
 * Returns vertical box holding all the buttons available for the second scene.
 * @return vertical box with four buttons.
 */
	private VBox createButtonBox() {
		VBox buttonBox = new VBox(SPACING_BETWEEN_BUTTONS);
		// Set properties for the second vertical box.
		buttonBox.setPrefHeight(VERTICAL_SIZE);
		buttonBox.setPrefWidth(HORIZONTAL_SIZE - HORIZONTAL_GRID_SIZE);
		buttonBox.setBackground(PREP_VBOX_BACKGROUND);
		// Create buttons and add them to the button box.
		Button placeShips = new Button("Place ships");
		Button playOffline = new Button("Play Offline");
		Button playOnline = new Button("Play Online");
		Button back = new Button("Return");
		// Set preferred size for buttons.
		placeShips.setPrefWidth(BUTTONS_WIDTH);
		playOffline.setPrefWidth(BUTTONS_WIDTH);
		playOnline.setPrefWidth(BUTTONS_WIDTH);
		back.setPrefWidth(BUTTONS_WIDTH);
		// Add listeners which will call the controller.
		placeShips.setOnAction(e -> controller.placeShipsClicked());
		back.setOnAction(e -> controller.returnClicked());
		playOffline.setOnAction(e -> controller.playOfflineClicked());
		playOnline.setOnAction(e -> controller.playOnlineClicked());
		// Disable play Buttons.
		playOffline.setDisable(true);
		playOnline.setDisable(true);
		// Assign pointers to play buttons.
		this.playOffline = playOffline;
		this.playOnline = playOnline;
		// Set background for buttons
		placeShips.setBackground(BUTTON_BACKGROUND);
		back.setBackground(BUTTON_BACKGROUND);
		playOffline.setBackground(BUTTON_BACKGROUND);
		playOnline.setBackground(BUTTON_BACKGROUND);
		// Add buttons to the button box.
		buttonBox.getChildren().add(placeShips);
		buttonBox.getChildren().add(playOnline);
		buttonBox.getChildren().add(playOffline);
		buttonBox.getChildren().add(back);
		// Set alignment for the button box.
		buttonBox.setAlignment(Pos.CENTER_LEFT);
		return buttonBox;
	}
	
	public Button getPlayOfflineButton() {
		return this.playOffline;
	}
	
	public Button getPlayOnlineButton() {
		return this.playOnline;
	}
	

/**
 * Fill the grid pane with panes and returns an array with pointers to panes for future
 * use.
 * @requires window is open.
 * @param grid of type GridPane to fill.
 * @param grid_height height of the grid.
 * @param grid_width width of the grid.
 * @param isOpponentField boolean indicating if the gridPane passed represents the opponent field. If true,
 * the listeners are added, which listen for mouse event and call controller.fireMisile(row, column) if the pane
 * was clicked.
 * @return a grid object containing an array with pointers to the panes.
 */
	private Grid fillGrid(GridPane grid, double grid_height, double grid_width, boolean isOpponentField) {
		// Grid array to store the rectangles in the array form.
		Grid gr = new Grid();
		Pane rect;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				rect = new Pane();
				rect.setPrefHeight((double) grid_height / 10);
				rect.setPrefWidth((double) grid_width / 15);
				rect.setBackground(EMPTY_CELL_BACKGROUND);
				rect.setStyle(EMPTY_CELL_BORDER);
				if (isOpponentField) {
					addListenerToMouseClick(rect, i, j);
				}
				gr.add(rect, i, j);
				grid.add(rect, j, i, 1, 1);
			}
		}
		return gr;
	}
	
	// Adds listener to mouse clicks on opponent field.
	private void addListenerToMouseClick(Pane rect, int i, int j) {
		rect.setOnMouseClicked(e -> {
			controller.fireMissile(i, j);
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
