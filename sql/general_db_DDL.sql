CREATE TABLE Exam (
    id SERIAL PRIMARY KEY,
    examiner_id INTEGER,
    name VARCHAR(70) NOT NULL,
    description TEXT,
    programming_language VARCHAR(20) NOT NULL,
    max_duration INTERVAL,
    TTL INTERVAL,
    creation_date TIMESTAMP
);

CREATE TABLE Task (
    id SERIAL PRIMARY KEY,
    exam_id INTEGER REFERENCES Exam(id),
    name VARCHAR(70) NOT NULL,
    description TEXT,
    author_source_code TEXT
);

CREATE TABLE Test (
    id SERIAL PRIMARY KEY,
    task_id INTEGER REFERENCES Task(id),
    name VARCHAR(70),
    input_data TEXT,
    expected_output_data TEXT
);

CREATE TABLE Examinee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone_number VARCHAR(15)
);

CREATE TABLE Exam_session (
    id SERIAL PRIMARY KEY,
    exam_id INTEGER REFERENCES Exam(id),
    examinee_id INTEGER REFERENCES Examinee(id),
    status VARCHAR(10),
    start_timestamp TIMESTAMP,
    finish_timestamp TIMESTAMP,
    expired BOOLEAN
);

CREATE TABLE Submission (
    id SERIAL PRIMARY KEY,
    task_id INTEGER REFERENCES Task(id),
    exam_session_id INTEGER REFERENCES Exam_session(id),
    user_source_code TEXT,
    submit_time TIMESTAMP
);

CREATE TABLE Test_result (
    id SERIAL PRIMARY KEY,
    submission_id INTEGER REFERENCES Submission(id),
    test_id INTEGER REFERENCES Test(id),
    actual_output_data TEXT,
    passed BOOLEAN NOT NULL,
    elapsed_time INTERVAL
);