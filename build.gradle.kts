plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jooq.jooq-codegen-gradle") version "3.19.29"
}

group = "com.example.notes"
version = "0.0.1-SNAPSHOT"
description = "notes"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:4.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jooq:4.0.1")
    jooqCodegen("org.postgresql:postgresql:42.7.2")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/postgres"
            user = "mustufain"
            password = "password"
        }
        generator {
            name = "org.jooq.codegen.DefaultGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
            }
            generate {
                isDeprecated = false
                isRecords = true
                isImmutablePojos = true
                isFluentSetters = true
            }
            target {
                packageName = "com.example.notes.infrastructure.db.jooq"
                directory = "build/generated-sources/jooq"
            }
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
