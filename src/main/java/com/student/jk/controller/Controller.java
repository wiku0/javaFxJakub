package com.student.jk.controller;


import com.student.jk.controller.Utils.Fisher;
import com.student.jk.controller.Utils.LoaderDataBase;
import com.student.jk.controller.Utils.Sfs;
import com.student.jk.controller.Utils.Val;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;


public class Controller implements Initializable {
    double[][] acerMatrix;
    double[][] quercusMatrix;

    double[][] acerTrainMatrix;
    double[][] quercusTrainMatrix;

    double[][] acerMatrixParted;
    double[][] quercusMatrixParted;

    Val featuresSelection;

    @FXML
    ComboBox noOfFeatures, classifiers, kNumbers;

    @FXML
    ToggleGroup group;

    @FXML
    Button computeB, executeButton;

    @FXML
    TextArea textArea;

    @FXML
    TextField textFieldTraingPart;

    @FXML
    Tab classifiersTab;

    @FXML
    public void loadFromFile() throws FileNotFoundException {
        File file = LoaderDataBase.fileChooser();
        if (file != null && file.isFile()) {
            List<String> listOfLines = LoaderDataBase.getStringListFromFile(file);
            acerMatrix = LoaderDataBase.makeMatrix("Acer", listOfLines);
            quercusMatrix = LoaderDataBase.makeMatrix("Quercus", listOfLines);
            computeB.setDisable(false); //odblokowanie przycisku compute po zaladowaniu bazy
            classifiersTab.setDisable(false);
        }
    }

    @FXML
    public void computeButton() {
        switch (((RadioButton) group.getSelectedToggle()).getText()) {

            case ("Fisher"):
                Fisher fisher = new Fisher(acerMatrix, quercusMatrix, (int) noOfFeatures.getSelectionModel().getSelectedItem());
                featuresSelection = fisher.getFisher();
                textArea.setText("Numer cechy: " + Arrays.toString(Arrays.stream(featuresSelection.getFeatures()).map(i -> i + 1).toArray()) + "\nFisher: " + featuresSelection.getFisher());
                break;
            case ("SFS"):
                Sfs sfs = new Sfs(acerMatrix, quercusMatrix, (int) noOfFeatures.getSelectionModel().getSelectedItem());
                featuresSelection = sfs.getFisher();
                textArea.setText("Numer cechy: " + Arrays.toString(Arrays.stream(featuresSelection.getFeatures()).map(i -> i + 1).toArray()) + "\nFisher: " + featuresSelection.getFisher());
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfFeatures.getItems().addAll(IntStream.rangeClosed(1, 64).mapToObj(Integer::new).toArray());
        noOfFeatures.getSelectionModel().select(0);

        classifiers.getItems().addAll(Classifiers.values());
        classifiers.getSelectionModel().select(0);

        kNumbers.getItems().addAll(3, 5, 7, 9);
        kNumbers.getSelectionModel().select(0);
    }

    @FXML
    public void trainButton() {
        int percentPart = Integer.parseInt(textFieldTraingPart.getText());

        acerTrainMatrix = new double[acerMatrix.length][acerMatrix[0].length * percentPart / 100];
        quercusTrainMatrix = new double[quercusMatrix.length][quercusMatrix[0].length * percentPart / 100];

        for (int i = 0; i < acerTrainMatrix.length; i++) {
            for (int j = 0; j < acerTrainMatrix[0].length; j++) {
                acerTrainMatrix[i][j] = acerMatrix[i][j];
            }
        }
        for (int i = 0; i < quercusMatrix.length; i++) {
            for (int j = 0; j < quercusTrainMatrix[0].length; j++) {
                quercusTrainMatrix[i][j] = quercusMatrix[i][j];
            }
        }

        acerMatrixParted = new double[acerMatrix.length][acerMatrix.length - acerTrainMatrix.length];
        quercusMatrixParted = new double[quercusTrainMatrix.length][quercusMatrix.length - quercusTrainMatrix.length];

        for (int i = 0; i < acerMatrixParted.length; i++) {
            for (int j = 0; j < acerMatrixParted[0].length; j++) {
                acerMatrixParted[i][j] = acerMatrix[i][acerTrainMatrix.length + j];
            }
        }

        for (int i = 0; i < quercusMatrixParted.length; i++) {
            for (int j = 0; j < quercusMatrixParted[0].length; j++) {
                quercusMatrixParted[i][j] = quercusMatrix[i][quercusTrainMatrix.length + j];
            }
        }


        executeButton.setDisable(false);

    }

    @FXML
    public void classifiersOnAction() {
        switch ((Classifiers) classifiers.getSelectionModel().getSelectedItem()) {
            case NM:
                kNumbers.setDisable(true);
                break;
            case NN:
                kNumbers.setDisable(true);
                break;
            case KNM:
                kNumbers.setDisable(false);
                break;

        }
    }

}
