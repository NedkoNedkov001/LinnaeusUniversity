import math
import operator
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap
import csv


def distance(point_one, point_two):
    x_one = point_one[0]
    y_one = point_one[1]
    x_two = point_two[0]
    y_two = point_two[1]
    x_distance = (x_one-x_two)
    y_distance = (y_one-y_two)
    distance = math.sqrt(x_distance**2+y_distance**2)
    return distance


def predict(k, point):
    if kNN(k, point) == 0:
        return 'Fail'
    else: return 'OK'


# Used material from 
# https://www.analyticsvidhya.com/blog/2018/03/introduction-k-neighbours-algorithm-clustering/
# Referenced in Lecture 1
def kNN(k, instance):
    distances = {}

    for x in range(len(data)):
        dist = distance(data[x], instance)
        distances[x] = dist

    sorted_d = sorted(distances.items(), key=operator.itemgetter(1))

    neighbors = []

    for x in range(k):
        neighbors.append(sorted_d[x][0])
    classVotes = {}

    for x in range(len(neighbors)):
        response = data[neighbors[x]][2]

        if response in classVotes:
            classVotes[response] += 1
        else:
            classVotes[response] = 1

    sortedVotes = sorted(classVotes.items(),
                         key=operator.itemgetter(1), reverse=True)
    selectedClass = sortedVotes[0][0]
    return selectedClass


def plotForK(k, ax):
    xx, yy = np.meshgrid(np.linspace(-1, 1.5, len(data)),
                         np.linspace(-1, 1.5, len(data)))

    Z = np.zeros(xx.shape)
    for i in range(xx.shape[0]):
        for j in range(xx.shape[1]):
            point = [xx[i, j], yy[i,j]]
            Z[i, j] = kNN(k, point)

    (failed_x, failed_y, ok_x, ok_y) = get_failed_ok_x_y()
    ax.scatter(failed_x, failed_y, color="red", s=5)
    ax.scatter(ok_x, ok_y, color="green", s=5)
    ax.contourf(xx, yy, Z, cmap=ListedColormap(['r', 'g']), alpha=0.2)
    ax.title.set_text(f"k = {k}, training errors = {count_errors(k)}")


def count_errors(k):
    err_count = 0
    for entry in data:
        point = [entry[0], entry[1]]
        expected_result = entry[2]
        if kNN(k, point) != expected_result:
            err_count += 1
    return err_count


def get_failed_ok_x_y():
    failed_x = []
    failed_y = []
    ok_x = []
    ok_y = []
    for entry in data:
        if entry[2] == 0:
            failed_x.append(entry[0])
            failed_y.append(entry[1])
        elif entry[2] == 1:
            ok_x.append(entry[0])
            ok_y.append(entry[1])
    return (failed_x, failed_y, ok_x, ok_y)


def plot_data(data):
    (failed_x, failed_y, ok_x, ok_y) = get_failed_ok_x_y()

    plt.figure()
    plt.scatter(failed_x, failed_y, marker='x', c="red")
    plt.scatter(ok_x, ok_y, marker='o', c="green")
    plt.title('Original Data')
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.show()


def read_data(path):
    #    FAIL X     FAIL Y       OK X       OK Y
    # data[0][0] data[0][1] data[1][0] data[1][1]
    data = []
    with open(path, "r") as file:
        csvreader = csv.reader(file)
        for row in csvreader:
            data.append([float(row[0]), float(row[1]), int(row[2])])
    return data


data = read_data('nn222mx_A1/A1_datasets/microchips.csv')
plot_data(data)

points_to_predict = [[-0.3, 1.0],[-0.5, -0.1],[0.6, 0.0]]
k_to_predict = [1, 3, 5, 7]
for k in k_to_predict:
    print(f'For {k} neighbors: ')
    for i in range(len(points_to_predict)):
        print(f'\tchip{i+1} {points_to_predict[i]} ==> {predict(k, points_to_predict[i])}')

fig, ax = plt.subplots(2, 2)
plotForK(1, ax[0, 0])
plotForK(3, ax[0, 1])
plotForK(5, ax[1, 0])
plotForK(7, ax[1, 1])
fig.tight_layout(pad=1.0)
plt.show()