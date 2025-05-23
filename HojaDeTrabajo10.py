import networkx as nx

# Diccionario de nombres personalizados para los nodos
nombres = {
    'A': 'Ciudad de Guatemala',
    'B': 'Zacapa',
    'C': 'Chiquimula',
    'D': 'Quetzaltenango',
    'E': 'Cobán'
}

# Crear grafo dirigido y ponderado
G = nx.DiGraph()
for nodo, nombre in nombres.items():
    G.add_node(nodo, nombre=nombre)

# Agregar aristas iniciales con pesos
G.add_weighted_edges_from([
    ('A', 'B', 3),
    ('A', 'D', 7),
    ('B', 'C', 1),
    ('B', 'E', 8),
    ('C', 'D', 2),
    ('D', 'E', 3),
    ('E', 'A', 4)
])

def floyd_matriz():
    """
    Muestra la matriz de distancias más cortas entre todos los pares de nodos usando el algoritmo de Floyd-Warshall.
    """
    ciudades = list(G.nodes)
    floyd = dict(nx.floyd_warshall(G, weight='weight'))

    print("\nMatriz de distancias (Floyd-Warshall):")
    for i in ciudades:
        for j in ciudades:
            dist = floyd[i][j]
            print(f"{dist:>5}" if dist != float('inf') else "  ∞", end=" ")
        print(f" <- {G.nodes[i]['nombre']}")

def centro_del_grafo():
    """
    Calcula y muestra el centro del grafo (nodo con menor excentricidad).
    """
    floyd = dict(nx.floyd_warshall(G, weight='weight'))
    excentricidades = {}
    for origen in floyd:
        # Calcula la excentricidad de cada nodo (la mayor distancia a otro nodo)
        distancias = [d for destino, d in floyd[origen].items() if d < float('inf') and origen != destino]
        if distancias:
            excentricidades[origen] = max(distancias)
    if excentricidades:
        centro = min(excentricidades, key=excentricidades.get)
        print(f"\nCentro del grafo: {G.nodes[centro]['nombre']} ({centro})")
    else:
        print("No se pudo determinar un centro (grafo desconectado).")

def mostrar_grafo():
    """
    Muestra todas las aristas del grafo con sus pesos y nombres de nodos.
    """
    print("\nGrafo actual:")
    for u, v, data in G.edges(data=True):
        print(f"{G.nodes[u]['nombre']} ({u}) -> {G.nodes[v]['nombre']} ({v}) con peso {data['weight']}")

def menu():
    """
    Muestra el menú interactivo para manipular el grafo.
    """
    while True:
        print("\n--- MENÚ ---")
        print("1. Mostrar matriz de caminos más cortos (Floyd)")
        print("2. Calcular centro del grafo")
        print("3. Agregar nodo")
        print("4. Eliminar nodo")
        print("5. Agregar arista")
        print("6. Eliminar arista")
        print("7. Mostrar grafo")
        print("8. Salir")
        opcion = input("Seleccione una opción: ")

        if opcion == '1':
            floyd_matriz()
        elif opcion == '2':
            centro_del_grafo()
        elif opcion == '3':
            # Agregar un nuevo nodo
            nodo = input("ID del nuevo nodo (letra): ").strip().upper()
            if nodo in G:
                print("Ese nodo ya existe.")
            else:
                nombre = input("Nombre del nodo: ")
                G.add_node(nodo, nombre=nombre)
                print(f"Nodo {nodo} agregado.")
        elif opcion == '4':
            # Eliminar un nodo existente
            nodo = input("ID del nodo a eliminar: ").strip().upper()
            if nodo in G:
                G.remove_node(nodo)
                print(f"Nodo {nodo} eliminado.")
            else:
                print("Ese nodo no existe.")
        elif opcion == '5':
            # Agregar una arista entre dos nodos
            origen = input("Nodo origen: ").strip().upper()
            destino = input("Nodo destino: ").strip().upper()
            peso = int(input("Peso de la arista: "))
            if origen in G and destino in G:
                G.add_edge(origen, destino, weight=peso)
                print("Arista agregada.")
            else:
                print("Uno o ambos nodos no existen.")
        elif opcion == '6':
            # Eliminar una arista existente
            origen = input("Nodo origen: ").strip().upper()
            destino = input("Nodo destino: ").strip().upper()
            if G.has_edge(origen, destino):
                G.remove_edge(origen, destino)
                print("Arista eliminada.")
            else:
                print("La arista no existe.")
        elif opcion == '7':
            mostrar_grafo()
        elif opcion == '8':
            print("Saliendo del programa...")
            break
        else:
            print("Opción no válida.")

if __name__ == "__main__":
    menu()