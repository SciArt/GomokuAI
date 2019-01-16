package controller;

import model.Board;
import model.Piece;
import model.Player;

import java.util.ArrayList;
import java.util.Random;

public class AiRandomPlayer extends Player {

    private Random r = new Random();

    public AiRandomPlayer(){

    }

    public Board.Position makeMove(Board b, model.Piece.Color c) {

        Board.Position pos = new Board.Position(Math.abs(r.nextInt()%15), Math.abs(r.nextInt()%15) );

        return pos;
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

        return r.nextBoolean();
    }

    public Piece.Color pickColor(Board b) {

        if( r.nextBoolean() )
            return Piece.Color.White;
        else
            return Piece.Color.Black;
    }
}