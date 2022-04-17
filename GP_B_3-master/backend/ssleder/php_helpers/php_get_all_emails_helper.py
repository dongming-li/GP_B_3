#!/usr/bin/python
import sys
import MySQLdb
import json
from get_all_emails import get_all_emails


def main():
	try:
		user_json = get_all_emails()

		print user_json
	except:
		print "No Emails Found"	

if __name__ == "__main__":
	main()
