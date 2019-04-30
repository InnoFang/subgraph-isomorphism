import argparse
import numpy as np
import time


def args_parser():
    parser = argparse.ArgumentParser(prog="Unweighted Graph Generator")
    # --vertex --edge --number --output
    parser.add_argument('-v', '--vertex', type=int, dest='vertex', help="the number of vertex (Required)", required=True)
    parser.add_argument('-e', '--edge', type=int, dest='edge', help='the number of edge   (Required)', required=True)
    # parser.add_argument('-n', '--number', type=int, dest='number', help='the number of graph you want to generate',
    #                     default=1)
    parser.add_argument('-o', '--output', dest='output', help='the output file name')
    args = parser.parse_args()

    # Ensure: vertex < edge < vertex ** 2
    if args.edge > args.vertex ** 2:
        print('[ERROR] the number of edge is more than the square of the number of vertex')
        exit(1)
    if args.edge < args.vertex:
        print('[ERROR] the number of edge is too small,'
              ' it cannot be less than the number of vertex because we must ensure the digraph is connected')
        exit(1)

    return args.vertex, args.edge, args.output


if __name__ == '__main__':
    vertex, edge, output = args_parser()
    output = 'V{}E{}.txt'.format(vertex, edge) if not output else output
    f = open(output, 'w')

    print('# Directed graph (each unordered pair of nodes is saved once): {}'.format(output), file=f)
    print('# Generated by unweighted-digraph-generator.py on {}'.format(
        time.strftime("%Y-%m-%d %H:%M:%S %A", time.localtime())), file=f)
    print('# Vertexes: {} Edges: {}'.format(vertex, edge), file=f)
    print('# FromVertexId	ToVertexId', file=f)

    vertex_array = np.arange(vertex)
    np.random.shuffle(vertex_array)
    edge_set = set()
    for _ in range(edge):
        edge = vertex_array[np.random.randint(vertex, size=2)]
        stamp = str(edge)
        while stamp in edge_set:
            edge = vertex_array[np.random.randint(vertex, size=2)]
            stamp = str(edge)
        edge_set.add(stamp)
        print('{}   {}'.format(edge[0], edge[1]), file=f)

    print('the generate graph have been save in: {}.'.format(output))
    f.close()