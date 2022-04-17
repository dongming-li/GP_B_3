##
# @file php_add_rider_helper.py
# @author Steven Sleder
# Program to interface between PHP POST handler and Python database handler
# for creating a new ride. Also handles address translation to long and lat.

#!/usr/bin/python

import sys
import json # everything needs a format
import urllib
from get_user_info import get_user_info
from create_and_find_rides import create_pending

## @brief Main that accepts arguments to ARGV
# This function handles all interface between the PHP POST hanlders
# and Python SQL handlers.
# @param argv[0] Contains email
# @param argv[1] Contains time
# @param argv[2] Contains end_address
# @param argv[3] Contains end_address 
# @param argv[4] Contains max_passengers
# @param argv[5] Contains willing_distance
# @return returns 0 (success), 1 (no account), 2 (Google API error), 3 (database error)
def main(argv):
	email = argv[0]
	time = argv[1]
	start_addr = argv[2]
	end_addr = argv[3]
	max_passengers = argv[4]
	willing_distance = argv[5]
	
	# Google API key
	KEY = "AIzaSyChLkxPwhSYUEegZop-Nt19aOeZ69E-zSE"

	try:
		user_json = json.loads(get_user_info(email))
		try:
			start_addr = json.loads(urllib.urlopen("https://maps.googleapis.com/maps/api/geocode/json?address="+start_addr+"&key="+KEY).read())["results"][0]["geometry"]["location"]
			end_addr = json.loads(urllib.urlopen("https://maps.googleapis.com/maps/api/geocode/json?address="+end_addr+"&key="+KEY).read())["results"][0]["geometry"]["location"]
			try:
				full_info = {"driver_UID" : str(user_json['UID']),
					"time" : time,
					"start_lat" : start_addr['lat'],
					"start_long" : start_addr['lng'],
					"end_lat" : end_addr['lat'],
					"end_long" : end_addr['lng'],
					"max_passengers" : max_passengers,
					"max_radius" : str(willing_distance) }
				create_pending(json.dumps(full_info))
				print "0"
			except:
				print "3" # error forming ride
		
		except:
			print "2" # error with Google API
	except:
		print "1" # error getting UID

if __name__ == "__main__":
	main(sys.argv[1:])

