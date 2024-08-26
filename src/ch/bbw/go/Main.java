package ch.bbw.go;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    boolean isFlagmodeOn = false;
    static int DEFAULT = 0, WON = 1, LOST = 2;
    static String LIGHT = "FFFFFF", DARK = "000000";
    String colorMode = LIGHT;
    int gameState = DEFAULT;
    int buttonSize = 50;
    Stage primaryStage;
    MineMap myMineMap;
    MenuBar menuBar;
    Home myHome;
    HBox myHBox = new HBox();
    VBox myVBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setTitle("MineSweeper");

        menuBar = new MenuBar(this);
        menuBar.setAlignment(Pos.CENTER);

        myHome = new Home(this);

        setVBoxColor(myVBox, colorMode);

        myHBox.setAlignment(Pos.CENTER);
        myVBox.getChildren().add(myHome);
        myVBox.setAlignment(Pos.CENTER);
        myHBox.setFillHeight(true);

        Scene myScene = new Scene(myVBox);

        primaryStage.setScene(myScene);

        primaryStage.show();
    }

    public void setVBoxColor(VBox vBox, String color) {
        vBox.setStyle("-fx-background-color: #" + color + ";");
    }

    public void setButtonColor(Button button, String color) {
        button.setStyle("-fx-background-color: #" + color + ";");
    }

    public void toggleColormode() {
        if (colorMode.equals(LIGHT)) {
            colorMode = DARK;
        } else {
            colorMode = LIGHT;
        }

        myVBox.getChildren().removeAll(myHome);

        menuBar = new MenuBar(this);
        menuBar.setAlignment(Pos.CENTER);

        myHome = new Home(this);

        setVBoxColor(myVBox, colorMode);

        myVBox.getChildren().add(myHome);
    }

    public void toggleFlagmode() {
        isFlagmodeOn = !isFlagmodeOn;
    }

    public void setHomeScene(Main main) {
        myVBox.getChildren().removeAll(menuBar, myHBox);
        myHome.refreshGameState(main);
        myVBox.getChildren().add(myHome);
    }

    public void setGameScene() {
        myVBox.getChildren().remove(myHome);
        myVBox.getChildren().addAll(menuBar, myHBox);
    }

    public void setMode(int size) {
        myHBox.getChildren().remove(myMineMap);
        myMineMap = new MineMap(this, size);
        myHBox.getChildren().add(myMineMap);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
