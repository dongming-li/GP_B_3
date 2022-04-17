## 
# @file get_all_emails.py
# This method returns all email addresses in the database.

#!/usr/bin/python

import MySQLdb
import json

def main():
	get_all_emails()

## @brief Returns an arrray of all email addresses in the databasze
# This function directly interfaces with the database to retrieve all email addresses
# associated with a user account
# @return email_list Array of all emails in the database.
def get_all_emails():
	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	#try:
	emails_command = ("SELECT EMAIL FROM USERINFO")
	cursor.execute(emails_command)


	email_list = json.dumps({"emails": cursor.fetchall()})

	cursor.close()
	poolPartyDB.close()

	return email_list

main()
