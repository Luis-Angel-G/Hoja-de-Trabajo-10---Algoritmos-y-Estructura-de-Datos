import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para las funcionalidades principales del grafo y algoritmos asociados.
 * Utiliza JUnit 5 para validar operaciones sobre la clase GenericGraph, Floyd y CentroDelGrafo.
 */
public class MainTest {

    /** Grafo de prueba utilizado en los tests. */
    GenericGraph grafo;

    /**
     * Inicializa el grafo antes de cada prueba, agregando nodos, nombres y aristas.
     */
    @BeforeEach
    void setUp() {
        grafo = new GenericGraph(true, true);
        grafo.asignarNombre(0, "A");
        grafo.asignarNombre(1, "B");
        grafo.asignarNombre(2, "C");
        grafo.addEdge(0, 1, 5);
        grafo.addEdge(1, 2, 2);
        grafo.addEdge(0, 2, 10);
    }

    /**
     * Prueba la adición de un vértice y la asignación de su nombre.
     */
    @Test
    void testAddVertexAndName() {
        grafo.addVertex(3);
        grafo.asignarNombre(3, "D");
        assertEquals("D", grafo.obtenerNombre(3));
    }

    /**
     * Prueba la adición de aristas y la correcta generación de la matriz de adyacencia.
     */
    @Test
    void testAddEdgeAndAdjacencyMatrix() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        assertEquals(5, matriz[0][1]);
        assertEquals(2, matriz[1][2]);
        assertEquals(10, matriz[0][2]);
        assertEquals(GenericGraph.infinito, matriz[2][0]);
    }

    /**
     * Prueba la eliminación de una arista y su reflejo en la matriz de adyacencia.
     */
    @Test
    void testRemoveEdge() {
        grafo.removeEdge(0, 1);
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        assertEquals(GenericGraph.infinito, matriz[0][1]);
    }

    /**
     * Prueba la eliminación de un vértice y la actualización de la matriz y nodos.
     */
    @Test
    void testRemoveVertex() {
        grafo.removeVertex(1);
        assertEquals("Nodo 1", grafo.obtenerNombre(1));
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        int[] nodos = grafo.obtenerNodos();
        // Solo nodos 0 y 2 deben quedar
        assertEquals(2, matriz.length);
        assertArrayEquals(new int[]{0, 2}, nodos);
        // Verifica que no hay aristas entre los nodos eliminados
        for (int i = 0; i < nodos.length; i++) {
            for (int j = 0; j < nodos.length; j++) {
                if (i != j) {
                    // No debe haber arista si no existía originalmente
                    if (!(nodos[i] == 0 && nodos[j] == 2)) {
                        assertEquals(GenericGraph.infinito, matriz[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Prueba el algoritmo de Floyd-Warshall para obtener caminos más cortos.
     */
    @Test
    void testFloydShortestPaths() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        assertEquals(7, resultado.distancias[0][2]); // 0->1->2 = 5+2=7, menor que el directo 10
        assertEquals(0, resultado.distancias[0][0]);
        assertEquals(GenericGraph.infinito, resultado.distancias[2][0]);
    }

    /**
     * Prueba la matriz de nodos siguientes generada por Floyd-Warshall.
     */
    @Test
    void testFloydNextMatrix() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        assertEquals(1, resultado.siguiente[0][2]); // Siguiente de 0 a 2 es 1 (0->1->2)
        assertEquals(-1, resultado.siguiente[2][0]);
    }

    /**
     * Prueba el cálculo del centro del grafo.
     */
    @Test
    void testCentroDelGrafo() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        int centro = CentroDelGrafo.calcular(resultado.distancias, GenericGraph.infinito);
        // El nodo 1 es el centro (máxima distancia a otros es 2)
        assertEquals(1, centro);
    }

    /**
     * Prueba que la impresión del grafo no lanza excepciones.
     */
    @Test
    void testPrintGraphNoException() {
        assertDoesNotThrow(() -> grafo.printGraph());
    }
}