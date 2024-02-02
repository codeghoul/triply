-- Use the database
USE triply;

-- Create role table
CREATE TABLE IF NOT EXISTS `role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create company table
CREATE TABLE IF NOT EXISTS `company` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create employee table
CREATE TABLE IF NOT EXISTS `employee` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `company_id` BIGINT DEFAULT NULL,
    `password` VARCHAR(255) DEFAULT NULL,
    `username` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee` (`company_id` , `username`),
    CONSTRAINT `FK_employee_company` FOREIGN KEY (`company_id`)
        REFERENCES `company` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create employee_role table
CREATE TABLE IF NOT EXISTS `employee_role` (
    `employee_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    PRIMARY KEY (`employee_id` , `role_id`),
    CONSTRAINT `FK_employee_role_employee` FOREIGN KEY (`employee_id`)
        REFERENCES `employee` (`id`),
    CONSTRAINT `FK_employee_role_role` FOREIGN KEY (`role_id`)
        REFERENCES `role` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create vehicle_model table
CREATE TABLE IF NOT EXISTS `vehicle_model` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `emission_per_km` DOUBLE DEFAULT NULL,
    `fuel_type` VARCHAR(32) DEFAULT NULL,
    `make` VARCHAR(255) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_vehicle_model_name` (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create vehicle table
CREATE TABLE IF NOT EXISTS `vehicle` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `registration_number` VARCHAR(255) DEFAULT NULL,
    `employee_id` BIGINT DEFAULT NULL,
    `vehicle_model_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_vehicle_registration` (`registration_number`),
    UNIQUE KEY `uk_vehicle_employee` (`employee_id`),
    KEY `FK_vehicle_employee` (`employee_id`),
    KEY `FK_vehicle_model` (`vehicle_model_id`),
    CONSTRAINT `FK_vehicle_employee` FOREIGN KEY (`employee_id`)
        REFERENCES `employee` (`id`),
    CONSTRAINT `FK_vehicle_model` FOREIGN KEY (`vehicle_model_id`)
        REFERENCES `vehicle_model` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;


-- Create mileage table
CREATE TABLE IF NOT EXISTS `mileage` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `distance_travelled` DOUBLE DEFAULT NULL,
    `energy_consumed` DOUBLE DEFAULT NULL,
    `fuel_consumed` DOUBLE DEFAULT NULL,
    `month` ENUM('JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER') DEFAULT NULL,
    `total_emission` DOUBLE DEFAULT NULL,
    `vehicle_id` BIGINT DEFAULT NULL,
    `week` INT DEFAULT NULL,
    `year` SMALLINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_mileage_vehicle` FOREIGN KEY (`vehicle_id`)
        REFERENCES `vehicle` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- Create refresh_token table
CREATE TABLE IF NOT EXISTS `refresh_token` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `created_at` DATETIME(6) NOT NULL,
    `created_by` BIGINT DEFAULT NULL,
    `is_deleted` BIT(1) DEFAULT NULL,
    `updated_at` DATETIME(6) NOT NULL,
    `updated_by` BIGINT DEFAULT NULL,
    `version` BIGINT DEFAULT NULL,
    `employee_id` BIGINT DEFAULT NULL,
    `expiry_date` DATETIME(6) NOT NULL,
    `token` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_refresh_token_token` (`token`),
    CONSTRAINT `FK_refresh_token_employee` FOREIGN KEY (`employee_id`)
        REFERENCES `employee` (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;