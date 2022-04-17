##
# @file join_and_leave_rides.py
# @author Steven Sleder
# @brief This file handles all database interfaces with joining and leaving rides as a user.

#!/usr/bin/python

import MySQLdb
import json

## @brief This method removes a user from a ride
# This method directly interfaces with the database via the MySQLdb package to
# remove a user from a single ride.
# @param ride_ID The ride ID associated with a unique ride.
# @param email  The email address associated with the account to remove from the ride.
# @return void
def leave_ride(ride_ID, email):
	# TODO: SANITIZE INPUTS, particularly the time since it is a string now...
	#	frontend should already do this, but it is important to be thorough

	# try to open the database connection
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		return

	# create cursor to query database
	cursor = poolPartyDB.cursor()
	
	SQL_command = ("SELECT * FROM PENDINGPOOLS WHERE RIDEID = '" + str(ride_ID) + "'")
	
	cursor.execute(SQL_command)

	rideData = cursor.fetchone()

	SQL_command = ("UPDATE PENDINGPOOLS SET RIDERS = '" + rideData[8].replace(' ' + email, '') + "' WHERE RIDEID ='" + str(ride_ID) + "'")
	cursor.execute(SQL_command)

	# commit changes
	poolPartyDB.commit()
	# close cursor and database connection
	cursor.close()
	poolPartyDB.close()

## @brief This method adds a single user to an existing ride
# This method directly interfaces with the database via the MySQLdb package to
# add user to a single ride.
# @param ride_ID The ride ID associated with a unique ride.
# @param email  The email address associated with the account to remove from the ride.
# @return void
def join_ride(ride_ID, email):
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')

	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	SQL_command = ("SELECT * FROM PENDINGPOOLS WHERE RIDEID = '" + str(ride_ID) + "'")
	
	cursor.execute(SQL_command)

	rideData = cursor.fetchone()

	SQL_command = ("UPDATE PENDINGPOOLS SET RIDERS = '" + rideData[8] + ' ' + email + "' WHERE RIDEID ='" + str(ride_ID) + "'")
	cursor.execute(SQL_command)

	poolPartyDB.commit()

	cursor.close()
	poolPartyDB.close()

## @brief This method finds all riders associated with a particular ride.
# This method directly interfaces with the database via the MySQLdb package to
# get all riders in a single ride
# @param ride_ID The ride ID associated with a unique ride.
# @return void
def get_riders(ride_ID):
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
		passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
			db='db309gpb3')

	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	SQL_command = ("SELECT RIDERS FROM PENDINGPOOLS WHERE RIDEID = '" + str(ride_ID) + "'")
	cursor.execute(SQL_command)

	return cursor.fetchone()[0]
