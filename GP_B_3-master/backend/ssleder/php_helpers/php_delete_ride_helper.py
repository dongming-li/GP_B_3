#!/usr/bin/python
import sys
import MySQLdb
import json
from create_and_find_rides import delete_ride


def main(argv):
	print argv
	try:
		delete_ride(argv[0])
		print "0"
	except:
		print "1"

if __name__ == "__main__":
	main(sys.argv[1:])
