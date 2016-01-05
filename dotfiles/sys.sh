#!/bin/bash
##################################################
DATE=`date +%m/%d/%Y`
TIME=`date +%k:%M:%S`
USERS=`uptime | sed 's/users.*$//' | gawk '{print $NF}'`
LOAD=`uptime | gawk '{print $NF}'`
FREE=`vmstat 1 2 | sed -n '/[0-9]/p' | sed -n '2p' | gawk '{print $4}'`
IDLE=`vmstat 1 2 | sed -n '/[0-9]/p' | sed -n '2p' | gawk '{print $15}'`
#########################################
# Send Statistics
#########################################
echo "=================="
echo "DATE: ==> $DATE"
echo "TIME: ==> $TIME"
echo "USERS: ==> $USERS"
echo "LOAD: ==> $LOAD"
echo "FREE: ==> $FREE"
echo "IDLE: ==> $IDLE"
echo "=================="

Systeminfo | Find "Up Time"