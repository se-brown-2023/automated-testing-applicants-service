create table if not exists exam
(
    id                   serial primary key,
    examiner_id          integer,
    name                 varchar(70) not null,
    description          text,
    programming_language varchar(20) not null,
    max_duration         interval,
    ttl                  interval,
    creation_date        timestamp
);

create table if not exists task
(
    id                 serial primary key,
    exam_id            integer references exam (id),
    name               varchar(70) not null,
    description        text,
    author_source_code text
);

create table if not exists test
(
    id                   serial primary key,
    task_id              integer references task (id),
    name                 varchar(70),
    input_data           text,
    expected_output_data text
);

create table if not exists examinee
(
    id           serial primary key,
    first_name   varchar(15) not null,
    last_name    varchar(20) not null,
    email        varchar(30) not null,
    phone_number varchar(15)
);

create table if not exists exam_session
(
    id               uuid primary key,
    exam_id          integer references exam (id),
    examinee_id      integer references examinee (id),
    status           varchar(10),
    start_timestamp  timestamp,
    finish_timestamp timestamp
);

create table if not exists submission
(
    id                    serial primary key,
    task_id               integer references task (id),
    exam_session_id       integer references exam_session (id),
    user_source_code      text,
    submit_time           timestamp,
    status                text not null,
    processing_start_time timestamp
);

create table if not exists test_result
(
    id                 serial primary key,
    submission_id      integer references submission (id),
    test_id            integer references test (id),
    actual_output_data text,
    passed             boolean not null,
    elapsed_time       interval
);
