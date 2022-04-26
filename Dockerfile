FROM gradle:7.4.2-jdk11 as builder

WORKDIR /usr/src/
COPY . .
RUN gradle bootJar

FROM adoptopenjdk/openjdk11:debian as runner

# install bluez related packages
RUN apt-get update && apt-get install -y \
    bluez \
    dbus \
    sudo
 
# setup and build application
WORKDIR /usr/src/app
COPY --from=builder usr/src/build/libs/thinker-*.jar ./thinker.jar

# setup bluetooth permissions
COPY --from=builder usr/src/bluezuser.conf /etc/dbus-1/system.d/
COPY docker-entrypoint.sh .
RUN useradd -m bluezuser \
 && adduser bluezuser sudo \
 && passwd -d bluezuser
RUN chown bluezuser:sudo ./docker-entrypoint.sh
RUN chown bluezuser:sudo .
RUN chmod +x ./docker-entrypoint.sh
USER bluezuser

# setup startup script
CMD ./docker-entrypoint.sh