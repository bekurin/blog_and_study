tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

dependencies {
    runtimeOnly("com.h2database:h2")
}