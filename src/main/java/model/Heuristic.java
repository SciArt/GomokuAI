package model;

public class Heuristic {
	private int playerPoints;
	private int opponentPoints;
	
	public Heuristic() {
		playerPoints = 0;
		opponentPoints = 0;
	}
	
	public int getPoints(Board board, Piece.Color playerColor) {
		playerPoints = getContestantPoints(board, playerColor);
		
		if (playerColor == Piece.Color.White)
			opponentPoints = getContestantPoints(board, Piece.Color.Black);
		else
			opponentPoints = getContestantPoints(board, Piece.Color.White);
			
		return playerPoints - opponentPoints;
	}
	
	public int getContestantPoints (Board board, Piece.Color color) {
		int pointSum = 0;
		for(int i=0;i<board.size();i++)
			pointSum += checkRow(i, board, color);
		
		for(int i=0;i<board.size();i++)
			pointSum += checkColumn(i, board, color);
		
		pointSum += checkCantLeftToRight(0, 0, board, color);
		for(int i=1;i<board.size();i++) {
			pointSum += checkCantLeftToRight(0, i, board, color);
			pointSum += checkCantLeftToRight(i, 0, board, color);
		}
		
		pointSum += checkCantRightToLeft(board.size()-1, board.size()-1, board, color);
		for(int i=0;i<board.size()-1;i++) {
			pointSum += checkCantRightToLeft(i, 0, board, color);
			pointSum += checkCantRightToLeft(board.size()-1, i, board, color);
		}
		
		return pointSum;
	}

	public int checkRow(int numberOfRow, Board board, Piece.Color color) {
		int pointSum = 0;
		int whichInLine = 0;
		for(int i=0;i<board.size();i++) {
			if (board.getPiece(i, numberOfRow) != null) {
				if (board.getPiece(i, numberOfRow).getColor() == color) {
					pointSum += (int)Math.pow(10, whichInLine);
					//overline
					if (whichInLine >= 4)
						whichInLine = 0;
					else
						whichInLine++;
				}
				else
					whichInLine = 0;
			}
			else
				whichInLine = 0;
		}
		
		return pointSum;
	}
	
	
	public int checkColumn(int numberOfColumn, Board board, Piece.Color color) {
		int pointSum = 0;
		int whichInLine = 0;
		for(int i=0;i<board.size();i++) {
			if (board.getPiece(numberOfColumn, i) != null) {
				if (board.getPiece(numberOfColumn, i).getColor() == color) {
					pointSum += (int)Math.pow(10, whichInLine);
					//overline
					if (whichInLine >= 4)
						whichInLine = 0;
					else
						whichInLine++;
				}
				else
					whichInLine = 0;
			}
			else
				whichInLine = 0;
		}
		
		return pointSum;
	}
	
	
	public int checkCantLeftToRight(int startRow, int startColumn, Board board, Piece.Color color) {
		int pointSum = 0;
		int whichInLine = 0;
		int currentColumn = startColumn;
		for(int currentRow = startRow;currentRow < board.size() && currentColumn < board.size();
				currentRow++, currentColumn++) {
			
			
			if (board.getPiece(currentRow, currentColumn) != null) {
				if (board.getPiece(currentRow, currentColumn).getColor() == color) {
					pointSum += (int)Math.pow(10, whichInLine);
					//overline
					if (whichInLine >= 4)
						whichInLine = 0;
					else
						whichInLine++;
				}
				else
					whichInLine = 0;
			}
			else
				whichInLine = 0;
		
		//currentColumn++;
		//if (currentColumn >= board.size())
		//	break;
		}
		
		return pointSum;
	}
	
	
	public int checkCantRightToLeft(int startRow, int startColumn, Board board, Piece.Color color) {
		int pointSum = 0;
		int whichInLine = 0;
		int currentRow = startRow;
		for(int currentColumn = startColumn;currentColumn < board.size();currentColumn++) {
			
			
			if (board.getPiece(currentRow, currentColumn) != null) {
				if (board.getPiece(currentRow, currentColumn).getColor() == color) {
					pointSum += (int)Math.pow(10, whichInLine);
					//overline
					if (whichInLine >= 4)
						whichInLine = 0;
					else
						whichInLine++;
				}
				else
					whichInLine = 0;
			}
			else
				whichInLine = 0;
		
		currentRow--;
		if (currentRow < 0)
			break;
		}
		
		return pointSum;
	}

}
