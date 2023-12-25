-- CREATE DIVISION TABLE
CREATE TABLE DIVISION (
                          DIVISION_ID BIGSERIAL,
                          NAME VARCHAR(255) NOT NULL,
                          NAME_LOCAL VARCHAR(255) NOT NULL,
                          ACTIVE BOOLEAN NOT NULL,
                          CREATED_AT TIMESTAMPTZ NOT NULL,
                          UPDATED_AT TIMESTAMPTZ NOT NULL,

                          CONSTRAINT DIVISION_DIVISION_ID_PK PRIMARY KEY (DIVISION_ID),
                          CONSTRAINT DIVISION_NAME_UK UNIQUE (NAME),
                          CONSTRAINT DIVISION_NAME_LOCAL_UK UNIQUE (NAME_LOCAL),
                          CONSTRAINT DIVISION_ACTIVE_CHK CHECK (ACTIVE IN (TRUE, FALSE))
);

-- INSERT DATA INTO DIVISION TABLE
INSERT INTO DIVISION (NAME, NAME_LOCAL, ACTIVE, CREATED_AT, UPDATED_AT) VALUES
                                                                            ('Dhaka', 'ঢাকা', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                            ('Rajshahi', 'রাজশাহী', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CREATE DISTRICT TABLE
CREATE TABLE DISTRICT (
                          DISTRICT_ID BIGSERIAL,
                          NAME VARCHAR(255) NOT NULL,
                          NAME_LOCAL VARCHAR(255) NOT NULL,
                          ACTIVE BOOLEAN NOT NULL,
                          CREATED_AT TIMESTAMPTZ NOT NULL,
                          UPDATED_AT TIMESTAMPTZ NOT NULL,
                          CONSTRAINT DISTRICT_DISTRICT_ID_PK PRIMARY KEY (DISTRICT_ID),
                          CONSTRAINT DISTRICT_NAME_UK UNIQUE (NAME),
                          CONSTRAINT DISTRICT_NAME_LOCAL_UK UNIQUE (NAME_LOCAL),
                          CONSTRAINT DISTRICT_ACTIVE_CHK CHECK (ACTIVE IN (TRUE, FALSE))
);


-- INSERT DATA INTO DISTRICT TABLE
INSERT INTO DISTRICT (NAME, NAME_LOCAL, ACTIVE, CREATED_AT, UPDATED_AT) VALUES
                                                                            ('Dhaka', 'ঢাকা', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                            ('Gazipur', 'গাজীপুর', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                            ('Rajshahi', 'রাজশাহী', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                            ('Chapainawabganj', 'চাঁপাইনবাবগঞ্জ', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- CREATE UPAZILA TABLE
CREATE TABLE UPAZILA (
                         UPAZILA_ID BIGSERIAL,
                         NAME VARCHAR(255) NOT NULL,
                         NAME_LOCAL VARCHAR(255) NOT NULL,
                         ACTIVE BOOLEAN NOT NULL,
                         CREATED_AT TIMESTAMPTZ NOT NULL,
                         UPDATED_AT TIMESTAMPTZ NOT NULL,
                         CONSTRAINT UPAZILA_UPAZILA_ID_PK PRIMARY KEY (UPAZILA_ID),
                         CONSTRAINT UPAZILA_NAME_UQ UNIQUE (NAME),
                         CONSTRAINT UPAZILA_NAME_LOCAL_UK UNIQUE (NAME_LOCAL),
                         CONSTRAINT UPAZILA_ACTIVE_CHK CHECK (ACTIVE IN (TRUE, FALSE))
);

-- INSERT DATA INTO UPAZILA TABLE
INSERT INTO UPAZILA (NAME, NAME_LOCAL, ACTIVE, CREATED_AT, UPDATED_AT) VALUES
                                                                           -- UPAZILA UNDER DHAKA
                                                                           ('Tangail Sadar', 'টাঙ্গাইল সদর', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Sakhipur', 'সখিপুর', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Basail', 'বাসাইল', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

                                                                           -- UPAZILA UNDER CHAPAINAWABGANJ
                                                                           ('Bholahat', 'ভোলাহাট', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Gomastapur', 'গোমস্তাপুর', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Nachole', 'নাচোল', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Chapainawabganj Sadar', 'চাঁপাইনবাবগঞ্জ সদর', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Shibganj', 'শিবগঞ্জ', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Create ADDRESS table
CREATE TABLE ADDRESS (
                         ADDRESS_ID BIGSERIAL,
                         ADDRESS_TYPE VARCHAR(50) NOT NULL,
                         DIVISION_ID BIGINT NOT NULL,
                         DISTRICT_ID BIGINT NOT NULL,
                         UPAZILA_ID BIGINT NOT NULL,
                         IS_ACTIVE BOOLEAN NOT NULL,
                         CREATED_AT TIMESTAMPTZ NOT NULL,
                         UPDATED_AT TIMESTAMPTZ NOT NULL,

                         CONSTRAINT ADDRESS_ADDRESS_ID_PK PRIMARY KEY (ADDRESS_ID),
                         CONSTRAINT ADDRESS_ADDRESS_TYPE_CHK CHECK (ADDRESS_TYPE IN ('PRESENT', 'PERMANENT')),
                         CONSTRAINT ADDRESS_DIVISION_ID_FK FOREIGN KEY (DIVISION_ID) REFERENCES DIVISION(DIVISION_ID),
                         CONSTRAINT ADDRESS_DISTRICT_ID_FK FOREIGN KEY (DISTRICT_ID) REFERENCES DISTRICT(DISTRICT_ID),
                         CONSTRAINT ADDRESS_UPAZILA_ID_FK FOREIGN KEY (UPAZILA_ID) REFERENCES UPAZILA(UPAZILA_ID)
);

-- Create Subject table
CREATE TABLE SUBJECT (
                         SUBJECT_ID BIGSERIAL,
                         SUBJECT_NAME VARCHAR(255)  NOT NULL,
                         NAME_LOCAL VARCHAR(255)  NOT NULL,
                         CREATED_AT TIMESTAMPTZ NOT NULL,
                         UPDATED_AT TIMESTAMPTZ NOT NULL,

                         CONSTRAINT SUBJECT_SUBJECT_ID_PK PRIMARY KEY (SUBJECT_ID),
                         CONSTRAINT SUBJECT_NAME_UK UNIQUE (SUBJECT_NAME),
                         CONSTRAINT SUBJECT_NAME_LOCAL_UK UNIQUE (NAME_LOCAL)
);

-- INSERT DATA INTO SUBJECT TABLE
INSERT INTO SUBJECT (SUBJECT_NAME, NAME_LOCAL, CREATED_AT, UPDATED_AT) VALUES
                                                                           ('Bangla', 'বাংলা', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('English', 'ইংরেজি', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('General Math', 'সাধারিত গণিত', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Higher Math', 'উচ্চতর গণিত', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('ICT', 'তথ্য ও যোগাযোগ প্রযুক্তি', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('Social Science', 'সামাজিক বিজ্ঞান', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                           ('General Science', 'সাধারিত বিজ্ঞান', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- Create ACADEMIC_INFO table
CREATE TABLE ACADEMIC_INFO (
                               ACADEMIC_INFO_ID BIGSERIAL,
                               ACADEMIC_LEVEL VARCHAR(255) NOT NULL,
                               GRADE DOUBLE PRECISION NOT NULL,
                               CLASS VARCHAR(255) NOT NULL,
                               CREATED_AT TIMESTAMPTZ NOT NULL,
                               UPDATED_AT TIMESTAMPTZ NOT NULL,

                               CONSTRAINT ACADEMIC_INFO_ACADEMIC_INFO_ID_PK PRIMARY KEY (ACADEMIC_INFO_ID)
);

-- Create join table ACADEMIC_INFO_SUBJECT for Many-to-Many relationship
CREATE TABLE ACADEMIC_INFO_SUBJECT (
                                       ACADEMIC_INFO_ID BIGINT,
                                       SUBJECT_ID BIGINT, 
                                       PRIMARY KEY (ACADEMIC_INFO_ID, SUBJECT_ID),
                                       FOREIGN KEY (ACADEMIC_INFO_ID) REFERENCES ACADEMIC_INFO(ACADEMIC_INFO_ID),
                                       FOREIGN KEY (SUBJECT_ID) REFERENCES SUBJECT(SUBJECT_ID)
);

-- Create CMS_USER table                                                                                                                                        ('PERMANENT', 2, 4, 5, TRUE, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
CREATE TABLE CMS_USER (
                          CMS_USER_ID BIGSERIAL,
                          MOBILE_NUMBER VARCHAR(255) UNIQUE NOT NULL,
                          EMAIL VARCHAR(255) UNIQUE,
                          NAME VARCHAR(255) NOT NULL,
                          GENDER VARCHAR(255) NOT NULL,
                          USER_STATUS VARCHAR(10) NOT NULL,
                          IS_ACTIVE BOOLEAN NOT NULL,
                          CREATED_AT TIMESTAMPTZ NOT NULL,
                          UPDATED_AT TIMESTAMPTZ NOT NULL,

                          CONSTRAINT CMS_USER_CMS_USER_ID_PK PRIMARY KEY (CMS_USER_ID),
                          CONSTRAINT CMS_USER_MOBILE_NUMBER_UQ UNIQUE (MOBILE_NUMBER),
                          CONSTRAINT CMS_USER_EMAIL_UQ UNIQUE (EMAIL),
                          CONSTRAINT CMS_USER_GENDER_CHK CHECK (GENDER IN ('MALE', 'FEMALE', 'OTHER')),
                          CONSTRAINT CMS_USER_STATUS_CHK CHECK (USER_STATUS IN ('ACTIVE', 'INACTIVE')),
                          CONSTRAINT CMS_USER_IS_ACTIVE_CHK CHECK (IS_ACTIVE IN (TRUE, FALSE))
);