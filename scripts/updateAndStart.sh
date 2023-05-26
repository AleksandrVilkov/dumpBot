cd /home/server/dumpBot/ &&
git pull &&
mvn -B package --file pom.xml && cd &&
cd .. &&
kill $(lsof -t -i:442) ;
cp /home/server/dumpBot/target/dumpBot-0.0.1-SNAPSHOT.jar /home/server/ &&
cd /home/server/ &&
nohup java -jar -Dspring.profiles.active=prod -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 dumpBot-0.0.1-SNAPSHOT.jar > log.txt & disown
