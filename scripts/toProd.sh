#!/usr/bin/env bash

#echo 'Copy files...'
#scp -i ~/.ssh/id_rsa.pub \
#    /home/vilkov/IdeaProjects/dumpbot/target/dumpBot-0.0.1-SNAPSHOT.jar \
#    root@85.193.82.129:/home/server

ssh -i ~/.ssh/id_rsa.pub root@85.193.82.129 'pkill -f dumpBot-0.0.1-SNAPSHOT.jar'
ssh -i ~/.ssh/id_rsa.pub root@85.193.82.129 'java -jar -Dspring.profiles.active=prod /home/server/dumpBot-0.0.1-SNAPSHOT.jar > log.txt &'
EOF
echo 'Bye'