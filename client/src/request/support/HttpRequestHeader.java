 package request.support;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class HttpRequestHeader {

    private final Map<String, String> mapHeader;

    public HttpRequestHeader() {
        mapHeader = new HashMap<>();
        mapHeader.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
    }

    public void add(String key, String value) {
        mapHeader.put(key.toLowerCase(), value);
    }

    public String get(String key) {
        return mapHeader.get(key);
    }

    public void remove(String key) {
        mapHeader.remove(key);
    }

    @Override
    public String toString() {
        return "_Header{" + "map=" + Arrays.toString(toArray()) + '}';
    }

    public String[][] toArray() {
        if (mapHeader.size() > 0) {
            String[][] res = new String[mapHeader.size()][2];
            int i = 0;
            for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                res[i][0] = key;
                res[i++][1] = value;
            }
            return res;
        }
        return null;
    }
}
