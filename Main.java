import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GenericGraph grafo = new GenericGraph(true, true); // dirigido y ponderado

        // Inicial: A:0 B:1 C:2 D:3 E:4
        grafo.addEdge(0, 1, 3); // A -> B
        grafo.addEdge(0, 3, 7); // A -> D
        grafo.addEdge(1, 2, 1); // B -> C
        grafo.addEdge(1, 4, 8); // B -> E
        grafo.addEdge(2, 3, 2); // C -> D
        grafo.addEdge(3, 4, 3); // D -> E
        grafo.addEdge(4, 0, 4); // E -> A

        boolean continuar = true;

        while (continuar) {
            System.out.println("1. Mostrar matriz de caminos más cortos (Floyd-Warshall)");
            System.out.println("2. Calcular centro del grafo");
            System.out.println("3. Agregar nodo");
            System.out.println("4. Eliminar nodo");
            System.out.println("5. Agregar arista");
            System.out.println("6. Eliminar arista");
            System.out.println("7. Mostrar grafo");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1: {
                    int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
                    int[][] distancias = Floyd.calcular(matriz, GenericGraph.infinito);
                    System.out.println("\nMatriz de distancias más cortas:");
                    Floyd.imprimirMatriz(distancias, GenericGraph.infinito);
                    break;
                }
                case 2: {
                    int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
                    int[][] distancias = Floyd.calcular(matriz, GenericGraph.infinito);
                    int centro = CentroDelGrafo.calcular(distancias, GenericGraph.infinito);
                    System.out.println("Centro del grafo: " + centro);
                    break;
                }
                case 3: {
                    System.out.print("Ingrese el número del nuevo nodo: ");
                    int nodo = scanner.nextInt();
                    grafo.addVertex(nodo);
                    System.out.println("Nodo " + nodo + " agregado.");
                    break;
                }
                case 4: {
                    System.out.print("Ingrese el número del nodo a eliminar: ");
                    int nodo = scanner.nextInt();
                    grafo.removeVertex(nodo);
                    System.out.println("Nodo " + nodo + " eliminado.");
                    break;
                }
                case 5: {
                    System.out.print("Nodo origen: ");
                    int from = scanner.nextInt();
                    System.out.print("Nodo destino: ");
                    int to = scanner.nextInt();
                    System.out.print("Peso de la arista: ");
                    int peso = scanner.nextInt();
                    grafo.addEdge(from, to, peso);
                    System.out.println("Arista " + from + " -> " + to + " agregada.");
                    break;
                }
                case 6: {
                    System.out.print("Nodo origen: ");
                    int from = scanner.nextInt();
                    System.out.print("Nodo destino: ");
                    int to = scanner.nextInt();
                    grafo.removeEdge(from, to);
                    System.out.println("Arista " + from + " -> " + to + " eliminada.");
                    break;
                }
                case 7: {
                    System.out.println("Grafo (lista de adyacencia):");
                    grafo.printGraph();
                    break;
                }
                case 8: {
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    break;
                }
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }
}