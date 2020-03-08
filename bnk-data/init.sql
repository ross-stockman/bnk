use bnk;
CREATE TABLE vendor (
  _key VARCHAR(36) UNIQUE,
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50)
);
CREATE TABLE category (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50)
);
CREATE TABLE customer (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50)
);
CREATE TABLE bank (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50)
);
CREATE TABLE party (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50),
  vendor_id BIGINT,
  category_id BIGINT
);
CREATE TABLE account (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  name VARCHAR(50),
  initial_balance DECIMAL(13,2),
  bank_id BIGINT,
  customer_id BIGINT
);
CREATE TABLE `transaction` (
  _key VARCHAR(36),
  _version VARCHAR(36),
  _created DATETIME,
  _updated DATETIME,
  id BIGINT,
  trans_date DATETIME,
  check_num VARCHAR(20),
  description VARCHAR(200),
  memo VARCHAR(200),
  amount DECIMAL(13,2),
  account_id BIGINT,
  party_id BIGINT
);

CREATE VIEW transaction_detail AS SELECT
  transaction.id AS transaction_id,
  transaction.trans_date,
  transaction.check_num,
  transaction.description,
  transaction.memo,
  transaction.amount,
  transaction.party_id,
  party.name AS party_name,
  party.vendor_id,
  vendor.name AS vendor_name,
  party.category_id,
  category.name AS category_name,
  transaction.account_id,
  account.name AS account_name,
  account.bank_id,
  bank.name AS bank_name,
  account.customer_id,
  customer.name AS customer_name
FROM transaction
  INNER JOIN party ON transaction.party_id = party.id
  INNER JOIN vendor ON party.vendor_id = vendor.id
  INNER JOIN category ON party.category_id = category.id
  INNER JOIN account ON transaction.account_id = account.id
  INNER JOIN bank ON account.bank_id = bank.id
  INNER JOIN customer ON account.customer_id = customer.id
;

ALTER TABLE vendor ADD PRIMARY KEY (id);
ALTER TABLE category ADD PRIMARY KEY (id);
ALTER TABLE customer ADD PRIMARY KEY (id);
ALTER TABLE bank ADD PRIMARY KEY (id);
ALTER TABLE party ADD PRIMARY KEY (id);
ALTER TABLE account ADD PRIMARY KEY (id);
ALTER TABLE transaction ADD PRIMARY KEY (id);

