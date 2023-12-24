-- Create Division table
CREATE TABLE DIVISION (
                          divisionId SERIAL,
                          name VARCHAR(255) NOT NULL,
                          nameLocal VARCHAR(255)  NOT NULL,
                          active BOOLEAN NOT NULL,

                          CONSTRAINT DIVISION_DIVISION_ID_PK PRIMARY KEY (divisionId),
                          CONSTRAINT DIVISION_NAME_UK UNIQUE (name),
                          CONSTRAINT DIVISION_NAME_LOCAL_UK UNIQUE (nameLocal),
                          CONSTRAINT DIVISION_ACTIVE_CHK CHECK (active IN (TRUE, FALSE))
);

-- Create District table
CREATE TABLE DISTRICT (
                          districtId SERIAL,
                          name VARCHAR(255)  NOT NULL,
                          nameLocal VARCHAR(255)  NOT NULL,
                          active BOOLEAN NOT NULL,
                          divisionId INT NOT NULL,

                          CONSTRAINT DISTRICT_DISTRICT_ID_PK PRIMARY KEY (districtId),
                          CONSTRAINT DISTRICT_NAME_UQ UNIQUE (name),
                          CONSTRAINT DISTRICT_NAME_LOCAL_UQ UNIQUE (nameLocal),
                          CONSTRAINT DISTRICT_ACTIVE_CHK CHECK (active IN (TRUE, FALSE)),
                          CONSTRAINT DISTRICT_DIVISION_FK FOREIGN KEY (divisionId) REFERENCES Division(divisionId)
);

-- Create Upazila table
CREATE TABLE UPAZILA (
                         upazilaId SERIAL,
                         name VARCHAR(255)  NOT NULL,
                         nameLocal VARCHAR(255)  NOT NULL,
                         active BOOLEAN NOT NULL,
                         districtId INT,

                         CONSTRAINT UPAZILA_UPAZILA_ID_PK PRIMARY KEY (upazilaId),
                         CONSTRAINT UPAZILA_NAME_UQ UNIQUE (name),
                         CONSTRAINT UPAZILA_NAME_LOCAL_UQ UNIQUE (nameLocal),
                         CONSTRAINT UPAZILA_ACTIVE_CHK CHECK (active IN (TRUE, FALSE)),
                         CONSTRAINT UPAZILA_DISTRICT_FK FOREIGN KEY (districtId) REFERENCES District(districtId)
);

-- Create Subject table
CREATE TABLE SUBJECT (
                         subjectId SERIAL,
                         name VARCHAR(255) NOT NULL,
                         nameLocal VARCHAR(255) NOT NULL,

                         CONSTRAINT SUBJECT_SUBJECT_ID_PK PRIMARY KEY (subjectId),
                         CONSTRAINT SUBJECT_NAME_UQ UNIQUE (name),
                         CONSTRAINT SUBJECT_NAME_LOCAL_UQ UNIQUE (nameLocal)
);


-- Insert data into Division
INSERT INTO DIVISION (name, nameLocal, active) VALUES
                                                   ('Dhaka', 'ঢাকা', true),
                                                   ('Rajshahi', 'রাজশাহী', true);

-- Insert data into District
INSERT INTO DISTRICT (name, nameLocal, active, divisionId) VALUES
                                                               ('Dhaka', 'ঢাকা', true, 1),
                                                               ('Gazipur', 'গাজীপুর', true, 1),
                                                               ('Narayanganj', 'নারায়ণগঞ্জ', true, 1),
                                                               ('Rajshahi', 'রাজশাহী', true, 2),
                                                               ('Bogra', 'বগুড়া', true, 2),
                                                               ('Naogaon', 'নওগাঁ', true, 2);

-- Insert data into Upazila
INSERT INTO UPAZILA (name, nameLocal, active, districtId) VALUES
                                                              ('Dhaka City', 'ঢাকা সিটি', true, 1),
                                                              ('Gazipur City', 'গাজীপুর সিটি', true, 2),
                                                              ('Narayanganj City', 'নারায়ণগঞ্জ সিটি', true, 3),
                                                              ('Rajshahi City', 'রাজশাহী সিটি', true, 4),
                                                              ('Bogra City', 'বগুড়া সিটি', true, 5),
                                                              ('Naogaon City', 'নওগাঁ সিটি', true, 6);

-- Insert data into Subject
INSERT INTO SUBJECT (name, nameLocal) VALUES
                                          ('Bangla', 'বাংলা'),
                                          ('English', 'ইংরেজি'),
                                          ('General Math', 'সাধারিত গণিত'),
                                          ('Higher Math', 'উচ্চতর গণিত'),
                                          ('ICT', 'তথ্য ও যোগাযোগ প্রযুক্তি'),
                                          ('Social Science', 'সামাজিক বিজ্ঞান'),
                                          ('General Science', 'সাধারিত বিজ্ঞান');
