package com.student.jk.controller.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sfs {
    double[][] acerMatrix;
    double[][] quercusMatrix;
    int noOfFeatures;
    List<Val> results;
    Val bestVal;

    public Sfs(double[][] acerMatrix, double[][] quercusMatrix, int noOfFeatures) {
        this.acerMatrix = acerMatrix;
        this.quercusMatrix = quercusMatrix;
        this.noOfFeatures = noOfFeatures;
        results = new ArrayList<>();
        if (noOfFeatures == 1) count();
        else count2();
    }

    private void count2() {
        count();
        bestVal = getFisher();
        results.clear();

        for (int z = 1; z < noOfFeatures; z++) {

            int[] featuresOfBestVal = bestVal.getFeatures();
            List<Val> temp = new ArrayList<>();
            List<Integer> listOfFeatures = Arrays.stream(featuresOfBestVal).boxed().collect(Collectors.toList());

            for (int j = 0; j < acerMatrix.length; j++) {
                if (!listOfFeatures.contains(j)) {
                    listOfFeatures.add(j);
                    int[] arrayOfFeatures = listOfFeatures.stream().mapToInt(Integer::new).toArray();

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

                    temp.add(t);

                    listOfFeatures.remove((Object) j);
                }
            }
            bestVal = temp.stream().max(Comparator.comparing(Val::getFisher)).get();
        }

        int[] toSort = bestVal.getFeatures();
        int[] sortedFeatures = Arrays.stream(toSort).boxed().collect(Collectors.toList()).stream().sorted().mapToInt(i -> i).toArray();
        double fisher = bestVal.getFisher();
        results.add(new Val(sortedFeatures, fisher));
    }

    private void count() {
        for (int i = 0; i < acerMatrix.length; i++) {
            Val v = CalculateHelper.oneFeature(acerMatrix, quercusMatrix, i);
            results.add(v);
        }
    }

    public Val getFisher() {
        return results.stream().max(Comparator.comparing(Val::getFisher)).get();
    }


}
