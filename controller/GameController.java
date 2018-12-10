package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Gametable;
import model.Piece;
import model.Player;
import view.GameBoard;
import view.Sidebar;

import java.util.Optional;

public class GameController {

	Sidebar sidebar = new Sidebar(this);

	GametableThread gametableThread;
	Gametable gametable = new Gametable();
	HumanPlayer player1 = new HumanPlayer();
	HumanPlayer player2 = new HumanPlayer();

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
		root.setCenter(new GameBoard(600, 600, this));

		gametable.setFirstPlayer(player1);
		gametable.setSecondPlayer(player2);
	}

	void setCurrentPlayer(Player p){
		if( p.equals(player1) )
			sidebar.setCurrentInfoText("Player1's turn");
		else
			sidebar.setCurrentInfoText("Player2's turn");
	}

	public void startGame(){
		sidebar.setCurrentInfoText("Game is starting...");
		if( gametableThread != null )
			gametableThread.quit();
		gametableThread = new GametableThread(gametable, this);
		gametableThread.start();
	}

	void stopGame( Piece.Color winner ){
		if( winner != null ) {
			if (winner == Piece.Color.White)
				sidebar.setCurrentInfoText("White wins!");
			else
				sidebar.setCurrentInfoText("Black wins!");
		}
	}

	public void closeApp(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Quit");
		alert.setContentText("Do you really want to quit?");

		Optional<ButtonType> result = alert.showAndWait();

		if( result.get() != ButtonType.OK )
			return;

		if( gametableThread != null )
			gametableThread.quit();

		Platform.exit();
	}

	public boolean makeMove(int number){
		//Gametable nie jest podpięty jeszcze więc pozwalam na ruch
		return true; // Normalnie to trzeba by było zwrócić info o Piece jaki ma być wstawiony
	}
}

class GametableThread extends Thread {
	private Gametable gametable;
	private GameController gameController;

	private Piece.Color winner;
	private boolean running = false;

	GametableThread(Gametable gametable, GameController gameController) {
		this.gametable = gametable;
		this.gameController = gameController;
	}

	public void run(){
		running = true;
		winner = gametable.startGame();
		running = false;
		gameController.stopGame( winner );
	}

	public boolean isRunning(){
		return running;
	}

	Piece.Color getWinner(){
		if( !isRunning() )
			return winner;
		else
			return null;
	}

	public void quit(){
		System.out.println("Closing thread with gametable...");
		stop(); // :( Trzeba zrobić interrupt i łapać to w Gametable
	}
}
