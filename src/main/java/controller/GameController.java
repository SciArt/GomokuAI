package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Board;
import model.Gametable;
import model.Piece;
import model.Player;
import view.GameBoard;
import view.Sidebar;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class GameController implements Observer
{
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

	public void setPlayer1AsHuman(){
		player1 = new HumanPlayer(this);
		gametable.setFirstPlayer(player1);
		System.out.println("setPlayer1AsHuman");
	}
	public void setPlayer1AsRandom(){
		player1 = new AiRandomPlayer(this);
		gametable.setFirstPlayer(player1);
		System.out.println("setPlayer1AsRandom");
	}
	public void setPlayer1AsAI(int depth){
		player1 = new AiHeuristicPlayer(this, depth);
		gametable.setFirstPlayer(player1);
		System.out.println("setPlayer1AsAI");
	}

	public void setPlayer2AsHuman(){
		player2 = new HumanPlayer(this);
		gametable.setSecondPlayer(player2);
		System.out.println("setPlayer2AsHuman");
	}
	public void setPlayer2AsRandom(){
		player2 = new AiRandomPlayer(this);
		gametable.setSecondPlayer(player2);
		System.out.println("setPlayer2AsRandom");
	}
	public void setPlayer2AsAI(int depth){
		player2 = new AiHeuristicPlayer(this, depth);
		gametable.setSecondPlayer(player2);
		System.out.println("setPlayer2AsAI");
	}

	public void update(Observable o, Object ob)
	{
		model.Board board = gametable.getBoard();
		for(int i=0; i<board.size(); ++i) 
		for(int j=0; j<board.size(); ++j)
		if(board.getPiece(i, j) != null && gameBoard.isEmpty(j*15 + i))
		{
			view.Piece.PieceType color = (board.getPiece(i, j).getColor() == model.Piece.Color.Black) ?
					view.Piece.PieceType.BLACK : view.Piece.PieceType.WHITE;
			
			int position = j*15 + i;
			
			gameBoard.addPiece(position, color);
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
		sidebar.setCurrentInfoText("Game is starting...");
		if( gametableThread != null )
			gametableThread.quit();
		gameBoard.cleanBoard();

		gametableThread = new GametableThread(gametable, this);
		gametableThread.start();
	}

	void stopGame( Piece.Color winner ){
		System.out.println("Winner " + winner);
		if( winner != null ) {
			if (winner == Piece.Color.White)
				Platform.runLater(() -> sidebar.setCurrentInfoText("White wins!"));
			else
				Platform.runLater(() -> sidebar.setCurrentInfoText("Black wins!"));
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
