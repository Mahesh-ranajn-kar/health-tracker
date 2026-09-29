plugins {
    kotlin("jvm") version "2.4.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Javalin core dependency
    implementation("io.javalin:javalin:6.1.3")
    // SLF4J Logger
    implementation("org.slf4j:slf4j-simple:2.0.12")
    // Jackson for JSON (Optional)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}