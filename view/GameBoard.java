package view;

import controller.GameController;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameBoard extends GridPane {
    GameController gameController;

    BoardField fields[];

    public void clickedField(int number){
        System.out.println("Field [" + number%15 + "][" + number/15 + "] is clicked." );
        gameController.saveMove(number);
        //if( gameController.makeMove(number) );
        //fields[number].addPiece();
    }
    
    public void addPiece(int number, Piece.PieceType color) {
    	fields[number].addPiece(color);
    }

    public boolean isEmpty(int number)
    {
    	return fields[number].getPiece() == null;
    }

    public void cleanBoard(){
        for (int i=0; i<15*15; i++) {
            fields[i].clean();
        }
    }

    public GameBoard(double width, double height, GameController gameController){
        this.gameController = gameController;
        setStyle("-fx-background-color: DAE6F3;");
        setMinSize(width, height);

        fields = new BoardField[15*15];

        for (int i=0; i<=15; i++) {
            add(new ImageView(new Image("grey.png")), 0, 15-i);

            add(new ImageView(new Image("grey.png")), i, 15);

            if( i != 0 ) {
                Text iText = new Text(String.valueOf(i));
                add(iText, 0, 15-i);
                setHalignment(iText, HPos.CENTER);
                setValignment(iText, VPos.CENTER);

                Text iText2 = new Text( "" + (char)('a'+i-1) );
                add(iText2, i, 15);
                setHalignment(iText2, HPos.CENTER);
                setValignment(iText2, VPos.CENTER);
            }
        }

        for (int i=0; i<15*15; i++) {
            if( i == 0 )
                fields[i] = new BoardField("background-top-left.png", i, this);
            else if( i == 14 )
                fields[i] = new BoardField("background-top-right.png", i, this);
            else if( i == 15*14)
                fields[i] = new BoardField("background-bottom-left.png", i, this);
            else if( i == 15*15 - 1)
                fields[i] = new BoardField("background-bottom-right.png", i, this);
            else if( i/15 == 0 )
                fields[i] = new BoardField("background-top.png", i, this);
            else if( i/15 == 14 )
                fields[i] = new BoardField("background-bottom.png", i, this);
            else if( i%15 == 0 )
                fields[i] = new BoardField("background-left.png", i, this);
            else if( i%15 == 14 )
                fields[i] = new BoardField("background-right.png", i, this);
            else
                fields[i] = new BoardField("background.png", i, this);

            add(fields[i], i%15 + 1, i/15 );
        }
    }
}
