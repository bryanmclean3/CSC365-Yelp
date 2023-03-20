package Assignment2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class HT implements Serializable {
    static final class Node {
        Object key;
        Node next;
        Object value;
        Node(Object k, Object v, Node n) { key = k; value = v; next = n; }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;

    boolean contains(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return true;
        }
        return false;
    }

    //get corresponding value with key
    Object getValue(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return e.value;
        }
        return null;
    }

    //get corresponding key with value
    Object getKey(Object value) {
        for( int i = 0; i< table.length; i++) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return e.key;
                }
            }
        }
       return null;
    }

    void add(Object key, Object value) throws NullPointerException{
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key)) {
                // added computations for increasing the count for
                // a key instead of ignoring it to avoid repetition
                return;
            }
        }
        table[i] = new Node(key, value, table[i]);
        ++size;
        if ((float)size/table.length >= 0.75f)
            resize();
    }
    void resize() {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            for (Node e = oldTable[i]; e != null; e = e.next) {
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                newTable[j] = new Node(e.key, e.value, newTable[j]);
            }
        }
        table = newTable;
    }
    void remove(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        Node e = table[i], p = null;
        while (e != null) {
            if (key.equals(e.key)) {
                if (p == null)
                    table[i] = e.next;
                else
                    p.next = e.next;
                break;
            }
            p = e;
            e = e.next;
        }
    }
    void printAll() {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                // changed the output to show both the key and its frequency
                System.out.println(e.key + "  :  " + e.value);
    }
    private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                s.writeObject(e.key);
            }
        }
    }
    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
        int n = s.readInt();
        for (int i = 0; i < n; ++i)
            add(s.readObject(), null);
    }

    //return business with highest tf-idf value
    Object getHighVal() {
        double k = 0.0;
        Object c = null;
        for( int i = 0; i< table.length; i++) {
            for (Node e = table[i]; e != null; e = e.next) {
                if (k <= (Double) e.value) {
                    k = (Double) e.value;
                    c = e.key;
                }
            }
        }
        return c;
    }
}