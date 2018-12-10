package application;

import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	GameController gameController;

	@Override
	public void start(Stage primaryStage) {

		gameController = new GameController(primaryStage);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
				gameController.closeApp();
			}
		});

		primaryStage.setFullScreenExitHint("");

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}