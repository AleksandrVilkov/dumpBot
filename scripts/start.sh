#!/bin/bash
cd &&
cd .. &&
kill $(lsof -t -i:442);
cd /home/server/ &&
nohup java -jar -Dspring.profiles.active=prod -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 dumpBot-0.0.1-SNAPSHOT.jar > log.txt & disown

