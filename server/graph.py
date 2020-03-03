import matplotlib.pyplot as plt
import numpy as np

class Diagram:
	def __init__(self):
		pass
	
	def test(self):
		x = np.linspace(1, 100, 100)
		y = np.sin(x)
		plt.plot(x, y)
		plt.savefig('test.png')

if __name__ == '__main__':
	Diagram().test()
		

