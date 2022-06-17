# Thinker's back-end

Welcome to the back-end part of the thinker!

Thinker is an application server, that offers you possibility to control **mibe** smart house. Thinker builds smart-home
network upon your LAN/WLAN. Devices are communicating via MQTT and configurations are being transferred using BLE.

Application is developed for Linux systems, designed to run inside container.

See *main* thinker repository to learn more about project: https://github.com/mibe-iot/thinker

See related mibe iot projects to learn about *devices*:

- **IOT device mirror:** https://github.com/mibe-iot/mirror
- **IOT Sample device:** https://github.com/mibe-iot/bit

## Introduction

Thinker is Spring based reactive web server that controls and communicates with smart-home devices.
The process of connecting new device to the network is semi-automatic: it requires user verification.

Devices are able to communicate inside network. System uses MQTT for message transferring. MQTT broker is responsible
for message delivery. Devices support `actions` - an MQTT endpoint via which back-end could call some actions.

All possible ways to control smart house network are exposed via **api** endpoints.

## Device connection contract

Thinker application is responsible for connecting iot devices to the iot-network. Mibe-iot uses **Bluetooth** for device
connection and configuration.

Thinker uses these UUIDs for service and characteristics identification:

| Service name                | UUID                                 |
|-----------------------------|--------------------------------------|
| BIT_SERVICE                 | 29d9d932-8a5f-11ec-a8a3-0242ac120002 |

| Characteristic name         | UUID                                 |
|-----------------------------|--------------------------------------|
| BIT_CHARACTERISTIC_NAME     | 70f3674a-8a62-11ec-a8a3-0242ac120002 |
| BIT_CHARACTERISTIC_SSID     | 63be641a-8a5f-11ec-a8a3-0242ac120002 |
| BIT_CHARACTERISTIC_PASSWORD | 7385dab8-8a5f-11ec-a8a3-0242ac120002 |

When discovery process is active, thinker seeks for devices nearby. If device exposes `BIT_SERVICE` UUID, this device is
considered as 'known', which means it is expected to support thinker bluetooth connection contract.

Connection contract is simple:

1. Thinker uses bluetooth to pair to the discovered device.
2. If pairing is successful and services are read, thinker verifies that device has service with `BIT_SERVICE` UUID.
3. If everything is all right, thinker tries to write 3 characteristics:
    1. It writes iot-device-id to the `BIT_CHARACTERISTIC_NAME`.
    2. It writes WLAN SSID to `BIT_CHARACTERISTIC_SSID`.
    3. It writes WLAN password to `BIT_CHARACTERISTIC_PASSWORD`.
4. If all characteristics are written successfully, device is considered to be configured, otherwise connection process
   is interrupted.

## Device communication contract

Devices and thinker us MQTT for communication. There are several static topics that are required to be handled by all
devices:

### Device-out message mappings

| Mapping                  | Description                                                                                                                                             |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| /mibe/actions            | Devices **must** send a message containing their name, actions, and supported report types to this endpoint immediately after configuration is complete |
| /mibe/reports/{deviceId} | Devices should send their reports to this topic, with their id inlined into path|

### Device-in message mappings

| Mapping                       | Description                                                                     |
|-------------------------------|---------------------------------------------------------------------------------|
| /mibe/{deviceId}/{actionName} | Devices could accept action-execution requests through this mapping             |
| /mibe/{deviceId}/delete       | This action is being executed by thinker  when user removes device from network |

Action name could be any String, except reserved ones:

* `delete` **Devices must handle this action to properly react to their deletion from the iot-system. This action
  call means this device's id is banned, and it couldn't communicate with thinker anymore. Device could want to turn on
  Bluetooth adapter again after receiving this action call to be able to be reconnected to the iot-network.**

All MQTT communications are in JSON format. There are schemas of possible messages:
- Sharing device data after it is configured is done using following object (`DeviceActionsDataModel`):

    ```json
    {
      "deviceId": "String",
      "deviceClass": "String",
      "actions": [ "actionName1: String", "actionName2: String" ],
      "reportTypes": [ "reportType1: String", "reportType2: String" ]
    }
    ```

- Reports are shared using `DeviceReportModel`:

    ```json
    {
      "reportType": "String",
      "reportData": {
        "key1: String": "value1: Any",
        "key2: String": "value2: Any"
      }
    }
    ```
  
- When Thinker sends an action to device, it uses `ActionInvocation`:

    ```json
    {
      "actionName": "String"
    }
    ```

## System requirements

Application is designed to be run on Linux system. It requires Bluetooth adapter like hci0 to be available for use.
Also, if you run application inside a container, make sure that your `bluetooth` service is turned off. To check
service status execute:

