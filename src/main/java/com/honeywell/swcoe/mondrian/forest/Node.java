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
    //    private double[] lower_bounds, upper_bounds;
    private List<Double> lowerbounds, upperbounds;

    public Node(Map<String, Object> map) {
        left_child = (Integer) (map.get("lc"));
        right_child = (Integer) (map.get("rc"));
        feature = (Integer) (map.get("f"));
        threshold = (Double) (map.get("th"));
        tau = handleInf(map.get("ta"));
        variance = (Double) (map.get("vr"));
        lowerbounds = (List<Double>) map.get("lb");
        upperbounds = (List<Double>) map.get("ub");
    }

    public Node(int aLeftChild, int aRightChild, int aFeature, double aThreshold, double aTau, double aVariance, List<Double> aLbounds, List<Double> aUbounds) {
        left_child = aLeftChild;
        right_child = aRightChild;
        feature = aFeature;
        threshold = aThreshold;
        tau = aTau;
        variance = aVariance;
        lowerbounds = aLbounds;
        upperbounds = aUbounds;

    }

    private double handleInf(Object val) {
        if (val instanceof String) {
            return 999999999D;
        } else {
            return (Double) (val);
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
        return upperbounds.get(idx);
    }

    public List<Double> getUpperBound() {
        return upperbounds;
    }

    public double getLowerBound(int idx) {
        return lowerbounds.get(idx);
    }

    public List<Double> getLowerBound() {
        return lowerbounds;
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
                ", lower_bounds=" + lowerbounds +
                ", upper_bounds=" + upperbounds +
                '}' + "\n\t\t";
    }
}
