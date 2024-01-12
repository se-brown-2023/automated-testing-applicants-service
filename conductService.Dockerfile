FROM gradle:8.5.0-jdk21-alpine AS builder
WORKDIR /opt/swe2023brown/exam-conduct-service
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY common common
COPY exam-conduct-service exam-conduct-service
RUN gradle clean :exam-conduct-service:bootJar

FROM azul/zulu-openjdk-alpine:21-jre
ARG VERSION
ENV VERSION ${VERSION}
WORKDIR /opt/swe2023brown/exam-conduct-service
COPY --from=builder /opt/swe2023brown/exam-conduct-service/exam-conduct-service/build/libs/exam-conduct-service-${VERSION}.jar .
CMD java -jar exam-conduct-service-${VERSION}.jar --spring.config.location=classpath:/application.yml,optional:/etc/swe2023brown/exam-conduct-service/application.yml
