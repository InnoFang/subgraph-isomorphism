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

def draw_graph_for(name, show_edge_value=False):
    assert name == 'target' or name == 'query'

    params = {
        'target' : {
            'file_path': TARGET_PATH,
            'title': 'Target',
            'node_color': 'b',
            'edge_color': 'y'
        },
        'query': {
            'file_path': QUERY_PATH,
            'title': 'Query',
            'node_color': 'r',
            'edge_color': 'g'
        }
    }

    file_path = params[name]['file_path']
    title = params[name]['title']
    node_color = params[name]['node_color']
    edge_color = params[name]['edge_color']
    plt.title('{} Graph'.format(title))
    graph = read_graph_from(file_path)[0]
    labels = nx.get_edge_attributes(graph, 'label')
    pos = nx.spring_layout(graph)
    nx.draw(graph, pos=pos, node_color=node_color, edge_color=edge_color, with_labels=True, font_weight='bold')
    if show_edge_value:
        nx.draw_networkx_edge_labels(graph, pos, edge_labels=labels)

subplot = [121, 122]
names = ['target', 'query']
 
if __name__ == '__main__':
    for sub, name in zip(subplot, names):
        plt.subplot(sub)
        draw_graph_for(name)
    
    plt.show()
    