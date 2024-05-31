import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans, AgglomerativeClustering
from sklearn.preprocessing import MinMaxScaler
from Common import bkmeans, read_file_data


def plot_bkmeans(x_lst, x_clusters_lst, sets_lst):
    for i in range(3):
        plt.subplot(3, 3, i+1)
        plt.title(sets_lst[i])
        Yresult = bkmeans(x_lst[i], x_clusters_lst[i], 10)
        plt.scatter(x_lst[i][:, 0], x_lst[i][:, 1], c=Yresult)


def plot_kmeans(x_lst, x_clusters_lst, sets_lst):
    for i in range(3):
        plt.subplot(3, 3, i+4)
        kmeans = KMeans(x2_clusters)
        kmeans.fit(x_lst[i])
        plt.scatter(x_lst[i][:, 0], x_lst[i][:, 1], c=kmeans.labels_)


def plot_hierarchical(x_lst, x_clusters_lst, sets_lst):
    for i in range(3):
        plt.subplot(3, 3, i+7)
        agglo = AgglomerativeClustering(n_clusters=x_clusters_lst[i])
        agglo.fit(x_lst[i])
        plt.scatter(x_lst[i][:, 0], x_lst[i][:, 1], c=agglo.labels_)


set_one = 'ArsenicLung.csv'
var_num_one = 4
set_two = 'Diabetes.csv'
var_num_two = 8
set_three = 'TicTacToe.csv'
var_num_three = 9

x1, y1 = read_file_data('ArsenicLung.csv', 4)
x2, y2 = read_file_data('Diabetes.csv', 8)
x3, y3 = read_file_data('TicTacToe.csv', 9)

scaler = MinMaxScaler()
x1, x2, x3 = [scaler.fit_transform(x) for x in [x1, x2, x3]]

x1_clusters, x2_clusters, x3_clusters = [len(x.T) for x in [x1, x2, x3]]
pca = PCA(n_components=2)
x1, x2, x3 = [pca.fit_transform(x) for x in [x1, x2, x3]]

x_lst = [x1, x2, x3]
x_clusters_lst = [x1_clusters, x2_clusters, x3_clusters]
sets_lst = [set_one, set_two, set_three]

plt.subplot(3, 3, 1)
plt.ylabel("Bkmeans")
plot_bkmeans(x_lst, x_clusters_lst, sets_lst)

plt.subplot(3, 3, 4)
plt.ylabel("Kmeans")
plot_kmeans(x_lst, x_clusters_lst, sets_lst)

plt.subplot(3, 3, 7)
plt.ylabel("Hierarchical")
plot_hierarchical(x_lst, x_clusters_lst, sets_lst)

plt.show()
