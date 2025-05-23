import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericGraph {
    class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private final boolean directed;
    private final boolean weighted;
    private final Map<Integer, List<Edge>> adjList;
    private final Map<Integer, String> nombreNodos;
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
        // Mapea los nodos existentes a índices de matriz
        int n = adjList.size();
        int[] nodos = adjList.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(matriz[i], infinito);
            matriz[i][i] = 0;
        }

        // Llenar la matriz usando el mapeo de nodos
        for (int i = 0; i < n; i++) {
            int de = nodos[i];
            for (Edge edge : adjList.get(de)) {
                int j = -1;
                // Buscar el índice del nodo destino
                for (int k = 0; k < n; k++) {
                    if (nodos[k] == edge.to) {
                        j = k;
                        break;
                    }
                }
                if (j != -1) {
                    matriz[i][j] = edge.weight;
                }
            }
        }

        return matriz;
    }

    /**
     * Devuelve los nodos actuales en orden de índice de matriz.
     */
    int[] obtenerNodos() {
        return adjList.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();
    }
}