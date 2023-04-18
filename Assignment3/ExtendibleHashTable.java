package Assignment3;

import java.util.ArrayList;
import java.util.List;

public class ExtendibleHashTable {
    private int global_depth;
    public static List<Bucket> directory;

    public ExtendibleHashTable() {
        this.global_depth = 0;
        this.directory = new ArrayList<>();
        directory.add(new Bucket());
    }

    public Bucket getBucket(Object k) {
        int h = k.hashCode();
        return directory.get(h & ((1 << global_depth) - 1));
    }

    public void add(String k, Object v) {
        Bucket p = getBucket(k);
        boolean full = p.isFull();
        p.put(k, v);
        if (full) {
            if (p.local_depth == global_depth) {
                int directorySize = directory.size();
                for (int i = 0; i < directorySize; i++) {
                    directory.add(directory.get(i));
                }
                global_depth++;
            }

            Bucket p0 = new Bucket();
            Bucket p1 = new Bucket();
            p0.local_depth = p1.local_depth = p.local_depth + 1;
            int high_bit = p.getLocalHighBit();
            for (Bucket.Node pair : p.map) {
                String k2 = pair.key;
                Object v2 = pair.value;
                int h = k2.hashCode();
                Bucket new_p = (h & high_bit) == 0 ? p0 : p1;
                new_p.put(k2, v2);
            }

            int len = directory.size();
            for (int i = k.hashCode() & (high_bit - 1); i < len; i += high_bit) {
                directory.set(i, (i & high_bit) == 0 ? p0 : p1);
            }
        }
    }

    public Object get(String k) {
        return getBucket(k).get(k);
    }

    public void printAll() {
        for (Bucket bucket : directory) {
            for (Bucket.Node pair : bucket.map) {
                System.out.println(pair.key + ", " + pair.value);
            }
        }
    }
}
