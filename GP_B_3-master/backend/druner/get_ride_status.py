#!/usr/bin/python

# prints off the status of the rides in the users queue

import cgi #lets the script be run as a website
import cgitb # lets the script display debugging info
import json # everything needs a format
from dolater import check
from dolater import get_status

cgitb.enable() # enable debugging

FORM = cgi.FieldStorage() # get the POST data form

print "Content-Type: text/html"     # HTML is following
print ""                            # blank line, end of headers

if check(form["username"], form["password"]):
	print get_status(form["username"])
