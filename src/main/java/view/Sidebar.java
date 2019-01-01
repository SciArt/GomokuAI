package view;

import controller.GameController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Sidebar extends ScrollPane {
    private GameController gameController;
    private Game game;

    private Player player1;
    private Player player2;

    class Player extends VBox {

        int treeDepth = 2;

        private Text playerText;
        private ChoiceBox<String> choiceBox;
        private Text treeDepthText = new Text(" Tree depth: " + treeDepth);
        private Text heuristicText = new Text("Heuristic parameters:");
        Slider treeDepthSlider = new Slider();
        HBox heuristicParams = new HBox();
        TextField heuristicParam1TextField = new TextField();
        TextField heuristicParam2TextField = new TextField();
        TextField heuristicParam3TextField = new TextField();
        TextField heuristicParam4TextField = new TextField();

        Player(String playerName) {
            playerText = new Text(playerName);

            choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                    "Human", "AI", "RandomAI"));

            choiceBox.setOnAction( e -> changeSetup());

            setSpacing(20);

            treeDepthSlider.setMin(0);
            treeDepthSlider.setMax(6);
            treeDepthSlider.setValue(treeDepth);
            treeDepthSlider.setShowTickLabels(true);
            treeDepthSlider.setShowTickMarks(true);
            treeDepthSlider.setMajorTickUnit(1);
            treeDepthSlider.setMinorTickCount(0);
            treeDepthSlider.setBlockIncrement(1);
            treeDepthSlider.setSnapToTicks(true);

            heuristicParams.getChildren().addAll(
                    heuristicParam1TextField,
                    heuristicParam2TextField,
                    heuristicParam3TextField,
                    heuristicParam4TextField
            );
            heuristicParams.setSpacing(10);

            treeDepthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if( treeDepth != Math.round(newVal.floatValue()) ) {
                    treeDepth = Math.round(newVal.floatValue());
                    treeDepthText.setText("Tree depth: " + treeDepth);
                    Platform.runLater(this::setupAI);
                }
            });

            getChildren().addAll( playerText, choiceBox );

            choiceBox.setValue("Human");
        }

        void changeSetup() {
            if(choiceBox.getValue().equals("Human")) {
                setupHuman();
            }
            else if(choiceBox.getValue().equals("AI")) {
                setupAI();
            }
            else if (choiceBox.getValue().equals("RandomAI")) {
                setupRandom();
            }
        }

        void setupAI() {
            if( !getChildren().contains(treeDepthText) )
                getChildren().add(treeDepthText);
            if( !getChildren().contains(treeDepthSlider) )
                getChildren().add(treeDepthSlider);
            /*if( !getChildren().contains(heuristicText) )
                getChildren().add(heuristicText);
            if( !getChildren().contains(heuristicParams) )
                getChildren().add(heuristicParams);*/

            setPlayerTypeAsAI(this, treeDepth);
        }

        void setupHuman() {
            getChildren().removeAll(treeDepthText, treeDepthSlider, heuristicText, heuristicParams);
            setPlayerTypeAsHuman(this);
        }

        void setupRandom() {
            getChildren().removeAll(treeDepthText, treeDepthSlider, heuristicText, heuristicParams);
            setPlayerTypeAsRandom(this);
        }
    }

    class Game extends VBox {
        private Text currentPlayerText = new Text("Game is not started.");

        Game() {
            Button newgameBtn = new Button("New Game");
            Button stopBtn = new Button("Stop Game");
            Button quitBtn = new Button("Quit");

            setSpacing(20);

            quitBtn.setOnMouseClicked(e -> gameController.closeApp());
            newgameBtn.setOnMouseClicked(e -> gameController.startGame());
            stopBtn.setOnMouseClicked(e -> gameController.forceStopGame());

            getChildren().addAll(currentPlayerText,newgameBtn,stopBtn,quitBtn);
        }

        void setCurrentInfoText(String s){
            currentPlayerText.setText(s);
        }
    }

    public Sidebar(GameController gameController) {
        this.gameController = gameController;

        setMaxWidth(200);
        setMinWidth(200);
        setMinHeight(640);
        setMaxHeight(640);
        setStyle("-fx-background:#DDDDDD; -fx-padding: 0;");

        VBox vBox = new VBox();

        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-background-color:#DDDDDD;");
        vBox.setMinWidth(180);
        vBox.setMaxWidth(180);

        game = new Game();
        player1 = new Player("Player1");
        player2 = new Player( "Player2");

        vBox.getChildren().addAll(
                game,
                player1,
                player2
        );

        setContent(vBox);
    }

    public void setCurrentInfoText(String s){
        game.setCurrentInfoText(s);
    }

    public void setPlayerTypeAsHuman(Player p) {
        if( p == player1 ) {
            gameController.setPlayer1AsHuman();
        }
        else if( p == player2 ) {
            gameController.setPlayer2AsHuman();
        }
    }

    public void setPlayerTypeAsAI(Player p, int depth) {
        if( p == player1 ) {
            gameController.setPlayer1AsAI(depth);
        }
        else if( p == player2 ) {
            gameController.setPlayer2AsAI(depth);
        }
    }

    public void setPlayerTypeAsRandom(Player p) {
        if( p == player1 ) {
            gameController.setPlayer1AsRandom();
        }
        else if( p == player2 ) {
            gameController.setPlayer2AsRandom();
        }
    }
}