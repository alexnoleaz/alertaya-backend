plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.alertaya"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	val mapstructVersion = "1.6.3"
	val jwtVersion = "0.12.6"
	val springDocVersion = "2.8.9"

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
 	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
  	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")
 	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
 	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
 	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// tasks.withType<Test> {
// 	useJUnitPlatform()
// }
