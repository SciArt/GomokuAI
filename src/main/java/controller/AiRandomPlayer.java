package controller;

import javafx.application.Platform;
import model.Board;
import model.Piece;
import model.Player;

import java.util.ArrayList;
import java.util.Random;

public class AiRandomPlayer extends Player {

    GameController gameController;
    Random r = new Random();

    public AiRandomPlayer(GameController g){
        this.gameController = g;
    }

    public Board.Position makeMove(Board b, model.Piece.Color c) {
        Platform.runLater(() ->gameController.setCurrentPlayer(this, c));

        Board.Position pos = new Board.Position(Math.abs(r.nextInt()%15), Math.abs(r.nextInt()%15) );

        System.out.println("Pos = " + pos.x + ", " + pos.y + " Color = " + c + "| AI");

        return pos;
    }

    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        for(int i = 0; i < black_count; ++i) {
            black.add(makeMove(b, Piece.Color.Black));
        }

        Platform.runLater(() -> gameController.setCurrentPlayer(this, Piece.Color.White));
        for(int i = 0; i < white_count; ++i) {
            white.add(makeMove(b, Piece.Color.White));
        }

        return new Board.Move(black, white);
    }

    public boolean doPickColor(Board b) {

        return r.nextBoolean();
    }

    public Piece.Color pickColor(Board b) {

        if( r.nextBoolean() )
            return Piece.Color.White;
        else
            return Piece.Color.Black;
    }
}