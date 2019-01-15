package model;

@SuppressWarnings("ALL")
public class Heuristic2 {
    private int playerPoints;
    private int opponentPoints;

    private boolean wins = false;
    /*private boolean playerWins = false;
    private boolean opponentLoose = false;*/

    private int base = 10;

    public Heuristic2() {
        playerPoints = 0;
        opponentPoints = 0;
    }

    public int getPoints(Board board, Piece.Color playerColor) {
        wins = false;

        playerPoints = getContestantPoints(board, playerColor);

        if( wins )
            playerPoints = Integer.MAX_VALUE;

        wins = false;

        if (playerColor == Piece.Color.White)
            opponentPoints = getContestantPoints(board, Piece.Color.Black);
        else
            opponentPoints = getContestantPoints(board, Piece.Color.White);

        if( wins ) {
            playerPoints = 0;
            opponentPoints = Integer.MAX_VALUE;
        }

       if( opponentPoints == Integer.MAX_VALUE )
           return -opponentPoints;
        if( playerPoints == Integer.MAX_VALUE )
            return playerPoints;

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
        int emptyCurrent = 0;
        int emptyBefore = 0;
        for(int i=0;i<board.size();i++) {
            // Na polu znajduje się już jakiś kamyk
            if (board.getPiece(i, numberOfRow) != null) {
                // Przerwany został szereg pustych pól, więc zapisujemy ile było pustych i zerujemy
                // Uwzględnia to, że pomiedzy naszymi kamykami może być wiele przerw
                if( emptyCurrent != 0 ) {
                    emptyBefore += emptyCurrent;
                    emptyCurrent = 0;
                }
                // Nasz kolor
                if (board.getPiece(i, numberOfRow).getColor() == color) {
                    whichInLine++;
                }
                // Kolor przeciwnika, trzeba wyzerować wszystko i ewentualnie przypisać punkty
                else {
                    // emptyCurrent == 0, dlatego nie sprawdzam
                    if( whichInLine > 0 && whichInLine <= 5 && emptyBefore+whichInLine >= 5) {
                        pointSum += (int) Math.pow(base, whichInLine);
                        if( whichInLine == 5 ) {
                            wins = true;
                        }
                    }
                    whichInLine = 0;
                    emptyBefore = 0;
                }
            }
            // Pole jest puste
            else {
                if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
                    pointSum += (int) Math.pow(base, whichInLine);
                    emptyCurrent = 0;
                    if( whichInLine == 5 ) {
                        wins = true;
                    }
                }
                emptyCurrent++;

                whichInLine = 0;
            }
        }

