package com.student.jk.controller;


import com.student.jk.controller.Utils.LoaderDataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Controller implements Initializable {
    double[][] acerMatrix;
    double[][] quercusMatrix;

    @FXML
    Label noObjects, noFeatures, noAcer, noQuercus;

    @FXML
    ComboBox noOfFeatures;

    @FXML
    ToggleGroup group;

    @FXML
    public void loadFromFile() throws Exception {
        File file = LoaderDataBase.fileChooser();
        List<String> listOfLines = LoaderDataBase.getStringListFromFile(file);
        acerMatrix = LoaderDataBase.makeMatrix("Acer", listOfLines);
        quercusMatrix = LoaderDataBase.makeMatrix("Quercus", listOfLines);
        noObjects.setText(String.valueOf(acerMatrix.length+quercusMatrix.length));
        noFeatures.setText(String.valueOf(acerMatrix[0].length));
        noAcer.setText(String.valueOf(acerMatrix.length));
        noQuercus.setText(String.valueOf(quercusMatrix.length));
    }

    @FXML
    public void computeButton(){
        System.out.println(((RadioButton)group.getSelectedToggle()).getText());
        System.out.println(noOfFeatures.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noOfFeatures.getItems().addAll(IntStream.rangeClosed(1,64).mapToObj(Integer::new).toArray());
        noOfFeatures.getSelectionModel().select(0);
    }
}
