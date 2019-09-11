package com.honeywell.swcoe.mondrian.forest;

import com.honeywell.swcoe.mondrian.forest.protobuf.Mondrian;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {
    /*
     * BELOW ARE FOR LOADING FROM JSON FILE TO POJO
     *
     *
     */

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
        List<Double> values = new ArrayList<Double>(valueArray.length());
        for (int i = 0; i < valueArray.length(); i++) {
            values.add(valueArray.getDouble(i));
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


    /*
     * BELOW ARE FOR CONVERSION FROM POJO TO PROTOBUF
     *
     *
     */
    public static Mondrian.Node nodeToPB(Node node) {
        Mondrian.Node.Builder nb = Mondrian.Node.newBuilder();
        nb.setLeftChild(node.getLeft_child());
        nb.setRightChild(node.getRight_child());
        nb.setTau(node.getTau());
        nb.setThreshold(node.getThreshold());
        nb.setFeature(node.getFeature());
        nb.addAllLowerBounds(node.getLowerBound());
        nb.addAllUpperBounds(node.getUpperBound());
        nb.setVariance(node.getVariance());
        return nb.build();
    }

    public static Mondrian.Tree treeToPB(Tree tree) {
        Mondrian.Tree.Builder tb = Mondrian.Tree.newBuilder();
        List<Mondrian.Node> nodes = new ArrayList<Mondrian.Node>(tree.getNodes().size());
        for (Node nodei : tree.getNodes()) {
            nodes.add(nodeToPB(nodei));
        }

        tb.addAllNodes(nodes);
        tb.addAllValues(tree.getValues());
        tb.setRoot(tree.getRoot());

        return tb.build();
    }

    public static Mondrian.Forest forestToPB(Forest forest) {
        Mondrian.Forest.Builder fb = Mondrian.Forest.newBuilder();
        List<Tree> trees = forest.getTrees();
        List<Mondrian.Tree> mts = new ArrayList<Mondrian.Tree>(trees.size());
        for (Tree t : trees) {
            mts.add(treeToPB(t));
        }
        fb.addAllTrees(mts);
        return fb.build();
    }

    /*
     * BELOW ARE FOR CONVERSION FROM JSON FILE TO PROTOBUF BYTES
     *
     *
     */
    public static byte[] jsonFiletoPbBytes(String jsonFile) throws IOException {
        // JOSN TO POJO
        FileReader reader = new FileReader(jsonFile);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = convertJsonToForest(json);

        // POJO TO PB
        Mondrian.Forest mforest = forestToPB(forest);
        byte[] mforestBytes = mforest.toByteArray();
        return mforestBytes;

    }

    public static  Node fromPbNode(Mondrian.Node pbNode) {
        return new Node(pbNode.getLeftChild(), pbNode.getRightChild(), pbNode.getFeature(),
                pbNode.getThreshold(), pbNode.getTau(), pbNode.getVariance(), pbNode.getLowerBoundsList(), pbNode.getUpperBoundsList());
    }

    public static Tree fromPbTree(Mondrian.Tree pbTree) {
        List<Mondrian.Node> pbNodes = pbTree.getNodesList();
        List<Node> nodes = new ArrayList<Node>(pbNodes.size());
        for (Mondrian.Node pnode : pbNodes) {
            nodes.add(fromPbNode(pnode));
        }

        List<Double> pbValues = pbTree.getValuesList();
        int pbRoot = pbTree.getRoot();

        return new Tree(nodes, pbValues, pbRoot);
    }

    public static Forest fromPbForest(Mondrian.Forest pbForest) {
        List<Mondrian.Tree> pbTrees = pbForest.getTreesList();

        List<Tree> trees = new ArrayList<Tree>(pbTrees.size());
        for (Mondrian.Tree pbTree : pbTrees) {
            trees.add(fromPbTree(pbTree));
        }

        return new Forest(trees);
    }

    public static Forest fromPbFile(String pbFile) throws IOException {
        FileInputStream fileInputStream = null;
        File file = new File(pbFile);
        byte[] bFile = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bFile);
        fileInputStream.close();
        Mondrian.Forest pbFoest = Mondrian.Forest.parseFrom(bFile);
        return fromPbForest(pbFoest);
    }

    public static void main(String[] args) throws IOException {
        /////////// LOAD JSON TO POJO
        long t1 = System.currentTimeMillis();

        String jsonFile = "/home/wlu/work/git/gas-ml/gas-model/wrapper2/ben_r2_0828_2.mdr.json";
        FileReader reader = new FileReader(jsonFile);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONArray json = new JSONArray(line);
        Forest forest = convertJsonToForest(json);

        long t2 = System.currentTimeMillis();
        System.out.println("json load" + (t2 - t1));

//        byte[] bytes = jsonFiletoPbBytes(jsonFile);
//        FileOutputStream fos = new FileOutputStream(jsonFile + "_.pb");
//        fos.write(bytes);

        ///////// LOAD PB BYTE TO POJO
        t1 = System.currentTimeMillis();

//        FileInputStream fileInputStream = null;
//        File file = new File(jsonFile + "_.pb");
//        byte[] bFile = new byte[(int) file.length()];
//        fileInputStream = new FileInputStream(file);
//        fileInputStream.read(bFile);
//        fileInputStream.close();
//        Mondrian.Forest pbFoest = Mondrian.Forest.parseFrom(bFile);
//        Forest forest2 = fromPbForest(pbFoest);
        Forest forest2 = fromPbFile(jsonFile + "_.pb");
        t2 = System.currentTimeMillis();
        System.out.println("pb load" + (t2 - t1));

        t1 = System.currentTimeMillis();
        Result res1 = null;
        for (int i = 0; i < 10; i++) {
            res1 = forest.predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        }
        t2 = System.currentTimeMillis();
        System.out.println(res1 + " " + (t2 - t1));

        t1 = System.currentTimeMillis();
        Result res2 = null;
        for (int i = 0; i < 10; i++) {
            res2 = forest2.predict(new double[]{19681.841666666667, 11003.991666666667, 10850.358333333334, 53632.225});
        }
        t2 = System.currentTimeMillis();
        System.out.println(res2 + "  " + (t2 - t1));

    }

}
