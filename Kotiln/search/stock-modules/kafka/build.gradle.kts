tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
    implementation("org.springframework.kafka:spring-kafka")
}