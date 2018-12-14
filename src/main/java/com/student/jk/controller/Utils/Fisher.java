package com.student.jk.controller.Utils;

import Jama.Matrix;
import org.apache.commons.math3.util.Combinations;

import java.util.*;

public class Fisher {
    double[][] acerMatrix;
    double[][] quercusMatrix;
    int noOfFeatures;
    List<Val> results;


    public Fisher(double[][] acerMatrix, double[][] quercusMatrix, int noOfFeatures) {
        this.acerMatrix = acerMatrix;
        this.quercusMatrix = quercusMatrix;
        this.noOfFeatures = noOfFeatures;
        results = new ArrayList<>();
        if (noOfFeatures == 1) count();
        else count2();
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
            results.add(new Val(new int[]{i + 1}, fisher));
        }
    }


    private void count2() {
        Combinations combinations = new Combinations(acerMatrix.length, noOfFeatures);
        Iterator iterator = combinations.iterator();

        while (iterator.hasNext()) {
            int[] arrayOfFeatures = (int[]) iterator.next();
            List<Double> srednieCechAcer = new ArrayList<>();
            List<Double> srednieCechQuercus = new ArrayList<>();
            List<double[]> wartoscCechAcer = new ArrayList<>();
            List<double[]> wartoscCechQuercus = new ArrayList<>();

            for (int i = 0; i < arrayOfFeatures.length; i++) {
                double sredniaCechyAcer = Arrays.stream(acerMatrix[arrayOfFeatures[i]]).average().getAsDouble();
                double sredniaCechyQuercus = Arrays.stream(quercusMatrix[arrayOfFeatures[i]]).average().getAsDouble();
                srednieCechAcer.add(sredniaCechyAcer);
                srednieCechQuercus.add(sredniaCechyQuercus);
                wartoscCechAcer.add(acerMatrix[arrayOfFeatures[i]]);
                wartoscCechQuercus.add(quercusMatrix[arrayOfFeatures[i]]);
            }
            countOnMatrix(srednieCechAcer, srednieCechQuercus, wartoscCechAcer, wartoscCechQuercus, arrayOfFeatures);
        }


    }

    private void countOnMatrix(List<Double> srednieCechAcer, List<Double> srednieCechQuercus, List<double[]> wartoscCechAcer, List<double[]> wartoscCechQuercus, int[] arrayOfFeatures) {
        double[][] arrayOfSrednieCechAcer = new double[srednieCechAcer.size()][acerMatrix[0].length];
        double[][] arrayOfSrednieCechQuercus = new double[srednieCechQuercus.size()][quercusMatrix[0].length];

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

        double[][] arrayOfAcerFeatures = new double[wartoscCechAcer.size()][acerMatrix[0].length];
        double[][] arrayOfQuercusFeatures = new double[wartoscCechQuercus.size()][quercusMatrix[0].length];

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

        Matrix matrixKowariancjiAcer = roznicaAcer.times(roznicaAcer.transpose()).times((double) 1 / acerMatrix[0].length);
        Matrix matrixKowariancjiQuercus = roznicaQuercus.times(roznicaQuercus.transpose()).times((double) 1 / quercusMatrix[0].length);

        double[] srednie = new double[srednieCechAcer.size()];
        for (int i = 0; i < srednieCechAcer.size(); i++) {
            srednie[i] = srednieCechAcer.get(i) - srednieCechQuercus.get(i);
        }

        double[] srednieDoKwadratu = Arrays.stream(srednie).map(i -> Math.pow(i, 2)).toArray();
        double wynik = Math.sqrt(Arrays.stream(srednieDoKwadratu).sum());

        double result = wynik / (matrixKowariancjiAcer.det() + matrixKowariancjiQuercus.det());
        results.add(new Val(Arrays.stream(arrayOfFeatures).map(i -> i + 1).toArray(), result));
    }


    public Val getFisher() {
        return results.stream().max(Comparator.comparing(Val::getFisher)).get();
    }


}

