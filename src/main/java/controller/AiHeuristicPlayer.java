package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;
import model.*;

public class AiHeuristicPlayer extends Player {

    GameController gameController;
    Random r = new Random();
    AlfaBetaAlgorithm alfabeta;
    //Piece.Color color;

    public AiHeuristicPlayer(GameController g, int depth){
        this.gameController = g;
        alfabeta = new AlfaBetaAlgorithm(depth);
    }

    public Board.Position makeMove(Board b, model.Piece.Color c) {
        Platform.runLater(() ->gameController.setCurrentPlayer(this, c));

        int h = alfabeta.minimaxAlfabeta(b, c);
        Board.Position pos = alfabeta.getTheBestMove();
        
        System.out.println("Pos = " + pos.x + ", " + pos.y + " Color = " + c + "| AI Heuristic h=" + h + "\n");

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
        //int hBlack = alfabeta.minimaxAlfabeta(b, Piece.Color.Black);
        //int hWhite = alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        b.placePiece(alfabeta.getTheBestMove().x,alfabeta.getTheBestMove().y,new Piece(Piece.Color.White));

        Heuristic2 h = new Heuristic2();
        int hBlack = h.getPoints(b, Piece.Color.Black);
        int hWhite = h.getPoints(b, Piece.Color.White);

        System.out.println("doPickColor: (" + hBlack + " - " + hWhite + " != 0) == " + (hBlack - hWhite != 0) + "\n");

        return (hBlack - hWhite != 0);

        //return r.nextBoolean();
    }
    /*
    public Piece.Color pickColor(Board b) {

        if( r.nextBoolean() )
            color = Piece.Color.White;
        else
        	color = Piece.Color.Black;
        	
        return color;
    }*/
    
    public Piece.Color pickColor(Board b) {
        //Heuristic2 h = new Heuristic2();

        alfabeta.minimaxAlfabeta(b, Piece.Color.White);
        b.placePiece(alfabeta.getTheBestMove().x,alfabeta.getTheBestMove().y,new Piece(Piece.Color.White));

        if( alfabeta.minimaxAlfabeta(b, Piece.Color.White) > alfabeta.minimaxAlfabeta(b, Piece.Color.Black) ) {
            System.out.println("pickColor: Piece.Color.White\n");
            return Piece.Color.White;
        }
        else {
            System.out.println("pickColor: Piece.Color.Black\n");
            return Piece.Color.Black;
        }

        /*if( r.nextBoolean() )
            return Piece.Color.White;
        else
            return Piece.Color.Black;*/
    }
    
    public void setDepth(int newDepth) {
    	alfabeta.setDepth(newDepth);
    }
}
