package Assignment3;


import java.util .*;

public class Dijkstra {
    double[] dist;
    List<Integer> visited;
    PriorityQueue<Node> pqueue;
    int V; // Number of vertices
    List<Node> adj_list;

    //class constructor
    public Dijkstra(int V) {
        this.V = V;
        dist = new double[V];
        visited = new ArrayList<Integer>();
        pqueue = new PriorityQueue<Node>(V, new Node());
    }

    // Dijkstra's Algorithm implementation
    public void algo_dijkstra(List<Node> adj_list, int src_vertex) {
        this.adj_list = adj_list;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // first add source vertex to PriorityQueue
        pqueue.add(new Node(src_vertex, null, 0));

        // Distance to the source from itself is 0
        dist[src_vertex] = 0;
        while (visited.size() != V) {

            // u is removed from PriorityQueue and has min distance
            int u = pqueue.remove().node;

            // add node to finalized list (visited)
            visited.add(u);
            graph_adjacentNodes(u);
        }
    }

    // this methods processes all neighbours of the just visited node
    private void graph_adjacentNodes(int u) {
        double edgeDistance = -1;
        double newDistance = -1;

        // process all neighbouring nodes of u
        for (int i = 0; i < adj_list.size(); i++) {
            Node v = adj_list.get(i);

            //  proceed only if current node is not in 'visited'
            if (!visited.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // compare distances
                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;

                // Add the current vertex to the PriorityQueue
                pqueue.add(new Node(v.node, null, dist[v.node]));
            }
        }
    }

    public static void main(String arg[]) {
        int V = 3;
        int source = 0;
        // adjacency list representation of graph
        List<Node> adj_list = new ArrayList<Node>();
        // Initialize adjacency list for every node in the graph


        // Input graph edges
        adj_list.add(new Node(1, null, 5.6));
        adj_list.add(new Node(1, null,.3));
        adj_list.add(new Node(1,null, 1.2));
        adj_list.add(new Node(1,null, .6373));
        adj_list.add(new Node(2,null, .643));
        adj_list.add(new Node(2,null, 2.0));
        adj_list.add(new Node(2,null, 3.9));




        // call Dijkstra's algo method
        Dijkstra dpq = new Dijkstra(V);
        dpq.algo_dijkstra(adj_list, source);

        // Print the shortest path from source node to all the nodes
        System.out.println("The shorted path from source node to other nodes:");
        System.out.println("Source\t\t" + "Node#\t\t" + "Distance");
        for (int i = 0; i < dpq.dist.length; i++)
            System.out.println(source + " \t\t " + i + " \t\t " + dpq.dist[i]);
    }
}

