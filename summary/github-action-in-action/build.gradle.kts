import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val testContainersVersion: String = "1.19.5"
val mysqlConnectorVersion: String = "8.0.32"
val mockitoKotlinVersion: String = "4.1.0"
val okhttpVersion: String = "4.10.0"

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // mockito-kotlin and mockwebserver
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttpVersion")

    // testcontainers
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
    testImplementation("org.testcontainers:mysql:$testContainersVersion")
    testRuntimeOnly("mysql:mysql-connector-java:$mysqlConnectorVersion")
}

ktlint {
    enableExperimentalRules.set(false)
    filter {
        exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    testLogging {
        showCauses = true
        showExceptions = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
    useJUnitPlatform()
}
