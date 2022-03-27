import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

val kotestVersion = "4.6.1"
val mockkVersion = "1.12.0"
val embeddedMongodbVersion = "3.0.0"
val hateoasVersion = "1.3.3"
val webmvcVersion = "5.3.9"
val logbackVersion = "1.2.10"
val blessedVersion = "0.61.2"
val openApiVersion = "1.4.3"

allprojects {
    group = "com.mibe.iot"
    version = "0.2"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

subprojects {
    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        enabled = false
    }
}

dependencies {
    implementation(project(":thinker-domain"))
    implementation(project(":thinker-service"))
    implementation(project(":thinker-persistence"))

    // MQTT
    implementation("de.smartsquare:mqtt-starter:0.14.0")
    api("com.hivemq:hivemq-mqtt-client:1.2.2")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // Open API
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$openApiVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$openApiVersion")

    // Cache
    implementation("io.github.reactivecircus.cache4k:cache4k:0.3.0")

    // JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Logging
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.slf4j:slf4j-simple:1.7.32")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Validation
    implementation("io.konform:konform:${findProperty("konformVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // BLE
    implementation("com.github.mibe-iot:blessed-bluez:$blessedVersion")

    // Test
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-assertions-konform:1.0.0")
    testRuntimeOnly("io.kotest:kotest-assertions-core-jvm:$kotestVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")
}
