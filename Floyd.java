
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