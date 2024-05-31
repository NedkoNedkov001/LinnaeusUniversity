import numpy as np
import matplotlib.pyplot as plt
import Common as utils


def plot_features(X, y):
    for i in range(var_num):
        Xi = X[:, i]
        plt.subplot(2, 3, i + 1).scatter(Xi, y)
    plt.show()


def get_y_est(Xn, beta):
    return beta[0] + np.dot(beta[1:], Xn[0:])


var_num = 6
entry_num = 18
x, y = utils.read_data("A2_datasets/A2_datasets/GPUbenchmark.csv", var_num)

Xn, Xe = utils.normalize_data(x, entry_num, var_num)
plot_features(Xn, y)

mu = utils.get_mu(x, var_num)
sigma = utils.get_sigma(x, var_num)
beta = utils.get_beta(Xe, y)

Xn = [[] for i in range(var_num)]
x_vals = [2432, 1607, 1683, 8, 8, 256]
for i in range(var_num):
    Xn[i] = (x_vals[i] - mu[i]) / sigma[i]


y_est = get_y_est(Xn, beta)
print(f"\nResult with Normal Equation: {y_est}")

j = np.dot(Xe, beta)-y
cost_J = (j.T.dot(j)) / entry_num
print(f"Cost J(beta) with Normal Equation: {cost_J}\n")

ax = plt.subplot(1, 1, 1)

alpha = 0.025
N = 2000
print(f"Hyperparameters:\n  Alpha: {alpha}\n  Iterations: {N}")
beta, cost = utils.gradient_descent_linear(Xe, y, entry_num, alpha, N, (var_num + 1), ax)
plt.show()

y_est = get_y_est(Xn, beta)
print(f"Result with Gradient Descent: {y_est}")
print(f"Cost with Gradient Descent: {cost}")
