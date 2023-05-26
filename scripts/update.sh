#!/bin/bash
cd .. &&
cd /home/server/dumpBot/ &&
git pull &&
mvn -B package --file pom.xml &&
cd &&
cd .. &&
cp /home/server/dumpBot/target/dumpBot-0.0.1-SNAPSHOT.jar /home/server/

