package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.AlfaBetaAlgorithm;
import model.Board;
import model.Piece;

public class AlfaBetaTests {

	@Test
	public void onePiecedepthOne() {
		Board board = new Board(5);
		AlfaBetaAlgorithm alfabeta = new AlfaBetaAlgorithm(1);
		 
		int h = alfabeta.minimaxAlfabeta(board, Piece.Color.White);
	    Board.Position position = alfabeta.getTheBestMove();
	    Board.Position expectingPosition = new Board.Position(0, 0);
		 
		assertEquals(expectingPosition, position);
		assertEquals(4, h);
	}
	
	@Test
	public void fourPiecesdepthOne() {
		Board board = new Board(5);
		AlfaBetaAlgorithm alfabeta = new AlfaBetaAlgorithm(1);
		
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		
		board.placePiece(3, 0, white);
		board.placePiece(0, 0, black);
		board.placePiece(0, 1, black);
		 
		int h = alfabeta.minimaxAlfabeta(board, Piece.Color.Black);
	    Board.Position position = alfabeta.getTheBestMove();
	    Board.Position expectingPosition = new Board.Position(0, 2);
		 
		assertEquals(expectingPosition, position);
		assertEquals(116, h);
	}
	
	@Test
	public void sixPiecesdepthOne() {
		Board board = new Board(5);
		AlfaBetaAlgorithm alfabeta = new AlfaBetaAlgorithm(1);
		
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		
		board.placePiece(1, 2, white);
		board.placePiece(2, 2, white);
		board.placePiece(0, 0, black);
		board.placePiece(1, 0, black);
		board.placePiece(1, 1, black);
		 
		int h = alfabeta.minimaxAlfabeta(board, Piece.Color.Black);
	    Board.Position position = alfabeta.getTheBestMove();
	    Board.Position expectingPosition = new Board.Position(2, 0);
		 
		assertEquals(expectingPosition, position);
		assertEquals(134, h);
	}

}
