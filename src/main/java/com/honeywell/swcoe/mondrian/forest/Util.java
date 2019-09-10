package com.honeywell.swcoe.mondrian.forest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    public static Node convertJsonToNode(JSONObject json) {
        Map<String, Object> map = json.toMap();
        Node node = new Node(map);
        return node;

    }

    public static Tree convertJsonToTree(JSONObject json) {

        JSONArray nodesJson = json.getJSONArray("nodes");
        List<Node> nodes = new ArrayList<Node>(nodesJson.length());
        for (int i = 0; i < nodesJson.length(); i++) {
            JSONObject aNodeJson = nodesJson.getJSONObject(i);
            Node node = convertJsonToNode(aNodeJson);
            nodes.add(node);
        }

        int root = json.getInt("root");

        JSONArray valueArray = json.getJSONArray("values");
        double[] values = new double[valueArray.length()];
        for (int i = 0; i < valueArray.length(); i++) {
            values[i] = valueArray.getDouble(i);
        }

        return new Tree(nodes, values, root);

    }

    public static Forest convertJsonToForest(JSONArray json) {
        List<Tree> trees = new ArrayList<Tree>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject aTreeJson = json.getJSONObject(i);
            trees.add(convertJsonToTree(aTreeJson));
        }
        return new Forest(trees);
    }
}
