package com.student.jk.controller.Classifiers;

import Jama.Matrix;

import java.util.Arrays;

public class NMClassifiers {

    double[][] acerTrainMatrix;
    double[][] quercusTrainMatrix;
    double[][] acerMatrixParted;
    double[][] quercusMatrixParted;
    int[] features;
    double acerResult, quercusResult;


    public NMClassifiers(double[][] acerTrainMatrix, double[][] quercusTrainMatrix, double[][] acerMatrixParted, double[][] quercusMatrixParted, int[] features) {
        this.acerTrainMatrix = acerTrainMatrix;
        this.quercusTrainMatrix = quercusTrainMatrix;
        this.acerMatrixParted = acerMatrixParted;
        this.quercusMatrixParted = quercusMatrixParted;
        this.features = features;
        calculate();
    }

    public double getAcerResult() {
        return acerResult;
    }

    public double getQuercusResult() {
        return quercusResult;
    }

    private void calculate() {
        double[] meanOfAcerFeatures = new double[features.length];
        double[] meanOfQuercusFeatures = new double[features.length];

        for (int i = 0; i < features.length; i++) {
            meanOfAcerFeatures[i] = Arrays.stream(acerTrainMatrix[features[i]]).average().getAsDouble();
            meanOfQuercusFeatures[i] = Arrays.stream(quercusTrainMatrix[features[i]]).average().getAsDouble();
        }
        double[][] macierzRoznicAcer = new double[features.length][acerTrainMatrix[0].length];
        double[][] macierzRoznicQuercus = new double[features.length][quercusTrainMatrix[0].length];
        for (int i = 0; i < features.length; i++) {
            for (int j = 0; j < macierzRoznicAcer[0].length; j++) {
                macierzRoznicAcer[i][j] = acerTrainMatrix[features[i]][j] - meanOfAcerFeatures[i];
            }
            for (int j = 0; j < macierzRoznicQuercus[0].length; j++) {
                macierzRoznicQuercus[i][j] = quercusTrainMatrix[features[i]][j] - meanOfQuercusFeatures[i];
            }
        }

        Matrix matrixRoznicAcer = new Matrix(macierzRoznicAcer);
        Matrix matrixRoznicQuercus = new Matrix(macierzRoznicQuercus);

        Matrix matrixTransponowanaAcer = matrixRoznicAcer.transpose();
        Matrix matrixTransponowanaQuercus = matrixRoznicQuercus.transpose();

        Matrix macierzKowariancjiAcer = matrixRoznicAcer.times(matrixTransponowanaAcer).times(1 / macierzRoznicAcer[0].length);
        Matrix macierzKowariancjiQuercus = matrixRoznicQuercus.times(matrixTransponowanaQuercus).times(1 / macierzRoznicQuercus[0].length);

        Matrix macierzKowariancjiOdwroconaAcer = macierzKowariancjiAcer.inverse(); //Tu wywala, trzeba jakos to odwrocic
        Matrix macierzKowariancjiOdwroconaQuercus = macierzKowariancjiQuercus.inverse();
        int acerCount = 0;
        int quercusCount = 0;
        for (int z = 0; z < acerMatrixParted[0].length; z++) {
            double[] acerTempAcer = new double[features.length];
            double[] acerTempQuercus = new double[features.length];

            for (int i = 0; i < features.length; i++) {
                acerTempAcer[i] = acerMatrixParted[features[i]][z] - meanOfAcerFeatures[i];
                acerTempQuercus[i] = acerMatrixParted[features[i]][z] - meanOfQuercusFeatures[i];
            }

            Matrix matrixAcerTempAcer = new Matrix(acerTempAcer, 1);
            Matrix matrixAcerTempQuercus = new Matrix(acerTempQuercus, 1);

            double acerResultAcer = matrixAcerTempAcer.times(macierzKowariancjiOdwroconaAcer).times(matrixAcerTempAcer.transpose()).det();
            double acerResultQuercus = matrixAcerTempQuercus.times(macierzKowariancjiOdwroconaQuercus).times(matrixAcerTempQuercus.transpose()).det();
            if (acerResultAcer < acerResultQuercus)
                acerCount++;
        }

        for (int z = 0; z < quercusMatrixParted[0].length; z++) {
            double[] quercusTempAcer = new double[features.length];
            double[] quercusTempQuercus = new double[features.length];

            for (int i = 0; i < features.length; i++) {
                quercusTempAcer[i] = quercusMatrixParted[features[i]][z] - meanOfAcerFeatures[i];
                quercusTempQuercus[i] = quercusMatrixParted[features[i]][z] - meanOfQuercusFeatures[i];
            }

            Matrix matrixQuercusTempAcer = new Matrix(quercusTempAcer, 1);
            Matrix matrixQuercusTempQuercus = new Matrix(quercusTempQuercus, 1);

            double quercusResultAcer = matrixQuercusTempAcer.times(macierzKowariancjiOdwroconaAcer).times(matrixQuercusTempAcer.transpose()).det();
            double quercusResultQuercus = matrixQuercusTempQuercus.times(macierzKowariancjiOdwroconaQuercus).times(matrixQuercusTempQuercus.transpose()).det();
            if (quercusResultQuercus < quercusResultAcer)
                quercusCount++;
        }
        acerResult = (double) acerCount / acerMatrixParted[0].length;
        quercusResult = (double) quercusCount / quercusMatrixParted[0].length;

    }
}
