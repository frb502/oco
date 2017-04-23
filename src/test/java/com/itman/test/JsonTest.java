package com.itman.test;

import com.itman.oco.json.JSONObject;

/**
 * Created by furongbin on 17/4/23.
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();
        j1.put("name", "xiaom");
        j2.put("aa", 1);
        j2.put("bb", 2);
        j1.put("result", j2);
        System.out.println(j1.toString());
    }
}
