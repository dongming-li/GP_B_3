import MySQLdb
import json
import math
import sys

def distance(lat1, long1, lat2, long2, wants_kilometers=True):
    radius = 6371000

    theta1 = (lat1+90) / 180.0 * math.pi
    phi1 = (long1+180) / 180.0 * math.pi

    theta2 = (lat2+90) / 180.0 * math.pi
    phi2 = (long2+180) / 180.0 * math.pi

    # calculate x, y, and z 1
    x1 = radius*math.sin(theta1)*math.cos(phi1)
    y1 = radius*math.sin(theta1)*math.sin(phi1)
    z1 = radius*math.cos(theta1)

    # calculate x, y, and z 2
    x2 = radius*math.sin(theta2)*math.cos(phi2)
    y2 = radius*math.sin(theta2)*math.sin(phi2)
    z2 = radius*math.cos(theta2)

    # calculate the distance between them
    tunnel_distance = ((x1-x2)**2 + (y1-y2)**2 + (z1-z2)**2)**0.5

    return radius * (math.acos(1 - (tunnel_distance**2 / (2*radius**2)))) * (.001 if wants_kilometers else 0.0006213712)



def get_all_rides():
	try:
		poolPartyDB = MySQLdb.connect(user='dbu309gpb3',
			passwd='rQ@x3B0T', host='mysql.cs.iastate.edu',
				db='db309gpb3')
		print "\nConnection to server successful.\n"

	except:
		print "ERROR CONNECTING"
	# create cursor to query database
	cursor = poolPartyDB.cursor()

	command_base = ("SELECT * FROM PENDINGPOOLS")
	cursor.execute(command_base)

	rows = cursor.fetchall()

	ride_array = []

	for index, row in enumerate(rows):
		print row
		ride_dictionary = {"time": row[0], "start_lat": row[1], "start_long": row[2], "end_lat": row[3], "end_long": row[4], "max_passengers": row[5], "driver_UID": row[6], "riders": row[7]}
		ride_array.append(ride_dictionary)
	return ride_array


	cursor.close()
	poolPartyDB.close()

all_rides = get_all_rides()
requested_ride = json.loads(sys.argv[1])

rated_rides = {}
for ride in all_rides:# 																																												                                    # TODO change this later, we are not sure what format timing is in
	rated_rides[ride] = distance(ride["start_lat"], ride["start_long"], requested_ride["start_lat"], requested_ride["start_long"]) + distance(ride["end_lat"], ride["end_long"], requested_ride["end_lat"], requested_ride["end_long"]) + abs(ride["time"] - requested_ride["time"])

print rated_rides






