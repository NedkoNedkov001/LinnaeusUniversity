from matplotlib.colors import ListedColormap
import numpy as np
import matplotlib.pyplot as plt
import Common as utils


def prob(x1, x2, beta, d):
    p = beta[0] + beta[1] * x1 + beta[2] * x2
    counter = 3
    for i in range(2, d + 1):
        for j in range(0, i + 1):
            beta_ = beta[counter]
            p += beta_ * (x1 ** (i-j)) * (x2 ** j)
            counter += 1

    return np.round(utils.sigmoid(p))


def plot(X, alpha, N, degree):
    Xe = utils.mapFeature(X[0], X[1], degree)
    beta, cost = utils.gradient_descent_logistic(
        Xe, y, entry_num, alpha, N, Xe.shape[1], plt.subplot(1, 2, 1))
    print(f"\nHyperparameters:\n  Alpha: {alpha}\n  Iterations: {N}")
    print(f"Cost of gradient descent: {cost}\n")
    # Used this to find suitable alpha, N:
    # alpha, N = utils.find_min_cost(Xe, y, entry_num)

    h = .01
    x_min, x_max = X[0].min()-0.1, X[0].max()+0.1
    y_min, y_max = X[1].min()-0.1, X[1].max()+0.1
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                         np.arange(y_min, y_max, h))
    x1, x2 = xx.ravel(), yy.ravel()
    XXe = utils.mapFeature(x1, x2, degree)
    p = utils.sigmoid(np.dot(XXe, beta))
    classes = p > 0.5
    clz_mesh = classes.reshape(xx.shape)
    cmap_1 = ListedColormap(['#FFAAAA', '#AAFFAA'])
    cmap_2 = ListedColormap(['#FF0000', '#00FF00'])
    plt.subplot(1, 2, 2).scatter(X[0], X[1], c=y,
                                 marker='.', s=10, cmap=cmap_2)
    plt.subplot(1, 2, 2).pcolormesh(
        xx, yy, clz_mesh, cmap=cmap_1, alpha=0.25)
    plt.subplot(1, 2, 2).title.set_text(
        f"Training errors: {utils.find_errors(Xe, y, beta)}")
    plt.show()


var_num = 2
entry_num = 118

x, y = utils.read_data("A2_datasets/A2_datasets/microchips.csv", var_num)

plot_colors = ListedColormap(["r", "g"])
plt.scatter(x[0], x[1], c=y, marker=".", cmap=plot_colors)
plt.show()

N = 3400
alpha = 9
deg = 2
plot(x, alpha, N, deg)

N = 4900
alpha = 16
deg = 5
plot(x, alpha, N, deg)
