
public class Floyd {

    public static class ResultadoFloyd {
        public int[][] distancias;
        public int[][] siguiente;

        public ResultadoFloyd(int[][] distancias, int[][] siguiente) {
            this.distancias = distancias;
            this.siguiente = siguiente;
        }
    }

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

    public static void imprimirMatrizConNombres(int[][] matriz, int infinito, GenericGraph grafo) {
        int n = matriz.length;

        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-20s", grafo.obtenerNombre(i));
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-4s ", grafo.obtenerNombre(i));
            for (int j = 0; j < n; j++) {
                if (matriz[i][j] == infinito) {
                    System.out.printf("%-20s", "INF");
                } else {
                    System.out.printf("%-20d", matriz[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static void imprimirMatrizSiguiente(int[][] next, GenericGraph grafo) {
        int n = next.length;

        System.out.println("\nTabla de rutas (siguiente nodo):");
        System.out.print("     ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-20s", grafo.obtenerNombre(i));
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%-4s ", grafo.obtenerNombre(i));
            for (int j = 0; j < n; j++) {
                if (i == j || next[i][j] == -1) {
                    System.out.printf("%-20s", "-");
                } else {
                    System.out.printf("%-20s", grafo.obtenerNombre(next[i][j]));
                }
            }
            System.out.println();
        }
    }
}