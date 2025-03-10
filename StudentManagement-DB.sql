CREATE DATABASE StudentManagement;
USE StudentManagement;
CREATE TABLE Country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE State (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES Country(id) ON DELETE CASCADE
);
INSERT INTO Country (name) VALUES 
('India'), 
('USA'), 
('Canada'),
('UK'),
('Australia');
INSERT INTO State (name, country_id) VALUES
-- States for India
('Telangana', 1),
('Karnataka', 1),
('Andhra Pradesh', 1),
('Tamil Nadu', 1),
('Uttar Pradesh', 1),
-- States for USA
('California', 2),
('Texas', 2),
('New York', 2),
('Florida', 2),
('Illinois', 2),
-- States for Canada
('Ontario', 3),
('Quebec', 3),
('British Columbia', 3),
('Alberta', 3),
('Nova Scotia', 3),
-- States for UK
('England', 4),
('Scotland', 4),
('Wales', 4),
('Northern Ireland', 4),
('Cornwall', 4),
-- States for Australia
('New South Wales', 5),
('Queensland', 5),
('Victoria', 5),
('South Australia', 5),
('Western Australia', 5);

-- Create Registration Table for storing user registration data
CREATE TABLE registration (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    student_age INT NOT NULL,
    student_gender VARCHAR(50) NOT NULL,
    student_phone VARCHAR(15),
    student_blood_group ENUM('O+', 'O-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-') NOT NULL, 
    student_email VARCHAR(255) UNIQUE NOT NULL,
    student_password VARCHAR(255) NOT NULL
);
-- Create Dashboard Table for storing additional student data
CREATE TABLE dashboard (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(255),
    student_age INT,
    student_gender VARCHAR(50),
    student_dob DATE,
    student_phone VARCHAR(15),
    student_date_of_joining DATE,
    student_fee DECIMAL(10, 2),
    student_branch VARCHAR(50),
    student_country INT,
    student_state INT,
    student_address TEXT,
    FOREIGN KEY (student_country) REFERENCES Country(id),
    FOREIGN KEY (student_state) REFERENCES State(id)
);

SELECT * FROM Country;
SELECT * FROM State;
SELECT * FROM registration;
SELECT * FROM dashboard;

Drop table Country;
Drop table State;
drop table registration;
drop table dashboard;

-------------------------------------------------------------------------------------------------------------------------------------------------
