package ch.bbw.go;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MineMap extends GridPane {
    Main main;

    static int WON = 1, LOST = 2;
    private final int DEFAULT = 0, BOMB = -1;
    private final int ZUGEDECKT = 0, AUFGEDECKT = 1, FLAGGED = 2;
    int [][] gridType;
    int [][] gridState;
    int size;
    int anzahlBomben = 0;
    int anzahlAufgedeckt = 0;

    FileInputStream[] imageReaders = new FileInputStream[9];
    FileInputStream hiddenImageReader, zeroImageReader, bombImageReader, flagImageReader;

    Image[] images = new Image[9];
    Image hidden, bomb, flag;

    public MineMap(Main main, int size) {
        this.main = main;
        this.size = size;
        gridType = new int [size][size];
        gridState = new int [size][size];
        //Random number between 0 and 1
        Random random = new Random();
        //create 3 rows
        for (int row = 0; row < size; row++) {
            //create 3 columns for each row
            for (int column = 0; column < size; column++){
                int blockType = random.nextInt(100);
                if (blockType <= 85) {
                    gridType [row][column] = DEFAULT;
                } else {
                    gridType[row][column] = BOMB;
                    anzahlBomben++;
                }
                gridState [row][column] = ZUGEDECKT;
                this.add(buildButton(row, column), column, row);
            }
        }

        try {
            hiddenImageReader = new FileInputStream("src/ressources/hidden.png");
            zeroImageReader = new FileInputStream("src/ressources/0.png");
            bombImageReader = new FileInputStream("src/ressources/bomb.png");
            flagImageReader = new FileInputStream("src/ressources/flag.png");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++){
            try {
                imageReaders[i] = new FileInputStream("src/ressources/" + i + ".png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        hidden = new Image(hiddenImageReader);
        bomb = new Image(bombImageReader);
        flag = new Image(flagImageReader);

        for (int i = 0; i < 9; i++) {
            images[i] = new Image(imageReaders[i]);
        }

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++){
                if(gridType[row][column] != BOMB) {
                    gridType[row][column] = countBombs(row, column);
                }
            }
        }
        refreshUI();
    }

    private void buttonClicked(int row, int column) {
        if(gridState[row][column] == AUFGEDECKT) return;

        if (main.isFlagmodeOn) {
            gridState[row][column] = FLAGGED;
        } else {
            uncoverField((Button) this.getChildren().get(row * size + column), row, column);
            if (gridType[row][column] == BOMB) {
                main.gameState = LOST;
                main.setHomeScene(main);
            }

            gridState[row][column] = AUFGEDECKT;

            if (anzahlAufgedeckt == Math.pow(size, 2) - anzahlBomben) {
                main.gameState = WON;
                main.setHomeScene(main);
            }
        }
        refreshUI();
    }

    private Button buildButton(int row, int column) {
        Button button = new Button();
        button.setOnAction((e) -> {
            buttonClicked(row, column);
        });

        setButtonGraphic(button, 0 , ZUGEDECKT);

        return button;
    }

    private int countBombs(int row, int column){
        int bombCounter = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(row+i < size && row+i >= 0 && column+j < size && column+j >= 0) {
                    if(gridType[row+i][column+j] == BOMB) {
                        bombCounter++;
                    }
                }
            }
        }

        return bombCounter;
    }

    private void uncoverField(Button button, int row, int column){
        if(gridState[row][column] == AUFGEDECKT) return;
        else gridState [row][column] = AUFGEDECKT;
        anzahlAufgedeckt++;

        //uncover the appropriate fields

        switch (gridType[row][column]) {
            case DEFAULT:
                surroundingButtons(row, column);
                break;
            default:
                break;
        }
    }

    private void surroundingButtons(int row, int column) {
        if (gridType[row][column] != BOMB) {
            //upper neighbour: row - 1, same column
            int rowUpper = row - 1, colUpper = column;
            //there is no upper neighbour on the 0 row
            if (row != 0) {
                if (gridType[rowUpper][colUpper] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowUpper * size + colUpper), rowUpper, colUpper);
                }
            }

            int rowUpperRight = row - 1, colUpperRight = column + 1;

            if (row != 0 && column != (size - 1)) {
                if (gridType[rowUpperRight][colUpperRight] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowUpperRight * size + colUpperRight), rowUpperRight, colUpperRight);
                }
            }

            int rowUpperLeft = row - 1, colUpperLeft = column - 1;

            if (row != 0 && column != 0) {
                if (gridType[rowUpperLeft][colUpperLeft] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowUpperLeft * size + colUpperLeft), rowUpperLeft, colUpperLeft);
                }
            }

            int rowLower = row + 1, colLower = column;

            if (row != (size - 1)) {
                if (gridType[rowLower][colLower] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowLower * size + colLower), rowLower, colLower);
                }
            }

            int rowRight = row, colRight = column + 1;

            if (column != (size - 1)) {
                if (gridType[rowRight][colRight] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowRight * size + colRight), rowRight, colRight);
                }
            }

            int rowLeft = row, colLeft = column - 1;

            if (column != 0) {
                if (gridType[rowLeft][colLeft] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowLeft * size + colLeft), rowLeft, colLeft);
                }
            }

            int rowLowerRight = row + 1, colLowerRight = column + 1;

            if (row != (size - 1) && column != (size - 1)) {
                if (gridType[rowLowerRight][colLowerRight] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowLowerRight * size + colLowerRight), rowLowerRight, colLowerRight);
                }
            }

            int rowLowerLeft = row + 1, colLowerLeft = column - 1;

            if (row != (size - 1) && column != 0) {
                if (gridType[rowLowerLeft][colLowerLeft] != BOMB) {
                    uncoverField((Button) this.getChildren().get(rowLowerLeft * size + colLowerLeft), rowLowerLeft, colLowerLeft);
                }
            }
        }
    }

    private void refreshUI(){
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++){
                setButtonGraphic((Button) this.getChildren().get(row * size + column), gridType[row][column], gridState[row][column]);
            }
        }
    }

    private void setButtonGraphic(Button button, int buttonType, int buttonState) {

        ImageView imageViews[] = new ImageView[9];
        ImageView hiddenImage = new ImageView(hidden);
        ImageView bombImage = new ImageView(bomb);
        ImageView flagImage = new ImageView(flag);

        for (int i = 0; i < 9; i++) {
            imageViews[i] = new ImageView(images[i]);
        }

        hiddenImage.setPreserveRatio(true);
        hiddenImage.setFitHeight(main.buttonSize);

        bombImage.setPreserveRatio(true);
        bombImage.setFitHeight(main.buttonSize);

        flagImage.setPreserveRatio(true);
        flagImage.setFitHeight(main.buttonSize);

        for (int i = 0; i < 9; i++) {
            imageViews[i].setPreserveRatio(true);
            imageViews[i].setFitHeight(main.buttonSize);
        }

        if (buttonState == FLAGGED) {
            button.setGraphic(flagImage);
        } else if (buttonState == ZUGEDECKT) {
            button.setGraphic(hiddenImage);
        } else {
            if (buttonType == BOMB) {
                button.setGraphic(bombImage);
            } else {
                switch (buttonType) {
                    case 0:
                        button.setGraphic(imageViews[0]);
                        break;
                    case 1:
                        button.setGraphic(imageViews[1]);
                        break;
                    case 2:
                        button.setGraphic(imageViews[2]);
                        break;
                    case 3:
                        button.setGraphic(imageViews[3]);
                        break;
                    case 4:
                        button.setGraphic(imageViews[4]);
                        break;
                    case 5:
                        button.setGraphic(imageViews[5]);
                        break;
                    case 6:
                        button.setGraphic(imageViews[6]);
                        break;
                    case 7:
                        button.setGraphic(imageViews[7]);
                        break;
                    case 8:
                        button.setGraphic(imageViews[8]);
                        break;
                }
            }
        }
        button.setPadding(new Insets(0.5));
        main.setButtonColor(button, main.colorMode);
    }
}
