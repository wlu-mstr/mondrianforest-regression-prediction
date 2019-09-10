package com.honeywell.swcoe.mondrian.forest;

import org.json.JSONArray;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ForestPredictTest {
    @Test
    public void predictForest() throws IOException {
        FileReader reader = new FileReader("/home/wlu/IdeaProjects/mondrian/src/test/java/com/honeywell/swcoe/mondrian/forest/trees.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = Util.convertJsonToForest(json);

        Result res = forest.predict(new double[]{19681.841666666667,11003.991666666667,10850.358333333334,53632.225});
        System.out.println(res);

        Result res0 = forest.getTrees().get(0).predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        System.out.println(res0);

    }

    @Test
    public void predictForest2() throws IOException {
        FileReader reader = new FileReader("/home/wlu/work/git/gas-ml/gas-model/wrapper2/ben_r2_0828.mdr.json");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = Util.convertJsonToForest(json);

        long time1 = System.currentTimeMillis();
        Result res = null;
        for (int i = 0; i < 10; i++) {
            res = forest.predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        }
        long time2 = System.currentTimeMillis();

        System.out.println(time2-time1);

        System.out.println(res);
        long time3 = System.currentTimeMillis();

        Result res0 = forest.getTrees().get(0).predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        long time4 = System.currentTimeMillis();
        System.out.println(time4-time3);

        System.out.println(res0);

    }

}
