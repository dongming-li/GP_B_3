#!/usr/bin/python

# this is a CGI script which takes data as a POST request
# and adds the data to the user database
# Steve Says Hi


import cgi #lets the script be run as a website
import cgitb # lets the script display debugging info
import json # everything needs a format
import random

from create_user import create_user # import SQL handler

cgitb.enable() # enable debugging

FORM = cgi.FieldStorage() # get the POST data form

print "Content-Type: text/html"     # HTML is following
print
print """\
<html>
<body>
<h2>add_user.cgi</h2>
</body>
</html>
"""


PASSWORD = FORM["password"].value
FIRST_NAME = FORM["firstname"].value
LAST_NAME = FORM["lastname"].value
#PROFILE_PIC = FORM["img_link"].value
EMAIL = FORM["email"].value
ID = random.randrange(0, 9999) # TODO STEVE: using a random number for now

full_info = {"UID": ID, "firstname": FIRST_NAME, "lastname": LAST_NAME, "email": EMAIL, "driver": 1, "rider": 1, "password": PASSWORD, "admin": 0}



create_user(json.dumps(full_info))
