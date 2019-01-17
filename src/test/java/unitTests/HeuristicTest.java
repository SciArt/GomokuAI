
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
	public void whenWin() {
		Board board = new Board(5);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		board.placePiece(0, 4, white);
		board.placePiece(0, 3, white);
		board.placePiece(0, 2, white);
		board.placePiece(0, 1, white);
		board.placePiece(0, 0, white);
		
		assertEquals(Integer.MAX_VALUE, h.getPoints(board, Piece.Color.White));
	}
	
	@Test
	public void colorTest() {
		Board board = new Board(5);
		Piece white = new Piece(Piece.Color.White);
		Piece black = new Piece(Piece.Color.Black);
		Heuristic h = new Heuristic();
		
		board.placePiece(1, 0, white);
		board.placePiece(3, 4, black);
		
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
		
		assertEquals(-50, h.getPoints(board, Piece.Color.White));
		assertEquals(50, h.getPoints(board, Piece.Color.Black));
		
		board.placePiece(9, 5, white);
		assertEquals(350, h.getPoints(board, Piece.Color.White));	
	}
	

	
	@Test
	public void whenOnlyOneColorInOneColumn() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		
		board.placePiece(0, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(50, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 1, white);
		assertEquals(2550, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 2, white);
		assertEquals(127550, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 4, white);
		assertEquals(6377600, h.checkColumn(0, board, Piece.Color.White));
		
		//przy 5 zwraca 0 i ustawia flage wins
		board.placePiece(0, 3, white);
		assertEquals(0, h.checkColumn(0, board, Piece.Color.White));
	}

	@Test
	public void whenOnlyOneColorAtTheEndOfOneColumn() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		
		board.placePiece(0, 9, white);
		Heuristic h = new Heuristic();
		
		assertEquals(50, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 8, white);
		assertEquals(2550, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 7, white);
		assertEquals(127550, h.checkColumn(0, board, Piece.Color.White));
		
		board.placePiece(0, 5, white);
		assertEquals(6377600, h.checkColumn(0, board, Piece.Color.White));
	}
	
	@Test
	public void whenOnlyOneColorInTheLastColumn() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		
		board.placePiece(9, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(50, h.checkColumn(9, board, Piece.Color.White));
		
		board.placePiece(9, 1, white);
		assertEquals(2550, h.checkColumn(9, board, Piece.Color.White));
		
		board.placePiece(9, 2, white);
		assertEquals(127550, h.checkColumn(9, board, Piece.Color.White));
		
		board.placePiece(9, 4, white);
		assertEquals(6377600, h.checkColumn(9, board, Piece.Color.White));
	}
	
	
	@Test
	public void whenOnlyOneColorInOneRow() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		
		board.placePiece(0, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(50, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(1, 0, white);
		assertEquals(2550, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(2, 0, white);
		assertEquals(127550, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(4, 0, white);
		assertEquals(6377600, h.checkRow(0, board, Piece.Color.White));
		
		//przy 5 zwraca 0 i ustawia flage wins
		board.placePiece(3, 0, white);
		assertEquals(0, h.checkRow(0, board, Piece.Color.White));
	}
	
	@Test
	public void whenOnlyOneColorInTheLastRow() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		
		board.placePiece(9, 0, white);
		Heuristic h = new Heuristic();
		
		assertEquals(50, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(8, 0, white);
		assertEquals(2550, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(7, 0, white);
		assertEquals(127550, h.checkRow(0, board, Piece.Color.White));
		
		board.placePiece(5, 0, white);
		assertEquals(6377600, h.checkRow(0, board, Piece.Color.White));
	}
	
		
	@Test
	public void checkCantLeftToRight() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(0, 0, white);
		assertEquals(50, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(1, 1, white);
		assertEquals(2550, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(2, 2, white);
		assertEquals(127550, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		assertEquals(0, h.checkCantLeftToRight(1, 0, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(0, 1, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(9, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(0, 9, board, Piece.Color.White));
		assertEquals(0, h.checkCantLeftToRight(9, 0, board, Piece.Color.White));
		
		board.placePiece(9, 9, white);
		assertEquals(127600, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
	}
	
	@Test
	public void checkCantLeftToRightAtypicalPlaces() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
		
		board.placePiece(0, 9, white);
		assertEquals(0, h.checkCantLeftToRight(0, 9, board, Piece.Color.White));
		
		board.placePiece(9, 0, white);
		assertEquals(0, h.checkCantLeftToRight(9, 0, board, Piece.Color.White));
		
		board.placePiece(6, 0, white);
		board.placePiece(7, 1, white);
		assertEquals(0, h.checkCantLeftToRight(6, 0, board, Piece.Color.White));
		
		board.placePiece(0, 7, white);
		board.placePiece(1, 8, white);
		assertEquals(0, h.checkCantLeftToRight(0, 7, board, Piece.Color.White));
		
		board.placePiece(5, 5, white);
		assertEquals(250, h.checkCantLeftToRight(0, 0, board, Piece.Color.White));
	}
	
	@Test
	public void checkCantRightToLeft() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
		
		board.placePiece(9, 0, white);
		assertEquals(50, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
		
		board.placePiece(8, 1, white);
		assertEquals(2550, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
		
		board.placePiece(7, 2, white);
		assertEquals(127550, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
		
		assertEquals(0, h.checkCantRightToLeft(8, 0, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(9, 1, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		assertEquals(0, h.checkCantRightToLeft(9, 9, board, Piece.Color.White));
		
		board.placePiece(0, 9, white);
		assertEquals(127600, h.checkCantRightToLeft(9, 0, board, Piece.Color.White));
	}
	
	
	@Test
	public void checkCantRightToLeftAtypicalPlaces() {
		Board board = new Board(10);
		Piece white = new Piece(Piece.Color.White);
		Heuristic h = new Heuristic();
		
		assertEquals(0, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		
		board.placePiece(0, 9, white);
		assertEquals(0, h.checkCantRightToLeft(0, 0, board, Piece.Color.White));
		
		board.placePiece(9, 9, white);
		assertEquals(0, h.checkCantRightToLeft(9, 9, board, Piece.Color.White));
		
		board.placePiece(3, 0, white);
		board.placePiece(2, 1, white);
		assertEquals(0, h.checkCantRightToLeft(3, 0, board, Piece.Color.White));
		
		board.placePiece(6, 9, white);
		board.placePiece(9, 6, white);
		assertEquals(0, h.checkCantRightToLeft(9, 6, board, Piece.Color.White));
		
		board.placePiece(5, 5, white);
		assertEquals(250, h.checkCantRightToLeft(9, 1, board, Piece.Color.White));
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
		
		assertEquals(-2550, h.getPoints(board, Piece.Color.White));
		assertEquals(2550, h.getPoints(board, Piece.Color.Black));
		
		board.placePiece(3, 2, white);
		assertEquals(-2450, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(4, 1, white);
		assertEquals(-2350, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(4, 2, white);
		assertEquals(2550, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(1, 2, black);
		assertEquals(-122450, h.getPoints(board, Piece.Color.White));
		
		board.placePiece(1, 3, black);
		assertEquals(-6247550, h.getPoints(board, Piece.Color.White));
	}

}

