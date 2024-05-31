import math
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error
import Common as utils
from sys import maxsize


def read_data():
    with open("A2_datasets/A2_datasets/cars-mpg.csv", "r") as file:
        data = file.readlines()

    del data[0]
    np.random.seed(1)
    np.random.shuffle(data)

    x_train = [[] for i in range(var_num)]
    y_train = []
    x_test = [[] for i in range(var_num)]
    y_test = []

    count = 0
    for row in data:
        row_vals = list(map(float, row.split(",")))
        if count < train_size:
            for i in range(var_num):
                x_train[i].append(row_vals[i + 1])
            y_train.append(row_vals[0])
        else:
            for i in range(var_num):
                x_test[i].append(row_vals[i])
            y_test.append(row_vals[var_num])

    x_train = np.stack((x_train[i] for i in range(var_num)), axis=1)
    y_train = np.array(y_train)
    x_test = np.stack((x_test[i] for i in range(var_num)), axis=1)
    y_test = np.array(y_test)

    return x_train, y_train, x_test, y_test


def forward_selection(X, y):
    checked_features = []
    unchecked_features = set(range(var_num))
    models = [([], maxsize)]

    for i in range(var_num):
        features_mse = []
        for feature in unchecked_features:
            features_curr = checked_features + [feature]
            x_mtx = X[:, features_curr ]
            model = utils.get_model(x_mtx, y)
            y_est = utils.predict_model(x_mtx, model)
            error = utils.MSE(y, y_est)
            features_mse.append((feature, error))
        features_mse.sort(key=lambda x: x[1])
        feature, mse = features_mse[0]

        unchecked_features.remove(feature)
        checked_features.append(feature)
        models.append((checked_features.copy(), mse))

    return models


var_num = 7
entry_num = 398
train_size = math.floor((entry_num/100)*80)

x_train, y_train, x_test, y_test = read_data()
models_sorted = forward_selection(x_train, y_train)

print(f"Best model: {models_sorted[len(models_sorted)-1][0]}")
print(f"Best feature: {models_sorted[len(models_sorted)-1][0][0]}")