FROM gradle:8.5.0-jdk21-alpine AS builder
WORKDIR /opt/swe2023brown/exam-judje-service
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY common common
COPY exam-judje-service exam-judje-service
RUN gradle clean :exam-judje-service:bootJar

FROM azul/zulu-openjdk-alpine:21-jre
ARG VERSION
WORKDIR /opt/swe2023brown/exam-judje-service
COPY --from=builder /opt/swe2023brown/exam-judje-service/exam-judje-service/build/libs/exam-judje-service-${VERSION}.jar .
CMD java -jar exam-judje-service-${VERSION}.jar --spring.config.location=classpath:/application.yml,optional:/etc/swe2023brown/exam-judje-service/application.yml
