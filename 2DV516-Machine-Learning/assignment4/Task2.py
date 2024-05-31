import numpy as np
from Common import sammon


n = 800
p = 8
print(sammon(np.random.rand(n, p), 15, 0.2, 0.001))