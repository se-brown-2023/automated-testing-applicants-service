plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.openapi.generator")  version "7.1.0"
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.keycloak:keycloak-spring-boot-starter:23.0.1")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose:6.1.5")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.modelmapper:modelmapper:3.2.0")

    runtimeOnly("org.postgresql:postgresql:42.6.0")

    implementation(project(":common:database"))
    implementation(project(":common:logging"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("com.sebrown2023.ExamConductApplication")
}

openApiGenerate {
    generatorName.set("spring")
    validateSpec.set(true)
    inputSpec.set("$projectDir/src/main/resources/exam-conduct-service-api.yml") // path to spec
    outputDir.set("${layout.buildDirectory.asFile.get()}/generated/java")
    sourceSets.main {
        java.srcDirs("${layout.buildDirectory.asFile.get()}/generated/java")
    }
    apiPackage.set("com.sebrown2023.controller")
    modelPackage.set("com.sebrown2023.model.dto")
    generateApiTests.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)

    globalProperties.set(
        mapOf(
            "generateSupportingFiles" to "false",
            "models" to "",
            "apis" to "",
        ),
    )


    configOptions.set(
        mapOf(
            "documentationProvider" to "none",
            "generatedConstructorWithRequiredArgs" to "true",
            "openApiNullable" to "false",
            "useSpringBoot3" to "true",
            "java8" to "false",
            "skipDefaultInterface" to "true",
            "interfaceOnly" to "true",
            "serviceInterface" to "true",
            "useTags" to "true",
            "fullJavaUtil" to "false",
            "hideGenerationTimestamp" to "true",
            "sourceFolder" to "",
            "library" to "spring-boot",
            "serializationLibrary" to "jackson",
        ),
    )
}

tasks.withType<JavaCompile> {
    dependsOn("openApiGenerate")
}