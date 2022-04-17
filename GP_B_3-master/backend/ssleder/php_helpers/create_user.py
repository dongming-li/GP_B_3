## @file create_user.py
# This program creates a single new user account.

#!/usr/bin/python

import MySQLdb
import json

## @brief Creates a new user account
# This function directly calls on the database to create a new user account.
# @param input_json This JSON input contains the fields {'UID', 'firstname', 'lastname', 'email', 'driver', 'rider'} that is used to create a new user.
# @return void
def create_user(input_json):

	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
		print "\nConnection to server successful.\n"

	except:
		print "ERROR CONNECTING"
	cursor = poolPartyDB.cursor()

	info_json = json.loads(input_json)

	userInfoArgs = (info_json["UID"], info_json["firstname"], info_json["lastname"], info_json["email"], 0, info_json["driver"], info_json["rider"], "5", "1", "0")

	insertUserInfo = ("INSERT INTO USERINFO (UID, FIRSTNAME, LASTNAME, EMAIL, PREMIUM, DRIVER, RIDER, RATING, NUMRATES, NUMTRIPS ) "
				"VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)")
	cursor.execute(insertUserInfo, userInfoArgs)	

	userLoginArgs = (info_json["UID"], info_json["password"], info_json["admin"], 0)
	insertUserLogin = ("INSERT INTO USERLOGIN(UID, PASSWORD, ADMIN, BAN) VALUES(%s, %s, %s, %s)")
	cursor.execute(insertUserLogin, userLoginArgs)

	# commit changes
	poolPartyDB.commit()
	# close cursor and database connection
	cursor.close()
	poolPartyDB.close()

def test_create_user():
	pre_json = {"UID": 1, "firstname": "tester", "lastname": "mcgee", "email": "tester@mcgee.com", "driver": 1, "rider": 1, "password": "password", "admin": 1}
	create_user(json.dumps(pre_json))
	pre_json = {"UID": 1, "firstname": "tester", "lastname": "mcgee", "email": "dummyemail@iastate.edu", "driver": 1, "rider": 1, "password": "password", "admin": 1}
	create_user(json.dumps(pre_json))

	print "test_create() ran create_user()"
