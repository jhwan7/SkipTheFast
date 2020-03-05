import matplotlib.pyplot as plt
import CONST


class Diagram:
    def __init__(self, type):
        self.type = None
        if type in CONST.DIAGRAM_TYPES:
            self.type = type

    def plot(self, x, y):
        if self.type == 'plot':
            plt.plot(x, y)
            plt.savefig('tmp.png')

