import pickle
import numpy as np

model_file_ben = "wrapper2/ben_r2_0828.mdr"
load_ben = pickle.load(open(model_file_ben, 'rb'))

x = load_ben.__reduce__()

es = load_ben.estimators_

def val_fmt(x):
    if isinstance(x, str):
        return x
    return float("{0:.4f}".format(x))

import json


trees = []
for esi in es:
    # print(esi.tree_.__reduce__())
    d = esi.tree_.__getstate__()
    nodes = d['nodes']
    for nodei in nodes:
        nodei['lb'] = list(map(lambda xi: val_fmt(xi), nodei['lower_bounds'].tolist()))
        nodei['ub'] = list(map(lambda xi: val_fmt(xi), nodei['upper_bounds'].tolist()))
        nodei['ta'] = val_fmt("inf" if nodei['tau'] == np.inf else nodei['tau'])
        nodei['vr'] = val_fmt(nodei['variance'])
        nodei['lc'] = nodei['left_child']
        nodei['rc'] = nodei['right_child']
        nodei['f'] = nodei['feature']
        nodei['th'] = val_fmt(nodei['threshold'])

        del nodei['lower_bounds']
        del nodei['upper_bounds']
        del nodei['tau']
        del nodei['variance']
        del nodei['left_child']
        del nodei['right_child']
        del nodei['feature']
        del nodei['threshold']

        del nodei['impurity']
        del nodei['n_node_samples']
        del nodei['weighted_n_node_samples']

    values = d['values']
    d['values'] = [val_fmt(di[0][0]) for di in values]
    trees.append(d)

jstr = json.dumps(trees)

with open('wrapper2/ben_r2_0828_2.mdr.json', 'w') as f:
    f.write(jstr)
