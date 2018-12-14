package com.student.jk.controller;


import com.student.jk.controller.Utils.Fisher;
import com.student.jk.controller.Utils.LoaderDataBase;
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

    @FXML
    ComboBox noOfFeatures;

    @FXML
    ToggleGroup group;

    @FXML
    Button computeB;

    @FXML
    TextArea textArea;

    @FXML
    public void loadFromFile() throws FileNotFoundException {
        File file = LoaderDataBase.fileChooser();
        if (file != null && file.isFile()) {
            List<String> listOfLines = LoaderDataBase.getStringListFromFile(file);
            acerMatrix = LoaderDataBase.makeMatrix("Acer", listOfLines);
            quercusMatrix = LoaderDataBase.makeMatrix("Quercus", listOfLines);
            computeB.setDisable(false); //odblokowanie przycisku compute po zaladowaniu bazy

        }
    }

    @FXML
    public void computeButton() {
        switch (((RadioButton) group.getSelectedToggle()).getText()) {
            case ("Fisher"):
                Fisher fisher = new Fisher(acerMatrix, quercusMatrix, (int) noOfFeatures.getSelectionModel().getSelectedItem());
                Val bestFisher = fisher.getFisher();
                textArea.setText("Numer cechy: " + Arrays.toString(bestFisher.getFeatures()) + "\nFisher: " + bestFisher.getFisher());
                break;
            case ("SFS"):
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfFeatures.getItems().addAll(IntStream.rangeClosed(1, 64).mapToObj(Integer::new).toArray());
        noOfFeatures.getSelectionModel().select(0);
    }
}
