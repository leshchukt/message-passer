#!/bin/bash
clear
echo "---PLAYER TASK WITHIN ONE JAVA PROCESS---"
mvn -q compile
mvn -q exec:java -Dexec.mainClass=threads.Application

echo
echo "---PLAYER TASK IN SEPARATE JAVA PROCESSES---"
mvn -q exec:java -Dexec.mainClass=sockets.Application -Dexec.args="asServer" &
process_id1=$!
mvn -q exec:java -Dexec.mainClass=sockets.Application -Dexec.args="asClient" &
process_id2=$!
wait $process_id1 $process_id2 

echo
echo "---DONE---"
$SHELL
