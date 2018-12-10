package application;

import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		new GameController(primaryStage);
		primaryStage.setFullScreenExitHint("");

		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}