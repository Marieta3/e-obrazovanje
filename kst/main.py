
from flask import Flask
from flask import jsonify
from flask import request
import pickle
import pandas as pd
import numpy as np
import sys
sys.path.append('learning_spaces/')
from learning_spaces.kst import iita, simu, iita_exclude_transitive
from collections import defaultdict

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.

@app.route("/impl", methods=['GET', 'POST'])
def get_implications():
    if(request.method == 'GET'):
        simu_result = simu(6, 200, 0.3, 0.3, 0.5)
    elif(request.method == 'POST'):
        requestData = request.get_json(force=True)
        simu_result = simu(requestData['items'], requestData['size'],requestData['ce'], requestData['lg'], requestData['delta'])
    iita_result = iita(simu_result['dataset'], v=1)

    return jsonify(iita_result['implications'])

@app.route("/implications", methods=['GET', 'POST'])
def get_implications1():
    if (request.method == 'GET'):
        simu_result = simu(6, 10, 0.05, 0.05, 0.5)
    elif (request.method == 'POST'):
        requestData = request.get_json(force=True)
        simu_result = simu(requestData['items'], 100, 0.05, 0.05, 0.5)

    iita_result = iita_exclude_transitive(simu_result['dataset'], v=1)
    implications = iita_result['implications']

    paths = get_paths(implications)
    returns = {'implications': implications, 'paths': paths}
    print(returns)
    return jsonify(returns)

def get_paths(implications):
    nodes = list(set([node for pair in implications for node in pair]))
    start_nodes = nodes.copy()

    for impl in implications:
        if impl[1] in start_nodes:
            start_nodes.remove(impl[1])

    graph = Graph(len(nodes))
    for impl in implications:
        graph.addEdge(impl[0], impl[1])

    paths = []
    paths.append([])
    for s in start_nodes:
        for d in nodes:
            graph.printAllPaths(s, d, paths)
    if not has_all_nodes(paths, len(nodes)):
        paths.append(nodes)
    return paths

def has_all_nodes(paths, items):
    for path in paths:
        if len(path) == items:
            return True
    return False

# A class to represent a graph. A graph
# is the list of the adjacency lists.
# Size of the array will be the no. of the
# vertices "V"
class Graph:

    # Constructor
    def __init__(self, vertices):
        # No. of vertices
        self.V = vertices
        # default dictionary to store graph
        self.graph = defaultdict(list)

    def print_graph(self):
        print(self.graph)

    # function to add an edge to graph
    def addEdge(self, u, v):
        self.graph[u].append(v)

    def printAllPathsUtil(self, u, d, visited, path, paths):

        # Mark the current node as visited and store in path
        #visited[u] = True
        visited.append(u)
        path.append(u)

        # If current vertex is same as destination, then print
        # current path[]
        if u == d:
            print(path)
            paths.append(path.copy())
        else:
            # If current vertex is not destination
            # Recur for all the vertices adjacent to this vertex
            try:
                for i in self.graph[u]:
                    #if visited[i] == False:
                    if u in visited:
                        self.printAllPathsUtil(i, d, visited, path, paths)
            except:
                print('prazno')
        # Remove current vertex from path[] and mark it as unvisited
        path.pop()
        visited.remove(u)

    # Prints all paths from 's' to 'd'
    def printAllPaths(self, s, d, paths):

        # Mark all the vertices as not visited
        #visited = [False] * (self.V)
        visited = []
        # Create an array to store paths
        path = []

        # Call the recursive helper function to print all paths
        self.printAllPathsUtil(s, d, visited, path, paths)


if __name__ == '__main__':
    print_hi('PyCharm')
    print(app)

    simu_result1 = simu(15, 200, 0.05, 0.05, 0.5)
    #pickle.dump(simu_result, open('simu_result.pickle', 'wb'))
    simu_result = pickle.load(open('simu_result.pickle', 'rb'))

    print(simu_result1['dataset'])
    print("-----")
    print(simu_result['states'])
    print(len(simu_result['states']))
    # print("------")
    # np_data = np.array(simu_result['dataset'])
    # df = pd.DataFrame(data=np_data, columns=[i for i in range(0, 4)])
    # print(df)
    print("-----")

    iita_result = iita_exclude_transitive(simu_result['dataset'], v=1)
    implications = iita_result['implications']
    #implications = [(10, 18), (11, 14), (11, 17), (12, 13), (12, 16), (15, 12), (15, 14), (15, 17), (16, 17), (18, 13)]
    nodes = list(set([node for pair in implications for node in pair]))
    #
    start_nodes = nodes.copy()
    for impl in implications:
        if impl[1] in start_nodes:
            start_nodes.remove(impl[1])

    graph = Graph(len(nodes))
    for impl in implications:
        graph.addEdge(impl[0], impl[1])

    #graph.print_graph()

    paths1 = []
    paths1.append([])
    for s in start_nodes:
        for d in nodes:
            graph.printAllPaths(s, d, paths1)
    paths1.append(nodes)
    print('\nimplications:')
    print(implications)
    print(paths1)

    lista12 = [[1, 2, 3], [], [1, 2]]








