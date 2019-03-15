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

def draw_graph_for(name, graph, show_edge_value=False):
    assert name == 'target' or name == 'query'

    params = {
        'target' : {
            'title': 'Target',
            'node_color': 'b',
            'edge_color': 'y'
        },
        'query': {
            'title': 'Query',
            'node_color': 'r',
            'edge_color': 'g'
        }
    }

    title = params[name]['title']
    node_color = params[name]['node_color']
    edge_color = params[name]['edge_color']
    plt.title('{} Graph'.format(title))
    labels = nx.get_edge_attributes(graph, 'label')
    pos = nx.spring_layout(graph)
    nx.draw(graph, pos=pos, node_color=node_color, edge_color=edge_color, with_labels=True, font_weight='bold')
    if show_edge_value:
        nx.draw_networkx_edge_labels(graph, pos, edge_labels=labels)
 
if __name__ == '__main__':
    target = read_graph_from(TARGET_PATH)[0]
    query = read_graph_from(QUERY_PATH)[0] 

    graph_matcher = iso.GraphMatcher(target, query)		
    if graph_matcher.subgraph_is_isomorphic():		
        print("query_graph is isomorphisc target_graph")		
        print(graph_matcher.mapping)

    plt.subplot(121)
    draw_graph_for('target', target)
    plt.subplot(122)
    draw_graph_for('query', query)
    
    plt.show()
    