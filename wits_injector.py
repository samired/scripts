#!/usr/bin/python
# -*- coding= utf-8 -*-
#
# Name: wits_injector.py
#    This file is part of Mud Log Tools.
#
# Description:
#        This file is a TCP WITS server to inject predefined data.
#        (Wellsite Information Transfer System, Level0)
#
# Author:
#         Abdellah Chelli, 2009-2010
#

import threading
import SocketServer

import time
import sys

import getopt
import logging

# Info functions
def usage():
    print "Usage: python wits_injector.py [options]\n", "Options:\n" "\t-i --input inputfile\n", "\t-h --help\n", "\t-p --port portnumber (default=900)\n", "\t-t --timer periodinsec (default=3)\n", "\t-v --version\n"

def version():
    print "\nwits_injector.py\n"

# Server Class
class tcpServerHandler(SocketServer.BaseRequestHandler):
    SOCKETNB = 0
    def setup(self):
        tcpServerHandler.SOCKETNB=tcpServerHandler.SOCKETNB+1
        logger.info("Socket ready for:" + self.client_address[0] + " (" + str(tcpServerHandler.SOCKETNB) + " skt)" )

    def handle(self):
        logger.info(self.client_address[0] + ' connected.')
        while True:
            try:
                s = self.request.recv(1024)
                print s
                self.request.send('\r\n')

            except:
                logger.info(self.client_address[0] + ' disconnected!')
                break
            time.sleep(INTERVAL)

    def finish(self):
        tcpServerHandler.SOCKETNB=tcpServerHandler.SOCKETNB-1
        logger.info("Socket closed by:" + self.client_address[0] + " (" + str(tcpServerHandler.SOCKETNB) + " skt)")

class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    pass

# Main
if __name__ == "__main__":

    # Show version
    version()

    # Constants
    LOG_FILENAME = "wits_injector.log"
    LOG_FORMAT = "%(asctime)s-%(name)s-%(levelname)s-%(message)s"

    # Varaibles
    HOST, PORT, INTERVAL= "", 900, 1
    PARAMETERSFILE = "wits_equation.txt"
    global SOCKETNB
    SOCKETNB = 0

    # Log setup
    logging.basicConfig(filename=LOG_FILENAME,level=logging.DEBUG, format=LOG_FORMAT)
    global logger
    logger = logging.getLogger("Server")
    # create console handler and set level to debug
    ch = logging.StreamHandler()
    ch.setLevel(logging.DEBUG)
    # create formatter
    formatter = logging.Formatter(LOG_FORMAT)
    # add formatter to ch
    ch.setFormatter(formatter)
    # add ch to logger
    logger.addHandler(ch)

    logger.info( "Server up.")

    # Options parsing
    try:
        opts, args = getopt.gnu_getopt(sys.argv[1:], "i:p:t:hv", ["input=", "port=", "timer=", "help", "version"])
    except getopt.GetoptError, err:
        print str(err)
        usage()
        sys.exit(2)

    for o, a in opts:
        if o in ( "-v", "--version" ):
            version()
            sys.exit()
        elif o in ( "-h", "--help" ):
            usage()
            sys.exit()
        elif o in ( "-i", "--input" ):
            parameterFilePath = a
        elif o in ( "-p", "--port" ):
            PORT = int(a)
        elif o in ( "-t", "--timer" ):
            INTERVAL = int(a)
        else:
            assert False, " Unknown option"

    # Loading data file
    try:
        FILE = open( PARAMETERSFILE, 'r' )
    except:
        logger.error( "Couldn't open: " + PARAMETERSFILE )
        sys.exit(4)
    PARAMETERS = FILE.read()
    FILE.close()
    PARAMETERS=PARAMETERS.replace("\r\n","\n")
    PARAMETERS=PARAMETERS.replace("\n\r","\n")
    PARAMETERS=PARAMETERS.replace("\r","\n")
    PARAMETERS=PARAMETERS.replace("\n","\r\n")

    # Loading server
    try:
        server = ThreadedTCPServer( (HOST, PORT), tcpServerHandler )
        server_thread = threading.Thread( target=server.serve_forever )
        server_thread.setDaemon( True )
        server_thread.start()
    except:
        logger.error( "Server couldn't start!" + " Port:" + str(PORT) + " seems to be in use." )
        sys.exit(3)
    logger.info( "Server loop:" + server_thread.getName() + " Port:" + str(PORT) )

    # Closing application
    raw_input()
    logger.info("Server down!")
    sys.exit(0)
