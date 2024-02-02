-- Create Some Companies
INSERT INTO `triply`.`company`
(`id`,
`created_at`,
`created_by`,
`is_deleted`,
`updated_at`,
`updated_by`,
`version`,
`name`)
VALUES
(1, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'Triply'),
(2, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'Jayesh Co.'),
(3, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'Miniso');

-- Create Roles
INSERT INTO `triply`.`role`
(`id`,
`created_at`,
`created_by`,
`is_deleted`,
`updated_at`,
`updated_by`,
`version`,
`name`)
VALUES
(1, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'ROLE_SUPER_ADMIN'),
(2, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'ROLE_COMPANY_ADMIN'),
(3, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 'ROLE_COMPANY_EMPLOYEE');

-- Create Super Admin, using hashed password for security
INSERT INTO `triply`.`employee`
(`id`,
`created_at`,
`created_by`,
`is_deleted`,
`updated_at`,
`updated_by`,
`version`,
`company_id`,
`password`,
`username`)
VALUES
(1, '2024-02-02 12:00:00', 0, false, '2024-02-02 12:00:00', 0, 1, 1, '$2a$09$61n5rmDgufRunUN7LH7JwutvrRDVwr61FHNudUy1dpiP4j1a777g2', 'JOHN_DOE');

-- Attach Roles
INSERT INTO `triply`.`employee_role`
(`employee_id`,
`role_id`)
VALUES
(1, 1),
(1, 2),
(1, 3);
