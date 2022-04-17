#!/usr/bin/python
import sys
import json
import time

from get_user_info import *
from create_user import *
from create_and_find_rides import *

def main():
	while 1 == 1:
		print "\nOptions:"
		print "1 : pull user account"
		print "2 : add a user ( not done yet )"
		print "3 : pull all pending rides"
		print "4 : add three arbitrary pending rides"
		print "EXIT : exit this script"
	
		choice = raw_input()	

		if choice == "1":
			choice = raw_input("Enter the email of a record you want me to pull: ")

			print choice	
			try:
				test = get_user_info(choice)
				print test
			except:
				print "ERROR FETCHING RECORD"

		elif choice == "2":
			test_create_user()

		elif choice == "3":
			print "Printing all currently pending rides:"
			pending_rides = json.loads(fetch_pending())
			for ride in pending_rides:
				print ride

		elif choice == "4":
			test_create_pending()
	
		elif choice == "EXIT":
			return
		else:
			print "Make a new selection..."
			time.sleep(1)
	return

main()
