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

            double sumaAcer = 0;
            double sumaQuercus = 0;

            for (int j = 0; j < acerMatrix[i].length; j++) {
                sumaAcer = sumaAcer + acerMatrix[i][j];
            }

            for (int j = 0; j < quercusMatrix[i].length; j++) {
                sumaQuercus = sumaQuercus + quercusMatrix[i][j];
            }

            double sredniaKlasyAcer = sumaAcer / acerMatrix[i].length;
            double sredniaKlasyQuercus = sumaQuercus / quercusMatrix[i].length;

            double[] odchylenieAcer = new double[acerMatrix[i].length];
            double[] odchylenieQuercus = new double[quercusMatrix[i].length];

            for (int j = 0; j < acerMatrix[i].length; j++) {
                odchylenieAcer[j] = Math.pow(acerMatrix[i][j] - sredniaKlasyAcer, 2);
            }
            for (int j = 0; j < quercusMatrix[i].length; j++) {
                odchylenieQuercus[j] = Math.pow(quercusMatrix[i][j] - sredniaKlasyQuercus, 2);
            }

            double sumaOdchylenAcer =0;
            double sumaOdchylenQuercus =0;

            for(int j=0; j<odchylenieAcer.length;j++){
                sumaOdchylenAcer = sumaOdchylenAcer + odchylenieAcer[j];
            }

            for(int j=0; j<odchylenieQuercus.length;j++){
                sumaOdchylenQuercus = sumaOdchylenQuercus + odchylenieQuercus[j];
            }



            double sigmaAcer = Math.sqrt(sumaOdchylenAcer / acerMatrix[i].length);
            double sigmaQuercus = Math.sqrt(sumaOdchylenQuercus / quercusMatrix[i].length);

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
