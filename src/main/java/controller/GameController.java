package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import view.GameBoard;
import view.InfoDialog;
import view.Sidebar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class GameController implements Observer
{
	private long startTime = 0;
	int numberOfExperiments = 0;

	Semaphore mutexSend = new Semaphore(0);

	Sidebar sidebar = new Sidebar(this);
	GameBoard gameBoard = new GameBoard(600, 600, this);

	GametableThread gametableThread;
	Gametable gametable = new Gametable();
	Player player1 = new HumanPlayer(this);
	Player player2 = new HumanPlayer(this);

	private static class Lock
	{
		int number;
	}
	final Lock currentMove = new Lock();// = 0;

	view.Piece.PieceType pieceColor;

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
		root.setCenter(gameBoard);

		gametable.setFirstPlayer(player1);
		gametable.setSecondPlayer(player2);

		gametable.addObserver(this);
	}

	public void setPlayer1(Player p)
	{
		player1 = p;
		gametable.setFirstPlayer(player1);
		System.out.println(p);
	}

	public void setPlayer2(Player p)
	{
		player2 = p;
		gametable.setSecondPlayer(player2);
		System.out.println(p);
	}

	public void update(Observable o, Object ob)
	{
		if( gametable.getTurnNumber() > 10 ) {
			gametable.setFirstPlayer(player1);
			gametable.setSecondPlayer(player2);
		}

		long elapsedTime = System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();

		model.Board board = gametable.getBoard();

		/*Heuristic2 h = new Heuristic2();
		int points;
		if( gametable.getCurrentPlayerSlot().color == Piece.Color.Black )
			points = h.getPoints(board, Piece.Color.White);
		else
			points = h.getPoints(board, Piece.Color.Black);*/

		for(int i=0; i<board.size(); ++i)
		for(int j=0; j<board.size(); ++j)
		if(board.getPiece(i, j) != null && gameBoard.isEmpty(j*15 + i))
		{
			view.Piece.PieceType color = (board.getPiece(i, j).getColor() == model.Piece.Color.Black) ?
					view.Piece.PieceType.BLACK : view.Piece.PieceType.WHITE;

			int position = j*15 + i;

			gameBoard.addPiece(position, color);
		}

		if( gametable.getTurnNumber() > 10 ) {
			try {
				FileWriter fileWriter = new FileWriter("experiments_time.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(elapsedTime+"\n");
				bufferedWriter.close();
				/*fileWriter = new FileWriter("experiments_heuristic.txt", true);
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(points+"\n");
				bufferedWriter.close();*/
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void setCurrentPlayer(Player p, Piece.Color c){
		String color;
		if( c == c.White ) {
			color = "white";
			pieceColor = view.Piece.PieceType.WHITE;
		}
		else {
			color = "black";
			pieceColor = view.Piece.PieceType.BLACK;
		}

		if( p.equals(player1) )
			sidebar.setCurrentInfoText("Player1's turn - " + color);
		else
			sidebar.setCurrentInfoText("Player2's turn - " + color);
	}

	public void startGame(){
		numberOfExperiments = 0;

		sidebar.setCurrentInfoText("Game is starting...");
		if( gametableThread != null )
			gametableThread.quit();
		gameBoard.cleanBoard();

		gametableThread = new GametableThread(gametable, this);
		gametableThread.start();
	}

	public void startExperiments(){
		if( numberOfExperiments == 0 )
			numberOfExperiments = 10;

		gametable.setFirstPlayer(new AiRandomPlayer(this));
		gametable.setSecondPlayer(new AiRandomPlayer(this));

		sidebar.setCurrentInfoText("Experiments are starting...");
		if( gametableThread != null )
			gametableThread.quit();
		gameBoard.cleanBoard();

		gametableThread = new GametableThread(gametable, this);

		startTime = System.currentTimeMillis();
		gametableThread.start();
	}

	void stopGame( Gametable.PlayerSlot winner ){
		System.out.println("Winner " + winner);
		if( numberOfExperiments > 0 ) {
			try {
				FileWriter fileWriter = new FileWriter("experiments.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				if (winner != null) {
					if (winner.color == Piece.Color.White)
						if (winner.player == player1) {
							Platform.runLater(() -> sidebar.setCurrentInfoText("Player1 (white) wins!"));
							bufferedWriter.write("Player1\n");
						} else {
							Platform.runLater(() -> sidebar.setCurrentInfoText("Player2 (white) wins!"));
							bufferedWriter.write("Player2\n");
						}
					else if (winner.player == player1) {
						Platform.runLater(() -> sidebar.setCurrentInfoText("Player1 (black) wins!"));
						bufferedWriter.write("Player1\n");
					} else {
						Platform.runLater(() -> sidebar.setCurrentInfoText("Player2 (black) wins!"));
						bufferedWriter.write("Player2\n");
					}
				}
				else {
					Platform.runLater(() -> sidebar.setCurrentInfoText("Draw!"));
					bufferedWriter.write("Draw\n");
				}

				numberOfExperiments--;
				if( numberOfExperiments > 0 ) {
					Platform.runLater(this::startExperiments);
				}

				bufferedWriter.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			if (winner != null) {
				if (winner.color == Piece.Color.White)
					if (winner.player == player1) {
						Platform.runLater(() -> sidebar.setCurrentInfoText("Player1 (white) wins!"));
						Platform.runLater(() -> InfoDialog.showInfoDIalog("End of the game", "Player1 (white) wins!"));
					} else {
						Platform.runLater(() -> sidebar.setCurrentInfoText("Player2 (white) wins!"));
						Platform.runLater(() -> InfoDialog.showInfoDIalog("End of the game", "Player2 (white) wins!"));
					}
				else if (winner.player == player1) {
					Platform.runLater(() -> sidebar.setCurrentInfoText("Player1 (black) wins!"));
					Platform.runLater(() -> InfoDialog.showInfoDIalog("End of the game", "Player1 (black) wins!"));
				} else {
					Platform.runLater(() -> sidebar.setCurrentInfoText("Player2 (black) wins!"));
					Platform.runLater(() -> InfoDialog.showInfoDIalog("End of the game", "Player2 (black) wins!"));
				}
			} else {
				Platform.runLater(() -> sidebar.setCurrentInfoText("Draw!"));
				Platform.runLater(() -> InfoDialog.showInfoDIalog("End of the game", "Draw!"));
			}
		}
	}

	public void forceStopGame(){
		sidebar.setCurrentInfoText("Game is not running...");
		if( gametableThread != null )
			gametableThread.quit();
		gameBoard.cleanBoard();
	}

	public void closeApp(){
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Quit");
		alert.setHeaderText(null);
		alert.setContentText("Do you really want to quit?");

		Optional<ButtonType> result = alert.showAndWait();

		if( result.get() != ButtonType.OK )
			return;

		if( gametableThread != null )
			gametableThread.quit();

		Platform.exit();
	}

	public Board.Position makeMove(){
		synchronized(currentMove) {
			try {
				currentMove.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Tymczasowo, bo jeszcze nie ma obserwatora
		//gameBoard.addPiece(currentMove.number, pieceColor);

		return new Board.Position(currentMove.number%15,currentMove.number/15);
	}

	public void saveMove(int number) {
		currentMove.number = number;

		synchronized(currentMove) {
			currentMove.notify();
		}
	}
}

class GametableThread extends Thread {
	private Gametable gametable;
	private GameController gameController;

	private Gametable.PlayerSlot winner;
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

	Gametable.PlayerSlot getWinner(){
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
