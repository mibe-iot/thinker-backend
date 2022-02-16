plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "com.mibe.iot"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":thinker-domain"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // Kotlin
    implementation(kotlin("stdlib"))
}