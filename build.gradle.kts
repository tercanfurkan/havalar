plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "io.havalar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.create("build-all") {
    dependsOn(":havalar-client:copy", ":havalar-backend:buildFatJar")
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}