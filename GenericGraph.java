import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GenericGraph {
    class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private boolean directed;
    private boolean weighted;
    private Map<Integer, List<Edge>> adjList;
    private Map<Integer, String> nombreNodos;
    public static final int infinito = Integer.MAX_VALUE / 2;

    GenericGraph(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.adjList = new HashMap<>();
        this.nombreNodos = new HashMap<>();
    }

    void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    void asignarNombre(int id, String nombre) {
        nombreNodos.put(id, nombre);
    }

    String obtenerNombre(int id) {
        return nombreNodos.getOrDefault(id, "Nodo " + id);
    }

    void addEdge(int from, int to, int weight) {
        if (!weighted) {
            weight = 1;
        }

        addVertex(from);
        addVertex(to);

        adjList.get(from).add(new Edge(to, weight));
        if (!directed) {
            adjList.get(to).add(new Edge(from, weight));
        }
    }

    void removeEdge(int from, int to) {
        List<Edge> edgesFrom = adjList.get(from);
        if (edgesFrom != null) {
            edgesFrom.removeIf(edge -> edge.to == to);
        }
        if (!directed) {
            List<Edge> edgesTo = adjList.get(to);
            if (edgesTo != null) {
                edgesTo.removeIf(edge -> edge.to == from);
            }
        }
    }

    void removeVertex(int vertex) {
        adjList.remove(vertex);
        nombreNodos.remove(vertex);
        for (List<Edge> edges : adjList.values()) {
            edges.removeIf(edge -> edge.to == vertex);
        }
    }

    void printGraph() {
        for (int vertex : adjList.keySet()) {
            System.out.print(obtenerNombre(vertex) + " (" + vertex + "):");
            for (Edge edge : adjList.get(vertex)) {
                System.out.print(" -> " + obtenerNombre(edge.to) + "(" + edge.weight + ")");
            }
            System.out.println();
        }
    }

    int[][] obtenerMatrizDeAdyacencia() {
        int n = adjList.size();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(matriz[i], infinito);
            matriz[i][i] = 0;
        }

        for (int de : adjList.keySet()) {
            for (Edge edge : adjList.get(de)) {
                matriz[de][edge.to] = edge.weight;
            }
        }

        return matriz;
    }
}