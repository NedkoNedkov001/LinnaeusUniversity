import numpy as np
from Common import bkmeans

n = 100
p = 4
print(bkmeans(np.random.rand(n, p), 3, 10))
