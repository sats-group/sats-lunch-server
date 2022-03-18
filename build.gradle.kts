val ktorVersion: String = "2.0.0-beta-1"
val kotlinVersion: String = "1.6.10"
val logbackVersion: String = "1.2.11"

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "com.sats.lunch"
version = "0.1.0"

application {
    mainClass.set("com.sats.lunch.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")

    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()

    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { url = uri("https://customers.pspdfkit.com/maven") }
}

dependencies {
    // Ktor Client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    // Ktor Server
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Kotlin
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    // PSPDFKit
    implementation("com.pspdfkit:libraries-java:1.4.1")
}
