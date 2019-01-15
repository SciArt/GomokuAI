package controller;

import javafx.application.Platform;
import model.*;

import java.util.ArrayList;

public class AiHeuristicPlayer extends Player {

    private GameController gameController;
    private AlfaBetaAlgorithm alfabeta;

    public AiHeuristicPlayer(GameController g, int depth){
        this.gameController = g;
        alfabeta = new AlfaBetaAlgorithm(depth);
    }

    public Board.Position makeMove(Board b, model.Piece.Color c) {
        //Platform.runLater(() ->gameController.setCurrentPlayer(this, c));

        alfabeta.minimaxAlfabeta(b, c);

        return alfabeta.getTheBestMove();
    }

    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        for(int i = 0; i < black_count; ++i) {
            black.add(makeMove(b, Piece.Color.Black));
        }

        //Platform.runLater(() -> gameController.setCurrentPlayer(this, Piece.Color.White));
        for(int i = 0; i < white_count; ++i) {
            white.add(makeMove(b, Piece.Color.White));
        }

        return new Board.Move(black, white);
    }

    public boolean doPickColor(Board b) {
        //Platform.runLater(() -> gameController.setCurrentPlayer(this, null));
        alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        b.placePiece(alfabeta.getTheBestMove().x,alfabeta.getTheBestMove().y,new Piece(Piece.Color.White));

        Heuristic2 h = new Heuristic2();
        int hBlack = h.getPoints(b, Piece.Color.Black);
        int hWhite = h.getPoints(b, Piece.Color.White);

        return (hBlack - hWhite != 0);
    }
    
    public Piece.Color pickColor(Board b) {
        //Platform.runLater(() -> gameController.setCurrentPlayer(this, null));
        alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        b.placePiece(alfabeta.getTheBestMove().x,alfabeta.getTheBestMove().y,new Piece(Piece.Color.White));

        if( alfabeta.minimaxAlfabeta(b, Piece.Color.White) > alfabeta.minimaxAlfabeta(b, Piece.Color.Black) ) {
            return Piece.Color.White;
        }
        else {
            return Piece.Color.Black;
        }
    }
    
    public void setDepth(int newDepth) {
    	alfabeta.setDepth(newDepth);
    }
}
