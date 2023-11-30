DROP DATABASE IF EXISTS organ_manager;
CREATE DATABASE organ_manager;
use organ_manager;

CREATE TABLE user(
    user_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    username varchar(100) UNIQUE NOT NULL,
    password char(68) NOT NULL
);

CREATE TABLE role (
    role_id INTEGER NOT NULL,
    role_name varchar(100) NOT NULL,
    constraint pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user_roles (
    user_id BINARY(16),
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE patient_info (
    patient_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    user_id BINARY(16),
    pname varchar(200) NOT NULL,
    gender varchar(20) DEFAULT NULL,
    age int(11) NOT NULL,
    blood_type varchar(5) NOT NULL,
    mobile_number varchar(100) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (patient_id)
);

CREATE TABLE donor (
    donor_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    patient_id BINARY(16) NOT NULL,
    organ_donated varchar(50) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (donor_id)
);

CREATE TABLE recipient (
    rec_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    patient_id BINARY(16) NOT NULL,
    organ_requested varchar(50) NOT NULL,
    priority INTEGER NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (rec_id)
);

CREATE TABLE doctor_info (
    doctor_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    user_id BINARY(16) NOT NULL UNIQUE,
    doctor_name varchar(200) NOT NULL,
    speciality varchar(200) NOT NULL,
    phone varchar(50) NOT NULL,
    constraint pk_doctorInfo PRIMARY KEY (doctor_id)
);

CREATE TABLE matches (
    match_id BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    donor_id BINARY(16) NOT NULL,
    rec_id BINARY(16) NOT NULL,
    completed INTEGER,
    constraint pk_matches PRIMARY KEY (match_id)
);

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_userRoles
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE user_roles
ADD CONSTRAINT fk_role_userRoles
FOREIGN KEY (role_id) REFERENCES role(role_id);

ALTER TABLE doctor_info
ADD CONSTRAINT fk_doctorInfo
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE patient_info
ADD CONSTRAINT fk_patientInfo
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE donor
ADD CONSTRAINT fk_donorInfo
FOREIGN KEY (patient_id) REFERENCES patient_info(patient_id);

ALTER TABLE recipient
ADD CONSTRAINT fk_recInfo
FOREIGN KEY (patient_id) REFERENCES patient_info(patient_id);

ALTER TABLE matches
ADD CONSTRAINT fk_donorId
FOREIGN KEY (donor_id) REFERENCES donor(donor_id);

ALTER TABLE matches
ADD CONSTRAINT fk_recId
FOREIGN KEY (rec_id) REFERENCES recipient(rec_id);