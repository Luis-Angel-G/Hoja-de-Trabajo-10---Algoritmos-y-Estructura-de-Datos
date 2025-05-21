import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GenericGraph grafo = new GenericGraph(true, true); // dirigido y ponderado

        // A:0 B:1 C:2 D:3 E:4
        grafo.addEdge(0, 1, 3); // A -> B
        grafo.addEdge(0, 3, 7); // A -> D
        grafo.addEdge(1, 2, 1); // B -> C
        grafo.addEdge(1, 4, 8); // B -> E
        grafo.addEdge(2, 3, 2); // C -> D
        grafo.addEdge(3, 4, 3); // D -> E
        grafo.addEdge(4, 0, 4); // E -> A

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Mostrar matriz de caminos más cortos (Floyd-Warshall)");
            System.out.println("2. Calcular centro del grafo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
            int[][] distancias = Floyd.calcular(matriz, GenericGraph.infinito);

            switch (opcion) {
                case 1:
                    System.out.println("\nMatriz de distancias más cortas:");
                    Floyd.imprimirMatriz(distancias, GenericGraph.infinito);
                    break;
                case 2:
                    int centro = CentroDelGrafo.calcular(distancias, GenericGraph.infinito);
                    System.out.println("Centro del grafo: " + centro + " (0=A, 1=B, 2=C, 3=D, 4=E)");
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }
}