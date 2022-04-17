#!/usr/bin/python
import sys
import MySQLdb
import json
from create_and_find_rides import fetch_pending


def main():
	try:
		user_json = fetch_pending()

		print user_json
	except:
		print "No Rides Found"

if __name__ == "__main__":
	main()
