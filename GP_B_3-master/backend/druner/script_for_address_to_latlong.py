#!/usr/bin/python

# this is a CGI script which takes data as a POST request
# and adds the data to the user database

import cgi #lets the script be run as a website
import cgitb # lets the script display debugging info
import json # everything needs a format
import urllib

cgitb.enable() # enable debugging

FORM = cgi.FieldStorage() # get the POST data form

print "Content-Type: text/html"     # HTML is following
print ""                            # blank line, end of headers

address = FORM["address"].value
KEY = "AIzaSyChLkxPwhSYUEegZop-Nt19aOeZ69E-zSE"

print json.dumps(json.loads(urllib.urlopen("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+KEY).read())["results"]["geometry"]["location"])