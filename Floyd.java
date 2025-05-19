import java.util.Arrays;

public class Floyd {

    public static int[][] calcular(int[][] matriz, int infinito) {
        int n = matriz.length;
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            dist[i] = Arrays.copyOf(matriz[i], n);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != infinito && dist[k][j] != infinito) {
                        if (dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }
                }
            }
        }

        return dist;
    }

    public static void imprimirMatriz(int[][] matriz, int infinito) {
        System.out.println("Matriz de distancias:");
        for (int[] fila : matriz) {
            for (int valor : fila) {
                if (valor == infinito) {
                    System.out.print("INF ");
                } else {
                    System.out.print(valor + " ");
                }
            }
            System.out.println();
        }
    }
}