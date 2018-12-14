package com.student.jk.controller.Utils;

public class Val {
    int[] features;
    double fisher;

    public Val(int[] features, double fisher) {
        this.features = features;
        this.fisher = fisher;
    }

    public int[] getFeatures() {
        return features;
    }


    public double getFisher() {
        return fisher;
    }
}

