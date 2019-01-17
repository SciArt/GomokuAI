package model;

@SuppressWarnings("ALL")
public class Heuristic {
    private int playerPoints;
    private int opponentPoints;

    private int base = 50;

    private boolean wins = false;
    
    public Heuristic() {
        playerPoints = 0;
        opponentPoints = 0;
    }

    public int getPoints(Board board, Piece.Color playerColor) {
        wins = false;

        playerPoints = getContestantPoints(board, playerColor);

        if( wins ) {
            playerPoints = Integer.MAX_VALUE;
            return playerPoints;
        }

        wins = false;

        if (playerColor == Piece.Color.White)
            opponentPoints = getContestantPoints(board, Piece.Color.Black);
        else
            opponentPoints = getContestantPoints(board, Piece.Color.White);

        if( wins ) {
            opponentPoints = Integer.MAX_VALUE;
            return -opponentPoints;
        }

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
    	//dzialanie opiera sie na sprawdzaniu kolejnych sekwencji po 5 pol,ale w 1 iteracji
    	//przy czym kazda zaczyna sie zaraz za poprzednia, dlatego potrzeba pamietac informacje
    	//o 5 takich sekwencjach za pomoca 10 liczb - po 2 na sekwencje
    	
    	int howManyEmpty[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pustych pol, przy czym puste sa tez te zajmowane przez gracza
											//chodzi o to zeby mozna bylo ulozyc sekwencje 5 pionkow
    	int howManyElements[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pionkow gracza w sekwencji
    	
    	for(int i=0;i<board.size();i++) {
    		//pole puste - zwiekszamy wszystkim sekwencjom informacje o pustym polu
    		if (board.getPiece(i, numberOfRow) == null) {
    			howManyEmpty[0]++;
    			howManyEmpty[1]++;
    			howManyEmpty[2]++;
    			howManyEmpty[3]++;
    			howManyEmpty[4]++;
    		}
    		else {
	    		//nasz kolor - zwiekszamy informacje o ilosci pustych i pionkow gracza
	    		if (board.getPiece(i, numberOfRow).getColor() == color) {
	    			howManyEmpty[0]++; howManyElements[0]++;
	    			howManyEmpty[1]++; howManyElements[1]++;
	    			howManyEmpty[2]++; howManyElements[2]++;
	    			howManyEmpty[3]++; howManyElements[3]++;
	    			howManyEmpty[4]++; howManyElements[4]++;
	            }
	    		//kolor przeciwnika - nie robimy nic
    		}
    		
    		//podliczanie sekwencji, ktora osiagnela 5 pol
    		//jesli wszystkie 5 pol jest zajetych to od razu gracz wygrywa
    		if (howManyElements[i%5] == 5) {
    			wins = true;
    			return 0;
    		}
    		//punkty dodajemy tylko jesli istnieje mozliwosc zajecia wszystkich pol przez gracza
    		if (howManyEmpty[i%5] == 5 && howManyElements[i%5] > 0)
    			pointSum += (int) Math.pow(base, howManyElements[i%5]);
    		
    		//zerowanie podliczonej sekwencji
    		howManyEmpty[i%5] = 0;
    		howManyElements[i%5] = 0;
    	}
    	
    	return pointSum;
    }


    public int checkColumn(int numberOfColumn, Board board, Piece.Color color) {
    	int pointSum = 0;
    	//dzialanie opiera sie na sprawdzaniu kolejnych sekwencji po 5 pol,ale w 1 iteracji
    	//przy czym kazda zaczyna sie zaraz za poprzednia, dlatego potrzeba pamietac informacje
    	//o 5 takich sekwencjach za pomoca 10 liczb - po 2 na sekwencje
    	
    	int howManyEmpty[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pustych pol, przy czym puste sa tez te zajmowane przez gracza
											//chodzi o to zeby mozna bylo ulozyc sekwencje 5 pionkow
    	int howManyElements[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pionkow gracza w sekwencji
    	
    	for(int i=0;i<board.size();i++) {
    		//pole puste - zwiekszamy wszystkim sekwencjom informacje o pustym polu
    		if (board.getPiece(numberOfColumn, i) == null) {
    			howManyEmpty[0]++;
    			howManyEmpty[1]++;
    			howManyEmpty[2]++;
    			howManyEmpty[3]++;
    			howManyEmpty[4]++;
    		}
    		else {
	    		//nasz kolor - zwiekszamy informacje o ilosci pustych i pionkow gracza
	    		if (board.getPiece(numberOfColumn, i).getColor() == color) {
	    			howManyEmpty[0]++; howManyElements[0]++;
	    			howManyEmpty[1]++; howManyElements[1]++;
	    			howManyEmpty[2]++; howManyElements[2]++;
	    			howManyEmpty[3]++; howManyElements[3]++;
	    			howManyEmpty[4]++; howManyElements[4]++;
	            }
	    		//kolor przeciwnika - nie robimy nic
    		}
    		
    		//podliczanie sekwencji, ktora osiagnela 5 pol
    		//jesli wszystkie 5 pol jest zajetych to od razu gracz wygrywa
    		if (howManyElements[i%5] == 5) {
    			wins = true;
    			return 0;
    		}
    		//punkty dodajemy tylko jesli istnieje mozliwosc zajecia wszystkich pol przez gracza
    		if (howManyEmpty[i%5] == 5 && howManyElements[i%5] > 0)
    			pointSum += (int) Math.pow(base, howManyElements[i%5]);
    		
    		//zerowanie podliczonej sekwencji
    		howManyEmpty[i%5] = 0;
    		howManyElements[i%5] = 0;
    	}
    	return pointSum;
    }


    public int checkCantLeftToRight(int startRow, int startColumn, Board board, Piece.Color color) {
        int pointSum = 0;
    	//dzialanie opiera sie na sprawdzaniu kolejnych sekwencji po 5 pol,ale w 1 iteracji
    	//przy czym kazda zaczyna sie zaraz za poprzednia, dlatego potrzeba pamietac informacje
    	//o 5 takich sekwencjach za pomoca 10 liczb - po 2 na sekwencje
    	
    	int howManyEmpty[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pustych pol, przy czym puste sa tez te zajmowane przez gracza
											//chodzi o to zeby mozna bylo ulozyc sekwencje 5 pionkow
    	int howManyElements[] = {0, 0, 0, 0, 0};//trzyma informacje o liczbie pionkow gracza w sekwencji
        

        int currentColumn = startColumn;
        int seqIndex = 0; //pomocnicza zmienna okreslajaca aktualnie podliczana sekwencje
        
        for(int currentRow = startRow;currentRow < board.size() && currentColumn < board.size();
            currentRow++, currentColumn++) {
    		
    		//pole puste - zwiekszamy wszystkim sekwencjom informacje o pustym polu
    		if (board.getPiece(currentRow, currentColumn) == null) {
    			howManyEmpty[0]++;
    			howManyEmpty[1]++;
    			howManyEmpty[2]++;
    			howManyEmpty[3]++;
    			howManyEmpty[4]++;
    		}
    		else {
	    		//nasz kolor - zwiekszamy informacje o ilosci pustych i pionkow gracza
	    		if (board.getPiece(currentRow, currentColumn).getColor() == color) {
	    			howManyEmpty[0]++; howManyElements[0]++;
	    			howManyEmpty[1]++; howManyElements[1]++;
	    			howManyEmpty[2]++; howManyElements[2]++;
	    			howManyEmpty[3]++; howManyElements[3]++;
	    			howManyEmpty[4]++; howManyElements[4]++;
	            }
	    		//kolor przeciwnika - nie robimy nic
    		}
    		
    		//podliczanie sekwencji, ktora osiagnela 5 pol
    		//jesli wszystkie 5 pol jest zajetych to od razu gracz wygrywa
    		if (howManyElements[seqIndex] == 5) {
    			wins = true;
    			return 0;
    		}
    		//punkty dodajemy tylko jesli istnieje mozliwosc zajecia wszystkich pol przez gracza
    		if (howManyEmpty[seqIndex] == 5 && howManyElements[seqIndex] > 0)
    			pointSum += (int) Math.pow(base, howManyElements[seqIndex]);
    		
    		//zerowanie podliczonej sekwencji
    		howManyEmpty[seqIndex] = 0;
    		howManyElements[seqIndex] = 0;
    		
    		seqIndex = (seqIndex+1)%5;
    	}

        return pointSum;
    }


    public int checkCantRightToLeft(int startRow, int startColumn, Board board, Piece.Color color) {
    	int pointSum = 0;
    	
    	//dzialanie opiera sie na sprawdzaniu kolejnych sekwencji po 5 pol,ale w 1 iteracji
    	//przy czym kazda zaczyna sie zaraz za poprzednia, dlatego potrzeba pamietac informacje
    	//o 5 takich sekwencjach za pomoca 10 liczb - po 2 na sekwencje
    	
    	int howManyEmpty[] = {0, 0, 0, 0, 0};			//trzyma informacje o liczbie pustych pol, przy czym puste sa tez te zajmowane przez gracza
														//chodzi o to zeby mozna bylo ulozyc sekwencje 5 pionkow
    	int howManyElements[] = {0, 0, 0, 0, 0};		//trzyma informacje o liczbie pionkow gracza w sekwencji
        
    	
        int seqIndex = 0; 	//pomocnicza zmienna okreslajaca aktualnie podliczana sekwencje
        
        int currentRow = startRow;
        
        for(int currentColumn = startColumn;currentColumn < board.size();currentColumn++) {
    		
    		//pole puste - zwiekszamy wszystkim sekwencjom informacje o pustym polu
    		if (board.getPiece(currentRow, currentColumn) == null) {
    			howManyEmpty[0]++;
    			howManyEmpty[1]++;
    			howManyEmpty[2]++;
    			howManyEmpty[3]++;
    			howManyEmpty[4]++;
    		}
    		else {
	    		//nasz kolor - zwiekszamy informacje o ilosci pustych i pionkow gracza
	    		if (board.getPiece(currentRow, currentColumn).getColor() == color) {
	    			howManyEmpty[0]++; howManyElements[0]++;
	    			howManyEmpty[1]++; howManyElements[1]++;
	    			howManyEmpty[2]++; howManyElements[2]++;
	    			howManyEmpty[3]++; howManyElements[3]++;
	    			howManyEmpty[4]++; howManyElements[4]++;
	            }
	    		//kolor przeciwnika - nie robimy nic
    		}
    		
    		//podliczanie sekwencji, ktora osiagnela 5 pol
    		//jesli wszystkie 5 pol jest zajetych to od razu gracz wygrywa
    		if (howManyElements[seqIndex] == 5) {
    			wins = true;
    			return 0;
    		}
    		//punkty dodajemy tylko jesli istnieje mozliwosc zajecia wszystkich pol przez gracza
    		if (howManyEmpty[seqIndex] == 5 && howManyElements[seqIndex] > 0)
    			pointSum += (int) Math.pow(base, howManyElements[seqIndex]);
    		
    		//zerowanie podliczonej sekwencji
    		howManyEmpty[seqIndex] = 0;
    		howManyElements[seqIndex] = 0;

            currentRow--;
            if (currentRow < 0)
                break;
            
            seqIndex = (seqIndex+1)%5;
        }

        return pointSum;
    }

}