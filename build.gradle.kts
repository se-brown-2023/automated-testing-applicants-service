plugins {
    id("org.springframework.boot") version "3.1.4" apply false
    id("com.adarshr.test-logger") version "3.2.0" apply false
}
allprojects {
    group = "com.sebrown2023"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}