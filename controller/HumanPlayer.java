package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Board;
import model.Piece;
import model.Player;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

public class HumanPlayer extends Player {

	//Semaphore semaphore;
	GameController gameController;
	
	public HumanPlayer(GameController g){
		this.gameController = g;
	}

    public Board.Position makeMove(Board b, model.Piece.Color c) {
        Platform.runLater(() ->gameController.setCurrentPlayer(this, c));
        
        Board.Position pos = gameController.makeMove();
        
        System.out.println("Pos = " + pos.x + ", " + pos.y);
        
        return pos;
    }

    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        System.out.println("dziala");

        Platform.runLater(() ->gameController.setCurrentPlayer(this, Piece.Color.Black));
        for(int i = 0; i < black_count; ++i) {
        	black.add(gameController.makeMove());
        }

        Platform.runLater(() -> gameController.setCurrentPlayer(this, Piece.Color.White));
        for(int i = 0; i < white_count; ++i) {
        	white.add(gameController.makeMove());
        }
        
        /*
        gameController.setPlayer(this);

        gameController.setColor(Piece.PieceType.BLACK);
        for(int i = 0; i < black_count; ++i){
            gameController.makeMove
        }

        GameController.setColor(Piece.PieceType.WHITE);
        for(int i = 0; i < white_count; ++i){

        }
        */

        return new Board.Move(black, white);
    }

    public boolean doPickColor(Board b) {

        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
System.out.println("OMG WTF");
        alert.setTitle("Pick color");
        alert.setContentText("Do you want to pick color now?");
System.out.println("OMG WTF");
        Optional<ButtonType> result = alert.showAndWait();*/

        FutureTask<Optional<ButtonType>> futureTask = new FutureTask(
                new pickColorPrompt1()
        );
        Platform.runLater(futureTask);
        Optional<ButtonType> result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result.get() == ButtonType.OK;
    }

    public Piece.Color pickColor(Board b) {
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pick color");
        alert.setContentText("Which color do you want to pick?");

        ButtonType buttonTypeOne = new ButtonType("White");
        ButtonType buttonTypeTwo = new ButtonType("Black");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);*/

        FutureTask<Piece.Color> futureTask = new FutureTask(
                new pickColorPrompt2()
        );
        Platform.runLater(futureTask);
        Piece.Color result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    class pickColorPrompt1 implements Callable<Optional<ButtonType>> {
        @Override public Optional<ButtonType> call() throws Exception {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pick color");
            alert.setContentText("Do you want to pick color now?");
            Optional<ButtonType> result = alert.showAndWait();
            return result;
        }
    }

    class pickColorPrompt2 implements Callable<Piece.Color> {
        @Override public Piece.Color call() throws Exception {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pick color");
            alert.setContentText("Which color do you want to pick?");

            ButtonType buttonTypeOne = new ButtonType("White");
            ButtonType buttonTypeTwo = new ButtonType("Black");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            if( result.get() == buttonTypeOne )
                return Piece.Color.White;
            else
                return Piece.Color.Black;
        }
    }
}
