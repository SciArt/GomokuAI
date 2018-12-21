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
	
	public void setDepth(int newDepth) {
		originalDepth = newDepth;
	}
	
	public int minimaxAlfabeta(Board board, Piece.Color color){
		for (int i = 0; i < board.size()*board.size(); i++){
			if (board.getPiece(i/15, i%15) == null) {
				theBestMove = new Board.Position(i/15, i%15);
				break;
			}
		}
		//theBestMove = new Board.Position(0, 0);
		this.color = color;
		return alfabeta(board, originalDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
	}
	
	private int alfabeta(Board board, int depth, int alfa, int beta, boolean isPlayer) {
		if (depth == 0) { //dopisać opcje ze stanem końcowym
			return h.getPoints(board, color);
		}
		
		if (isPlayer) {
			Piece piece = new Piece(color);
			for (int i = 0; i < board.size(); i++)for (int j = 0; j < board.size(); j++) {
				
				if (board.getPiece(i, j) == null) {
					Board newboard = new Board(board);
					newboard.placePiece(i, j, piece);
					int newAlfa = alfa;
					alfa = Math.max(alfa, alfabeta(newboard, depth-1, alfa, beta, false));
					//checking if exist better position
					if (depth == originalDepth && newAlfa != alfa) {
						theBestMove = new Board.Position(i, j);
						//System.out.println("alfa = "+alfa+" beta = "+beta+ "   "+i+" "+j);
					}
					
					if (alfa >= beta)
						return beta;
				}
			}
			return alfa;
		}
		if (!isPlayer) {
			Piece piece;
				if (color == Piece.Color.Black)
					piece = new Piece(Piece.Color.White);
				else
					piece = new Piece(Piece.Color.Black);
			
			for (int i = 0; i < board.size(); i++)for (int j = 0; j < board.size(); j++) {
				
				if (board.getPiece(i, j) == null) {
					Board newboard = new Board(board);
					newboard.placePiece(i, j, piece);
					beta = Math.min(beta, alfabeta(newboard, depth-1, alfa, beta, true));
					if (alfa >= beta)
						return alfa;
				}
			}
			return beta;
		}
		return 0;
	}
	
	
	public Board.Position getTheBestMove(){
		return theBestMove;
	}
	
	
}
