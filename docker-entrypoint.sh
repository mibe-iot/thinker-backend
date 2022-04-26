#!/bin/bash
# start services
sudo service dbus stop
sudo service dbus start
sudo service bluetooth stop
sudo service bluetooth start

# wait for startup of services
msg="Waiting for services to start..."
time=0
echo -n $msg
while [[ "$(pidof start-stop-daemon)" != "" ]]; do
    sleep 1
    time=$((time + 1))
    echo -en "\r$msg $time s"
done
echo -e "\r$msg done! (in $time s)"

# reset bluetooth adapter by restarting it
sudo hciconfig hci0 down
sudo hciconfig hci0 up
sudo ln -s java /usr/bin/java
which java

# start application
sudo /opt/java/openjdk/bin/java -jar thinker.jar