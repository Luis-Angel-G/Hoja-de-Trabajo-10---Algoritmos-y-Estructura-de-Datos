/**
 * Implementa el algoritmo de Floyd-Warshall para encontrar caminos más cortos entre todos los pares de nodos.
 * Incluye métodos para calcular distancias y rutas, e imprimir las matrices resultantes.
 */
public class Floyd {

    /**
     * Estructura para almacenar el resultado del algoritmo de Floyd-Warshall.
     */
    public static class ResultadoFloyd {
        /** Matriz de distancias más cortas. */
        public int[][] distancias;
        /** Matriz de nodos siguientes para reconstruir caminos. */
        public int[][] siguiente;

        /**
         * Constructor del resultado de Floyd.
         * @param distancias matriz de distancias.
         * @param siguiente matriz de nodos siguientes.
         */
        public ResultadoFloyd(int[][] distancias, int[][] siguiente) {
            this.distancias = distancias;
            this.siguiente = siguiente;
        }
    }

    /**
     * Calcula las distancias y rutas más cortas entre todos los pares de nodos.
     * @param matriz matriz de adyacencia.
     * @param infinito valor que representa infinito.
     * @return resultado con matrices de distancias y siguientes.
     */
    public static ResultadoFloyd calcularConRutas(int[][] matriz, int infinito) {
        int n = matriz.length;
        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = matriz[i][j];
                if (i != j && matriz[i][j] != infinito) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != infinito && dist[k][j] != infinito) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }

        return new ResultadoFloyd(dist, next);
    }

    /**
     * Imprime la matriz de distancias con los nombres de los nodos.
     * @param matriz matriz de distancias.
     * @param infinito valor que representa infinito.
     * @param grafo grafo de referencia para los nombres.
     */
    public static void imprimirMatrizConNombres(int[][] matriz, int infinito, GenericGraph grafo) {
        int n = matriz.length;
        int colWidth = 22;

        System.out.println("\nMatriz de distancias más cortas:");
    
        System.out.printf("%-" + colWidth + "s", "");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-" + colWidth + "s", grafo.obtenerNombre(i));
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-" + colWidth + "s", grafo.obtenerNombre(i));
            for (int j = 0; j < n; j++) {
                String valor = (matriz[i][j] == infinito) ? "infinito" : String.valueOf(matriz[i][j]);
                System.out.printf("%-" + colWidth + "s", valor);
            }
            System.out.println();
        }
    }

    /**
     * Imprime la tabla de rutas (matriz de siguientes) con los nombres de los nodos.
     * @param next matriz de siguientes.
     * @param grafo grafo de referencia para los nombres.
     */
    public static void imprimirMatrizSiguiente(int[][] next, GenericGraph grafo) {
        int n = next.length;
        int colWidth = 22;

        System.out.println("\nTabla de rutas:");

        System.out.printf("%-" + colWidth + "s", "");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-" + colWidth + "s", grafo.obtenerNombre(i));
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-" + colWidth + "s", grafo.obtenerNombre(i));
            for (int j = 0; j < n; j++) {
                String valor;
                if (i == j || next[i][j] == -1) {
                    valor = "-";
                } else {
                    valor = grafo.obtenerNombre(next[i][j]);
                }
                System.out.printf("%-" + colWidth + "s", valor);
            }
            System.out.println();
        }
    }
}