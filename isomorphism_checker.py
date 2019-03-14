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
                graph.add_node(info[1], label=int(info[2]))
            elif info[0] == 'e' and len(info) == 4:
                graph.add_edge(info[1], info[2], label=int(info[3]))
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
labels = nx.get_edge_attributes(target, 'label')
position = nx.spring_layout(target)
nx.draw(target, pos=position, node_color='b', edge_color='y',with_labels=True, font_weight='bold')
# nx.draw_networkx_edge_labels(target, pos=position, edge_labels=labels)

plt.subplot(122)
labels = nx.get_edge_attributes(query, 'label')
position = nx.spring_layout(query)
nx.draw(query, pos=position, node_color='r', edge_color='g',with_labels=True, font_weight='bold', edge_labels=labels)
# nx.draw_networkx_edge_labels(query, pos=position, edge_labels=labels)

plt.show()
