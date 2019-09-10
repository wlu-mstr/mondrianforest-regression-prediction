import pickle
import numpy as np

fn = "wrapper2/ben_r2_0828.mdr"
model_file_ben = fn
load_ben = pickle.load(open(model_file_ben, 'rb'))

x = load_ben.__reduce__()

es = load_ben.estimators_

import json

trees = []
for esi in es:
    # print(esi.tree_.__reduce__())
    d = esi.tree_.__getstate__()
    nodes = d['nodes']
    for nodei in nodes:
        nodei['lower_bounds'] = nodei['lower_bounds'].tolist()
        nodei['upper_bounds'] = nodei['upper_bounds'].tolist()
        nodei['tau'] = "inf" if nodei['tau'] == np.inf else nodei['tau']

    values = d['values']
    d['values'] = [di[0][0] for di in values]
    trees.append(d)

jstr = json.dumps(trees)

with open('%s.json'%fn, 'w') as f:
    f.write(jstr)