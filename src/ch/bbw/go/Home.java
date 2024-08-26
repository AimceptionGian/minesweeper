package ch.bbw.go;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Home extends VBox {
    static int DEFAULT = 0, WON = 1, LOST = 2;
    static String LIGHT = "FFFFFF", DARK = "000000";

    Button state;

    Button easyMode;
    Button mediumMode;
    Button hardMode;

    HBox difficulty;

    FileInputStream wonImageReader;
    ImageView wonImageView;

    FileInputStream lostImageReader;
    ImageView lostImageView;

    FileInputStream defaultImageReader;
    ImageView defaultImageView;

    FileInputStream playImageReader;
    ImageView playImageView;

    FileInputStream easyImageReader;
    FileInputStream mediumImageReader;
    FileInputStream hardImageReader;

    FileInputStream exitImageReader;
    FileInputStream lightImageReader;
    FileInputStream darkImageReader;

    public Home(Main main) {
        this.setAlignment(Pos.CENTER);
        main.setVBoxColor(this, main.colorMode);

        gameStateSetup(main);
        refreshGameState(main);

        difficulty = new HBox();
        difficulty.setAlignment(Pos.CENTER);

        difficultySetup(main);

        Button exit = new Button();
        exit.setPadding(new Insets(20));
        main.setButtonColor(exit, main.colorMode);

        try {
            exitImageReader = new FileInputStream("src/ressources/exit.png");
            lightImageReader = new FileInputStream("src/ressources/lightmode.png");
            darkImageReader = new FileInputStream("src/ressources/darkmode.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        Image exitImage = new Image(exitImageReader);
        ImageView exitImageView = new ImageView(exitImage);
        exitImageView.setPreserveRatio(true);
        exitImageView.setFitHeight(60);
        exit.setGraphic(exitImageView);

        exit.setOnAction((e) -> {
            Platform.exit();
        });

        Button colormode = new Button();
        colormode.setPadding(new Insets(20));
        main.setButtonColor(colormode, main.colorMode);

        Image lightImage = new Image(lightImageReader);
        Image darkImage = new Image(darkImageReader);
        ImageView lightImageView = new ImageView(lightImage);
        ImageView darkImageView = new ImageView(darkImage);
        lightImageView.setPreserveRatio(true);
        lightImageView.setFitHeight(60);
        darkImageView.setPreserveRatio(true);
        darkImageView.setFitHeight(60);

        if (main.colorMode == DARK) {
            colormode.setGraphic(lightImageView);
        } else {
            colormode.setGraphic(darkImageView);
        }

        colormode.setOnAction((e) -> {
            main.toggleColormode();
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(exit, colormode);

        this.getChildren().add(state);
        this.getChildren().addAll(difficulty, hBox);
        refreshGameState(main);
    }

    private void gameStateSetup(Main main) {
        state = new Button();
        state.setPadding(new Insets(20));
        main.setButtonColor(state, main.colorMode);

        try {
            wonImageReader = new FileInputStream("src/ressources/won.png");
            lostImageReader = new FileInputStream("src/ressources/lost.png");
            defaultImageReader = new FileInputStream("src/ressources/minesweeper.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image wonImage = new Image(wonImageReader);
        wonImageView = new ImageView(wonImage);

        Image lostImage = new Image(lostImageReader);
        lostImageView = new ImageView(lostImage);

        Image defaultImage = new Image(defaultImageReader);
        defaultImageView = new ImageView(defaultImage);

        wonImageView.setPreserveRatio(true);
        wonImageView.setFitHeight(100);
        lostImageView.setPreserveRatio(true);
        lostImageView.setFitHeight(100);
        defaultImageView.setPreserveRatio(true);
        defaultImageView.setFitHeight(155);
    }

    public void refreshGameState(Main main) {
        if (main.gameState == WON){
            state.setGraphic(wonImageView);
        } else if (main.gameState == LOST){
            state.setGraphic(lostImageView);
        } else {
            state.setGraphic(defaultImageView);
        }

        this.getChildren().remove(playImageView);
        if (main.gameState != DEFAULT) {
            playSetup();
            if (this.getChildren().size() != 0) {
                this.getChildren().add(1, playImageView);
            }
        }
    }

    private void playSetup() {
        try {
            playImageReader = new FileInputStream("src/ressources/play.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image playImage = new Image(playImageReader);
        playImageView = new ImageView(playImage);

        playImageView.setPreserveRatio(true);
        playImageView.setFitHeight(103);
    }

    private void difficultySetup(Main main) {
        easyMode = new Button();
        mediumMode = new Button();
        hardMode = new Button();

        easyMode.setPadding(new Insets(20));
        mediumMode.setPadding(new Insets(20));
        hardMode.setPadding(new Insets(20));

        main.setButtonColor(easyMode, main.colorMode);
        main.setButtonColor(mediumMode, main.colorMode);
        main.setButtonColor(hardMode, main.colorMode);

        try {
            easyImageReader = new FileInputStream("src/ressources/easy.png");
            mediumImageReader = new FileInputStream("src/ressources/medium.png");
            hardImageReader = new FileInputStream("src/ressources/hard.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image easyImage = new Image(easyImageReader);
        Image mediumImage = new Image(mediumImageReader);
        Image hardImage = new Image(hardImageReader);
        ImageView easyImageView = new ImageView(easyImage);
        ImageView mediumImageView = new ImageView(mediumImage);
        ImageView hardImageView = new ImageView(hardImage);
        easyImageView.setPreserveRatio(true);
        easyImageView.setFitHeight(60);
        mediumImageView.setPreserveRatio(true);
        mediumImageView.setFitHeight(60);
        hardImageView.setPreserveRatio(true);
        hardImageView.setFitHeight(60);

        easyMode.setGraphic(easyImageView);
        mediumMode.setGraphic(mediumImageView);
        hardMode.setGraphic(hardImageView);

        easyMode.setOnAction((e) -> {
            main.setMode(6);
            main.setGameScene();
        });

        mediumMode.setOnAction((e) -> {
            main.setMode(11);
            main.setGameScene();
        });

        hardMode.setOnAction((e) -> {
            main.setMode(16);
            main.setGameScene();
        });

        difficulty.getChildren().addAll(easyMode, mediumMode, hardMode);
    }
}
