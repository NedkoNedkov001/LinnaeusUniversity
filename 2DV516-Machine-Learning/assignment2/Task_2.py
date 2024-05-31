import math
import numpy as np
import matplotlib.pyplot as plt
import random
import Common as utils


def read_data():
    with open("A2_datasets/A2_datasets/secret_polynomial.csv", "r") as file:
        data = file.readlines()

    random.shuffle(data)
    return data


def separate_data(data):
    train = [[] for i in range(2)]
    test = [[] for i in range(2)]

    for i in range(len(data)):
        row_vals = list(map(float, data[i].split(',')))
        for j in range(2):
            if i < train_num:
                train[j].append(row_vals[j])
            else:
                test[j].append(row_vals[j])
    return train, test


def get_sorted_points(train, test):
    points_train = []
    points_test = []
    for i in range(train_num):
        points_train.append([train[0][i], train[1][i]])
    for i in range(test_num):
        points_test.append([test[0][i], test[1][i]])

    points_train = sorted(points_train, key=lambda x: x[0])
    points_test = sorted(points_test, key=lambda x: x[0])

    return points_train, points_test


def get_x_y(points):
    [X, y] = [[] for i in range(2)]

    for i in range(len(points)):
        X.append(points[i][0])
        y.append(points[i][1])

    X = np.array(X)
    y = np.array(y)
    return X, y


def get_grad_desc(X, beta, d):
    y_est = predict_y(X, beta, d)

    return X, y_est


def get_beta(X, y, d):
    Xe = np.c_[np.ones((len(X), 1)), np.array(
        [X ** i for i in range(1, d+1)]).T]

    beta = utils.get_beta(Xe, y)

    return beta


def predict_y(X, beta, d):
    y_est = [beta[0] + sum(beta[i] * (x**i)
                           for i in range(1, d+1)) for x in X]

    return y_est


def plot(X, y, grad_desc, i):
    plt.subplot(2, 3, i).scatter(X, y, s=2)
    plt.subplot(2, 3, i).plot(
        grad_desc[0], grad_desc[1], linestyle="-")


entry_num = 300
train_num = math.floor((entry_num/100)*80)
test_num = entry_num-train_num

data = read_data()
train, test = separate_data(data)

points_train, points_test = get_sorted_points(
    train, test)

for i in range(1, 7):
    X, y = get_x_y(points_train)
    beta = get_beta(X, y, i)
    grad_desc_train = get_grad_desc(X, beta, i)

    X, y = get_x_y(points_test)
    y_est = predict_y(X, beta, i)
    mse = utils.MSE(y, y_est)
    print(f'MSE for d={i}: {mse}')
    plot(X, y, grad_desc_train, i)

plt.show()
