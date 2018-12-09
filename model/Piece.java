package model;

public class Piece 
{
	public enum Color
	{
		White,
		Black
	}
	
	private final Color color;
	
	public Color getColor() 
	{
		return color;
	}
	
	public Piece(Piece p)
	{
		this.color = p.color;
	}
	
	public Piece(Color color) {
		this.color = color;
	}
}

