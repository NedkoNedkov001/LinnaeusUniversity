import numpy as np
from sklearn.cluster import KMeans
from sys import maxsize
from sklearn.metrics import pairwise_distances
import csv


def bkmeans(X, k, iter):
    n = X.shape[0]
    clusters = np.zeros(n, dtype=int)

    for i in range(1, k):
        sizes = np.bincount(clusters)
        largest = np.argmax(sizes)
        elements = np.where(clusters == largest)[0]

        largest_X = X[elements]
        labels = None
        min_sse = maxsize

        for j in range(iter):
            kmeans = KMeans(n_clusters=2).fit(largest_X)
            sse = kmeans.inertia_
            if sse < min_sse:
                min_sse = sse
                labels = kmeans.labels_

        clusters[elements[labels == 1]] = i

    return clusters.reshape(-1, 1)


def sammon_stress(dX, dY):
    dX_nonzero = np.maximum(dX, 0.0000000001)
    return (1.0 / dX_nonzero.sum()) * np.sum((dX - dY) ** 2 / dX_nonzero)


def compute_gradient(dX, dY, Y):
    n = Y.shape[0]
    
    delta_dist = dY - dX
    gradient = np.zeros(Y.shape)

    for i in range(n):
        for j in range(n):
            if i == j:
                continue
            
            denom = dY[i, j] * dX[i, j] + 0.0000000001
            gradient[i] += delta_dist[i, j] * (Y[i] - Y[j]) / denom

    return gradient


def sammon(X, iter, error_threshold, alpha):
    Y = np.random.rand(X.shape[0], 2)

    dX = pairwise_distances(X)

    for i in range(iter):
        dY = pairwise_distances(Y)

        stress = sammon_stress(dX, dY)

        if stress < error_threshold:
            return Y

        gradient = compute_gradient(dX, dY, Y)

        Y = Y - (alpha * gradient)

    return Y


def read_file_data(file_name, var_num):
    X = []
    y = []

    with open(file_name, "r", encoding="UTF-8") as file:
        csvreader = csv.reader(file)
        next(csvreader)
        for entry in csvreader:
            X.append(tuple(float(entry[i]) for i in range(0, var_num)))
            y.append(float(entry[var_num]))
        x = np.array(X)
        y = np.array(y)
    return x, y
