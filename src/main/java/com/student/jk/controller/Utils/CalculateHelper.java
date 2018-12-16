package com.student.jk.controller.Utils;

import Jama.Matrix;

import java.util.Arrays;
import java.util.List;

public class CalculateHelper {

    public static Val oneFeature(double[][] acerMatrix, double[][] quercusMatrix, int i) {
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
        double sumaOdchylenAcer = 0;
        double sumaOdchylenQuercus = 0;
        for (int j = 0; j < odchylenieAcer.length; j++) {
            sumaOdchylenAcer = sumaOdchylenAcer + odchylenieAcer[j];
        }
        for (int j = 0; j < odchylenieQuercus.length; j++) {
            sumaOdchylenQuercus = sumaOdchylenQuercus + odchylenieQuercus[j];
        }
        double sigmaAcer = Math.sqrt(sumaOdchylenAcer / acerMatrix[i].length);
        double sigmaQuercus = Math.sqrt(sumaOdchylenQuercus / quercusMatrix[i].length);
        double fisher = Math.abs(sredniaKlasyAcer - sredniaKlasyQuercus) / (sigmaAcer + sigmaQuercus);

        return new Val(new int[]{i}, fisher);
    }

    public static Val nFeatures(List<Double> srednieCechAcer, List<Double> srednieCechQuercus, List<double[]> wartoscCechAcer, List<double[]> wartoscCechQuercus, int[] arrayOfFeatures) {
        double[][] arrayOfSrednieCechAcer = new double[srednieCechAcer.size()][wartoscCechAcer.get(0).length];
        double[][] arrayOfSrednieCechQuercus = new double[srednieCechQuercus.size()][wartoscCechQuercus.get(0).length];

        for (int i = 0; i < arrayOfSrednieCechAcer.length; i++) {
            for (int j = 0; j < arrayOfSrednieCechAcer[i].length; j++) {
                arrayOfSrednieCechAcer[i][j] = srednieCechAcer.get(i);
            }
        }

        for (int i = 0; i < arrayOfSrednieCechQuercus.length; i++) {
            for (int j = 0; j < arrayOfSrednieCechQuercus[i].length; j++) {
                arrayOfSrednieCechQuercus[i][j] = srednieCechQuercus.get(i);
            }
        }

        Matrix matrixOfSrednieCechyAcer = new Matrix(arrayOfSrednieCechAcer);
        Matrix matrixOfSrednieCecnyQuercus = new Matrix(arrayOfSrednieCechQuercus);

        double[][] arrayOfAcerFeatures = new double[wartoscCechAcer.size()][wartoscCechAcer.get(0).length];
        double[][] arrayOfQuercusFeatures = new double[wartoscCechQuercus.size()][wartoscCechQuercus.get(0).length];

        for (int i = 0; i < arrayOfAcerFeatures.length; i++) {
            arrayOfAcerFeatures[i] = wartoscCechAcer.get(i);
        }

        for (int i = 0; i < arrayOfAcerFeatures.length; i++) {
            arrayOfQuercusFeatures[i] = wartoscCechQuercus.get(i);
        }

        Matrix matrixOfAcerFeatures = new Matrix(arrayOfAcerFeatures);
        Matrix matrixOfQuercusFeatures = new Matrix(arrayOfQuercusFeatures);

        Matrix roznicaAcer = matrixOfAcerFeatures.minus(matrixOfSrednieCechyAcer);
        Matrix roznicaQuercus = matrixOfQuercusFeatures.minus(matrixOfSrednieCecnyQuercus);

        Matrix matrixKowariancjiAcer = roznicaAcer.times(roznicaAcer.transpose()).times((double) 1 / wartoscCechAcer.get(0).length);
        Matrix matrixKowariancjiQuercus = roznicaQuercus.times(roznicaQuercus.transpose()).times((double) 1 / wartoscCechQuercus.get(0).length);

        double[] srednie = new double[srednieCechAcer.size()];
        for (int i = 0; i < srednieCechAcer.size(); i++) {
            srednie[i] = srednieCechAcer.get(i) - srednieCechQuercus.get(i);
        }

        double[] srednieDoKwadratu = Arrays.stream(srednie).map(i -> Math.pow(i, 2)).toArray();
        double wynik = Math.sqrt(Arrays.stream(srednieDoKwadratu).sum());

        double result = wynik / (matrixKowariancjiAcer.det() + matrixKowariancjiQuercus.det());

        return new Val(arrayOfFeatures, result);
    }


}
