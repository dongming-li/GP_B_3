#!/usr/bin/python
import sys
import MySQLdb
from create_and_find_rides import fetch_email_rider


def main(argv):
	try:
		rider = fetch_email_rider(argv[0])

		print rider

	except:
		print "[]"	

if __name__ == "__main__":
	main(sys.argv[1:])
