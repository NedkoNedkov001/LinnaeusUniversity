import csv
import numpy as np
import matplotlib.pyplot as plt


def read_data(file_name, var_num):
    x = [[] for i in range(var_num)]
    y = []
    with open(file_name, "r") as file:
        csvreader = csv.reader(file)
        for entry in csvreader:
            entry_vals = list(map(float, entry))
            for i in range(var_num):
                x[i].append(entry_vals[i])
            y.append(entry_vals[var_num])

    for i in range(var_num):
        x[i] = np.array(x[i])

    y = np.array(y)
    return x, y


def sigmoid(X):
    return 1 / (1 + np.exp(-X))


def find_min_cost(X, y, size):
    min_cost = 10
    nom_N = 100
    nom_alpha = 0
    for N in range(100, 10000, 100):
        for alpha in np.arange(0, 20, 1):
            cost = get_cost(X, y, size, alpha, N)
            if min_cost > cost:
                min_cost = cost
                nom_N = N
                nom_alpha = alpha
    return nom_alpha, nom_N


def get_cost(X, y, size, alpha, N):
    beta = np.zeros(X.shape[1])
    j_values = []

    for i in range(N):
        beta = beta - (alpha/size) * (X.T.dot(sigmoid(X.dot(beta)) - y))
        j_values.append(cost_function(X, y, size, beta))

    return j_values[len(j_values) - 1]


def cost_function(X, y, size, beta):
    return -((1/size)*(y.T.dot(np.log(sigmoid(X.dot(beta)))) + (1 - y).T.dot(np.log(1 - sigmoid(X.dot(beta))))))


def gradient_descent_logistic(X, y, size, alpha, iterations, beta_shape, ax):
    beta = np.zeros(beta_shape)
    j_values = []

    for i in range(iterations):
        beta = beta - (alpha/size) * (X.T.dot(sigmoid(X.dot(beta)) - y))
        j_values.append(cost_function(X, y, size, beta))

    ax.plot(np.array([i for i in range(iterations)]), j_values)

    ax.title.set_text(f"Cost function J(beta)")
    cost = j_values[len(j_values) - 1]
    return beta, cost


def gradient_descent_linear(X, y, size, alpha, iterations, beta_shape, ax):
    beta = np.zeros(beta_shape)
    J_values = []

    for i in range(iterations):
        beta = beta - np.dot(np.dot(alpha, X.T), np.dot(X, beta) - y)
        j = np.dot(X, beta)-y
        J_values.append((j.T.dot(j)) / size)

    ax.plot(np.array([i for i in range(iterations)]), J_values)
    cost = J_values[len(J_values) - 1]
    return beta, cost


def normalize_x(X, mu, sigma, var_num):
    xn = [[] for i in range(var_num)]

    for i in range(var_num):
        xn[i] = (X[:, i] - mu[i]) / sigma[i]

    Xn = np.array(xn).T
    return Xn


def normalize_data(x, size, var_num):
    x = np.stack((x[i] for i in range(var_num)), axis=1)
    mu = [np.mean(x[:, i]) for i in range(var_num)]
    sigma = [np.std(x[:, i]) for i in range(var_num)]

    Xn = normalize_x(x, mu, sigma, var_num)

    Xne = np.c_[np.ones((size, 1)), Xn]
    return  Xn, Xne


def get_mu(x, var_num):
    x = np.stack((x[i] for i in range(var_num)), axis=1)
    mu = [np.mean(x[:, i]) for i in range(var_num)]
    return mu


def get_sigma(x, var_num):
    x = np.stack((x[i] for i in range(var_num)), axis=1)
    sigma = [np.std(x[:, i]) for i in range(var_num)]
    return sigma


def get_beta(X, y):
    beta = np.linalg.inv(X.T.dot(X)).dot(X.T).dot(y)
    return beta

    
def get_yy_pp(X, y, beta):
    z = np.dot(X, beta).reshape(-1, 1)
    p = sigmoid(z)
    pp = np.round(p)
    yy = y.reshape(-1, 1)
    return pp,yy


def find_accuracy(X, y, beta, size):
    pp, yy = get_yy_pp(X, y, beta)
    acc = ((np.sum(yy == pp)) / size) * 100
    return acc


def find_errors(X, y, beta):
    pp, yy = get_yy_pp(X, y, beta)
    err = np.sum(yy != pp)
    return err


def mapFeature(X1, X2, D, ones=True):
    if ones == True:
        one = np.ones([len(X1), 1])
        Xe = np.c_[one, X1, X2]
    else: 
        Xe = np.c_[X1, X2]
        
    for i in range(2, D+1):
        for j in range(0, i+1):
            Xnew = X1**(i-j)*X2**j
            Xnew = Xnew.reshape(-1, 1)
            Xe = np.append(Xe, Xnew, 1)
    return Xe


def MSE(y_actual, y_estimated):
    return np.mean((y_actual - y_estimated) ** 2)


def get_model(X, y):
    X = np.hstack((np.ones((X.shape[0], 1)), X))
    model = np.linalg.inv(X.T.dot(X)).dot(X.T.dot(y))
    return model


def predict_model(X, coefficients):
    X = np.hstack((np.ones((X.shape[0], 1)), X))
    return X.dot(coefficients)