```shell
systemctl status bluetooth
```

If service is `active`, run either `systemctl stop bluetooth` to stop it temporarily or `systemctl disable bluetooth`
to disable this service completely. To start service again run `systemctl start bluetooth` or, to enable it again,
run `systemctl enable bluetooth`.

Also, system requires LAN or WLAN connection to build smart-home network upon it.

## Building application (optional)

If you just need to run application and would like to use docker, see [Running application](#running-application)

Back-end uses Gradle as build system. To use gradle you need java to be installed on your system and `JAVA_HOME`
env variable to be set. To build application from sources, run:

```shell
./gradlew bootJar
```

This will build a ready-to-launch jar, that you can find inside `./build/libs`.

### List of docker image-specific files:

- [bluezuser.conf](./bluezuser.conf) - linux BlueZ user conf that adds user access to bluetooth hardware.
- [.dockerignore](./.dockerignore) - docker ignore file for all build-unrelated files.
- [docker-entrypoint.sh](./docker-entrypoint.sh) - docker entrypoint. Manages dbus and bluetooth services on container
  start, resets hci0 adapter and then runs thinker jar.
- [Dockerfile](./Dockerfile) - docker file for x64 systems
- [Dockerfile-arm](./Dockerfile-arm) - docker file for ARM systems

## Running application

> *Please notice that application **requires** MQTT broker to be run on port 1883, bluetooth adapter
> (see [requirements](#system-requirements)) and mongo DB on port 27017 to run normally in `default` Spring profile.
> You could also run it using `dev` profile with limited functionality. It will still require mongoDB, but it won't
> search for MQTT broker and bluetooth adapter. Ports could be configured in `application-default.yml`.
> To set active profile set `SPRING_PROFILES_ACTIVE` env variable.*

### Running application using docker

You don't need to build application from sources to run it. You can use docker. Both front-end and back-end apps are
designed to run inside container. **!Because of bluetooth usage, all application are configured to run in `host`
network mode!** If you are interested in running the whole iot-system (front-end, back-end, MQTT broker
and mongo DB) **on ARM-based hardware like Raspberry PI**, see
[thinker's docker-compose script](https://github.com/mibe-iot/thinker/blob/master/docker-compose.yml). This compose
file uses special front-end and back-end images built for ARM architecture.

To run application inside docker container, use this image: https://github.com/mibe-iot/thinker-backend/pkgs/container/thinker-backend

Use tag `arm` for ARM-based systems and tag `latest` or `x64` for x64 systems.

### Running application from sources

To run application from sources you could execute:

```shell
./gradlew bootRun
```

Or you can run jar, [built previously](#building-application-optional):

```shell
java -jar ./build/libs/thinker-*version*.jar
```

By default, application runs on port `8080`.

## Technologies used

- **Kotlin 1.6** - application logic is written with help of Kotlin language. It also utilizes several Kotlin-specific 
  features:
  - Coroutines - since server is non-blocking, coroutines help make asynchronous code more readable.
  - Null safety.
  - Kotlin SDK and extension functions.
  - Data classes.
  - See more about Kotlin: https://kotlinlang.org/
- **Gradle** - project build tool. Official website: https://gradle.org/
- **Spring Boot/Spring framework** - It is no-doubts the today's leader among all other framework that offer you a
  possibility to build web-server that will run on JVM. Spring modules used:
    - **Spring WebFlux** - offers you a possibility to build non-blocking web server.
    - **Spring Data MongoDB Reactive** - nice abstraction layer between your business-logic and data access layer. This
      starter also implements this layer as a non-blocking one.
    - **Spring Boot Validation** - validation, used by Konform library.
    - See all Spring projects: https://spring.io/projects/spring-framework
- **Mongo DB** as the NoSQL data storage implementation. Official website: https://www.mongodb.com/
- **Blessed for Bluez** - library that implements a high-level interface, through which your app is able to communicate with
  Bluetooth network. See library's repository: https://github.com/weliem/blessed-bluez
- **HiveMQ MQTT client** and **smartsquare's MQTT Spring Boot starter** - client and starter that let your application
  communicate with MQTT broker. 
  - HiveMQ MQTT Client repository: https://github.com/hivemq/hivemq-mqtt-client 
  - Smartsquare HiveMQ Mqtt client starter repository: https://github.com/SmartsquareGmbH/mqtt-starter

## Contacts:

Feel free to reach me out in case of any question:

**Linked-in:** https://www.linkedin.com/in/ilya-buhlakou-33860b205/

**Mail:** ilboogl@gmail.com
