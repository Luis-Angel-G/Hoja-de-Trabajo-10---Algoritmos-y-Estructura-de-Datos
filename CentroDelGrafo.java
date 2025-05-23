/**
 * Clase utilitaria para calcular el centro de un grafo a partir de la matriz de distancias.
 * El centro es el nodo con menor excentricidad (distancia máxima mínima a los demás nodos).
 */
public class CentroDelGrafo {

    /**
     * Calcula el centro del grafo a partir de la matriz de distancias.
     * @param distancias matriz de distancias.
     * @param infinito valor que representa infinito.
     * @return índice del nodo centro, o -1 si no existe.
     */
    public static int calcular(int[][] distancias, int infinito) {
        int n = distancias.length;
        int centro = -1;
        int menorExcentricidad = infinito;

        for (int i = 0; i < n; i++) {
            boolean conectado = false;
            int excentricidad = 0;

            for (int j = 0; j < n; j++) {
                if (i != j && distancias[i][j] != infinito) {
                    excentricidad = Math.max(excentricidad, distancias[i][j]);
                    conectado = true;
                }
            }

            if (conectado && excentricidad < menorExcentricidad) {
                menorExcentricidad = excentricidad;
                centro = i;
            }
        }

        return centro;
    }
}