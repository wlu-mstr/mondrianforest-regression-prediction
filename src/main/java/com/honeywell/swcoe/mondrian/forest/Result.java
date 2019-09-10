package com.honeywell.swcoe.mondrian.forest;

public class Result {
    public double mean;
    public double std;
    public Result(double m, double s) {
        mean = m;
        std = s;
    }

    @Override
    public String toString() {
        return "Result{" +
                "mean=" + mean +
                ", std=" + std +
                '}';
    }
}
