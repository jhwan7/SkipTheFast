import matplotlib.pyplot as plt
import CONST
import datetime
import numpy as np


class Diagram:
    def __init__(self, type):
        self.type = None
        if type in CONST.DIAGRAM_TYPES:
            self.type = type

    def plot(self, records):
        if self.type == 'plot':
            x = [_ for _ in range(0, 32)]
            y = [0 for _ in x]
            for key, record in records.items():
                ndx = int(datetime.datetime.strptime(record['time'].split(' ')[0], "%Y-%m-%d").day)
                y[ndx] = float(record['Price'])

            # plt.xticks(np.arange(np.min(x), np.max(x)+1))
            plt.xlabel('DAY')
            plt.ylabel('TOTAL MONEY SPENT($)')
            plt.title('Money spent on Fast food')
            plt.plot(x, y)
            plt.savefig('tmp.png')

