tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}

dependencies {
		implementation("org.springframework.boot:spring-boot-starter-batch")
		testImplementation("org.springframework.batch:spring-batch-test")
}