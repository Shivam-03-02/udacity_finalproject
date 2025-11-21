-- Reference SQL schema for Critter Chronologer (MySQL)
-- This file is for inspection or manual execution. It is not in src/main/resources
-- and therefore will not be auto-executed by Spring.

CREATE TABLE customer (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  notes VARCHAR(255),
  phone_number VARCHAR(255)
);

CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE employee_availability (
  employee_id BIGINT NOT NULL,
  day_of_week VARCHAR(50),
  CONSTRAINT FK_employee_availability_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE employee_skill (
  employee_id BIGINT NOT NULL,
  skill VARCHAR(50),
  CONSTRAINT FK_employee_skill_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE pet (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  birth_date DATE,
  owner_id BIGINT,
  name VARCHAR(255),
  notes VARCHAR(255),
  type VARCHAR(50),
  CONSTRAINT FK_pet_owner FOREIGN KEY (owner_id) REFERENCES customer(id)
);

CREATE TABLE schedule (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  date DATE
);

CREATE TABLE schedule_activity (
  schedule_id BIGINT NOT NULL,
  activity VARCHAR(50),
  CONSTRAINT FK_schedule_activity_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);

CREATE TABLE schedule_employee (
  employee_id BIGINT NOT NULL,
  schedule_id BIGINT NOT NULL,
  PRIMARY KEY (employee_id, schedule_id),
  CONSTRAINT FK_schedule_employee_employee FOREIGN KEY (employee_id) REFERENCES employee(id),
  CONSTRAINT FK_schedule_employee_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);

CREATE TABLE schedule_pet (
  pet_id BIGINT NOT NULL,
  schedule_id BIGINT NOT NULL,
  PRIMARY KEY (pet_id, schedule_id),
  CONSTRAINT FK_schedule_pet_pet FOREIGN KEY (pet_id) REFERENCES pet(id),
  CONSTRAINT FK_schedule_pet_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);

-- Indexes can be added as needed for performance.
