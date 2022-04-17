#!/usr/bin/python
import sys
import MySQLdb
import json
from get_user_info import get_user_info


def main(argv):
	try:
		user_json = (get_user_info(argv[0]))

		print user_json
	except:
		print "Account Not Found"	

if __name__ == "__main__":
	main(sys.argv[1:])
