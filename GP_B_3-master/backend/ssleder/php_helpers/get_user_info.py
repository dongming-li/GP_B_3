##
# @file get_user_info.py
# This file returns all user account information in JSON format.
#!/usr/bin/python

import MySQLdb
import json

## @brief Returns a JSON of user account info.
# This fucntion directly interfaces with the database to pull
# a record of all user account information given a single email.
# @param email The email address associated with the account.
# @return json.dumps(full_info) Returns a json in the format {'UID', 'firstname', 'lastname', 'email', 'premium', 'driver', 'rider', 'rating', 'number_of_ratings', 'number_of_trips', 'password', 'admin', 'ban'}
def get_user_info(email):
	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	#try:
	getUserInfo = ("SELECT * FROM USERINFO WHERE EMAIL = '" + email + "'")
	cursor.execute(getUserInfo)

	resultInfo = cursor.fetchone()

	getUserLogin = ("SELECT * FROM USERLOGIN WHERE UID = '" + str(resultInfo[0]) + "'")
	cursor.execute(getUserLogin)
		
	resultLogin = cursor.fetchone()


	full_info = {"UID": resultInfo[0], "firstname": resultInfo[1], "lastname": resultInfo[2], "email": resultInfo[3], "premium": resultInfo[4], "driver": resultInfo[5], "rider": resultInfo[6], "rating": resultInfo[7], "number_of_ratings": resultInfo[8], "number_of_trips": resultInfo[9], "password": resultLogin[1], "admin": resultLogin[2], "ban": resultLogin[3]}

	cursor.close()
	poolPartyDB.close()


	return json.dumps(full_info)
