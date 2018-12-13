package view;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

class BoardField extends StackPane {

    private ImageView smallDotImage;

    private Piece piece;

    private static int count = 0;

    BoardField( String bgPath, int number, GameBoard gameBoard ){

        setWidth(40);
        setHeight(40);

        ImageView background = new ImageView(new Image(bgPath));
        getChildren().add(background);

        background.setFitWidth(40);
        background.setFitHeight(40);

        smallDotImage = new ImageView(new Image("small-dot.png"));

        //setOnMouseClicked(e -> AddPiece());

        setOnMouseClicked( e-> {
            if( !getChildren().contains(piece) ) // Potem można zdjąć tego IF'a, na razie gametable nie jest podpięty
                gameBoard.clickedField(number);
        });

        setOnMouseEntered(e -> {
            if( !getChildren().contains(piece) )
                getChildren().add(smallDotImage);
        });

        setOnMouseExited(e -> getChildren().remove(smallDotImage));
    }

    public void addPiece( Piece.PieceType color ){
    	
        count += 1;
        piece = new Piece(color, count);
        Platform.runLater(() -> getChildren().add(piece));
        //Platform.runLater( getChildren().add(piece) );

    	/*if(!getChildren().contains(piece)){
            count += 1;
            if(count%2 == 0)
                piece = new Piece(Piece.PieceType.BLACK, count);
            else
                piece = new Piece(Piece.PieceType.WHITE, count);
            getChildren().add(piece);
        }*/
    }
}
