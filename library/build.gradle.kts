plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.hk"
version = "v1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}