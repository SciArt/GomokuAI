package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

class BoardField extends StackPane {

    private ImageView smallDotImage;

    private Piece piece;

    private static int count = 0;

    BoardField( String bgPath ){

        setWidth(40);
        setHeight(40);

        ImageView background = new ImageView(new Image(bgPath));
        getChildren().add(background);

        background.setFitWidth(40);
        background.setFitHeight(40);

        smallDotImage = new ImageView(new Image("small-dot.png"));

        setOnMouseClicked(e -> AddPiece());

        setOnMouseEntered(e -> {
            if( !getChildren().contains(piece) )
                getChildren().add(smallDotImage);
        });

        setOnMouseExited(e -> getChildren().remove(smallDotImage));
    }

    private void AddPiece(){
        if(!getChildren().contains(piece)){
            count += 1;
            if(count%2 == 0)
                piece = new Piece(Piece.PieceType.BLACK, count);
            else
                piece = new Piece(Piece.PieceType.WHITE, count);
            getChildren().add(piece);
        }
    }
}
