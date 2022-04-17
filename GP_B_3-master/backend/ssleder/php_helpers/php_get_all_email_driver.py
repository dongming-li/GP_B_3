#!/usr/bin/python
import sys
import MySQLdb
from create_and_find_rides import fetch_email_driver


def main(argv):
	try:
		driver = fetch_email_driver(argv[0])

		print driver

	except:
		print "[]"

if __name__ == "__main__":
	main(sys.argv[1:])
