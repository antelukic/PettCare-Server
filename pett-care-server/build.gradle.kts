
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "com.pettcare"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("io.ktor:ktor-server-core-jvm:2.2.4")
    implementation("io.ktor:ktor-serialization-jackson:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.2.4")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.2.4")
    implementation("io.ktor:ktor-server-default-headers-jvm:2.2.4")
    implementation("io.ktor:ktor-server-auth-jvm:2.2.4")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.2.4")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.4")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.2.4")
    implementation("io.ktor:ktor-server-cors:2.2.4")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")

    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")
    implementation("commons-codec:commons-codec:1.15")

    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("io.insert-koin:koin-ktor:3.2.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.2.0")

    implementation("com.zaxxer:HikariCP:5.0.1")
}
