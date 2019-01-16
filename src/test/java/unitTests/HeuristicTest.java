/*
package unitTests;
import model.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class HeuristicTest {

	@Test
	public void whenEmpty() {
		Board board = new Board(10);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.getPoints(board, Piece.Color.White));
		assertEquals(0, h.getPoints(board, Piece.Color.Black));
	}
	
	@Test
	public void colorTest() {
		Board board = new Board(5);
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		Heuristic h = new Heuristic();
		
		board.placePiece(1, 0, white);
		board.placePiece(1, 3, black);
		
		assertEquals(0, h.getPoints(board, Piece.Color.White));
		assertEquals(0, h.getPoints(board, Piece.Color.Black));
	}
	
	@Test
	public void whenTwoColorsInOneRow() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		Heuristic h = new Heuristic();
		
		board.placePiece(1, 0, white);
		board.placePiece(2, 0, black);
		
		assertEquals(0, h.getPoints(board, Piece.Color.White));
		assertEquals(0, h.getPoints(board, Piece.Color.Black));
		
		board.placePiece(9, 0, white);
		assertEquals(4, h.getPoints(board, Piece.Color.White));	
	}
	
	
	
	@Test
	public void whenOnlyOneColorInOneColumn() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		board.placePiece(0, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(1, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 1, white);
		assertEquals(11, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 2, white);
		assertEquals(111, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 4, white);
		assertEquals(112, h.checkColumn(0, board, Piece.Color.White));
	}
	
	
	@Test
	public void whenOnlyOneColorInOneRow() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		board.placePiece(0, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(10, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(2, 0, white);
		assertEquals(20, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(3, 0, white);
		assertEquals(110, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(4, 0, white);
		assertEquals(10000, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(5, 0, white);
		assertEquals(122, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(6, 0, white);
		assertEquals(1122, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(7, 0, white);
		assertEquals(11122, h.checkRow(0,board, Piece.Color.White));
		
		board.placePiece(2, 0, white);
		assertEquals(11222, h.checkRow(0,board, Piece.Color.White));
	}
	
	
	@Test
	public void checkCantLeftToRight() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(0, 0, white);
		assertEquals(1, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(1, 1, white);
		assertEquals(11, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(2, 2, white);
		assertEquals(111, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		assertEquals(0, h.checkCantLeftToRight(1, 0, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(0, 1, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(9, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(0, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(9, 0, board, Piece.Color.White));
		
		board.placePiece(3, 3, white);
		assertEquals(1111, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
	}
	
	
	@Test
	public void checkCantRightToLeft() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		
		board.placePiece(1, 1, white);
		assertEquals(9, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		
		board.placePiece(1, 1, white);
		assertEquals(1, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		
		board.placePiece(1, 3, white);
		assertEquals(1, h.checkCantRightToLeft(4, 0, board, Piece.Color.White));
		board.placePiece(0, 4, white);
		assertEquals(11, h.checkCantRightToLeft(4, 0, board, Piece.Color.White));
		
		assertEquals(0, h.checkCantRightToLeft(1, 0, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(0, 1, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(9, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(0, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
		
		board.placePiece(8, 9, white);
		assertEquals(1, h.checkCantRightToLeft(9, 8, board, Piece.Color.White));
		assertEquals(1, h.checkCantRightToLeft(8, 9, board, Piece.Color.White));
	}
	
	@Test
	public void heuricticTests() {
		Board board = new Board(5);
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		Heuristic h = new Heuristic();
		
		board.placePiece(2, 3, white);
		board.placePiece(1, 0, black);
		board.placePiece(1, 1, black);
		
		assertEquals(-13, h.getPoints(board, Piece.Color.White));
		assertEquals(13, h.getPoints(board, Piece.Color.Black));
		
		board.placePiece(3, 2, white);
		assertEquals(0, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(4, 1, white);
		assertEquals(103, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(4, 2, white);
		assertEquals(125, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(1, 2, black);
		assertEquals(22, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(1, 3, black);
		assertEquals(-981, h.getPoints(board, Piece.Color.White));
	}

}
*/
