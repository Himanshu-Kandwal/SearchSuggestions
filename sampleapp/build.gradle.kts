plugins {
    kotlin("jvm") version "2.0.20"
    application
}

group = "org.hk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    //implementation(project(":library"))
    //from jitpack not locally
    //implementation("com.github.HimanshuKandwal.SearchSuggestions:library:v1.0.1")
        implementation("com.github.Himanshu-Kandwal.SearchSuggestions:library:v1.0.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}