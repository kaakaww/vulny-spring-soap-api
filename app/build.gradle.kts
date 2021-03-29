
group = "com.stackhawk"
version = "1.0"

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.10"
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
        compileOnly("org.springframework.boot:spring-boot-devtools")
        compileOnly("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-web-services")
        implementation("wsdl4j:wsdl4j")
        implementation("com.sun.xml.wss:wss-security")
        implementation("com.sun.xml.wss:xws-security:3.0")
        implementation("javax.activation:activation")
        implementation(
            ("com.sun.xml.bind:jaxb-xjc:2.3.1"),
            ("com.sun.xml.bind:jaxb-impl:2.3.1"),
            ("org.glassfish.jaxb:jaxb-runtime:2.3.3")
        )
        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

// tag::xsd[]
    sourceSets["main"].java {
        srcDir("build/generated-sources/jaxb")
    }
}
// tag::jaxb[]
