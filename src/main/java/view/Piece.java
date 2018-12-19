package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Piece extends StackPane {

    public enum PieceType {
        WHITE,
        BLACK
    }

    Piece( PieceType type, int num ){
        Text numText = new Text(String.valueOf(num));
        if(type == PieceType.WHITE) {
            numText.setFill(Color.BLACK);
            getChildren().add(new ImageView(new Image("white-piece.png")));
        }
        else if(type == PieceType.BLACK) {
            numText.setFill(Color.WHITE);
            getChildren().add(new ImageView(new Image("black-piece.png")));
        }
        getChildren().add(numText);
    }

}
