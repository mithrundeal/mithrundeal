import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String = "2.1.3"



plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "org"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-network:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}