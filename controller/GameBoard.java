package controller;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

class GameBoard extends GridPane {
    BoardField fields[];
    GameBoard(double width, double height){
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
                fields[i] = new BoardField("background-top-left.png");
            else if( i == 14 )
                fields[i] = new BoardField("background-top-right.png");
            else if( i == 15*14)
                fields[i] = new BoardField("background-bottom-left.png");
            else if( i == 15*15 - 1)
                fields[i] = new BoardField("background-bottom-right.png");
            else if( i/15 == 0 )
                fields[i] = new BoardField("background-top.png");
            else if( i/15 == 14 )
                fields[i] = new BoardField("background-bottom.png");
            else if( i%15 == 0 )
                fields[i] = new BoardField("background-left.png");
            else if( i%15 == 14 )
                fields[i] = new BoardField("background-right.png");
            else
                fields[i] = new BoardField("background.png");

            add(fields[i], i%15 + 1, i/15 );
        }
    }
}
