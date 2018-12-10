package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BoardFieldController extends StackPane {

    private ImageView blackPieceImage;
    private ImageView whitePieceImage;
    private ImageView smallDotImage;

    BoardFieldController(){

        setWidth(40);
        setHeight(40);

        ImageView background = new ImageView(new Image("background.png"));
        getChildren().add(background);
        background.setFitWidth(40);
        background.setFitHeight(40);

        blackPieceImage = new ImageView(new Image("black-piece.png"));
        blackPieceImage.setFitWidth(40);
        blackPieceImage.setFitHeight(40);
        whitePieceImage = new ImageView(new Image("white-piece.png"));
        whitePieceImage.setFitWidth(40);
        whitePieceImage.setFitHeight(40);
        smallDotImage = new ImageView(new Image("small-dot.png"));


        setOnMouseClicked(e -> ChangePiece());

        setOnMouseEntered(e -> {getChildren().add(smallDotImage);});

        setOnMouseExited(e -> {getChildren().remove(smallDotImage);});
    }

    private void ChangePiece(){
        if( getChildren().contains(whitePieceImage) ) {
            getChildren().remove(whitePieceImage);
            getChildren().add(blackPieceImage);
        }
        else if( getChildren().contains(blackPieceImage) ) {
            getChildren().remove(blackPieceImage);
            getChildren().add(whitePieceImage);
        }
        else {
            getChildren().add(whitePieceImage);
        }
    }

}
