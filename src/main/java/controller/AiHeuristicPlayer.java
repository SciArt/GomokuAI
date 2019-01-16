package controller;

import model.*;

import java.util.ArrayList;

public class AiHeuristicPlayer extends Player {

    private AlfaBetaAlgorithm alfabeta;

    public AiHeuristicPlayer(int depth){

        alfabeta = new AlfaBetaAlgorithm(depth);
    }

    public Board.Position makeMove(Board b, model.Piece.Color c) {

        alfabeta.minimaxAlfabeta(b, c);

        return alfabeta.getTheBestMove();
    }

    public Board.Move makeMove(Board b, int black_count, int white_count) {

        ArrayList<Board.Position> black = new ArrayList<>();
        ArrayList<Board.Position> white = new ArrayList<>();

        for(int i = 0; i < black_count; ++i) {
            black.add(makeMove(b, Piece.Color.Black));
        }

        for(int i = 0; i < white_count; ++i) {
            white.add(makeMove(b, Piece.Color.White));
        }

        return new Board.Move(black, white);
    }

    public boolean doPickColor(Board b) {

        alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        b.placePiece(alfabeta.getTheBestMove().x,alfabeta.getTheBestMove().y,new Piece(Piece.Color.White));

        Heuristic h = new Heuristic();
        int hBlack = h.getPoints(b, Piece.Color.Black);
        int hWhite = h.getPoints(b, Piece.Color.White);

        return (hBlack - hWhite != 0);
    }
    
    public Piece.Color pickColor(Board b) {

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
