package controller;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Gametable;
import model.Piece;
import model.Player;

public class GameController {

	Sidebar sidebar = new Sidebar(this);

	public GameController(Stage stage) {
		final double width = 840;
		final double height = 640;

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, width, height);

		stage.setScene(scene);

		stage.setMinWidth(width);
		stage.setMinHeight(height);
		stage.setResizable(false);

		stage.setTitle("GomokuAI - swap2");

		root.setRight(sidebar);
		root.setCenter(new GameBoard(600, 600));

		Gametable gametable = new Gametable();
		HumanPlayer player1 = new HumanPlayer();
		HumanPlayer player2 = new HumanPlayer();
		gametable.setFirstPlayer(player1);
		gametable.setSecondPlayer(player2);

		//GametableThread gametableThread = new GametableThread(gametable);
		//gametableThread.start();
	}

	class GametableThread extends Thread {
		Gametable gametable;
		GameController gameController;

		Piece.Color winner;
		boolean running = false;

		GametableThread(Gametable gametable, GameController gameController) {
			this.gametable = gametable;
			this.gameController = gameController;
		}

		public void run(){
			running = true;
			winner = gametable.startGame();
			running = false;
			gameController.stopGame();
		}

		public boolean isRunning(){
			return running;
		}
		public Piece.Color getWinner(){
			return winner;
		}
	}

	void setCurrentPlayer(Player p){

	}

	void startGame(){
		sidebar.setCurrentPlayerText("Game is starting...");
	}
	void stopGame(){

	}
}
