-- Create Division table
CREATE TABLE division (
                          division_id INT AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          name_local VARCHAR(255) NOT NULL,
                          active BOOLEAN,
                          CONSTRAINT division_id_pk PRIMARY KEY (division_id),
                          CONSTRAINT division_name_uq UNIQUE (name),
                          CONSTRAINT division_name_local_uq UNIQUE (name_local),
                          CONSTRAINT division_active_chk CHECK (active IN (TRUE, FALSE))
);


-- Insert data into Division
INSERT INTO division (name, name_local, active) VALUES
                                                    ('Dhaka', 'ঢাকা', true),
                                                    ('Chittagong', 'চট্টগ্রাম', true),
                                                    ('Khulna', 'খুলনা', true),
                                                    ('Rajshahi', 'রাজশাহী', true),
                                                    ('Barisal', 'বরিশাল', true),
                                                    ('Sylhet', 'সিলেট', true),
                                                    ('Rangpur', 'রংপুর', true),
                                                    ('Mymensingh', 'ময়মনসিংহ', true);



