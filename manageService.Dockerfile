FROM gradle:8.5.0-jdk21-alpine AS builder
WORKDIR /opt/swe2023brown/exam-manage-service
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY common common
COPY exam-manage-service exam-manage-service
RUN gradle clean :exam-manage-service:bootJar

FROM azul/zulu-openjdk-alpine:21-jre
ARG VERSION
ENV VERSION ${VERSION}
WORKDIR /opt/swe2023brown/exam-manage-service
COPY --from=builder /opt/swe2023brown/exam-manage-service/exam-manage-service/build/libs/exam-manage-service-${VERSION}.jar .
CMD java -jar exam-manage-service-${VERSION}.jar --spring.config.location=classpath:/application.yml,optional:/etc/swe2023brown/exam-manage-service/application.yml
