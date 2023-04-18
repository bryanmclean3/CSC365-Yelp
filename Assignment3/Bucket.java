package Assignment3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bucket implements Serializable {

    static final class Node {
        String key;
        Object value;
        Node(String k, Object v) { key = k; value = v;}
    }

    public static final int BUCKET_SIZE = 10;
    public List<Node> map;
    public int local_depth;

    public Bucket() {
        this.map = new ArrayList<>();
        this.local_depth = 0;
    }

    public boolean isFull() {
        return map.size() >= BUCKET_SIZE;
    }

    public void put(String k, Object v) {
        for (int i = 0; i < map.size(); i++) {
            Node pair = map.get(i);
            if (pair.key.equals(k)) {
                map.remove(i);
                break;
            }
        }
        map.add(new Node(k, v));
    }

    public Object get(String k) {
        for (Node pair : map) {
            if (pair.key.equals(k)) {
                return pair.value;
            }
        }
        return null;
    }

    public int getLocalHighBit() {
        return 1 << local_depth;
    }
}
