#!/usr/bin/python

import MySQLdb

def main():

    #connect to the database (info from RedHat Server file at root)
    try:
        databaseConnection = MySQLdb.connect(host="mysql.cs.iastate.edu",
                user="dbu309gpb3", passwd="rQ@x3B0T",db="db309gpb3")
        print("\nConnection to server successful.\n")

    except:
        print("\nConnection to server failed.\n")

    #create a cursor object (see? python IS object oriented!)
    cursor = databaseConnection.cursor()

    while True:

        #get user input for a SQL command, sanitize inputs at some point
        command = input("Enter a command, q to exit: ")

        print("\nYou entered:", command, "\n")

        #check if user wants to quit
        if command == "q":
            print("\nClosing connection to server.\n")
            databaseConnection.close()
            exit()
        else:
            cursor.execute(command)


main()
