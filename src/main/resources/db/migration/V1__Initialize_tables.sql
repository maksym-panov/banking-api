CREATE SCHEMA IF NOT EXISTS domain;

CREATE TABLE domain.branch (
    branch_id   BIGINT          NOT NULL,
    "name"      VARCHAR(255)    NOT NULL,
    region      VARCHAR(63)     NOT NULL,
    city        VARCHAR(255)    NOT NULL,
    address     VARCHAR(255)    NOT NULL,
    postal_code CHAR(5)         NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS domain.branch_id_seq
START WITH 1
INCREMENT BY 1
OWNED BY branch.branch_id;

ALTER TABLE domain.branch
ADD CONSTRAINT branch_PK
PRIMARY KEY (branch_id);

ALTER TABLE domain.branch
ADD CONSTRAINT branch_name_AK
UNIQUE ("name");

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.department (
    dept_id BIGINT          NOT NULL,
    "name"  VARCHAR(255)    NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS domain.department_id_seq
START WITH 1
INCREMENT BY 1
OWNED BY department.dept_id;

ALTER TABLE domain.department
ADD CONSTRAINT department_PK
PRIMARY KEY (dept_id);

ALTER TABLE domain.department
ADD CONSTRAINT department_name_AK
UNIQUE ("name");

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.employee (
    emp_id              BIGINT      NOT NULL,
    fname               VARCHAR(63) NOT NULL,
    lname               VARCHAR(63) NOT NULL,
    title               VARCHAR(63) NOT NULL,
    start_date          DATE        NOT NULL,
    end_date            DATE,
    superior_emp_id     BIGINT,
    dept_id             BIGINT,
    assigned_branch_id  BIGINT
);

CREATE SEQUENCE IF NOT EXISTS domain.employee_id_seq
START WITH 1
INCREMENT BY 10
OWNED BY employee.emp_id;

ALTER TABLE domain.employee
ADD CONSTRAINT employee_PK
PRIMARY KEY (emp_id);

ALTER TABLE domain.employee
ADD CONSTRAINT employee_superior_employee_FK
FOREIGN KEY (superior_emp_id) REFERENCES domain.employee;

ALTER TABLE domain.employee
ADD CONSTRAINT employee_department_FK
FOREIGN KEY (dept_id) REFERENCES domain.department;

ALTER TABLE domain.employee
ADD CONSTRAINT employee_branch_FK
FOREIGN KEY (assigned_branch_id) REFERENCES domain.branch;

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.product_type (
    product_type_cd VARCHAR(10)     NOT NULL,
    "name"          VARCHAR(255)    NOT NULL
);

ALTER TABLE domain.product_type
ADD CONSTRAINT product_type_PK
PRIMARY KEY (product_type_cd);

ALTER TABLE domain.product_type
ADD CONSTRAINT product_type_name_AK
UNIQUE ("name");

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.product (
    product_cd      VARCHAR(10)     NOT NULL,
    "name"          VARCHAR(255)    NOT NULL,
    product_type_cd VARCHAR(10)     NOT NULL,
    date_offered    DATE            NOT NULL,
    date_retired    DATE            NOT NULL
);

ALTER TABLE domain.product
ADD CONSTRAINT product_PK
PRIMARY KEY (product_cd);

ALTER TABLE domain.product
ADD CONSTRAINT product_product_type_FK
FOREIGN KEY (product_type_cd) REFERENCES domain.product_type;

ALTER TABLE domain.product
ADD CONSTRAINT product_name_AK
UNIQUE ("name");

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.customer (
    cust_id                 UUID            NOT NULL,
    cust_type_descriptor    CHAR(2)         NOT NULL,
    region                  VARCHAR(63)     NOT NULL,
    city                    VARCHAR(63)     NOT NULL,
    address                 VARCHAR(255)    NOT NULL,
    postal_code             CHAR(5)         NOT NULL
);

ALTER TABLE domain.customer
ADD CONSTRAINT customer_PK
PRIMARY KEY (cust_id);

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.individual (
    cust_id     UUID        NOT NULL,
    tin         CHAR(10)    NOT NULL, -- TAXPAYER IDENTIFICATION NUMBER
    fname       VARCHAR(63) NOT NULL,
    lname       VARCHAR(63) NOT NULL,
    birth_day   DATE        NOT NULL
);

ALTER TABLE domain.individual
ADD CONSTRAINT individual_PK
PRIMARY KEY (cust_id);

ALTER TABLE domain.individual
ADD CONSTRAINT individual_customer_FK
FOREIGN KEY (cust_id) REFERENCES domain.customer;

ALTER TABLE domain.individual
ADD CONSTRAINT individual_tin_AK
UNIQUE (tin);

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.business (
    cust_id     UUID            NOT NULL,
    usreou      CHAR(8)         NOT NULL, -- Unified State Register of Enterprises and Organizations of Ukraine
    "name"      VARCHAR(255)    NOT NULL,
    state_id    VARCHAR(10)     NOT NULL,
    incorp_date DATE
);

ALTER TABLE domain.business
ADD CONSTRAINT business_PK
PRIMARY KEY (cust_id);

ALTER TABLE domain.business
ADD CONSTRAINT business_customer_FK
FOREIGN KEY (cust_id) REFERENCES domain.customer;

ALTER TABLE domain.business
ADD CONSTRAINT business_usreou_AK
UNIQUE (usreou);

ALTER TABLE domain.business
ADD CONSTRAINT business_state_id_allowed_values
CHECK ( state_id IN ('ACTIVE', 'INACTIVE') );

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.officer (
    officer_id  BIGINT NOT NULL,
    cust_id     UUID NOT NULL,
    fname       VARCHAR(63) NOT NULL,
    lname       VARCHAR(63) NOT NULL,
    title       VARCHAR(63),
    start_date  DATE NOT NULL,
    end_date    DATE
);

CREATE SEQUENCE IF NOT EXISTS domain.officer_id_seq
START WITH 1
INCREMENT BY 5
OWNED BY officer.officer_id;

ALTER TABLE domain.officer
ADD CONSTRAINT officer_PK
PRIMARY KEY (officer_id);

ALTER TABLE domain.officer
ADD CONSTRAINT officer_business_FK
FOREIGN KEY (cust_id) REFERENCES domain.business;

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.account (
    account_id          BIGINT          NOT NULL,
    product_cd          VARCHAR(10)     NOT NULL,
    cust_id             UUID            NOT NULL,
    open_date           DATE            NOT NULL,
    close_date          DATE,
    last_activity_date  DATE            NOT NULL,
    status              VARCHAR(10)     NOT NULL,
    open_branch_id      BIGINT,
    open_emp_id         BIGINT,
    avail_balance       DECIMAL(19, 2)  NOT NULL,
    pending_balance     DECIMAL(19, 2)  NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS domain.account_id_seq
START WITH 1
INCREMENT BY 1
OWNED BY account.account_id;

ALTER TABLE domain.account
ADD CONSTRAINT account_PK
PRIMARY KEY (account_id);

ALTER TABLE domain.account
ADD CONSTRAINT account_product_FK
FOREIGN KEY (product_cd) REFERENCES domain.product;

ALTER TABLE domain.account
ADD CONSTRAINT account_customer_FK
FOREIGN KEY (cust_id) REFERENCES domain.customer;

ALTER TABLE domain.account
ADD CONSTRAINT account_open_branch_FK
FOREIGN KEY (open_branch_id) REFERENCES domain.branch;

ALTER TABLE domain.account
ADD CONSTRAINT account_open_employee_FK
FOREIGN KEY (open_emp_id) REFERENCES domain.employee;

--------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS domain.transaction (
    txn_id              BIGINT          NOT NULL,
    txn_time            TIMESTAMP       NOT NULL,
    account_id          BIGINT          NOT NULL,
    txn_type_cd         VARCHAR(6)      NOT NULL,
    amount              DECIMAL(19, 2)  NOT NULL,
    description         TEXT,
    teller_emp_id       BIGINT,
    funds_avail_date    DATE            NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS domain.transaction_id_seq
START WITH 1
INCREMENT BY 10
OWNED BY transaction.txn_id;

ALTER TABLE domain.transaction
ADD CONSTRAINT transaction_PK
PRIMARY KEY (txn_id);

ALTER TABLE domain.transaction
ADD CONSTRAINT transaction_account_FK
FOREIGN KEY (account_id) REFERENCES domain.account;

ALTER TABLE domain.transaction
ADD CONSTRAINT transaction_teller_employee_FK
FOREIGN KEY (teller_emp_id) REFERENCES domain.employee;

ALTER TABLE domain.transaction
ADD CONSTRAINT transaction_type_allowed_values
CHECK ( txn_type_cd IN ('DEBIT', 'CREDIT') );