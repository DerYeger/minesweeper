plugins {
    java
    jacoco
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
}

val javaVersion = JavaVersion.VERSION_12

group = "eu.yeger"
version = "0.6.0"

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}



javafx {
    version = javaVersion.toString()
    modules("javafx.controls")
}

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("eu.yeger:kotlin.javafx:0.1.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")

    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")

    testImplementation("io.mockk:mockk:1.9.3")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
        }
    }

    test {
        useJUnitPlatform()
    }
}
