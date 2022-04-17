#!/usr/bin/python
import sys
from join_and_leave_rides import leave_ride


def main(argv):
	try:
		leave_ride(argv[0], argv[1])

		print "0"

	except:
		print "1"

if __name__ == "__main__":
	main(sys.argv[1:])
