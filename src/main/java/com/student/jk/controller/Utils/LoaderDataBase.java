package com.student.jk.controller.Utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoaderDataBase {

    public static File fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().size();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop"));
        fileChooser.setTitle("Select database");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dokument tekstowy", "*.txt"));
        return fileChooser.showOpenDialog(null);
    }

    public static List<String> getStringListFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<>();
        while(scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
        scanner.close();
        return list;
    }
}
