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

        setOnMouseClicked( e-> {
            if( !getChildren().contains(piece) )
                gameBoard.clickedField(number);
        });

        setOnMouseEntered(e -> {
            if( !getChildren().contains(piece) )
                getChildren().add(smallDotImage);
        });

        setOnMouseExited(e -> getChildren().remove(smallDotImage));
    }

    public void clean(){
        count = 0;
        if(getChildren().contains(piece)) {
            getChildren().remove(piece);
        }
        
        piece = null;
    }

    public void addPiece( Piece.PieceType color ) {
        count += 1;
        piece = new Piece(color, count);
        Platform.runLater(() -> getChildren().add(piece));
    }
    
    public Piece getPiece(){
    	
        return piece;
    }
}
