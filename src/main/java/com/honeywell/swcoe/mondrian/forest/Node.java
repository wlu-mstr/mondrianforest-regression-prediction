package com.honeywell.swcoe.mondrian.forest;

import java.util.List;
import java.util.Map;

//{'left_child': 1, 'right_child': 10,
// 'feature': 3, 'threshold': 53580.62317652191,
// 'tau': 0.008123871870338917,
// 'variance': 2.282281642112669,
// 'lower_bounds': array([19606., 10992., 10814., 53446.], dtype=float32),
// 'upper_bounds': array([19709., 11009., 10871., 53637.], dtype=float32)
public class Node {
    private int left_child, right_child, feature = 0;
    private double threshold;
    private double tau, variance;
    private double[] lower_bounds, upper_bounds;

    public Node(Map<String, Object> map) {
        left_child = (Integer) (map.get("lc"));
        right_child = (Integer) (map.get("rc"));
        feature = (Integer) (map.get("f"));
        threshold = (Double) (map.get("th"));
        tau = handleInf(map.get("ta"));
        variance = (Double) (map.get("vr"));
        List<Double> lowerbounds = (List<Double>) map.get("lb");
        lower_bounds = new double[lowerbounds.size()];
        for (int i = 0; i < lowerbounds.size(); i++) lower_bounds[i] = lowerbounds.get(i);

        List<Double> upperbounds = (List<Double>) map.get("ub");
        upper_bounds = new double[upperbounds.size()];
        for (int i = 0; i < upperbounds.size(); i++) upper_bounds[i] = upperbounds.get(i);

    }

    private double handleInf(Object val) {
        if (val instanceof String) {
            return 999999999D;
        } else {
            return (Double)(val);
        }
    }

    public int getLeft_child() {
        return left_child;
    }

    public int getRight_child() {
        return right_child;
    }

    public int getFeature() {
        return feature;
    }

    public double getThreshold() {
        return threshold;
    }


    public double getTau() {
        return tau;
    }

    public double getVariance() {
        return variance;
    }

    public double getUpperBound(int idx) {
        return upper_bounds[idx];
    }

    public double getLowerBound(int idx) {
        return lower_bounds[idx];
    }

    @Override
    public String toString() {
        return "Node{" +
                "left_child=" + left_child +
                ", right_child=" + right_child +
                ", feature=" + feature +
                ", threshold=" + threshold +
                ", tau=" + tau +
                ", variance=" + variance +
                ", lower_bounds=" + lower_bounds +
                ", upper_bounds=" + upper_bounds +
                '}' + "\n\t\t";
    }
}
