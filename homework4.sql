DROP SCHEMA IF EXISTS homework3;
CREATE SCHEMA homework3;
USE homework3;

DROP SCHEMA IF EXISTS homework3_test;
CREATE SCHEMA homework3_test;
-- USE homework3_test;

DROP TABLE IF EXISTS opportunity;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS leads;
DROP TABLE IF EXISTS sales_rep_table;

CREATE TABLE sales_rep(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY(id)
    );

CREATE TABLE leads(
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(255),
phone_number VARCHAR(255),
email VARCHAR(255),
company_name VARCHAR(255),
sales_rep_id BIGINT,
PRIMARY KEY(id),
FOREIGN KEY (sales_rep_id) REFERENCES sales_rep(id)
);

CREATE TABLE account(
id BIGINT NOT NULL AUTO_INCREMENT,
industry VARCHAR(255),
employee_count INT,
city VARCHAR(255),
country VARCHAR(255),
PRIMARY KEY(id)
);

CREATE TABLE contact(
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(255),
phone_number VARCHAR(255),
email VARCHAR(255),
company_name VARCHAR(255),
account_id BIGINT,
PRIMARY KEY(id),
FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE opportunity(
    id BIGINT NOT NULL AUTO_INCREMENT,
    product VARCHAR(255),
    quantity INT,
    decision_maker_id BIGINT,
    status VARCHAR(255),
    account_id BIGINT,
    sales_rep_id BIGINT,
	PRIMARY KEY(id),
	FOREIGN KEY (account_id) REFERENCES account(id),
	FOREIGN KEY (decision_maker_id) REFERENCES contact(id),
	FOREIGN KEY (sales_rep_id) REFERENCES sales_rep(id)
    );
    
    