import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Strategy

val jooqVersion: String = "3.19.5"

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("dev.monosoul.jooq-docker") version "6.0.14"
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
	implementation("org.springframework.boot:spring-boot-starter-jooq") {
		exclude(group = "org.jooq")
	}

	jooqCodegen("org.jooq:jooq:${jooqVersion}")
	jooqCodegen("org.jooq:jooq-meta:${jooqVersion}")
	jooqCodegen("org.jooq:jooq-codegen:${jooqVersion}")
	jooqCodegen("org.flywaydb:flyway-core:10.8.1")
	jooqCodegen("org.flywaydb:flyway-mysql:10.8.1")

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

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	version = "${jooqVersion}"
	withContainer {
		image {
			name = "mysql:8.0.29"
			envVars = mapOf(
				"MYSQL_ROOT_PASSWORD" to "passwd",
				"MYSQL_DATABASE" to "sakila"
			)
		}

		db {
			username = "root"
			password = "passwd"
			name = "sakila"
			port = 3306
			jdbc {
				schema = "jdbc:mysql"
				driverClassName = "com.mysql.cj.jdbc.Driver"
			}
		}
	}
}

tasks {
	generateJooqClasses {
		schemas.set(listOf("sakila"))
		outputDirectory.set(project.layout.projectDirectory.dir("src/generated"))
		includeFlywayTable.set(false)

		usingJavaConfig {
			generate = Generate()
				.withJavaTimeTypes(true)
				.withDeprecated(false)
				.withDaos(true)
				.withFluentSetters(true)
				.withRecords(true)

			withStrategy(
				Strategy().withName("jooq.custom.generator.JPrefixGeneratorStrategy")
			)

			database.withForcedTypes(
				ForcedType()
					.withUserType("java.lang.Long")
					.withTypes("int unsigned"),
				ForcedType()
					.withUserType("java.lang.Integer")
					.withTypes("tinyint unsigned"),
				ForcedType()
					.withUserType("java.lang.Integer")
					.withTypes("smallint unsigned")
			)
		}
	}
}
