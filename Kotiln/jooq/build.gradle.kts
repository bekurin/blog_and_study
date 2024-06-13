import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.MountableFile


val jooqVersion: String = "3.19.5"

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("com.mysql:mysql-connector-j:8.3.0")
		classpath("org.testcontainers:mysql:1.19.8")
		classpath("org.jooq:jooq-codegen:3.18.7")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	main {
		java {
			srcDirs(listOf("src/main/java", "src/generated"))
		}
	}
}

tasks.register("jooqCodegen") {
	doLast {
		val schemaFile = layout.projectDirectory.file("src/main/resources/schema.sql").asFile
		val schemaPath = schemaFile.path

		println("Schema path: $schemaPath")

		if (schemaFile.exists().not() || schemaFile.canRead().not()) {
			throw IllegalStateException("Schema file is not accessible: $schemaFile")
		}

		val containerInstance = MySQLContainer<Nothing>("mysql:8.0.36")
			.apply {
				withCopyToContainer(MountableFile.forHostPath(schemaPath), "/docker-entrypoint-initdb.d/init.sql")
				start()
			}

		Configuration()
			.withJdbc(
				Jdbc()
					.withDriver("com.mysql.cj.jdbc.Driver")
					.withUrl(containerInstance.jdbcUrl)
					.withUser(containerInstance.username)
					.withPassword(containerInstance.password)
			)
			.withGenerator(
				Generator()
					.withDatabase(Database().withInputSchema("test"))
					.withTarget(
						Target()
							.withPackageName("org.jooq.generated")
							.withDirectory("${layout.projectDirectory}/src/generated")
					)
			).also(GenerationTool::generate)

		containerInstance.stop()
	}
}

tasks.withType<KotlinCompile> {
	dependsOn("jooqCodegen")
}
