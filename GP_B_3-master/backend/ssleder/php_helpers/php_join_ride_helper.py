#!/usr/bin/python
import sys
from join_and_leave_rides import join_ride


def main(argv):
	try:
		join_ride(argv[0], argv[1])

		print "0"

	except:
		print "1"

if __name__ == "__main__":
	main(sys.argv[1:])
