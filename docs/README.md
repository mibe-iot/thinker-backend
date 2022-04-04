# Thinker
Thinker is an application server, that offers you possibilities to control *mibe* smart house.

Application is developed for Linux systems

**For technical details view [Wiki](https://github.com/mibe-iot/thinker/wiki).**

## Introduction

Thinker is Spring based reactive web server that uses Bluetooth, Mqtt and HTTP to let you control *Bits*. Bits is the term for all slave devices within smart house network. 

All possible ways to control smart house network are exposed via thinker's endpoints. Relative API path is /api/\*. 

[View endpoints](./swagger.html)

## Running application

To run MQTT broker execute `/opt/hivemq/bin/run.sh > /dev/null 2>&1 &`

*TO DO*

## Screenshots

*TO DO*

## Models Explanation

*TO DO*

## Technologies used
- Kotlin 1.6~
- Spring Boot
- Mongo DB
- Bluez
- Blessed for Bluez library
- ...tbc
