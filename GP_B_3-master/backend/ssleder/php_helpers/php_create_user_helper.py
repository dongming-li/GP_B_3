##
# @file php_create_user_helper.py
# @author Steven Sleder
# Interface between the PHP and Python handlers to create an account.

#!/usr/bin/python
import sys
import MySQLdb
import json
from create_user import create_user

## @brief Acts as the callable interface from the PHP POST handler
# This interfaces between the PHP frontend for the server and the Python
# database handlers
# @param argv Contains the JSON of account information to be used in creating
# a new account
# @return void
def main(argv):

	user_json = json.loads(get_user_info(sys.argv[1]))

	print user_json["UID"]

if __name__ == "__main__":
	main(sys.argv[1:])
