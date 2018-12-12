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
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        scanner.close();
        return list;
    }

    public static double[][] makeMatrix(String type, List<String> listOfLines) throws Exception {
        switch (type) {

            case "Acer":
                String[] acerArr = listOfLines.stream().filter(s -> s.contains("Acer")).toArray(String[]::new);
                double[][] acerMatrix = new double[acerArr.length][64];
                for (int i = 0; i < acerArr.length; i++) {
                    String[] features = acerArr[i].split(",");
                    for (int j = 1; j < 65; j++) {
                        acerMatrix[i][j-1] = Double.parseDouble(features[j]);
                    }
                }
                return acerMatrix;

            case "Quercus":
                String[] quercusArr = listOfLines.stream().filter(s -> s.contains("Quercus")).toArray(String[]::new);
                double[][] quercusMatrix = new double[quercusArr.length][64];
                for (int i = 0; i < quercusArr.length; i++) {
                    String[] features = quercusArr[i].split(",");
                    for (int j = 1; j < 65; j++) {
                        quercusMatrix[i][j-1] = Double.parseDouble(features[j]);
                    }
                }
                return quercusMatrix;

            default:
                throw new Exception("Bad class name");

        }
    }
}
