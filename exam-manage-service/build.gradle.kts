plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.openapi.generator") version "7.1.0"
}

group = "org.itmo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(project(mapOf("path" to ":common:database")))

    //MAPSTRUCT
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    //LOMBOK
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    //OPENAPI
    implementation("org.openapitools", "openapi-generator", "4.3.0")

    //TESTS
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    dependsOn("openApiGenerate")
}