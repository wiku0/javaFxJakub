package com.student.jk.controller.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Fisher {
    double[][] acerMatrix;
    double[][] quercusMatrix;
    int noOfFeatures;
    List<Double> results;


    public Fisher(double[][] acerMatrix, double[][] quercusMatrix, int noOfFeatures) {
        this.acerMatrix = acerMatrix;
        this.quercusMatrix = quercusMatrix;
        this.noOfFeatures = noOfFeatures;
        results = new ArrayList<>();
        count();
    }


    private void count() {

        for (int i = 0; i < acerMatrix.length; i++) {
            double sredniaKlasyAcer = Arrays.stream(acerMatrix[i]).average().getAsDouble();
            double sredniaKlasyQuercus = Arrays.stream(quercusMatrix[i]).average().getAsDouble();

            //rozncia wartosci cechy od sredniej potrzebna do wyliczenia odchylenia
            double[] odchylenieAcer = Arrays.stream(acerMatrix[i]).map(x -> Math.pow(x - sredniaKlasyAcer, 2)).toArray();
            double[] odchylenieQuercus = Arrays.stream(quercusMatrix[i]).map(x -> Math.pow(x - sredniaKlasyQuercus, 2)).toArray();

            double sigmaAcer = Math.sqrt(Arrays.stream(odchylenieAcer).sum() / acerMatrix[i].length);
            double sigmaQuercus = Math.sqrt(Arrays.stream(odchylenieQuercus).sum() / quercusMatrix[i].length);

            double fisher = Math.abs(sredniaKlasyAcer - sredniaKlasyQuercus) / (sigmaAcer + sigmaQuercus);
            results.add(fisher);
        }
    }


    public double getFisher() {
        return results.stream().mapToDouble(i -> i).max().getAsDouble();
    }

    public int getNumberOfFeature() {
        return results.indexOf(getFisher());
    }

}
