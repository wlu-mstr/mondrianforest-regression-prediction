package com.honeywell.swcoe.mondrian.forest;

import java.util.List;

public class Forest {
    private List<Tree> trees = null;

    public Forest(List<Tree> aTrees) {
        trees = aTrees;
    }


    public Result predictOneTree(double[] x, Tree atree) {
        return atree.predict(x);
    }

    public Result treeResultsWrapper(Result[] results) {
        double sMean = 0.0, sStd = 0.0;
        for (Result res : results) {
            double mean = res.mean;
            double std  = res.std;
            sMean += mean;
            sStd += std * std + mean * mean;
        }
        sMean = sMean / trees.size();
        sStd  = sStd  / trees.size();

        sStd = sStd - sMean * sMean;
        sStd = sStd > 0 ? Math.sqrt(sStd) : 0.0;
        return new Result(sMean, sStd);
    }

    public Result predict(double[] x) {
        Result[] results = new Result[trees.size()];
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            Result res = this.predictOneTree(x, tree);
            results[i] = res;
        }
        return treeResultsWrapper(results);
    }

    public List<Tree> getTrees() {
        return trees;
    }

    @Override
    public String toString() {
        return "Forest{" +
                "trees=" + trees +
                '}';
    }
}
