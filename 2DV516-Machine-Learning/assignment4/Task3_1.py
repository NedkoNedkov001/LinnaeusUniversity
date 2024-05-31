from Common import sammon, sammon_stress, compute_gradient, read_file_data
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from sklearn.manifold import TSNE
import matplotlib.pyplot as plt


def plot_sammon_mapping(sammon_lst, y_lst, sets_lst):
    for i in range(3):
        plt.subplot(3, 3, i+1)
        plt.title(sets_lst[i])
        plt.scatter(sammon_lst[i][:, 0], sammon_lst[i]
                    [:, 1], c=y_lst[i], cmap='viridis')


def plot_pca(pca_lst, y_lst):
    for i in range(3):
        plt.subplot(3, 3, i+4)
        plt.scatter(pca_lst[i][:, 0], pca_lst[i]
                    [:, 1], c=y_lst[i], cmap='viridis')


def plot_tsne(tsne_lst, y_lst):
    for i in range(3):
        plt.subplot(3, 3, i + 7)
        plt.scatter(tsne_lst[i][:, 0], tsne_lst[i]
                    [:, 1], c=y_lst[i], cmap='viridis')


set_one = 'ArsenicLung.csv'
var_num_one = 4
set_two = 'Diabetes.csv'
var_num_two = 8
set_three = 'TicTacToe.csv'
var_num_three = 9

x1, y1 = read_file_data('ArsenicLung.csv', 4)
x2, y2 = read_file_data('Diabetes.csv', 8)
x3, y3 = read_file_data('TicTacToe.csv', 9)

sc = StandardScaler()
x1, x2, x3 = [sc.fit_transform(x) for x in [x1, x2, x3]]

pca = PCA(n_components=2)
x1_pca, x2_pca, x3_pca = [pca.fit_transform(x) for x in [x1, x2, x3]]

tsne = TSNE(n_components=2)
x1_tsne, x2_tsne, x3_tsne = [tsne.fit_transform(x) for x in [x1, x2, x3]]

x1_sammon, x2_sammon, x3_sammon = [
    sammon(x, 10, 0.2, 0.005) for x in [x1, x2, x3]]

plt.figure(figsize=(20, 6))

y_lst = [y1, y2, y3]
pca_lst = [x1_pca, x2_pca, x3_pca]
tsne_lst = [x1_tsne, x2_tsne, x3_tsne]
sammon_lst = [x1_sammon, x2_sammon, x3_sammon]
sets_lst = [set_one, set_two, set_three]

plt.subplot(3, 3, 1)
plt.ylabel('Sammon Mapping')
plot_sammon_mapping(sammon_lst, y_lst, sets_lst)

plt.subplot(3, 3, 4)
plt.ylabel('PCA')
plot_pca(pca_lst, y_lst)

plt.subplot(3, 3, 7)
plt.ylabel('t-SNE')
plot_tsne(tsne_lst, y_lst)

plt.show()
