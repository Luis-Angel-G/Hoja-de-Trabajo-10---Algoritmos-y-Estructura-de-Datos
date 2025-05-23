import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de un grafo genérico dirigido y/o ponderado.
 * Permite agregar y eliminar vértices y aristas, asignar nombres a los nodos,
 * obtener la matriz de adyacencia y mostrar el grafo.
 */
public class GenericGraph {

    /**
     * Crea un nuevo grafo.
     * @param directed true si el grafo es dirigido.
     * @param weighted true si el grafo es ponderado.
     */
    GenericGraph(boolean directed, boolean weighted) {
        this.directed = directed;
        this.weighted = weighted;
        this.adjList = new HashMap<>();
        this.nombreNodos = new HashMap<>();
    }

    /**
     * Agrega un vértice al grafo.
     * @param vertex identificador del vértice.
     */
    void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Asigna un nombre a un nodo.
     * @param id identificador del nodo.
     * @param nombre nombre a asignar.
     */
    void asignarNombre(int id, String nombre) {
        nombreNodos.put(id, nombre);
    }

    /**
     * Obtiene el nombre de un nodo.
     * @param id identificador del nodo.
     * @return nombre del nodo.
     */
    String obtenerNombre(int id) {
        return nombreNodos.getOrDefault(id, "Nodo " + id);
    }

    /**
     * Agrega una arista entre dos nodos.
     * @param from nodo origen.
     * @param to nodo destino.
     * @param weight peso de la arista.
     */
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

    /**
     * Elimina una arista entre dos nodos.
     * @param from nodo origen.
     * @param to nodo destino.
     */
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

    /**
     * Elimina un nodo y todas sus aristas asociadas.
     * @param vertex identificador del nodo.
     */
    void removeVertex(int vertex) {
        adjList.remove(vertex);
        nombreNodos.remove(vertex);
        for (List<Edge> edges : adjList.values()) {
            edges.removeIf(edge -> edge.to == vertex);
        }
    }

    /**
     * Imprime la lista de adyacencia del grafo.
     */
    void printGraph() {
        for (int vertex : adjList.keySet()) {
            System.out.print(obtenerNombre(vertex) + " (" + vertex + "):");
            for (Edge edge : adjList.get(vertex)) {
                System.out.print(" -> " + obtenerNombre(edge.to) + "(" + edge.weight + ")");
            }
            System.out.println();
        }
    }

    /**
     * Obtiene la matriz de adyacencia del grafo.
     * @return matriz de adyacencia.
     */
    int[][] obtenerMatrizDeAdyacencia() {
        int n = adjList.size();
        int[] nodos = adjList.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(matriz[i], infinito);
            matriz[i][i] = 0;
        }

        for (int i = 0; i < n; i++) {
            int de = nodos[i];
            for (Edge edge : adjList.get(de)) {
                int j = -1;
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
     * Obtiene los identificadores de los nodos del grafo.
     * @return arreglo de identificadores.
     */
    int[] obtenerNodos() {
        return adjList.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();
    }

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
}