package com.honeywell.swcoe.mondrian.forest;


//mean_and_std = self.tree_.predict(
//        X, return_std=return_std, is_regression=True)
//        if return_std:
//        return mean_and_std
//        return mean_and_std[0]

import java.util.Arrays;
import java.util.List;

public class Tree {
    private List<Node> nodes;
    private List<Double> values;
    private int root = 0;

    private static final int TREE_LEAF = -1;

    public Tree(List<Node> aNodes, List<Double> aValues, int aRoot) {
        this.nodes = aNodes;
        this.values = aValues;
        this.root = aRoot;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Double> getValues() {
        return values;
    }

    public int getRoot() {
        return root;
    }


    public Result predict(double[] x) {
        // Step 3
        double parentTau = 0.0;
        double pNsy = 1.0;
        int nodeId = this.root;
        double wj = 0.0, pjs = 0.0, delta = 0.0;
        double mean = 0.0, std = 0.0;
        while (true) {
            Node node = this.nodes.get(nodeId);
            // step 5: first part
            // calculate delta
            delta = node.getTau() - parentTau;
            parentTau = node.getTau();
            // step 5: second part
            // calculate eta
            double eta = 0.0;
            for (int xIdx = 0; xIdx < x.length; xIdx++) {
                double xVal = x[xIdx];
                double diffUpper = Math.max(xVal - node.getUpperBound(xIdx), 0);
                double diffLower = Math.max(node.getLowerBound(xIdx) - xVal, 0);
                eta += diffLower + diffUpper;
            }
            // Step 6: Calculate p_j
            // Step 7-11
            if (node.getLeft_child() == TREE_LEAF) {
                wj = pNsy;
            } else {
                pjs = 1 - Math.exp(-delta * eta);
                wj = pNsy * pjs;
            }

            mean += wj * this.values.get(nodeId);
            std += wj * (this.values.get(nodeId) * this.values.get(nodeId) + node.getVariance());

            if (node.getLeft_child() == TREE_LEAF) {
                break;
            }

            pNsy = pNsy * (1 - pjs);

            // Step 12-14
            if (x[node.getFeature()] <= node.getThreshold()) {
                nodeId = node.getLeft_child();
            } else {
                nodeId = node.getRight_child();
            }
        }

        std -= mean * mean;
        if (std < 0) {
            std = 0.0;
        }
        std = Math.sqrt(std);

        return new Result(mean, std);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "nodes=" + nodes +
                ", values=" + values +
                ", root=" + root +
                '}' + "\n\t";
    }
}
