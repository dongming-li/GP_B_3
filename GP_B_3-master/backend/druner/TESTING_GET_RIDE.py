#!/usr/bin/python

# this is a CGI script which takes data as a POST request
# and adds the data to the user database

import cgi #lets the script be run as a website
import cgitb # lets the script display debugging info
import json # everything needs a format
import urllib


from find_distances import distance # get distances between two points on Earth


cgitb.enable() # enable debugging

FORM = cgi.FieldStorage() # get the POST data form

print "Content-Type: text/html"     # HTML is following
print ""                            # blank line, end of headers

# get time, start_location, end_location, num_seats
time = FORM["time"]
num_seats = FORM["num_seats"]

KEY = "AIzaSyChLkxPwhSYUEegZop-Nt19aOeZ69E-zSE"
start_location = json.loads(urllib.urlopen("https://maps.googleapis.com/maps/api/geocode/json?address="+FORM["start_location"]+"&key="+KEY).read())["results"]["geometry"]["location"]
end_location = json.loads(urllib.urlopen("https://maps.googleapis.com/maps/api/geocode/json?address="+FORM["end_location"]+"&key="+KEY).read())["results"]["geometry"]["location"]

def close_time(t1, t2):
	return False

for ride in []:
	if close_time(time, ride["time"]) and distance(start_location["lat"], ride["start"]["lat"], start_location["long"], ride["start"]["long"]) < 20 and distance(start_location["lat"], ride["start"]["lat"], start_location["long"], ride["start"]["long"]) < 20:
		pass


# print json.dumps({
# "Ride_Time": 17.75,
# "Start_Address": "1111 maple st. townsville IA",
# "End_Address": "1112 willow st. citiesville IA",
# "Driver_Name": "Jack",
# })