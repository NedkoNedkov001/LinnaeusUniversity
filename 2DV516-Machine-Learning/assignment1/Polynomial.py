from mailbox import linesep
import math
import operator
import numpy as np
import matplotlib.pyplot as plt
import csv


def distance(point_one, point_two):
    x_one = point_one
    x_two = point_two
    distance = math.fabs(x_one-x_two)
    return distance


def mean_square_error(expected_y, actual_y):
    squared_diff = [(actual_y[i] - expected_y[i]) **
                    2 for i in range(len(actual_y))]
    mse = sum(squared_diff) / len(actual_y)
    return mse


def func(x):
    return (5 + (12*x) - (x**2) + (0.025*x**3) + np.random.normal(0, 5))


def get_x_y_coordinates(set):
    x = []
    y = []
    for entry in set:
        x.append(entry[0])
        y.append(entry[1])

    return (x, y)


def plot_knn_regression(k, set, ax):
    (x_coordinates, actual_y_lst) = get_x_y_coordinates(set)
    calc_y_lst = get_calc_y_lst(k, set)
    
    plot_set(
            training_set, f'polynomial_train, k = {k}, MSE = {mean_square_error(calc_y_lst, actual_y_lst)}', ax)

    calculated_points = []
    for i in range(len(set)):
        point = [set[i][0], calc_y_lst[i]]
        calculated_points.append(point)

    sorted_calculated_points = sorted(calculated_points, key=lambda x: x[0])
    (sorted_x, sorted_y) = get_x_y_coordinates(sorted_calculated_points)
    ax.plot(sorted_x, sorted_y, linestyle='-', color='cyan')


def get_mse(k, set):
    (x_coordinates, actual_y_lst) = get_x_y_coordinates(set)
    calc_y_lst = get_calc_y_lst(k, set)
    return mean_square_error(calc_y_lst, actual_y_lst)


def get_calc_y_lst(k, set):
    calc_y_lst = []
    for x in range(len(set)):
        calculated_y = kNN(k, set[x])
        calc_y_lst.append(calculated_y)
    return calc_y_lst


# Used material from
# https://www.analyticsvidhya.com/blog/2018/03/introduction-k-neighbours-algorithm-clustering/
# Referenced in Lecture 1
def kNN(k, curr):
    distances = {}

    for x in range(len(training_set)):
        dist = distance(curr[0], training_set[x][0])
        distances[x] = dist

    sorted_d = sorted(distances.items(), key=operator.itemgetter(1))
    
    neighbors = []
    for x in range(k):
        neighbors.append(sorted_d[x][0])

    totalNeighborValue = 0
    for x in range(len(neighbors)):
        totalNeighborValue += training_set[neighbors[x]][1]

    avgNeighborsValue = totalNeighborValue / k
    return avgNeighborsValue


def plot_set(set, title, ax):
    (x, y) = get_x_y_coordinates(set)
    ax.scatter(x, y, s=5, c="blue")
    ax.title.set_text(title)


def read_data(path):
    #    FAIL X     FAIL Y       OK X       OK Y
    # data[0][0] data[0][1] data[1][0] data[1][1]
    # data = [[[], []], [[], []]]
    training_set = []
    test_set = []
    with open(path, "r") as file:
        csvreader = csv.reader(file)
        for row in csvreader:
            training_set.append([float(row[0]), float(row[1])])
    test_set = training_set[100:]
    training_set = training_set[:100]
    return (training_set, test_set)


(training_set, test_set) = read_data('nn222mx_A1/A1_datasets/polynomial200.csv')

print('Showing original data')
fig, ax = plt.subplots(1, 2)
plot_set(training_set, 'Training Set', ax[0])
plot_set(test_set, 'Test Set', ax[1])
plt.show()

print('Showing training set data')
fig, ax = plt.subplots(2, 3)
plot_knn_regression(1, training_set, ax[0, 0])
plot_knn_regression(3, training_set, ax[0, 1])
plot_knn_regression(5, training_set, ax[0, 2])
plot_knn_regression(7, training_set, ax[1, 0])
plot_knn_regression(9, training_set, ax[1, 1])
plot_knn_regression(11, training_set, ax[1, 2])
plt.show()

# for i in [1, 3, 5,  7, 9, 11]:
#     print(f'Training MSE for k = {i}: {get_mse(i, test_set)}')

# See regression plots for test set
print('Showing test set data')
fig, ax = plt.subplots(2, 3)
plot_knn_regression(1, test_set, ax[0, 0])
plot_knn_regression(3, test_set, ax[0, 1])
plot_knn_regression(5, test_set, ax[0, 2])
plot_knn_regression(7, test_set, ax[1, 0])
plot_knn_regression(9, test_set, ax[1, 1])
plot_knn_regression(11, test_set, ax[1, 2])
plt.show()
