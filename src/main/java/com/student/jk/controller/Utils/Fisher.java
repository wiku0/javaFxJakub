package com.student.jk.controller.Utils;

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
            Val v = CalculateHelper.oneFeature(acerMatrix, quercusMatrix, i);
            results.add(v);
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
            Val t = CalculateHelper.nFeatures(srednieCechAcer, srednieCechQuercus, wartoscCechAcer, wartoscCechQuercus, arrayOfFeatures);
            results.add(t);
        }
    }


    public Val getFisher() {
        return results.stream().max(Comparator.comparing(Val::getFisher)).get();
    }


}

