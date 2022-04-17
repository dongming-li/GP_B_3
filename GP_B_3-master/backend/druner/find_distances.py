# this is a backend script that finds the distance between two points
# later it will prob do more, but for now think of it like a little proof of concept thing
import math


# returns the distance between two points given the latitude and longitude
# default is to do kilometers, otherwise it does miles
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

    print radius * (math.acos(1 - (tunnel_distance**2 / (2*radius**2)))) * (.001 if wants_kilometers else 0.0006213712)


distance(5, 24, 13, 32, False)
