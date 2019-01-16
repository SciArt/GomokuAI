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

public class HumanPlayer extends Player {

    private GameController gameController;
	
	public HumanPlayer(GameController g){

	    this.gameController = g;
	}

    public Board.Position makeMove(Board b, model.Piece.Color c) {

        return gameController.makeMove();
    }

    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        for(int i = 0; i < black_count; ++i) {
        	black.add(gameController.makeMove());
        }

        for(int i = 0; i < white_count; ++i) {
        	white.add(gameController.makeMove());
        }

        return new Board.Move(black, white);
    }

    public boolean doPickColor(Board b) {

	    FutureTask<Optional<ButtonType>> futureTask = new FutureTask(
                new pickColorPrompt1()
        );
        Platform.runLater(futureTask);
        Optional<ButtonType> result = Optional.empty();
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return result.get() == ButtonType.OK;
    }

    public Piece.Color pickColor(Board b) {

        FutureTask<Piece.Color> futureTask = new FutureTask(
                new pickColorPrompt2()
        );
        Platform.runLater(futureTask);
        Piece.Color result = null;
        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    class pickColorPrompt1 implements Callable<Optional<ButtonType>> {

	    @Override public Optional<ButtonType> call() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pick color");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to pick color now?");
            return alert.showAndWait();
        }
    }

    class pickColorPrompt2 implements Callable<Piece.Color> {

	    @Override public Piece.Color call() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pick color");
            alert.setHeaderText(null);
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
