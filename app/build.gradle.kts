import org.jetbrains.kotlin.cli.common.getLibraryFromHome
import org.springframework.boot.gradle.tasks.run.BootRun

group = "com.stackhawk.vuln.soap"
version = "1.0"

plugins {
    application
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.intershop.gradle.jaxb") version "4.3.0"
    id("application")
    id("distribution")
    kotlin("jvm") version "1.4.32"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

buildscript {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    files("${System.getenv()["JAVA_HOME"]}/lib/tools.jar")
}

apply(plugin = "io.spring.dependency-management")

tasks.bootJar {
    archiveFileName.set("vulnsoap.jar")
    setClasspath("com.stackhawk.vuln.soap")
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://maven.springframework.org/release")
        }
    }

    dependencies {
        implementation("wsdl4j:wsdl4j")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web-services")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("javax.activation:activation:1.1.1")
        implementation("com.h2database:h2")
        implementation("com.sun.xml.wss:xws-security:3.0") {
            exclude("javax.xml.crypto", "xmldsig")
        }
        implementation("org.springframework.ws:spring-ws-security") {
            exclude("org.springframework.security", "spring-security-core")
        }
        implementation("org.springframework.boot:spring-boot-devtools")
        implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        constraints {
            implementation("javax.xml.bind:jaxb-api:2.3.0")
            implementation("javax.xml.bind:jaxb-xjc:2.0.2")
        }
    }
}

// tag::xsd[]
sourceSets["main"].java {
    srcDir("build/generated-sources/jaxb")
}

jaxb {
    // generate java code from schema
    javaGen {
        //generates a 'project' schema file from existing java code
        register("vulnsoap") {
            schema = file("src/main/resources/vulnsoap.xsd")
        }
    }

    //generates schema from java code
    schemaGen {
        //generates java code for project from project schema
        register("vulnsoap") {
            inputDir = file("src/main/java")
            include("db/vulny.db")
            namespaceconfigs = mapOf("http://www.stackhawk.com/vulnsoap" to "feature.xsd")
        }
    }
}

