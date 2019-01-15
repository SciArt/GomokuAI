package model;

import java.util.*;

public class Gametable extends Observable
{
	private final Board board = new Board(15);
	
	public class PlayerSlot
	{
		public String name;
		public Player player;
		public Piece.Color color;

		PlayerSlot(){
		}

		PlayerSlot(Player p, Piece.Color pc)
		{
			player = p;
			color = pc;
		}
		
		PlayerSlot(PlayerSlot ps) {
			player = ps.player;
			color = ps.color;
			name = ps.name;
		}
		
		public String toString()
		{
			return name + " (" + color + ")";
		}
	}
	private PlayerSlot first = new PlayerSlot(), second = new PlayerSlot();
	private PlayerSlot current;
	
	private int turn;
	
	public Gametable()
	{
		first.name = "Player 1";
		second.name = "Player 2";
	}
	
	public Player getFirstPlayer() 
	{
		return first.player;
	}

	public Player getSecondPlayer() 
	{
		return second.player;
	}
	
	public PlayerSlot getCurrentPlayerSlot()
	{
		return current;
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
		
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	private void endMove()
	{
		++turn;
		
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	private void switchCurrent()
	{
		if(current == first) current = second;
		else if(current == second) current = first;
	}
	
	private void setCurrentColor(Piece.Color color)
	{
		current.color = color;
		
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	private void endTurn()
	{
		this.switchCurrent();
		this.endMove();
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
		
	private boolean move(Board.Position pos, Piece.Color c, boolean end_turn)
	{
		if(!board.isValidPosition(pos)) return false;
		if(board.getPiece(pos) != null) return false;
		
		board.placePiece(pos.x, pos.y, new Piece(c));
		
		if(end_turn) this.endTurn();
		else this.endMove();
		
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
	
	private boolean isDraw()
	{
		return (this.turn >= this.board.size() * this.board.size());
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
	private void requestMove(int black_count, int white_count)
	{
		Board.Position pos;
		Piece.Color temp = current.color;
		
		this.setCurrentColor(Piece.Color.Black);
		for(; black_count > 0; --black_count)
			do pos = current.player.makeMove(this.getBoard(), Piece.Color.Black);
			while( !move(pos, Piece.Color.Black, false));
		
		this.setCurrentColor(Piece.Color.White);
		for(; white_count > 0; --white_count)
			do pos = current.player.makeMove(this.getBoard(), Piece.Color.White);
			while( !move(pos, Piece.Color.White, false));
		
		this.setCurrentColor(temp);
		this.switchCurrent();
	}
	
	private PlayerSlot regularGame()
	{
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
		
		Board.Position pos;
		
		current = (first.color == Piece.Color.White) ? first : second;
		
		do
		{
			if(isDraw()) return null;
			
			do pos = current.player.makeMove(this.getBoard(), current.color);
			while( !move(pos, current.color, true) );
			
		}
		while(!didConclude(pos));
		
		return (current == first) ? second : first;
	}
	/*
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
	}*/
	
	private PlayerSlot openingGameB()
	{
		Board.Position pos;
		
		this.requestMove(2, 1);
		
		if(second.player.doPickColor(this.getBoard()))
		{
			do second.color = second.player.pickColor(this.getBoard());
			while(second.color == null);
			first.color = (second.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
			
			return this.regularGame();
		}
		
		this.requestMove(1, 1);
		
		do first.color = first.player.pickColor(this.getBoard());
		while(first.color == null);
		second.color = (first.color == Piece.Color.Black) ? Piece.Color.White : Piece.Color.Black;
		
		return this.regularGame();
	}
	
	public PlayerSlot startGame()
	{
		if(!bothPlayersPresent()) return null;
		this.resetTable();

		PlayerSlot winner = this.openingGameB();
		if( winner == null )
			return null;
		else
			return new PlayerSlot(winner);
	}
}
