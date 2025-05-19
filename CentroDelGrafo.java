public class CentroDelGrafo {

    public static int calcular(int[][] distancias, int infinito) {
        int n = distancias.length;
        int centro = -1;
        int menorExcentricidad = infinito;

        for (int i = 0; i < n; i++) {
            int excentricidad = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && distancias[i][j] != infinito) {
                    excentricidad = Math.max(excentricidad, distancias[i][j]);
                }
            }

            if (excentricidad < menorExcentricidad) {
                menorExcentricidad = excentricidad;
                centro = i;
            }
        }

        return centro;
    }
}