plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.openapi.generator") version "7.1.0"
    id("org.jetbrains.kotlin.kapt") version "1.5.20"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation(project(mapOf("path" to ":common:database")))

    //MAPSTRUCT
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    //LOMBOK
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    //OPENAPI
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":common:logging"))

    //TESTS
    implementation("org.postgresql:postgresql:42.2.27")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.keycloak:keycloak-spring-boot-starter:23.0.1")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose:6.1.5")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

}

openApiGenerate {
    generatorName.set("spring")
    validateSpec.set(true)
    inputSpec.set("$projectDir/src/main/resources/api/openapi.yml") // path to spec
    outputDir.set("${layout.buildDirectory.asFile.get()}/generated/sources/annotationProcessor/java/main")
    apiPackage.set("com.sebrown2023.controller")
    modelPackage.set("com.sebrown2023.model.dto")
    generateApiTests.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)

    globalProperties.set(
            mapOf(
                    "generateSupportingFiles" to "false",
                    "models" to "", // generate all models
                    "apis" to "", // generate all apis
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

sourceSets.main {
    java.srcDirs("${layout.buildDirectory.asFile.get()}/generated/sources/annotationProcessor/java/main")
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("spring.bean.ignore", "false")
    }
}

tasks.test {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("com.sebrown2023.ExamManageApplication")
}

tasks.compileJava {
    dependsOn("openApiGenerate")
}