##
# @file create_and_find_rides.py
# Used to create and pull records of rides.

#!/usr/bin/python

import MySQLdb
import json


def test_create_pending():
	test_account = {"time": "9:09:09:00", "start_lat": 0, "start_long": 0, "end_lat": 0, "end_long": 0, "max_passengers": 3, "driver_UID": 1, "max_radius" : 1}
	create_pending(json.dumps(test_account))
	print "Added Pending Ride:"
	print test_account

	test_account = {"time": "10:00:00:00", "start_lat": 0, "start_long": 0, "end_lat": 0, "end_long": 0, "max_passengers": 3, "driver_UID": 99, "max_radius" : 1}
	create_pending(json.dumps(test_account))
	print "Added Pending Ride:"
	print test_account


	test_account = {"time": "11:11:11:00", "start_lat": 0, "start_long": 0, "end_lat": 0, "end_long": 0, "max_passengers": 3, "driver_UID": 100, "max_radius" : 1}
	create_pending(json.dumps(test_account))
	print "Added Pending Ride:"
	print test_account

## @brief Creates a single ride.
# This function directly interfaces with the database to create rides.
# @param input_json JSON in the format {'time', 'start_lat', 'start_long', 'end_lat', 'end_long', 'max_passengers', 'driver_UID', 'max_radius'}
# @return void
def create_pending(input_json):
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

	# break the json into an dictionary
	info_json = json.loads(input_json)

	# makes array of input json
	pool_info = (str(info_json["time"]), str(info_json["start_lat"]), str(info_json["start_long"]), str(info_json["end_lat"]), str(info_json["end_long"]), str(info_json["max_passengers"]), str(info_json["driver_UID"]), "", str(info_json["max_radius"]))
	
	# base string for executing the command
	create_pool_base = ("INSERT INTO PENDINGPOOLS (LEAVES, SLAT, SLON, ELAT, ELON, NUMRIDERS, DRIVERUID, RIDERS, WILLINGRANGE) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)")

	# executes the command
	cursor.execute(create_pool_base, pool_info)

	# commit changes
	poolPartyDB.commit()
	# close cursor and database connection
	cursor.close()
	poolPartyDB.close()

## @brief Fetches a list of all current rides.
# This function directly interfaces with the database to return all records of rides
# @return void
def fetch_pending():
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	# pull all records
	# TODO: potentially change this to select based off
	# of a range, or let calling method sort them and
	# don't change anything in here
	command_base = ("SELECT * FROM PENDINGPOOLS")

	# execute the command
	cursor.execute(command_base)

	# pull all rows out of the cursor, also lets me close cursor
	rows = cursor.fetchall()
	
	# dump row into json format

	"""
	This legacy code is if we return to using a read DATETIME for "TIME" in the SQL database,
	presently, JSON doesn't play nice so we are instead storing times as a string.
	"""
	# to hold all rides pulled
	ride_array = []

	for index, row in enumerate(rows):
		ride_dictionary = {"ride_ID": str(row[0]),"time": str(row[1]), "start_lat": row[2], "start_long": row[3], "end_lat": row[4], "end_long": row[5], "max_passengers": row[6], "driver_UID": row[7], "riders": row[8], "max_radius": str(row[9])}
		ride_array.append(ride_dictionary)
	
	cursor.close()
	poolPartyDB.close()

	return json.dumps(ride_array)

## @brief Fetches a list of all current rides.
# This function directly interfaces with the database to return all records
# associated with a particular user email as the driver
# @param email The email address of the user to check for driver records.
# @return void
def fetch_email_driver(email):
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	SQL_command = ("SELECT UID FROM USERINFO WHERE EMAIL = '" + email + "'")
	cursor.execute(SQL_command)

	user_UID = cursor.fetchone()


	# pull all records
	# TODO: potentially change this to select based off
	# of a range, or let calling method sort them and
	# don't change anything in here
	command_base = ("SELECT * FROM PENDINGPOOLS WHERE DRIVERUID = '" + str(user_UID[0]) + "'")

	# execute the command
	cursor.execute(command_base)

	# pull all rows out of the cursor, also lets me close cursor
	rows = cursor.fetchall()
	
	# dump row into json format

	"""
	This legacy code is if we return to using a read DATETIME for "TIME" in the SQL database,
	presently, JSON doesn't play nice so we are instead storing times as a string.
	"""
	# to hold all rides pulled
	ride_array = []

	for index, row in enumerate(rows):
		ride_dictionary = {"ride_ID": str(row[0]),"time": str(row[1]), "start_lat": row[2], "start_long": row[3], "end_lat": row[4], "end_long": row[5], "max_passengers": row[6], "driver_UID": row[7], "riders": row[8], "max_radius": str(row[9])}
		ride_array.append(ride_dictionary)
	
	cursor.close()
	poolPartyDB.close()

	return json.dumps(ride_array)

## @brief Fetches a list of all current rides.
# This function directly interfaces with the database to return all records
# associated with a particular user email as the rider.
# @param email The email address of the user to check for rider records.
# @return void
def fetch_email_rider(email):
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	# pull all records
	# TODO: potentially change this to select based off
	# of a range, or let calling method sort them and
	# don't change anything in here
	command_base = ("SELECT * FROM PENDINGPOOLS")

	# execute the command
	cursor.execute(command_base)

	# pull all rows out of the cursor, also lets me close cursor
	rows = cursor.fetchall()

	# dump row into json format

	"""
	This legacy code is if we return to using a read DATETIME for "TIME" in the SQL database,
	presently, JSON doesn't play nice so we are instead storing times as a string.
	"""
	# to hold all rides pulled
	ride_array = []

	for index, row in enumerate(rows):
		if row[8].find(email) != -1:
			ride_dictionary = {"ride_ID": str(row[0]),"time": str(row[1]), "start_lat": row[2], "start_long": row[3], "end_lat": row[4], "end_long": row[5], "max_passengers": row[6], "driver_UID": row[7], "riders": row[8], "max_radius": str(row[9])}
			ride_array.append(ride_dictionary)
	
	cursor.close()
	poolPartyDB.close()

	return json.dumps(ride_array)

## @brief Deletes a single ride.
# This function deletes a single ride from the database.
# @param ride_ID The unique ID associated with a ride used to select which to delete from the database.
# @return void
def delete_ride(ride_ID):
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
	except:
		return
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	# pull all records
	# TODO: potentially change this to select based off
	# of a range, or let calling method sort them and
	# don't change anything in here
	command_base = ("DELETE FROM PENDINGPOOLS WHERE RIDEID= '" + str(ride_ID) + "'")

	# execute the command
	cursor.execute(command_base)
	
	poolPartyDB.commit()
	cursor.close()
	poolPartyDB.close()
