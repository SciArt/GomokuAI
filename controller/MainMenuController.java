package controller;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Sprite;

public class MainMenuController {
	private Stage stage;
	private Scene scene;
	private Group root;
	private Sprite background;
	private Text newGameText;
	private Text quiText;
	
	public MainMenuController(Stage getStage) {
		stage = getStage;
		root = new Group();
		scene = new Scene(root, 800, 800);
		stage.setScene(scene);
		stage.setTitle("GOMOKU - swap2");
		
		background = new Sprite(0, 0, "file:E:\\Studia\\pszt\\GomokuAI\\graphic/tmp.jpg", root);
		newGameText = new Text("NEW GAME");
		newGameText.setX(50);
		newGameText.setY(300);
		newGameText.setFont(Font.font("verdana", FontWeight.BOLD, 50));
		root.getChildren().add(newGameText);
		
		quiText = new Text("QUIT");
		quiText.setX(50);
		quiText.setY(500);
		quiText.setFont(Font.font("verdana", FontWeight.BOLD, 50));
		root.getChildren().add(quiText);
		
		quiText.setOnMouseClicked(e -> Platform.exit());
	}
	
	public Text getText() {
		return newGameText;
	}
	


}
