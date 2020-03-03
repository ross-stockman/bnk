use auth;
CREATE TABLE user (
  username VARCHAR(20) UNIQUE,
  password VARCHAR(50),
  expiration_date DATETIME,
  active TINYINT(1),
  locked TINYINT(1)
);
CREATE TABLE application (
  name VARCHAR(50) unique
);
CREATE TABLE role (
  name varchar(50) unique
);
CREATE TABLE application_role (
  id INT UNIQUE,
  application_name VARCHAR(50),
  role_name VARCHAR(50)
);
CREATE TABLE user_role (
  username VARCHAR(20),
  application_role_id INT
);
CREATE VIEW application_role_detail AS SELECT
  concat_ws ('_', application_name, role_name) AS role_id,
  application_name AS application,
  role_name AS role,
  id AS application_role_id
FROM application_role
;
CREATE VIEW user_role_detail AS SELECT
  username,
  user_role.application_role_id AS application_role_id,
  role_id,
  application,
  role
FROM user_role
  INNER JOIN application_role_detail ON user_role.application_role_id = application_role_detail.application_role_id
;
INSERT INTO user (username, password, expiration_date, active, locked) VALUES ('trucker', 'trucker', current_timestamp, 1, 0), ('dooley', 'dooley', current_timestamp, 1, 0), ('twitch', 'twitch', current_timestamp, 1, 0);
INSERT INTO application (name) VALUES ('bnk-api-vendor'), ('bnk-api-category');
INSERT INTO role (name) VALUES ('READ'), ('WRITE');
INSERT INTO application_role (id, application_name, role_name) VALUES (1, 'bnk-api-vendor', 'READ'), (2, 'bnk-api-vendor', 'WRITE'), (3, 'bnk-api-category', 'READ'), (4, 'bnk-api-category', 'WRITE');
INSERT INTO user_role (username, application_role_id) VALUES ('trucker', 1), ('trucker', 2), ('trucker', 3), ('dooley', 1), ('dooley', 3), ('dooley', 4), ('twitch', 1);
