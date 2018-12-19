package model;

import java.util.*;

public class Gametable extends Observable
{
	private final Board board = new Board(15);
	
	private class PlayerSlot
	{
		Player player;
		Piece.Color color;
	}
	private PlayerSlot first = new PlayerSlot(), second = new PlayerSlot();
	private PlayerSlot current;
	
	private int turn;
	
	public Player getFirstPlayer() 
	{
		return first.player;
	}

	public Player getSecondPlayer() 
	{
		return second.player;
	}
	
	public Board getBoard()
	{
		return new Board(board);
	}
	
	public void setFirstPlayer(Player fir) 
	{
		if(fir == null) return;
		this.first.player = fir;
	}
	
	public void setSecondPlayer(Player sec) 
	{
		if(sec == null) return;
		this.second.player = sec;
	}
	
	public boolean bothPlayersPresent()
	{
		if(first.player != null && second.player != null) return true;
		else return false;
	}
	
	public int getTurnNumber()
	{
		return turn;
	}
//------------------------------------------------------------------------------------------	
	
	private void resetTable()
	{
		for(int i=0;i<board.size();++i) for(int j=0;j<board.size();++j)
			board.removePiece(i, j);
		
		first.color = null;
		second.color = null;
		current = first;
		turn = 0;
	}
	
	private void endTurn()
	{
		++turn;
		
		if(current == first) current = second;
		else if(current == second) current = first;
		
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	private boolean move(Board.Move move, int black_count, int white_count)
	{
		if(move.getMove(Piece.Color.Black).size() != black_count) return false;
		if(move.getMove(Piece.Color.White).size() != white_count) return false;
		
		for(Piece.Color c : Piece.Color.values()) 
			if(!board.isValidMove(move, c)) return false;
		
		for(Piece.Color c : Piece.Color.values()) 
			for(Board.Position pos : move.getMove(c))
				board.placePiece(pos.x, pos.y, new Piece(c));
		
		this.endTurn();
		
		return true;
	}
	
	private boolean move(Board.Position pos, Piece.Color c)
	{
		if(!board.isValidPosition(pos)) return false;
		if(board.getPiece(pos) != null) return false;
		
		board.placePiece(pos.x, pos.y, new Piece(c));
		
		this.endTurn();
		
		return true;
	}
	
	private int recursiveConclusion(Board.Position pos, int x, int y, Piece.Color color)
	{
		Board.Position temp = new Board.Position(pos.x + x, pos.y + y);
		
		if(!board.isValidPosition(temp)) return 0;
		if(board.getPiece(temp) == null) return 0;
		if(board.getPiece(temp).getColor() != color) return 0; 
		
		return this.recursiveConclusion(temp, x, y, color) + 1;
	}
	
	private boolean didConclude(Board.Position pos)
	{
		if(!board.isValidPosition(pos)) return false;
		if(board.getPiece(pos) == null) return false;
		
		Piece.Color color = board.getPiece(pos).getColor();
		
		int i=0, j=0;
		for(int k=0; k<4; k++)
		{
			switch(k)
			{
				case 0:{ i=0; j=1; }break;
				case 1:{ i=1; j=0; }break;
				case 2:{ i=1; j=1; }break;
				case 3:{ i=1; j=-1;}break;
			}
			
			if(this.recursiveConclusion(pos, i, j, color) + this.recursiveConclusion(pos, -i, -j, color) + 1 == 5) return true;
		}
		
		return false;
	}
	//quick fix
	private void requestMove(Player player, int black_count, int white_count)
	{
		Board.Position pos;
		
		for(; black_count > 0; --black_count)
			do pos = player.makeMove(this.getBoard(), Piece.Color.Black);
			while( !move(pos, Piece.Color.Black));
		
		for(; white_count > 0; --white_count)
			do pos = player.makeMove(this.getBoard(), Piece.Color.White);
			while( !move(pos, Piece.Color.White));
	}
	
	private PlayerSlot regularGame()
	{
		Board.Position pos;
		
		current = (first.color == Piece.Color.White) ? first : second;
		
		do
		{
			do pos = current.player.makeMove(this.getBoard(), current.color);
			while( !move(pos, current.color) );
			
		}
		while(!didConclude(pos));
		
		return (current == first) ? second : first;
	}
	
	private PlayerSlot openingGame()
	{
		Board.Move pos;
		do pos = first.player.makeMove(this.getBoard(), 2, 1);
		while( !move(pos, 2, 1));
		
		if(second.player.doPickColor(this.getBoard()))
		{
			do second.color = second.player.pickColor(this.getBoard());
			while(second.color == null);
			first.color = (second.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
			
			return this.regularGame();
		}
		
		do pos = second.player.makeMove(this.getBoard(), 1, 1);
		while( !move(pos, 1, 1) );
		
		do first.color = first.player.pickColor(this.getBoard());
		while(first.color == null);
		second.color = (first.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
		
		return this.regularGame();
	}
	
	private PlayerSlot openingGameB()
	{
		Board.Position pos;
		
		this.requestMove(first.player, 2, 1);
		
		if(second.player.doPickColor(this.getBoard()))
		{
			do second.color = second.player.pickColor(this.getBoard());
			while(second.color == null);
			first.color = (second.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
			
			return this.regularGame();
		}
		
		this.requestMove(second.player, 1, 1);
		
		do first.color = first.player.pickColor(this.getBoard());
		while(first.color == null);
		second.color = (first.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
		
		return this.regularGame();
	}
	
	public Piece.Color startGame()
	{
		if(!bothPlayersPresent()) return null;
		this.resetTable();
		
		return this.openingGameB().color;
	}
	
	/*public static void main(String[] args)
	{
		Gametable g = new Gametable();
		
		ArrayList<Board.Position> black = new ArrayList<Board.Position>(), white = new ArrayList<Board.Position>();
		
		white.add(new Board.Position(1, 1));
		white.add(new Board.Position(2, 2));
		white.add(new Board.Position(3, 3));
		white.add(new Board.Position(4, 4));
		white.add(new Board.Position(5, 5));
		
		Board.Move move = new Board.Move(black, white);
		g.move(move, 0, 5);
		
		System.out.println(g.didConclude(new Board.Position(3,3)));
	}*/
}
