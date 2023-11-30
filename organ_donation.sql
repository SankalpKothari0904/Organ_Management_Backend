DROP DATABASE IF EXISTS organ_manager;
CREATE DATABASE organ_manager;
use organ_manager;

CREATE TABLE user(
    user_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    username varchar(100) UNIQUE NOT NULL,
    password char(68) NOT NULL
);

CREATE TABLE role (
    role_id INTEGER NOT NULL,
    role_name varchar(100) NOT NULL,
    constraint pk_role PRIMARY KEY (role_id)
);

CREATE TABLE user_roles (
    user_id BINARY(128),
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE patient_info (
    patient_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    user_id BINARY(128),
    pname varchar(200) NOT NULL,
    gender varchar(20) DEFAULT NULL,
    age int(11) NOT NULL,
    blood_type varchar(5) NOT NULL,
    mobile_number varchar(100) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (patient_id)
);

CREATE TABLE donor (
    donor_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    patient_id BINARY(128) NOT NULL,
    organ_donated varchar(50) NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (donor_id)
);

CREATE TABLE recipient (
    rec_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    patient_id BINARY(128) NOT NULL,
    organ_requested varchar(50) NOT NULL,
    priority INTEGER NOT NULL,
    constraint pk_userInfo1 PRIMARY KEY (rec_id)
);

CREATE TABLE doctor_info (
    doctor_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    user_id BINARY(128) NOT NULL UNIQUE,
    doctor_name varchar(200) NOT NULL,
    speciality varchar(200) NOT NULL,
    phone varchar(50) NOT NULL,
    constraint pk_doctorInfo PRIMARY KEY (doctor_id)
);

CREATE TABLE matches (
    match_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    donor_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
    rec_id BINARY(128) DEFAULT (UUID_TO_BIN(UUID(), 1)),
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

INSERT INTO role values (1,'ADMIN'),(2,'DOCTOR'),(3,'USER');
INSERT INTO user values ("0465b3e4-8f88-11ee-b9d1-0242ac120002","admin","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
-- test123

INSERT INTO user values ("1ad23882-8f88-11ee-b9d1-0242ac120002","doctor1","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values ("52b1a210-8f88-11ee-b9d1-0242ac120002","doctor2","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values ("8c1f90fc-8f88-11ee-b9d1-0242ac120002","doctor3","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");

INSERT INTO user values ("950aa454-8f88-11ee-b9d1-0242ac120002","sankalp","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values ("2092030a-8f89-11ee-b9d1-0242ac120002","siddharth","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values ("2b1f17e0-8f89-11ee-b9d1-0242ac120002","charan","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");
INSERT INTO user values ("381ceeb8-8f89-11ee-b9d1-0242ac120002","prachoday","$2a$08$qlXB4Pk7sF9ApzimkeQI0eDGvPWGal.Y265Goukid6hdlzz/QN/cy");

INSERT INTO user_roles values ("0465b3e4-8f88-11ee-b9d1-0242ac120002",1);
INSERT INTO user_roles values ("1ad23882-8f88-11ee-b9d1-0242ac120002",2);
INSERT INTO user_roles values ("52b1a210-8f88-11ee-b9d1-0242ac120002",2);
INSERT INTO user_roles values ("8c1f90fc-8f88-11ee-b9d1-0242ac120002",2);
INSERT INTO user_roles values ("950aa454-8f88-11ee-b9d1-0242ac120002",3);
INSERT INTO user_roles values ("2092030a-8f89-11ee-b9d1-0242ac120002",3);
INSERT INTO user_roles values ("2b1f17e0-8f89-11ee-b9d1-0242ac120002",3);
INSERT INTO user_roles values ("381ceeb8-8f89-11ee-b9d1-0242ac120002",3);

INSERT INTO doctor_info values ("439e8c9c-8f89-11ee-b9d1-0242ac120002","1ad23882-8f88-11ee-b9d1-0242ac120002","John","Respiratory diseases","9988776655");
INSERT INTO doctor_info values ("5f5ed55e-8f89-11ee-b9d1-0242ac120002","52b1a210-8f88-11ee-b9d1-0242ac120002","Chris","Cardiovascular","9769769768");
INSERT INTO doctor_info values ("6275266c-8f89-11ee-b9d1-0242ac120002","8c1f90fc-8f88-11ee-b9d1-0242ac120002","Chris","Surgeon","9769769768");
INSERT INTO patient_info values ("71bb1212-8f89-11ee-b9d1-0242ac120002","950aa454-8f88-11ee-b9d1-0242ac120002","Sankalp","M",20,"B+","1010101010");
INSERT INTO patient_info values ("6b2feb34-8f89-11ee-b9d1-0242ac120002","2092030a-8f89-11ee-b9d1-0242ac120002","Siddharth","M",40,"A-","3203203202");
INSERT INTO patient_info values ("6691d042-8f89-11ee-b9d1-0242ac120002","2b1f17e0-8f89-11ee-b9d1-0242ac120002","Charan","M",26,"B+","9999999999");
INSERT INTO patient_info values ("782e2198-8f89-11ee-b9d1-0242ac120002","381ceeb8-8f89-11ee-b9d1-0242ac120002","Prachoday","M",32,"O+","8880008880");

INSERT INTO donor values ("c119a508-8f89-11ee-b9d1-0242ac120002","71bb1212-8f89-11ee-b9d1-0242ac120002","Kidney");
INSERT INTO donor values ("c55162b4-8f89-11ee-b9d1-0242ac120002","6b2feb34-8f89-11ee-b9d1-0242ac120002","Liver");

INSERT INTO recipient values ("db77f3f2-b2c6-493e-9b11-15a0816ee733","6691d042-8f89-11ee-b9d1-0242ac120002","Liver",7);
INSERT INTO recipient values ("1f7fc27f-7298-4943-81b9-7ff3f2008a42","6691d042-8f89-11ee-b9d1-0242ac120002","Kidney",10);
INSERT INTO recipient values ("daac0b0e-09cf-48dd-9336-426f75e58620","782e2198-8f89-11ee-b9d1-0242ac120002","Eyes",3);

INSERT INTO matches values ("8610ab1f-cd1e-43d3-970a-51b2ceb5106d","c119a508-8f89-11ee-b9d1-0242ac120002","1f7fc27f-7298-4943-81b9-7ff3f2008a42",0);