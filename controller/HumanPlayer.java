package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Board;
import model.Piece;
import model.Player;

import java.util.ArrayList;
import java.util.Optional;

public class HumanPlayer extends Player {

    @Override
    public Board.Position makeMove(Board b, model.Piece.Color c) {
        Board.Position pos = new Board.Position(0,0);

        return pos;
    }

    @Override
    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

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
        alert.setTitle("Pick color");
        alert.setContentText("Do you want to pick color now?");

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