ALTER TABLE vendor MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE category MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE customer MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE bank MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE party MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE account MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE transaction MODIFY id BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE party ADD FOREIGN KEY (vendor_id) REFERENCES vendor(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE party ADD FOREIGN KEY (category_id) REFERENCES category(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE account ADD FOREIGN KEY (bank_id) REFERENCES bank(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE account ADD FOREIGN KEY (customer_id) REFERENCES customer(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE transaction ADD FOREIGN KEY (account_id) REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE transaction ADD FOREIGN KEY (party_id) REFERENCES party(id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE vendor ADD CONSTRAINT uc_vendor_key UNIQUE (_key);
ALTER TABLE category ADD CONSTRAINT uc_category_key UNIQUE (_key);
ALTER TABLE customer ADD CONSTRAINT uc_customer_key UNIQUE (_key);
ALTER TABLE bank ADD CONSTRAINT uc_bank_key UNIQUE (_key);
ALTER TABLE party ADD CONSTRAINT uc_party_key UNIQUE (_key);
ALTER TABLE account ADD CONSTRAINT uc_account_key UNIQUE (_key);
ALTER TABLE transaction ADD CONSTRAINT uc_transaction_key UNIQUE (_key);

DELIMITER ;;

CREATE TRIGGER before_insert_vendor
BEFORE INSERT ON vendor
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_vendor
BEFORE UPDATE ON vendor
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_category
BEFORE INSERT ON category
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_category
BEFORE UPDATE ON category
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_customer
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_customer
BEFORE UPDATE ON customer
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_bank
BEFORE INSERT ON bank
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_bank
BEFORE UPDATE ON bank
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_party
BEFORE INSERT ON party
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_party
BEFORE UPDATE ON party
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_account
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_account
BEFORE UPDATE ON account
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;
CREATE TRIGGER before_insert_transaction
BEFORE INSERT ON transaction
FOR EACH ROW
BEGIN
  IF new._key IS NOT NULL THEN
    SET new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  ELSE
    SET new._key = UUID(), new._version = UUID(), new._created = CURRENT_TIMESTAMP, new._updated = CURRENT_TIMESTAMP;
  END IF;
END
;;
CREATE TRIGGER before_update_transaction
BEFORE UPDATE ON transaction
FOR EACH ROW
BEGIN
  IF new._version IS NOT NULL AND new._version <> old._version THEN
    SIGNAL SQLSTATE '45000' set message_text = 'Dirty write prevented';
  END IF;
  SET new._key = old._key, new._version = UUID(), new._created = old._created, new._updated = CURRENT_TIMESTAMP;
END
;;

DELIMITER ;

INSERT INTO vendor (name) VALUES ('uncategorized'),('sumac.io'),('rstockman.org') ;
INSERT INTO category (name) VALUES ('uncategorized'),('payroll'),('utility bill'),('grocery'),('health'),('auto'),('taxes'),('business'),('dining'),('education/training') ;
INSERT INTO party (name, vendor_id, category_id) VALUES ('uncategorized', 1, 1),('sumac.io supplies', 2, 8),('rstockman.org pay', 3, 2),('sumac.io pay', 2, 2) ;
INSERT INTO customer (name) VALUES ('trucker'),('trigger');
INSERT INTO bank (name) VALUES ('carver bank'),('springfield bank'),('goodneighbor bank');
INSERT INTO account (name, customer_id, bank_id, initial_balance) VALUES ('carver savings', 1, 1, 0),('carver checking', 1, 1, 0),('credit card', 1, 2, 0),('checkbook', 2, 3, 1232.97);
INSERT INTO transaction (trans_date, check_num, description, memo, amount, party_id, account_id) VALUES
('2020-01-24 22:15:10', '', 'payment 10000018', '', 473.32, 4, 1),
('2019-12-24 22:15:10', '', 'payment 10000017', '', 473.31, 4, 1),
('2019-11-24 22:15:10', '', 'payment 10000016', '', 473.31, 4, 1),
('2019-10-24 22:15:10', '', 'payment 10000015', '', 473.32, 4, 1),
('2019-09-24 22:15:10', '', 'payment 10000014', '', 473.32, 4, 4),
('2019-08-24 22:15:10', '', 'payment 10000013', '', 473.33, 4, 4),
('2019-07-24 22:15:10', '', 'payment 10000012', '', 473.32, 4, 4),
('2019-06-24 22:15:10', '', 'payment 10000011', '', 473.33, 4, 4),
('2019-05-24 22:15:10', '', 'payment 10000010', '', 473.32, 4, 4),
('2019-09-24 22:15:10', '', 'payment 10000009', '', 473.32, 4, 1),
('2019-08-24 22:15:10', '', 'payment 10000008', '', 473.33, 4, 1),
('2019-07-24 22:15:10', '', 'payment 10000007', '', 473.32, 4, 1),
('2019-06-24 22:15:10', '', 'payment 10000006', '', 473.33, 4, 1),
('2019-05-24 22:15:10', '', 'payment 10000005', '', 473.32, 4, 1),
('2019-04-24 22:15:10', '', 'payment 10000004', '', 473.32, 4, 1),
('2019-03-24 22:15:10', '', 'payment 10000003', '', 473.33, 3, 2),
('2019-02-24 22:15:10', '', 'payment 10000002', '', 473.32, 3, 2),
('2019-01-24 22:15:10', '', 'payment 10000001', '', 473.32, 3, 2),
('2019-01-23 22:15:10', '2322', 'plummer', 'frozen pipes', -243.94, 1, 3);
