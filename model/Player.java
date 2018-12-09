package model;

public abstract class Player 
{
	public abstract Board.Position makeMove(Board b, Piece.Color c);
	public abstract Board.Move makeMove(Board b, int black_count, int white_count);
	public abstract boolean doPickColor(Board b);
	public abstract Piece.Color pickColor(Board b);
}
