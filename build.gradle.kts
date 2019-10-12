plugins {
    java
    jacoco
    id("org.openjfx.javafxplugin") version "0.0.7"
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
}

val javaVersion = JavaVersion.VERSION_12
val jUnitVersion = "5.5.2"
val testFXVersion = "4.0.16-alpha"

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

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")

    testImplementation("org.testfx:testfx-core:$testFXVersion")
    testImplementation("org.testfx:testfx-junit5:$testFXVersion")
    testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
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
