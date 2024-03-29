# Automated system for testing applicants

| Service | Build status |
|---------|--------------|
|Exam conduct service|[![Build exam conduct service](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-conduct-service.yml/badge.svg)](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-conduct-service.yml)|
|Exam judging service|[![Build exam judging service](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-judging-service.yml/badge.svg)](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-judging-service.yml)|
|Exam manage service|[![Build exam manage service](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-manage-service.yml/badge.svg)](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/build.exam-manage-service.yml)|

| Release | [![release](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/release.yml/badge.svg?branch=master)](https://github.com/se-brown-2023/automated-testing-applicants-service/actions/workflows/release.yml) |
|---------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

# API
### [Exam conduct service](https://github.com/se-brown-2023/automated-testing-applicants-service/wiki/Exam-conduct-service-api)
### [Exam manage service](https://github.com/se-brown-2023/automated-testing-applicants-service/wiki/Exam-manage-service-api)

# Usage

1. Set up [docker compose](docker-compose.yml) volumes for application.yml configs
2. Run `docker compose up -d`

# Graylog

```shell
cd graylogServer
docker compose up -d
```

Also see: https://github.com/Graylog2/docker-compose

# Monitoring

```shell
cd monitoring
docker compose up -d
```
