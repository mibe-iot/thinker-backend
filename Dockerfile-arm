FROM arm64v8/openjdk:11.0-jdk-buster

# install bluez related packages
RUN apt update
RUN apt-get install -y \
    bluez \
    dbus \
    sudo

# setup bluetooth permissions
COPY ./bluezuser.conf /etc/dbus-1/system.d/

COPY docker-entrypoint.sh .
RUN chmod +x ./docker-entrypoint.sh

# add user
RUN useradd -m bluezuser \
 && adduser bluezuser sudo \
 && passwd -d bluezuser

# add user permissions for run files
RUN chown bluezuser:sudo ./docker-entrypoint.sh
RUN chown bluezuser:sudo .

# copy jar
COPY app/thinker-*.jar /home/bluezuser/thinker.jar

USER bluezuser

# setup startup script
CMD  ["/bin/bash", "-c", "./docker-entrypoint.sh"]