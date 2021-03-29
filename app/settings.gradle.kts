rootProject.name = "vulny-spring-soap-api"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://maven.springframework.org/release")
        }
    }
}

