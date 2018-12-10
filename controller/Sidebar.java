package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class SidebarController extends VBox {
    SidebarController(double width, double height) {
        setSpacing(20);
        setPadding(new Insets(20));
        setStyle("-fx-background-color:#DDDDDD;");
        setMinWidth(200);
        setMaxWidth(200);

        Button newgameBtn = new Button("New Game");

        VBox players = new VBox();
        HBox player1 = new HBox();
        HBox player2 = new HBox();
        Text player1Text = new Text("Player1");
        Text player2Text = new Text("Player2");
        ChoiceBox<String> player1ChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Human", "AI"));
        player1ChoiceBox.setValue("Human");
        ChoiceBox<String> player2ChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Human", "AI"));
        player2ChoiceBox.setValue("Human");
        player1.getChildren().addAll(player1Text, player1ChoiceBox);
        player2.getChildren().addAll(player2Text, player2ChoiceBox);
        players.getChildren().addAll(player1, player2);
        player1.setSpacing(20);
        player2.setSpacing(20);
        players.setSpacing(20);
        player1.setAlignment(Pos.BASELINE_CENTER);
        player2.setAlignment(Pos.BASELINE_CENTER);

        Text treeDepthText = new Text(" Tree depth:");
        Slider treeDepthSlider = new Slider();
        treeDepthSlider.setMin(0);
        treeDepthSlider.setMax(10);
        treeDepthSlider.setValue(4);
        treeDepthSlider.setShowTickLabels(true);
        treeDepthSlider.setShowTickMarks(true);
        treeDepthSlider.setMajorTickUnit(1);
        treeDepthSlider.setMinorTickCount(0);
        treeDepthSlider.setBlockIncrement(1);
        treeDepthSlider.setSnapToTicks(true);

        Text heuristicText = new Text("Heuristic parameters:");

        HBox heuristicParams = new HBox();
        TextField heuristicParam1TextField = new TextField();
        TextField heuristicParam2TextField = new TextField();
        TextField heuristicParam3TextField = new TextField();
        TextField heuristicParam4TextField = new TextField();
        heuristicParams.getChildren().addAll(
                heuristicParam1TextField,
                heuristicParam2TextField,
                heuristicParam3TextField,
                heuristicParam4TextField
        );
        heuristicParams.setSpacing(10);

        Button quitBtn = new Button("Quit");

        getChildren().addAll(
                newgameBtn,
                players,
                treeDepthText,
                treeDepthSlider,
                heuristicText,
                heuristicParams,
                quitBtn);

        quitBtn.setOnMouseClicked(e -> Platform.exit());
    }

}