        if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
            pointSum += (int) Math.pow(base, whichInLine);
            emptyCurrent = 0;
            if( whichInLine == 5 ) {
                wins = true;
            }
        }

        return pointSum;
    }


    public int checkColumn(int numberOfColumn, Board board, Piece.Color color) {
        int pointSum = 0;
        int whichInLine = 0;
        int emptyCurrent = 0;
        int emptyBefore = 0;
        for(int i=0;i<board.size();i++) {
            if (board.getPiece(numberOfColumn, i) != null) {
                // Przerwany został szereg pustych pól, więc zapisujemy ile było pustych i zerujemy
                // Uwzględnia to, że pomiedzy naszymi kamykami może być wiele przerw
                if( emptyCurrent != 0 ) {
                    emptyBefore += emptyCurrent;
                    emptyCurrent = 0;
                }
                // Nasz kolor
                if (board.getPiece(numberOfColumn, i).getColor() == color) {
                    whichInLine++;
                }
                // Kolor przeciwnika, trzeba wyzerować wszystko i ewentualnie przypisać punkty
                else {
                    // emptyCurrent == 0, dlatego nie sprawdzam
                    if( whichInLine > 0 && whichInLine <= 5 && emptyBefore+whichInLine >= 5) {
                        pointSum += (int) Math.pow(base, whichInLine);
                        if( whichInLine == 5 ) {
                            wins = true;
                        }
                    }
                    whichInLine = 0;
                    emptyBefore = 0;
                }
            }
            // Pole jest puste
            else {
                if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
                    pointSum += (int) Math.pow(base, whichInLine);
                    emptyCurrent = 0;
                    if( whichInLine == 5 ) {
                        wins = true;
                    }
                }
                emptyCurrent++;

                whichInLine = 0;
            }
        }

        if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
            pointSum += (int) Math.pow(base, whichInLine);
            emptyCurrent = 0;
            if( whichInLine == 5 ) {
                wins = true;
            }
        }

        return pointSum;
    }


    public int checkCantLeftToRight(int startRow, int startColumn, Board board, Piece.Color color) {
        int pointSum = 0;
        int whichInLine = 0;
        int emptyCurrent = 0;
        int emptyBefore = 0;
        int currentColumn = startColumn;
        for(int currentRow = startRow;currentRow < board.size() && currentColumn < board.size();
            currentRow++, currentColumn++) {


            if (board.getPiece(currentRow, currentColumn) != null) {
                // Przerwany został szereg pustych pól, więc zapisujemy ile było pustych i zerujemy
                // Uwzględnia to, że pomiedzy naszymi kamykami może być wiele przerw
                if( emptyCurrent != 0 ) {
                    emptyBefore += emptyCurrent;
                    emptyCurrent = 0;
                }
                // Nasz kolor
                if (board.getPiece(currentRow, currentColumn).getColor() == color) {
                    whichInLine++;
                }
                // Kolor przeciwnika, trzeba wyzerować wszystko i ewentualnie przypisać punkty
                else {
                    // emptyCurrent == 0, dlatego nie sprawdzam
                    if( whichInLine > 0 && whichInLine <= 5 && emptyBefore+whichInLine >= 5) {
                        pointSum += (int) Math.pow(base, whichInLine);
                        if( whichInLine == 5 ) {
                            wins = true;
                        }
                    }
                    whichInLine = 0;
                    emptyBefore = 0;
                }
            }
            // Pole jest puste
            else {
                if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
                    pointSum += (int) Math.pow(base, whichInLine);
                    emptyCurrent = 0;
                    if( whichInLine == 5 ) {
                        wins = true;
                    }
                }
                emptyCurrent++;

                whichInLine = 0;
            }
        }

        if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
            pointSum += (int) Math.pow(base, whichInLine);
            emptyCurrent = 0;
            if( whichInLine == 5 ) {
                wins = true;
            }
        }

        return pointSum;
    }


    public int checkCantRightToLeft(int startRow, int startColumn, Board board, Piece.Color color) {
        int pointSum = 0;
        int whichInLine = 0;
        int emptyCurrent = 0;
        int emptyBefore = 0;
        int currentRow = startRow;
        for(int currentColumn = startColumn;currentColumn < board.size();currentColumn++) {

            if (board.getPiece(currentRow, currentColumn) != null) {
                // Przerwany został szereg pustych pól, więc zapisujemy ile było pustych i zerujemy
                // Uwzględnia to, że pomiedzy naszymi kamykami może być wiele przerw
                if( emptyCurrent != 0 ) {
                    emptyBefore += emptyCurrent;
                    emptyCurrent = 0;
                }
                // Nasz kolor
                if (board.getPiece(currentRow, currentColumn).getColor() == color) {
                    whichInLine++;
                }
                // Kolor przeciwnika, trzeba wyzerować wszystko i ewentualnie przypisać punkty
                else {
                    // emptyCurrent == 0, dlatego nie sprawdzam
                    if( whichInLine > 0 && whichInLine <= 5 && emptyBefore+whichInLine >= 5) {
                        pointSum += (int) Math.pow(base, whichInLine);
                        if( whichInLine == 5 ) {
                            wins = true;
                        }
                    }
                    whichInLine = 0;
                    emptyBefore = 0;
                }
            }
            // Pole jest puste
            else {
                if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
                    pointSum += (int) Math.pow(base, whichInLine);
                    emptyCurrent = 0;
                    if( whichInLine == 5 ) {
                        wins = true;
                    }
                }
                emptyCurrent++;

                whichInLine = 0;
            }

            currentRow--;
            if (currentRow < 0)
                break;
        }

        if( whichInLine > 0 && whichInLine <= 5 && emptyCurrent+emptyBefore+whichInLine >= 5 ) {
            pointSum += (int) Math.pow(base, whichInLine);
            emptyCurrent = 0;
            if( whichInLine == 5 ) {
                wins = true;
            }
        }

        return pointSum;
    }

}