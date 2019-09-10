package com.honeywell.swcoe.mondrian.forest;

import org.junit.Test;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class NodeTest {
    @Test
    public void loadTest() throws IOException {
        FileReader reader = new FileReader("/home/wlu/IdeaProjects/mondrian/src/test/java/com/honeywell/swcoe/mondrian/forest/one_node.txt");
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        JSONObject json = new JSONObject(line);
        Map<String, Object> map = json.toMap();
        Node node = new Node(map);
        System.out.println(node);



    }
}
