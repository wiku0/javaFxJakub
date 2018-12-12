package com.student.jk.controller.Utils;

import javafx.stage.FileChooser;

import java.io.File;

public class LoaderDataBase {

    public static File fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().size();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        fileChooser.setTitle("Select database");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dokument tekstowy", "*.txt"));
        return fileChooser.showOpenDialog(null);
    }
}
