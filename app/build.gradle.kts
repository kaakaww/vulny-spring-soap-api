import org.springframework.boot.gradle.tasks.run.BootRun

group = "com.stackhawk.vuln.soap"
version = "1.0"

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.intershop.gradle.jaxb") version "4.3.0"
    kotlin("jvm") version "1.4.10"
}

tasks.bootJar {
    archiveFileName.set("vulny-soap.jar")
    setClasspath("com.stackhawk.vuln.soap")
}

tasks.named<BootRun>("bootRun") {
    main = "com.stackhawk.vuln.soap.SoapCourseManagementApplication"
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
        compileOnly("org.springframework.boot:spring-boot-starter-web-services")
        compileOnly("org.springframework.boot:spring-boot-devtools")
        compileOnly("wsdl4j:wsdl4j")
        compileOnly("org.springframework.ws:spring-ws-security") {
            exclude("org.springframework.security", "spring-security-core")
        }
        compileOnly("com.sun.xml.wss:xws-security:3.0") {
            exclude("javax.xml.crypto", "xmldsig")
        }
        compileOnly("javax.activation:activation:1.1.1")
        compileOnly("com.h2database:h2")
        implementation("org.springframework.boot:spring-boot-gradle-plugin:2.4.3")
        implementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

// tag::xsd[]
    sourceSets["main"].java {
        srcDir("build/generated-sources/jaxb")
    }
}


jaxb {
    // generate java code from schema
    javaGen {
        //generates a 'project' schema file from existing java code
        register("vuln-soap") {
            schema = file("src/main/resources/vuln-soap.xsd")
        }
    }

    //generates schema from java code
    schemaGen {
        //generates java code for project from project schema
        register("vuln-soap") {
            inputDir = file("src/main/java")
            outputDir = file("com/stackhawk/annotated/**/binding/**/*.java")
            namespaceconfigs = mapOf("http://stackhawk.com/courses" to "feature.xsd")
        }
    }
}

