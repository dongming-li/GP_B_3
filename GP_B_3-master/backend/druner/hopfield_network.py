import random
import math

class hopfield(object):
	"""docstring for hopfield"""
	def __init__(self, length):
		self.weights = [[random.random() if r!=c else None for r in range(length)] for c in range(length)]
		self.length = length
		self.values = [0.0 for i in range(length)]

	def __repr__(self):
		total = ""
		for r in range(self.length):
			for c in range(self.length):
				total += ("{:0.2f}".format(int(self.weights[r][c]*100)/100.0) + " ") if self.weights[r][c] != None else "None "
			total += "\n"
		return total

	def sig(self, x, deriv = False, inverse = False):
		print x, deriv, inverse
		if(inverse):
			return -1 * math.log(1/x - 1)
		if(deriv):
			return self.sig(x)*(1-self.sig(x))
		return (1/(1+(math.e*(-1*x))))

	def ripple(self, pattern, count):
		self.values = pattern
		for c in range(count):
			self.values = [self.sig(sum([ (self.values[j]*self.weights[j][i] if i!=j else 0) for i in range(self.length)]) / self.length) for j in range(self.length)]

	def learn(self, new_pattern, count):
		for c in range(count):
			for i in range(self.length):
				for j in range(self.length):
					if i!=j:
						self.weights[i][j] += (self.sig(self.sig(self.weights[i][j], False, True), True, False)* new_pattern[i]*new_pattern[j])/self.length

my_net = hopfield(5)
print my_net

my_net.learn([1,0,1,0,1], 5000)
my_net.ripple([1,0,1,0.5,1], 5000)
print my_net.values
