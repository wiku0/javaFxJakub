package com.student.jk.controller;


import com.student.jk.controller.Utils.LoaderDataBase;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Controller {
    double[][] acerMatrix;
    double[][] quercusMatrix;

    @FXML
    public void loadFromFile() throws Exception {
        File file = LoaderDataBase.fileChooser();
        List<String> listOfLines = LoaderDataBase.getStringListFromFile(file);
        acerMatrix = LoaderDataBase.makeMatrix("Acer", listOfLines);
        quercusMatrix = LoaderDataBase.makeMatrix("Quercus", listOfLines);

        System.out.println(acerMatrix.length);
        System.out.println(quercusMatrix.length);
    }

}
