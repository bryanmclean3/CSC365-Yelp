package Assignment3;

import java.util.Comparator;

// Node class
public class Node implements Comparator<Node> {
    public int node;
    public String name;
    public double cost;

    public Node() {
    } //empty constructor

    public Node(int node, String name, double cost) {
        this.node = node;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }
}
