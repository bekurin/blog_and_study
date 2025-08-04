rootProject.name = "search"

include("stock-api")
include("stock-batch")
include("stock-consumer")

include("stock-modules")
include("stock-modules:database")
include("stock-modules:web")

pluginManagement {
    repositories {
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.2.0"
        kotlin("plugin.spring") version "2.2.0"
        id("org.springframework.boot") version "4.0.0-M1"
        id("io.spring.dependency-management") version "1.1.7"
    }
}

