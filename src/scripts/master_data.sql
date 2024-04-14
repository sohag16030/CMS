-- INSERT DATA INTO DIVISION TABLE
INSERT INTO DIVISION (NAME, ACTIVE, CREATED_AT, UPDATED_AT) VALUES
    ('Dhaka', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Rajshahi', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT DATA INTO DISTRICT TABLE
INSERT INTO DISTRICT (NAME, ACTIVE, DIVISION_ID, CREATED_AT, UPDATED_AT) VALUES
    ('Dhaka', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Gazipur', TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Rajshahi', TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Chapainawabganj',  TRUE, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT DATA INTO UPAZILA TABLE
INSERT INTO UPAZILA (NAME, ACTIVE, DISTRICT_ID, CREATED_AT, UPDATED_AT) VALUES
    -- UPAZILA UNDER DHAKA
    ('Tangail Sadar',  TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sakhipur',  TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Basail',  TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- UPAZILA UNDER CHAPAINAWABGANJ
    ('Bholahat',  TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Gomastapur',  TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Nachole',  TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Chapainawabganj Sadar',  TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Shibganj', TRUE, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERT DATA INTO SUBJECT TABLE
INSERT INTO SUBJECT (SUBJECT_NAME, CREATED_AT, UPDATED_AT) VALUES
    ('Bangla',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('English', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('General Math',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Higher Math',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ICT',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Social Science',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('General Science', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--- INSERT DATA INTO RATING TABEL
-- INSERT INTO CONTENT_RATING (STAR, RATING_TYPE) VALUES
--     ('*', 'BRONZE'),
--     ('**', 'SILVER'),
--     ('***', 'GOLD'),
--     ('****', 'PLATINUM'),
--     ('*****', 'DIAMOND');
-- INSERT INTO cms_user (mobile_number, email, name, user_name, password, roles, gender, is_active, created_at, updated_at) VALUES
--                                                                                                                              ('01712345678', 'rafi_rahman@example.com', 'Rafi Rahman', 'rafi_rahman', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01823456789', 'rahia_islam@example.com', 'Rahia Islam', 'rahia_islam', 'password', 'admin', 'FEMALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01634567890', 'anwar_ali@example.com', 'Anwar Ali', 'anwar_ali', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01545678901', 'rakib_khan@example.com', 'Rakib Khan', 'rakib_khan', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01456789012', 'rasel_hossain@example.com', 'Rasel Hossain', 'rasel_hossain', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01367890123', 'selim_ahmed@example.com', 'Selim Ahmed', 'selim_ahmed', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01278901234', 'safin_chowdhury@example.com', 'Safin Chowdhury', 'safin_chowdhury', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01189012345', 'raju_islam@example.com', 'Raju Islam', 'raju_islam', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01090123456', 'sobuj_rahman@example.com', 'Sobuj Rahman', 'sobuj_rahman', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01901234567', 'atika_hossain@example.com', 'Atika Hossain', 'atika_hossain', 'password', 'admin', 'FEMALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01712341234', 'karim_ahmed@example.com', 'Karim Ahmed', 'karim_ahmed', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01823452345', 'rafia_rahman2@example.com', 'Rafia Rahman', 'rafia_rahman2', 'password', 'admin', 'FEMALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01634563456', 'sahi_islam2@example.com', 'Sahi Islam', 'sahi_islam2', 'password', 'user', 'FEMALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01545674567', 'anwar_ali2@example.com', 'Anwar Ali', 'anwar_ali2', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01456785678', 'rakib_khan2@example.com', 'Rakib Khan', 'rakib_khan2', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01367896789', 'rasel_hossain2@example.com', 'Rasel Hossain', 'rasel_hossain2', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01278907890', 'selim_ahmed2@example.com', 'Selim Ahmed', 'selim_ahmed2', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01189018901', 'safin_chowdhury2@example.com', 'Safin Chowdhury', 'safin_chowdhury2', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01090129012', 'raju_islam2@example.com', 'Raju Islam', 'raju_islam2', 'password', 'user', 'MALE', true, current_timestamp, current_timestamp),
--                                                                                                                              ('01901290123', 'sobuj_rahman2@example.com', 'Sobuj Rahman', 'sobuj_rahman2', 'password', 'admin', 'MALE', true, current_timestamp, current_timestamp);
