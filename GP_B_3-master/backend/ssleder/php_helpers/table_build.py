#!/usr/bin/python

import MySQLdb

def main():

	#connect to the database (info from RedHat Server file at root)
	try:
		poolPartyDB = MySQLdb.connect(host="mysql.cs.iastate.edu",
				user="dbu309gpb3", passwd="rQ@x3B0T", db="db309gpb3")
		print "\nConnection to server successful.\n"

	except:
		print "ERROR CONNECTING"
		return

	cursor = poolPartyDB.cursor()


	#stores name of the database
	DBName = "db309gpb3"

	#declare list of tables
	TABLES = {}

	#makes USERINFO table
	TABLES["USERINFO"] = (
		"CREATE TABLE USERINFO ("
		" UID INT NOT NULL,"
		" FIRSTNAME CHAR(30) NOT NULL,"
		" LASTNAME CHAR(30) NOT NULL,"
		" EMAIL CHAR(30) NOT NULL,"
		" PREMIUM BOOLEAN NOT NULL,"
		" DRIVER BOOLEAN NOT NULL,"
		" RIDER BOOLEAN NOT NULL,"
		" RATING TINYINT NOT NULL,"
		" NUMRATES INT NOT NULL,"
		" NUMTRIPS INT NOT NULL)")

	TABLES["USERLOGIN"] = (
		"CREATE TABLE USERLOGIN ("
		" UID INT NOT NULL,"
		" PASSWORD CHAR(30) NOT NULL,"
		" ADMIN BOOLEAN NOT NULL,"
		" BAN BOOLEAN NOT NULL )" )

	TABLES["PENDINGPOOLS"] = ( 
			"CREATE TABLE PENDINGPOOLS ("
			" RIDEID INT KEY NOT NULL AUTO_INCREMENT,"
			" LEAVES CHAR(20) NOT NULL,"
			" SLAT FLOAT NOT NULL,"
			" SLON FLOAT NOT NULL,"
			" ELAT FLOAT NOT NULL,"
			" ELON FLOAT NOT NULL,"
			" NUMRIDERS INT NOT NULL,"
			" DRIVERUID INT NOT NULL,"
			" RIDERS CHAR(200),"
			" WILLINGRANGE FLOAT NOT NULL )" )


	TABLES["ACTIVEPOOLS"] = ( 
			"CREATE TABLE ACTIVEPOOLS ("
			" LEAVES CHAR(10) NOT NULL,"
			" SLAT FLOAT NOT NULL,"
			" SLON FLOAT NOT NULL,"
			" ELAT FLOAT NOT NULL,"
			" ELON FLOAT NOT NULL,"
			" NUMRIDERS INT NOT NULL,"
			" DRIVERUID INT NOT NULL,"
			" RIDERS CHAR(200) )" )

	cursor.execute("DROP TABLE IF EXISTS USERINFO, USERLOGIN, PENDINGPOOLS, ACTIVEPOOLS")

	for k, v in TABLES.iteritems():
		print "Building Table: " + k
		cursor.execute(v)

	cursor.close()
	poolPartyDB.close()

main()
