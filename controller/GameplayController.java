package controller;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Sprite;

public class GameplayController {
	private Stage stage;
	private MainMenuController mainMenu;
	private GameController game;
	
	public GameplayController(Stage getStage) {
		stage = getStage;
		mainMenu = new MainMenuController(stage);
		mainMenu.getText().setOnMouseClicked(e -> startGame());
	}
	
	private void startGame() {
		game = new GameController(stage);
		mainMenu = null;
	}

}