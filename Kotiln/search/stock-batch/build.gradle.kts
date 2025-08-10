tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.batch:spring-batch-core")
}