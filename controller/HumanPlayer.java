package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Board;
import model.Piece;
import model.Player;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class HumanPlayer extends Player {

	//Semaphore semaphore;
	GameController gameController;
	
	public HumanPlayer(GameController g){
		this.gameController = g;
	}
	
    @Override
    public Board.Position makeMove(Board b, model.Piece.Color c) {
        gameController.setCurrentPlayer(this, c);
        
        Board.Position pos = gameController.makeMove();
        
        System.out.println("Pos = " + pos.x + ", " + pos.y);
        
        return pos;
    }

    @Override
    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        System.out.println("dziala");
        
        gameController.setCurrentPlayer(this, Piece.Color.Black);
        for(int i = 0; i < black_count; ++i) {
        	black.add(gameController.makeMove());
        }
        
        gameController.setCurrentPlayer(this, Piece.Color.White);
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

    @Override
    public boolean doPickColor(Board b) {
    	
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
System.out.println("OMG WTF");
        alert.setTitle("Pick color");
        alert.setContentText("Do you want to pick color now?");
System.out.println("OMG WTF");
        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

    @Override
    public Piece.Color pickColor(Board b) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pick color");
        alert.setContentText("Which color do you want to pick?");

        ButtonType buttonTypeOne = new ButtonType("White");
        ButtonType buttonTypeTwo = new ButtonType("Black");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return Piece.Color.White;
        }
        else {
            return Piece.Color.Black;
        }
    }
}
