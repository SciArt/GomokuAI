package model;

import java.util.ArrayList;

public class Board
{
	private final Piece[][] board;
	private final int size;
	
	public static class Position
	{
		public int x, y;//encapsulate
		
		@Override
		public boolean equals(Object o)
		{		
			if(o == null) return false;
			if(!(o instanceof Position)) return false;
			
			Position p = (Position)o;
			
			if(p.x != this.x) return false;
			if(p.y != this.y) return false;
			
			return true;
		}
		
		public Position(Position p)
		{
			this.x = p.x;
			this.y = p.y;
		}
		
		public Position(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public static class Move
	{
		private ArrayList<Board.Position> black;
		private ArrayList<Board.Position> white;
		
		public ArrayList<Board.Position> getMove(Piece.Color c)
		{
			if(c == Piece.Color.Black) return black;
			if(c == Piece.Color.White) return white;
			
			return null;
		}
		
		public Move(ArrayList<Board.Position> black, ArrayList<Board.Position> white)
		{
			this.black = black;
			this.white = white;
		}
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isValidPosition(int x, int y)
	{
		if(x < 0 || x >= size) return false;
		if(y < 0 || y >= size) return false;
		
		return true;
	}
	
	public boolean isValidPosition(Board.Position p)
	{
		return this.isValidPosition(p.x, p.y);
	}
	
	public boolean isValidMove(Board.Move move, Piece.Color c)
	{
		Board test = new Board(this);
		
		for(Board.Position p : move.getMove(c)) 
		{
			if(!test.isValidPosition(p)) return false;
			if(test.getPiece(p) != null) return false;
			test.placePiece(p.x, p.y, new Piece(c));
		}
		
		return true;
	}
	
	public Piece getPiece(int x, int y)
	{
		return board[x][y];
	}
	
	public Piece getPiece(Board.Position xy)
	{
		return board[xy.x][xy.y];
	}
	
	public void placePiece(int x, int y, Piece p)
	{
		board[x][y] = p;
	}
	
	public void removePiece(int x, int y)
	{
		board[x][y] = null;
	}
	
	public void movePiece(int xs, int ys, int xf, int yf)
	{
		board[xf][yf] = board[xs][ys];
		board[xs][ys] = null;
	}
	
	public Board(Board b)
	{
		size = b.size;
		
		board = new Piece[size][size];
		for(int i=0;i<size;i++)for(int j=0;j<size;j++)
		if(b.getPiece(i,j) != null) 
			this.placePiece(i, j, new Piece(b.getPiece(i,j)));

	}
	
	public Board(int n)
	{
		board = new Piece[n][n];
		size = n;
	}
}
