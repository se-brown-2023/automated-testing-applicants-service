FROM gradle:8.5.0-jdk21-alpine AS builder
WORKDIR /opt/swe2023brown/exam-judging-service
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY common common
COPY exam-judging-service exam-judging-service
RUN gradle clean :exam-judging-service:bootJar

FROM azul/zulu-openjdk-alpine:21-jre
ARG VERSION
ENV VERSION ${VERSION}
WORKDIR /opt/swe2023brown/exam-judging-service
COPY --from=builder /opt/swe2023brown/exam-judging-service/exam-judging-service/build/libs/exam-judging-service-${VERSION}.jar .
COPY --from=builder /opt/swe2023brown/exam-judging-service/exam-judging-service/src/main/conf/lib /opt/swe2023brown/exam-judging-service/lib
CMD java -Dlog.dir=/var/log/swe2023brown/exam-judging-service -jar exam-judging-service-${VERSION}.jar --spring.config.location=classpath:/application.yml,optional:/etc/swe2023brown/exam-judging-service/application.yml
