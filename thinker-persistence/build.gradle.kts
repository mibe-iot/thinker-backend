plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

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

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    dependsOn(":thinker-domain:build")
}
