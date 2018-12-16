package model;

public class AlfaBetaAlgorithm {
	private Board.Position theBestMove;
	Heuristic h;
	Piece.Color color;
	int originalDepth;
	
	
	public AlfaBetaAlgorithm(int depth) {
		theBestMove = new Board.Position(0, 0);
		h = new Heuristic();
		originalDepth = depth;
		color = Piece.Color.Black;
	}
	
	public int minimaxAlfabeta(Board board, Piece.Color color){
		theBestMove = new Board.Position(0, 0);
		this.color = color;
		return alfabeta(board, originalDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
	}
	
	private int alfabeta(Board board, int depth, int alfa, int beta, boolean isPlayer) {
		if (depth == 0) //dopisaæ opcje ze stanem koñcowym
			return h.getPoints(board, color);
		
		if (isPlayer)
			for (int i = 0; i < board.size(); i++)for (int j = 0; j < board.size(); j++) {
				Piece piece = new Piece(color);
				
				if (board.getPiece(i, j) == null) {
					board.placePiece(i, j, piece);
					int newAlfa = alfa;
					alfa = Math.max(alfa, alfabeta(board, depth-1, alfa, beta, false));
					//checking if exist better position
					if (depth == originalDepth && newAlfa != alfa)
						theBestMove = new Board.Position(i, j);
					
					if (alfa >= beta)
						return beta;
					return alfa;
				}
			}
		
		if (!isPlayer)
			for (int i = 0; i < board.size(); i++)for (int j = 0; j < board.size(); j++) {
				Piece piece;
				if (color == Piece.Color.Black)
					piece = new Piece(Piece.Color.White);
				else
					piece = new Piece(Piece.Color.Black);
				
				if (board.getPiece(i, j) == null) {
					board.placePiece(i, j, piece);
					beta = Math.min(beta, alfabeta(board, depth-1, alfa, beta, true));
					if (alfa >= beta)
						return beta;
					return alfa;
				}
			}
		return 0;
	}
	
	
	public Board.Position getTheBestMove(){
		return theBestMove;
	}
	
	
}
