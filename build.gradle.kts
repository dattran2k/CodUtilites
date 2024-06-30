plugins {
    kotlin("jvm") version "2.0.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.dat.cod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.openjfx:javafx-controls:17.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")
}
javafx {
    version = "22.0.1"
    modules = listOf("javafx.controls")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}