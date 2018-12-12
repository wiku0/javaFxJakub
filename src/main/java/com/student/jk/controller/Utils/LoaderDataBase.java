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

    public static double[][] makeMatrix(String type, List<String> listOfLines){
        String[] array = listOfLines.stream().filter(s -> s.contains(type)).toArray(String[]::new); //utworzenie tablicy stringow odpowiedniej klasy
        double[][] matrix = new double[array.length][64]; //utworzenie tablicy dwuwymiarowej
        for (int i = 0; i < array.length; i++) {
            String[] features = array[i].split(",");
            for (int j = 1; j < 65; j++) { // od j = 1, bo j=0 to nazwa klasy
                matrix[i][j - 1] = Double.parseDouble(features[j]); //dodanie do tabliczy poszczegolnych wartosci. Pierwszam wartosc z tablicy to numer klasy, druga to numer cechy
            }
        }
        return matrix;
    }
}
