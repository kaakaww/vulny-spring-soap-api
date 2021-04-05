rootProject.name = "vulny-spring-soap-api"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        jcenter {
            url = uri("http://jcenter.bintray.com/")
        }
        maven {
            url = uri("https://maven.springframework.org/release")
        }
    }
}
