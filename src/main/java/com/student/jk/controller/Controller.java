package com.student.jk.controller;


import com.student.jk.controller.Utils.LoaderDataBase;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;


public class Controller {

    @FXML
    public void loadFromFile() {
        File file = LoaderDataBase.fileChooser();
        System.out.println(file.getAbsolutePath());
    }

}
