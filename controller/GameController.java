package application;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameController {
	private Stage stage;
	private Scene scene;
	private Group root;
	private Sprite background;
	
	public GameController(Stage getStage) {
		stage = getStage;
		root = new Group();
		scene = new Scene(root, 800, 800);
		stage.setScene(scene);
		stage.setTitle("GOMOKU - swap2");
		
		background = new Sprite(0, 0, "file:E:\\Studia\\pszt\\GomokuAI\\graphic/tmp2.jpg", root);
	}

}
