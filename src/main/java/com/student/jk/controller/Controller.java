package com.student.jk.controller;


import com.student.jk.controller.Utils.LoaderDataBase;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Controller {

    @FXML
    public void loadFromFile() throws FileNotFoundException {
        File file = LoaderDataBase.fileChooser();
        List<String> listOfLines = LoaderDataBase.getStringListFromFile(file);

    }

}
