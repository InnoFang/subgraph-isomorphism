import sys
import os
import networkx as nx
from networkx.algorithms import isomorphism as iso
import matplotlib.pyplot as plt

DEFAULT_TARGET_PATH = './src/main/resources/data/target_graph.txt'
DEFAULT_QUERY_PATH = './src/main/resources/data/query_graph.txt'

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

def assertion():
    assert len(sys.argv) in [1, 2, 5], \
        """
        Wrong number of parameters. Please keep the number of parameters as 0, 1 or 4, the format is as follows: 

        usage: python {} <target_graph_file_path> <target_graph_number> <query_graph_file_path> <query_graph_number>

        usage: python {} -examples
        """.format(sys.argv[0], sys.argv[0])

    if len(sys.argv) == 1:
        return None, 0, None, 0

    if len(sys.argv) == 2:
        if sys.argv[1] == '-examples':
            print("""
            [0] python isomorphism_checker.py ./src/main/resources/data/target_graph.txt 0 ./src/main/resources/data/query_graph.txt 0
            [1] python isomorphism_checker.py ./src/main/resources/graphDB/mygraphdb.data 3720 ./src/main/resources/graphDB/Q4.my 40
            """)
        else:
            print("""
            usage: python {} -examples
            """.format(sys.argv[0]))
        os._exit(0)

    assert os.path.exists(sys.argv[1]) and os.path.exists(sys.argv[3]), \
        """
        <target_graph_file_path>: {}
        <query_graph_file_path>:  {}
        Please ensure that the above files exist.
        """.format(sys.argv[1], sys.argv[3])
    
    assert sys.argv[2].isdigit() and sys.argv[4].isdigit(), \
        """
        <target_graph_number>: {}
        <query_graph_number>:  {}
        The above parameters must be integers.
        """.format(sys.argv[2], sys.argv[4])

    assert int(sys.argv[2]) and int(sys.argv[4]) , \
        """
        <target_graph_number>: {}
        <query_graph_number>:  {}
        target graph number must be less than or equal to  10000 
        query  graph number must be less than or equal to  1000
        """.format(sys.argv[2], sys.argv[4])

    return sys.argv[1], int(sys.argv[2]), sys.argv[3], int(sys.argv[4])

if __name__ == '__main__':
    
    target_file_path, target_number, query_file_path, query_number = assertion()

    target_file_path = DEFAULT_TARGET_PATH if target_file_path == None else target_file_path
    query_file_path  = DEFAULT_QUERY_PATH  if query_file_path  == None else query_file_path

    target = read_graph_from(target_file_path)[target_number]
    query  = read_graph_from(query_file_path)[query_number] 

    graph_matcher = iso.GraphMatcher(target, query, 
        node_match=iso.categorical_node_match('label', -1), 
        edge_match=iso.categorical_edge_match('label', -1))		
    if graph_matcher.subgraph_is_isomorphic():		
        print("<SubGraph Isomorphism> query_graph {} is subgraph isomorphisc target_graph {}.".format(query_number, target_number))		
        print(graph_matcher.mapping)
    else:
        print("<No Result> query_graph {} is not subgraph isomorphisc target_graph {}".format(query_number, target_number))

    plt.subplot(121)
    draw_graph_for('target', target)
    plt.subplot(122)
    draw_graph_for('query', query)
    
    plt.show()
    