import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap
import csv
from sklearn.neighbors import KNeighborsClassifier


def kNN(knn, instance):
        return knn.predict([instance])[0]


def plot_for_k(k, ax):
    knn = KNeighborsClassifier(n_neighbors=k)
    knn.fit(x_train, y_train)

    xx, yy = np.meshgrid(np.linspace(-1, 1.5, 120), np.linspace(-1, 1.5, 120))
    Z = np.zeros(xx.shape)

    for i in range(xx.shape[0]):
        for j in range(xx.shape[1]):
            point = [xx[i, j], yy[i, j]]
            Z[i, j] = kNN(knn, point)

    ax.contourf(xx, yy, Z, cmap=ListedColormap(['r', 'g']), alpha=0.2)
    ax.scatter(x_train[:, 0], x_train[:, 1], c=y_train, cmap=ListedColormap(['r', 'g']), s=5)
    ax.set_title(f"k = {k}, training errors = {count_errors(knn)}")


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
x_train = np.array([[entry[0], entry[1]] for entry in data])
y_train = np.array([entry[2] for entry in data])
plot_data(data)


points_to_predict = [[-0.3, 1.0], [-0.5, -0.1], [0.6, 0.0]]
k_to_predict = [1, 3, 5, 7]

for k in k_to_predict:
    knn = KNeighborsClassifier(n_neighbors=k)
    knn.fit(x_train, y_train)
    print(f'For {k} neighbors: ')
    for i in range(len(points_to_predict)):
        pred = knn.predict([points_to_predict[i]])
        print(f'\tchip{i+1} {points_to_predict[i]} ==> {"Fail" if pred == 0 else "OK"}')

fig, ax = plt.subplots(2, 2)
plot_for_k(1, ax[0, 0])
plot_for_k(3, ax[0, 1])
plot_for_k(5, ax[1, 0])
plot_for_k(7, ax[1, 1])
fig.tight_layout(pad=1.0)
plt.show()
