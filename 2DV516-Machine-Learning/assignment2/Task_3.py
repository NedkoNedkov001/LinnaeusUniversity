import math
import numpy as np
import matplotlib.pyplot as plt
import Common as utils


def read_data():
    with open("A2_datasets/A2_datasets/banknote_authentication.csv", "r") as file:
        data = file.readlines()

    np.random.shuffle(data)

    x_train = [[] for i in range(var_num)]
    y_train = []
    x_test = [[] for i in range(var_num)]
    y_test = []

    for i in range(train_size):
        row_vals = list(map(float, data[i].split(",")))
        for i in range(var_num):
            x_train[i].append(row_vals[i])
        y_train.append(row_vals[var_num])
    for i in range(train_size, entry_num):
        row_vals = list(map(float, data[i].split(",")))
        for i in range(var_num):
            x_test[i].append(row_vals[i])
        y_test.append(row_vals[var_num])

    for i in range(var_num):
        x_train[i] = np.array(x_train[i])
        x_test[i] = np.array(x_test[i])
    y_train = np.array(y_train)
    y_test = np.array(y_test)

    return x_train, y_train, x_test, y_test


def print_err_acc(X, y, size, beta, type):
    acc_train = utils.find_accuracy(X, y, beta, size)
    err_train = utils.find_errors(X, y, beta)
    print(f"{type}ing errors: {err_train}\n{type}ing accuracy: {acc_train}\n")


entry_num = 1372
train_size = math.floor((entry_num/100)*80)
test_size = entry_num - train_size
var_num = 4

x_train, y_train, x_test, y_test = read_data()

Xn_train, Xne_train = utils.normalize_data(x_train, train_size, var_num)
Xn_test, Xne_test = utils.normalize_data(x_test, test_size, var_num)

# alpha, N = utils.find_min_cost(Xne_train, y_train, train_size)
# Finds that the higher the alpha
# and the more iterations, the better
# (for the first 10k iterations and alpha up to 20)
alpha = 20
N = 10000
print(f"\nHyperparameters:\n  Alpha: {alpha}\n  Iterations: {N}")

ax = plt.subplot(1, 1, 1)
beta, cost = utils.gradient_descent_logistic(
    Xne_train, y_train, train_size, alpha, N, (var_num+1), ax)
print(f"Cost of gradient descent: {cost}\n")

plt.show()

print_err_acc(Xne_train, y_train, train_size, beta, "Train")
print_err_acc(Xne_test, y_test, test_size, beta, "Test")
