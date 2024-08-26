package ch.bbw.go;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.xml.bind.annotation.XmlType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuBar extends HBox {
    static int DEFAULT = 0;
    FileInputStream homeReader, flagmodeReader, activeFlagmodeReader;

    public MenuBar(Main main) {
        homeSetup(main);
        flagmodeSetup(main);
    }

    private void homeSetup(Main main) {
        Button home = new Button();
        home.setPadding(new Insets(5));
        main.setButtonColor(home, main.colorMode);

        try {
            homeReader = new FileInputStream("src/ressources/home.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image homeImage = new Image(homeReader);
        ImageView homeImageView = new ImageView(homeImage);

        homeImageView.setPreserveRatio(true);
        homeImageView.setFitHeight(50);


        home.setGraphic(homeImageView);
        home.setOnAction((e) -> {
            main.buttonSize = 50;
            main.gameState = DEFAULT;
            main.setHomeScene(main);
        });

        this.getChildren().add(home);
    }

    private void flagmodeSetup(Main main) {
        Button flagmode = new Button();
        flagmode.setPadding(new Insets(5));
        main.setButtonColor(flagmode, main.colorMode);

        try {
            flagmodeReader = new FileInputStream("src/ressources/flagmode.png");
            activeFlagmodeReader = new FileInputStream("src/ressources/flagmodeActive.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image flagmodeImage = new Image(flagmodeReader);
        Image activeFlagmodeImage = new Image(activeFlagmodeReader);
        ImageView flagmodeImageView = new ImageView(flagmodeImage);
        ImageView activeFlagmodeImageView = new ImageView(activeFlagmodeImage);

        flagmodeImageView.setPreserveRatio(true);
        flagmodeImageView.setFitHeight(50);
        activeFlagmodeImageView.setPreserveRatio(true);
        activeFlagmodeImageView.setFitHeight(50);


        flagmode.setGraphic(flagmodeImageView);
        flagmode.setOnAction((e) -> {
            main.toggleFlagmode();
            if (main.isFlagmodeOn) {
                flagmode.setGraphic(activeFlagmodeImageView);
            } else {
                flagmode.setGraphic(flagmodeImageView);
            }
        });

        this.getChildren().add(flagmode);
    }
}
