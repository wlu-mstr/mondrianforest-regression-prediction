package com.honeywell.swcoe.mondrian.forest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TreeTest {

    @Test
    public void testOneTree() throws IOException {
        FileReader reader = new FileReader("/home/wlu/IdeaProjects/mondrian/src/test/java/com/honeywell/swcoe/mondrian/forest/trees.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        JSONObject oneTree = json.getJSONObject(0);
        Tree tree0 = Util.convertJsonToTree(oneTree);

        System.out.println(tree0);
    }

    @Test
    public void testForest() throws IOException {
        FileReader reader = new FileReader("/home/wlu/IdeaProjects/mondrian/src/test/java/com/honeywell/swcoe/mondrian/forest/trees.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = Util.convertJsonToForest(json);

        System.out.println(forest);
    }
}
