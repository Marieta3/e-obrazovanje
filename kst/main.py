
from flask import Flask
from flask import jsonify
from flask import request
import pandas as pd
import numpy as np
import sys
sys.path.append('learning_spaces/')
from learning_spaces.kst import iita, simu


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

    iita_result = iita(simu_result['dataset'], v=1)

    return jsonify(iita_result['implications'])

if __name__ == '__main__':
    print_hi('PyCharm')
    print(app)

    simu_result = simu(6, 100, 0.05, 0.05, 0.5)

    print(simu_result['dataset'])
    print(simu_result['implications'])
    print(simu_result['states'])
    # print("------")
    # np_data = np.array(simu_result['dataset'])
    # df = pd.DataFrame(data=np_data, columns=[i for i in range(0, 4)])
    # print(df)
    # print("-----")
    print(simu_result['dataset'])
    iita_result = iita(simu_result['dataset'], v=1)

    print(iita_result)







