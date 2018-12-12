package com.student.jk.controller;


import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;


public class Controller {

    @FXML
    public void loadFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().size();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+"/desktop"));
        fileChooser.setTitle("Select database");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Dokument tekstowy", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        System.out.println(file.getAbsolutePath());
    }

}
