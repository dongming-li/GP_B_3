#!/usr/bin/python
import sys
import MySQLdb
import json
from get_user_info import get_user_info


def main(argv):
	try:
		user_json = json.loads(get_user_info(sys.argv[1]))
		print user_json["password"]
	except:
		print "NULL"

if __name__ == "__main__":
	main(sys.argv[1:])
