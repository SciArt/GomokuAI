package controller;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

class GameBoardController extends GridPane {
    GameBoardController(double width, double height){
        setStyle("-fx-background-color: DAE6F3;");

        setMinSize(height, height);

        BoardFieldController fields[] = new BoardFieldController[15*15];

        for (int i=0; i<15*15; i++) {
            fields[i] = new BoardFieldController();
            add(fields[i], i/15, i%15);
        }
        setAlignment(Pos.CENTER);
    }
}
