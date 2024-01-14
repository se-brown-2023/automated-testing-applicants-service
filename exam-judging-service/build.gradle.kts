plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.adarshr.test-logger")
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.kafka:spring-kafka")

    implementation(project(":common:database"))
    implementation(project(":common:logging"))

    implementation("org.apache.commons:commons-exec:1.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:postgresql:1.19.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}

tasks.compileTestJava {
    options.compilerArgs.add("--enable-preview")
}

tasks.test {
    allJvmArgs = allJvmArgs.toMutableList().apply { add("--enable-preview") }
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    jvmArgs = jvmArgs!!.toMutableList().apply { add("--enable-preview") }
}

springBoot {
    mainClass.set("com.sebrown2023.ExamJudgingApplication")
}

