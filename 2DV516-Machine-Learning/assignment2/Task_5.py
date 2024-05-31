from matplotlib.colors import ListedColormap
import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import cross_val_score
import Common as utils


def plot(x, y, log_reg):
    h = .01
    x_min, x_max = x[0].min()-0.1, x[0].max()+0.1
    y_min, y_max = x[1].min()-0.1, x[1].max()+0.1

    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
    np.arange(y_min, y_max, h))

    for i in range(1, 10):
        Xe = utils.mapFeature(x[0], x[1], i, False)
        log_reg.fit(Xe, y)

        XXe = utils.mapFeature(xx.ravel(), yy.ravel(), i, False)
        Z = log_reg.predict(XXe)

        Z = Z.reshape(xx.shape)
        plt.subplot(3,3,i).scatter(x[0],x[1],c=y, marker=".",cmap=cmap_2)

        plt.subplot(3,3,i).scatter(x[0],x[1],c=y, marker=".",cmap=cmap_2)
        plt.subplot(3,3,i).pcolormesh(xx, yy, Z, cmap=cmap_1, alpha=0.5)
        plt.subplot(3,3,i).title.set_text(f"Decision Boundary degree = {i}")
    plt.show()


var_num = 2
entry_num = 118
x, y = utils.read_data("A2_datasets/A2_datasets/microchips.csv", var_num)

cmap_1 = ListedColormap(['#FFAAAA', '#AAFFAA'])
cmap_2 = ListedColormap(['#FF0000', '#00FF00'])
log_reg_unreg = LogisticRegression(solver="lbfgs", C=10000, tol=1e-6)
plot(x, y, log_reg_unreg)

log_reg_reg = LogisticRegression(solver="lbfgs", C=1, tol=1e-6)
plot(x, y, log_reg_reg)

for i in range(1, 10):
    X = utils.mapFeature(x[0], x[1], i, False)
    unregularized_val = np.mean(cross_val_score(log_reg_unreg, X, y, cv=6))
    regularized_val = np.mean(cross_val_score(log_reg_reg, X, y, cv=6))
    plt.scatter(i, unregularized_val, color="red")
    plt.scatter(i, regularized_val, color="green")

plt.xlabel("degree")
plt.ylabel("#errors")
plt.legend(["unregularized", "regularized"])
plt.show()
