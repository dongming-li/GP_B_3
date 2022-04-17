import socket
import sys


try:
    #                        AF_INET (IPv4)          STREAM (TCP)
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error, msg:
    print 'Failed to create socket. Error code: ' + str(msg[0]) + ' , Error message : ' + msg[1]
    sys.exit()
print 'Socket Created'







def connect_socket(the_socket, host, port):
    try:
        ip = socket.gethostbyname(host)
        the_socket.connect((ip , port))
        print "connected socket to port:", port, "of host", host, "at ip:", ip
    except socket.gaierror:
        #could not resolve
        print 'Hostname could not be resolved. Exiting'
    

connect_socket(my_socket, "www.apple.com", 80)


#Send some data to remote server
message = "GET / HTTP/1.1\r\n\r\n"
 
try :
    #Set the whole string
    my_socket.sendall(message)
except socket.error:
    #Send failed
    print 'Send failed'
    sys.exit()
 
print 'Message send successfully'

#Now receive data
reply = my_socket.recv(10000)
 
print reply





