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
    implementation(kotlin("stdlib"))

    // Validation
    implementation("io.konform:konform:${findProperty("konformVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}