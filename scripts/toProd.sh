#!/usr/bin/env bash

echo 'Copy files...'
scp -i ~/.ssh/id_rsa.pub \
    target/dumpBot-0.0.1-SNAPSHOT.jar \
    root@85.193.82.129:/home/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa.pub root@85.193.82.129 << EOF
pgrep java | xargs kill -9
nohup java -jar dumpBot-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'