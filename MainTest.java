import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Java

public class MainTest {

    GenericGraph grafo;

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

    @Test
    void testAddVertexAndName() {
        grafo.addVertex(3);
        grafo.asignarNombre(3, "D");
        assertEquals("D", grafo.obtenerNombre(3));
    }

    @Test
    void testAddEdgeAndAdjacencyMatrix() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        assertEquals(5, matriz[0][1]);
        assertEquals(2, matriz[1][2]);
        assertEquals(10, matriz[0][2]);
        assertEquals(GenericGraph.infinito, matriz[2][0]);
    }

    @Test
    void testRemoveEdge() {
        grafo.removeEdge(0, 1);
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        assertEquals(GenericGraph.infinito, matriz[0][1]);
    }

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

    @Test
    void testFloydShortestPaths() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        assertEquals(7, resultado.distancias[0][2]); // 0->1->2 = 5+2=7, which is less than direct 10
        assertEquals(0, resultado.distancias[0][0]);
        assertEquals(GenericGraph.infinito, resultado.distancias[2][0]);
    }

    @Test
    void testFloydNextMatrix() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        assertEquals(1, resultado.siguiente[0][2]); // Next from 0 to 2 is 1 (0->1->2)
        assertEquals(-1, resultado.siguiente[2][0]);
    }

    @Test
    void testCentroDelGrafo() {
        int[][] matriz = grafo.obtenerMatrizDeAdyacencia();
        Floyd.ResultadoFloyd resultado = Floyd.calcularConRutas(matriz, GenericGraph.infinito);
        int centro = CentroDelGrafo.calcular(resultado.distancias, GenericGraph.infinito);
        // Node 1 is the center (max distance to others is 2)
        assertEquals(1, centro);
    }

    @Test
    void testPrintGraphNoException() {
        assertDoesNotThrow(() -> grafo.printGraph());
    }
}