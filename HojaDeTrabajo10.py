import networkx as nx

# Crear grafo dirigido
G = nx.DiGraph()

# Nombres de las ciudades
nombres = {
    'A': 'Ciudad de Guatemala',
    'B': 'Zacapa',
    'C': 'Chiquimula',
    'D': 'Quetzaltenango',
    'E': 'Cobán'
}

# Agregar nodos
for key in nombres:
    G.add_node(key, nombre=nombres[key])

# Agregar aristas con pesos (∞ no se agrega)
G.add_weighted_edges_from([
    ('A', 'B', 3),
    ('A', 'D', 7),
    ('B', 'C', 1),
    ('B', 'E', 8),
    ('C', 'D', 2),
    ('D', 'E', 3),
    ('E', 'A', 4)
])

# Obtener matriz de distancias con Floyd-Warshall
floyd = dict(nx.floyd_warshall(G, weight='weight'))

# Mostrar matriz de distancias
print("Matriz de distancias (Floyd-Warshall):")
ciudades = list(nombres.keys())
for i in ciudades:
    for j in ciudades:
        dist = floyd[i][j]
        print(f"{floyd[i][j]:>5}" if dist != float('inf') else "  INF", end=" ")
    print(f" <- {nombres[i]}")

# Calcular el centro del grafo
def calcular_centro(matriz):
    excentricidades = {}
    for origen in matriz:
        max_dist = max(d for destino, d in matriz[origen].items() if d < float('inf') and origen != destino)
        excentricidades[origen] = max_dist
    return min(excentricidades, key=excentricidades.get)

centro = calcular_centro(floyd)
print(f"\nCentro del grafo: {nombres[centro]} ({centro})")

# Mostrar camino más corto entre dos estaciones
inicio = 'A'
fin = 'E'
try:
    camino = nx.reconstruct_path(inicio, fin, nx.floyd_warshall_predecessor_and_distance(G)[0])
    print(f"\nCamino más corto de {nombres[inicio]} a {nombres[fin]}: ", end="")
    print(" → ".join(nombres[n] for n in camino))
except:
    print(f"No hay camino de {inicio} a {fin}.")