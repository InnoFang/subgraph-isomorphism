import networkx as nx
from networkx.algorithms import isomorphism as iso
import matplotlib.pyplot as plt

TARGET_PATH = 'F:/IDEA/subgraph-isomorphism/src/main/resources/data/target_graph.txt'
QUERY_PATH = 'F:/IDEA/subgraph-isomorphism/src/main/resources/data/query_graph.txt'

def read_graph_from(file_name):
    graphs = []
    with open(file_name) as file:
        lines = file.readlines()
        graph = None
        for line in lines:

            line = line.strip()
            if line == "":
                continue
            
            info = line.split(" ")
            if info[0] == 't':
                if graph:
                    graphs.append(graph)
                graph = nx.Graph()
            elif info[0] == 'v' and len(info) == 3:
                graph.add_node(info[1], label=info[2])
            elif info[0] == 'e' and len(info) == 4:
                graph.add_edge(info[1], info[2], label=info[3])
            else:
                raise EOFError
    return graphs

target = read_graph_from(TARGET_PATH)[0]
query = read_graph_from(QUERY_PATH)[0]
 
graph_matcher = iso.GraphMatcher(target, query)
if graph_matcher.subgraph_is_isomorphic():
    print("query_graph is isomorphisc target_graph")
    print(graph_matcher.mapping)

plt.subplot(121)
nx.draw(target, pos=nx.spring_layout(target, iterations=200), node_color='b', edge_color='y',with_labels=True, font_weight='bold')
plt.subplot(122)
nx.draw(query, pos=nx.spring_layout(query, iterations=200), node_color='r', edge_color='g',with_labels=True, font_weight='bold')
plt.show()
