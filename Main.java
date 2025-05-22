import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            GenericGraph grafo = new GenericGraph(true, true); // dirigido y ponderado

            // Asignar nombres iniciales
            grafo.asignarNombre(0, "Ciudad de Guatemala");
            grafo.asignarNombre(1, "Zacapa");
            grafo.asignarNombre(2, "Chiquimula");
            grafo.asignarNombre(3, "Quetzaltenango");
            grafo.asignarNombre(4, "Cobán");

            // Aristas iniciales
            grafo.addEdge(0, 1, 3);
            grafo.addEdge(0, 3, 7);
            grafo.addEdge(1, 2, 1);
            grafo.addEdge(1, 4, 8);
            grafo.addEdge(2, 3, 2);
            grafo.addEdge(3, 4, 3);
            grafo.addEdge(4, 0, 4);

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
                case 1 -> {
                    int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
                    Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
                    System.out.println("\nMatriz de distancias más cortas:");
                    Floyd.imprimirMatrizConNombres(resultado.distancias, GenericGraph.infinito, grafo);
                    Floyd.imprimirMatrizSiguiente(resultado.siguiente, grafo);
                }
                case 2 -> {
                    int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
                    Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
                    int centro = CentroDelGrafo.calcular(resultado.distancias, GenericGraph.infinito);
                    if (centro == -1) {
                        System.out.println("No se encontró un centro del grafo (nodo aislado o sin conexiones).\n");
                    } else {
                        System.out.println("Centro del grafo: " + grafo.obtenerNombre(centro) + " (índice " + centro + ")");
                    }
                }
                case 3 -> {
                    System.out.print("Ingrese el número del nuevo nodo: ");
                    int nodo = scanner.nextInt();
                    grafo.addVertex(nodo);
                    System.out.print("Ingrese el nombre del nodo: ");
                    scanner.nextLine(); // limpiar buffer
                    String nombre = scanner.nextLine();
                    grafo.asignarNombre(nodo, nombre);
                    System.out.println("Nodo " + nodo + " (" + nombre + ") agregado.");
                }
                case 4 -> {
                    System.out.print("Ingrese el número del nodo a eliminar: ");
                    int nodo = scanner.nextInt();
                    grafo.removeVertex(nodo);
                    System.out.println("Nodo " + nodo + " eliminado.");
                }
                case 5 -> {
                    System.out.print("Nodo origen: ");
                    int from = scanner.nextInt();
                    System.out.print("Nodo destino: ");
                    int to = scanner.nextInt();
                    System.out.print("Peso de la arista: ");
                    int peso = scanner.nextInt();
                    grafo.addEdge(from, to, peso);
                    System.out.println("Arista " + from + " -> " + to + " agregada.");
                }
                case 6 -> {
                    System.out.print("Nodo origen: ");
                    int from = scanner.nextInt();
                    System.out.print("Nodo destino: ");
                    int to = scanner.nextInt();
                    grafo.removeEdge(from, to);
                    System.out.println("Arista " + from + " -> " + to + " eliminada.");
                }
                case 7 -> {
                    System.out.println("Grafo (lista de adyacencia):");
                    grafo.printGraph();
                }
                case 8 -> {
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        }
    }
}