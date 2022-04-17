##
# @file change_user.py
# @author Steven Sleder
# This method handles all modifications in the database for user accounts.

#!/usr/bin/python

import MySQLdb
import json

## @brief Deletes a single user account.
# This function deletes a single user account from the database
# entirely after looking up its UID.
# @param email The email of the user account to be deleted.
# @return returns a 1 on success, 0 on failure
def delete_user(email):
	## @brief Deletes a single user account.
	# This function deletes a single user account from the database
	# entirely after looking up its UID.
	#
	# @param email The email of the user account to be deleted.
	# @return returns a 1 on success, 0 on failure
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		get_UID = ("SELECT UID FROM USERINFO WHERE EMAIL = '" + email + "'")
		cursor.execute(get_UID)
		UID = cursor.fetchone()


		#delete entry from USERINFO
		delete_USERINFO = ("DELETE FROM USERINFO WHERE EMAIL = '" + email + "'")
		cursor.execute(delete_USERINFO)
	
		# delete entry from USERLOGIN
		delete_USERLOGIN = ("DELETE FROM USERLOGIN WHERE UID = '" + str(UID[0]) + "'")
		cursor.execute(delete_USERLOGIN)
	
		try:
			# delete all of user's PENDINGPOOLS
			delete_PENDINGPOOLS = ("DELETE FROM PENDINGPOOLS WHERE DRIVERUID = '" + str(UID[0]) + "'")
			cursor.execute(delete_PENDINGPOOLS)
		except:
			print "No PENDINGPOOLS"
	
		try:
			# delete all of user's ACTIVEPOOLS
			delete_ACTIVEPOOLS = ("DELETE FROM ACTIVEPOOLS WHERE DRIVERUID = '" + str(UID[0]) + "'")
			cursor.execute(delete_ACTIVEPOOLS)
		except:
			print "No ACTIVEPOOLS"
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the password of a given user.
# This function modifies the password of a given account by
# fetching the UID and using it to change the password field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param password The new password for the account.
# \return 1 on success, 0 on failure
def change_password(email, password):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		get_UID = ("SELECT UID FROM USERINFO WHERE EMAIL = '" + email + "'")
		cursor.execute(get_UID)
		UID = cursor.fetchone()

		change_PASSWORD = ("UPDATE USERLOGIN SET PASSWORD = '" + password + "' WHERE UID = '" + str(UID[0]) + "'")
		cursor.execute(change_PASSWORD)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the admin status of a given user.
# This function modifies the first name of a given account by
# fetching the UID and using it to change the firstname field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param firstname The new first name to be associated with the account.
# \return 1 on success, 0 on failure
def change_firstname(email, firstname):

	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		change_FIRSTNAME = ("UPDATE USERINFO SET FIRSTNAME = '" + firstname + "' WHERE EMAIL = '" + email + "'")
		cursor.execute(change_FIRSTNAME)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the admin status of a given user.
# This function modifies the last name of a given account by
# fetching the UID and using it to change the lastname field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param lastname The new last name to be associated with the account.
# \return 1 on success, 0 on failure
def change_lastname(email, lastname):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		change_LASTNAME = ("UPDATE USERINFO SET LASTNAME = '" + lastname + "' WHERE EMAIL = '" + email + "'")
		cursor.execute(change_LASTNAME)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the email address of a given user.
# This function modifies the email address of a given account by
# fetching the UID and using it to change the email field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param new_email The new email address to be associated with the account.
# \return 1 on success, 0 on failure
def change_email(email, new_email):i
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		change_EMAIL = ("UPDATE USERINFO SET EMAIL = '" + new_email + "' WHERE EMAIL = '" + email + "'")
		cursor.execute(change_EMAIL)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the rider status of a given user.
# This function modifies the admin status of a given account by
# fetching the UID and using it to change the rider field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param status The new rider status of the account.
# \return 1 on success, 0 on failure
def change_rider_status(email, status):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		change_RIDER = ("UPDATE USERINFO SET RIDER = '" + str(status) + "' WHERE EMAIL = '" + email + "'")
		cursor.execute(change_RIDER)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the driver status of a given user.
# This function modifies the driver status of a given account by
# fetching the UID and using it to change the admin field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param status The new driver status of the account.
# \return 1 on success, 0 on failure
def change_driver_status(email, status):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		change_DRIVER = ("UPDATE USERINFO SET DRIVER = '" + str(status) + "' WHERE EMAIL = '" + email + "'")
		cursor.execute(change_DRIVER)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the admin status of a given user.
# This function modifies the admin status of a given account by
# fetching the UID and using it to change the admin field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param status The new administrator status of the account.
# \return 1 on success, 0 on failure
def change_admin_status(email, status):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		get_UID = ("SELECT UID FROM USERINFO WHERE EMAIL = '" + email + "'")
		cursor.execute(get_UID)
		UID = cursor.fetchone()

		change_ADMIN = ("UPDATE USERLOGIN SET ADMIN = '" + str(status) + "' WHERE UID = '" + str(UID[0]) + "'")
		cursor.execute(change_ADMIN)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val

## \brief Changes the ban status of a given user.
# This function modifies the ban status of a given account by
# fetching the UID and using it to change the ban field 
# in the database.
#
# \param email The email of the user account to be modified.
# \param status The new status of the account's ban.
# \return 1 on success, 0 on failure
def change_ban_status(email, status):
	ret_val = 1;

	try:
		poolPartyDB=MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		print "ERROR CONNECTING"

	cursor = poolPartyDB.cursor()

	try:
		get_UID = ("SELECT UID FROM USERINFO WHERE EMAIL = '" + email + "'")
		cursor.execute(get_UID)
		UID = cursor.fetchone()

		change_BAN = ("UPDATE USERLOGIN SET BAN = '" + str(status) + "' WHERE UID = '" + str(UID[0]) + "'")
		cursor.execute(change_BAN)
	except:
		# return 0 for account not found
		ret_val = 0;	

	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
	return ret_val
