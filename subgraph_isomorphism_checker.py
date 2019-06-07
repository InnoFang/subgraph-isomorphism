#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import os
import networkx as nx
from networkx.algorithms import isomorphism as iso
import matplotlib.pyplot as plt

DEFAULT_SOURCE_PATH = './datasets/test/isomorphism/source_graph.txt'
DEFAULT_TARGET_PATH = './datasets/test/isomorphism/target_graph.txt'

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
                
                graph = nx.DiGraph() # create a directed graph

            elif info[0] == 'v' and len(info) == 3:
                graph.add_node(info[1], label=int(info[2]))
            elif info[0] == 'e' and len(info) == 4:
                graph.add_edge(info[1], info[2], label=int(info[3]))
            else:
                raise EOFError
    return graphs

def draw_graph_for(name, graph, show_edge_value=False):
    assert name == 'target' or name == 'source'

    params = {
        'source': {
            'title': 'Source',
            'node_color': 'r',
            'edge_color': 'g'
        },
        'target' : {
            'title': 'Target',
            'node_color': 'b',
            'edge_color': 'y'
        },
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

        usage: python {} <target_graph_file_path> <target_graph_number> <source_graph_file_path> <source_graph_number>

        usage: python {} -examples
        """.format(sys.argv[0], sys.argv[0])

    if len(sys.argv) == 1:
        return None, 0, None, 0

    if len(sys.argv) == 2:
        if sys.argv[1] == '-examples':
            print("""
            [0] python isomorphism_checker.py ./datasets/test/isomorphism/target_graph.txt 0 ./datasets/test/isomorphism/source_graph.txt 0
            [1] python isomorphism_checker.py ./datasets/graphDB/mygraphdb.test 3720 ./datasets/graphDB/Q4.txt 40
            """)
        else:
            print("""
            usage: python {} -examples
            """.format(sys.argv[0]))
        os._exit(0)

    assert os.path.exists(sys.argv[1]) and os.path.exists(sys.argv[3]), \
        """
        <target_graph_file_path>: {}
        <source_graph_file_path>:  {}
        Please ensure that the above files exist.
        """.format(sys.argv[1], sys.argv[3])
    
    assert sys.argv[2].isdigit() and sys.argv[4].isdigit(), \
        """
        <target_graph_number>: {}
        <source_graph_number>:  {}
        The above parameters must be integers.
        """.format(sys.argv[2], sys.argv[4])

    assert int(sys.argv[2]) and int(sys.argv[4]) , \
        """
        <target_graph_number>: {}
        <source_graph_number>:  {}
        target graph number must be less than or equal to  10000 
        source  graph number must be less than or equal to  1000
        """.format(sys.argv[2], sys.argv[4])

    return sys.argv[1], int(sys.argv[2]), sys.argv[3], int(sys.argv[4])

if __name__ == '__main__':
    
    target_file_path, target_number, source_file_path, source_number = assertion()

    target_file_path = DEFAULT_TARGET_PATH if target_file_path == None else target_file_path    
    source_file_path = DEFAULT_SOURCE_PATH if source_file_path == None else source_file_path

    target = read_graph_from(target_file_path)[target_number]
    source = read_graph_from(source_file_path)[source_number] 

    show_edge_value = input("Want to show the value of edge or not? (Y/N)")

    while show_edge_value.upper() not in ['Y', 'N']:
        show_edge_value = input("Please determine whether to show the value of edge? (Y/N)")

    show_edge_value = True if show_edge_value == 'Y' else False

    graph_matcher = iso.DiGraphMatcher(target, source,
        node_match=iso.categorical_node_match('label', -1), 
        edge_match=iso.categorical_edge_match('label', -1))		
    if graph_matcher.subgraph_is_isomorphic():		
        print("<SubGraph Isomorphism> source_graph {} is subgraph isomorphisc target_graph {}.".format(source_number, target_number))
        iso_num = 0
        for mapping in graph_matcher.subgraph_isomorphisms_iter():
            iso_num += 1
            dict = {}
            for k,v in mapping.items():
                dict[v] = k
            print(sorted(dict.items()))
        print('{} pairs of sub-graph isomorphism.'.format(iso_num))
    else:
        print("<No Result> source_graph {} is not subgraph isomorphisc target_graph {}".format(source_number, target_number))

    plt.subplot(121)
    draw_graph_for('source', source, show_edge_value)
    plt.subplot(122)
    draw_graph_for('target', target, show_edge_value)
    
    plt.show()
    