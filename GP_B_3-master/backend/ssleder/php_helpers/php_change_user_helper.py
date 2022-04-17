## 
# @file php_change_user_helper.py
# @author Steven Sleder
# This method handles interface between PHP POST handlers and 
# Python SQL handlers.

#!/usr/bin/python
import sys
import MySQLdb
import json
from change_user import *

## @brief Interfaces with database helpers to change an account.
# This function acts as an interface between the PHP POST handler and
# the Python database handlers to modify a single user account.
# @param argv[0] Contains email
# @param argv[1] Contains new_password
# @param argv[2] Contains new_firstname
# @param argv[3] Contains new_lastname
# @param argv[4] Contains new_email
# @param argv[5] Contains new_rider_status
# @param argv[6] Contains new_driver_status
# @param argv[7] Contains new_rider_status
# @param argv[8] Contains new_admin_status
# @param argv[9] Contains delete_account
# @return void
def main(argv):
	email = argv[0]
	new_password = argv[1]
	new_firstname = argv[2]
	new_lastname = argv[3]
	new_email = argv[4]
	new_rider_status = argv[5]
	new_driver_status = argv[6]
	new_admin_status = argv[7]
	new_ban_status = argv[8]
	delete_account = argv[9]

	if delete_account == 1:
		if delete_user(email) == 1:
			print "DELETED"
		else:
			print "NONEXISTANT"
	else:
		if change_password(email, new_password) == 1:
			print "PASSWORD CHANGED"
		else:
			print "NONEXISTANT"
	
		if change_firstname(email, new_firstname) == 1:
			print "CHANGED FIRSTNAME"
		else:
			print "NONEXISTANT"
		if change_lastname(email, new_lastname) == 1:
			print "CHANGED LASTNAME"
		else:
			print "NONEXISTANT"
		if change_email(email, new_email) == 1:
			print "CHANGED EMAIL"
		else:
			print "NONEXISTANT"
		if change_rider_status(email, new_rider_status) == 1:
			print "CHANGED RIDER"
		else:
			print "NONEXISTANT"
		if change_driver_status(email, new_driver_status) == 1:
			print "CHANGED DRIVER"
		else:
			print "NONEXISTANT"
		if change_admin_status(email, new_admin_status) == 1:
			print "CHANGED ADMIN"
		else:
			print "NONEXISTANT"
		if change_ban_status(email, new_ban_status) == 1:
			print "CHANGED BAN"
		else:
			print "NONEXISTANT"
	
if __name__ == "__main__":
	main(sys.argv[1:])
