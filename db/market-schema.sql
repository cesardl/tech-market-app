create table DISCOUNT_CODE
(
  DISCOUNT_CODE CHAR(1) not null
    primary key,
  RATE          DECIMAL(4, 2)
);

create table MANUFACTURER
(
  MANUFACTURER_ID INTEGER not null
    primary key,
  NAME            VARCHAR(30),
  ADDRESSLINE1    VARCHAR(30),
  ADDRESSLINE2    VARCHAR(30),
  CITY            VARCHAR(25),
  STATE           CHAR(2),
  ZIP             CHAR(10),
  PHONE           VARCHAR(12),
  FAX             VARCHAR(12),
  EMAIL           VARCHAR(40),
  REP             VARCHAR(30)
);

create table MICRO_MARKET
(
  ZIP_CODE    VARCHAR(10) not null
    primary key,
  RADIUS      DOUBLE,
  AREA_LENGTH DOUBLE,
  AREA_WIDTH  DOUBLE
);

create table CUSTOMER
(
  CUSTOMER_ID   INTEGER     not null
    primary key,
  DISCOUNT_CODE CHAR(1)     not null
    constraint FOREIGNKEY_DISCOUNT_CODE
    references DISCOUNT_CODE,
  ZIP           VARCHAR(10) not null
    constraint FOREIGNKEY_ZIP
    references MICRO_MARKET,
  NAME          VARCHAR(30),
  ADDRESSLINE1  VARCHAR(30),
  ADDRESSLINE2  VARCHAR(30),
  CITY          VARCHAR(25),
  STATE         CHAR(2),
  PHONE         CHAR(12),
  FAX           CHAR(12),
  EMAIL         VARCHAR(40),
  CREDIT_LIMIT  INTEGER
);

create table PRODUCT_CODE
(
  PROD_CODE     CHAR(2) not null
    primary key,
  DISCOUNT_CODE CHAR(1) not null,
  DESCRIPTION   VARCHAR(10)
);

create table PRODUCT
(
  PRODUCT_ID       INTEGER not null
    primary key,
  MANUFACTURER_ID  INTEGER not null
    constraint FOREIGNKEY_MANUFACTURER_ID
    references MANUFACTURER,
  PRODUCT_CODE     CHAR(2) not null
    constraint FOREIGNKEY_PRODUCT_CODE
    references PRODUCT_CODE,
  PURCHASE_COST    DECIMAL(12, 2),
  QUANTITY_ON_HAND INTEGER,
  MARKUP           DECIMAL(4, 2),
  AVAILABLE        VARCHAR(5),
  DESCRIPTION      VARCHAR(50)
);

create table PURCHASE_ORDER
(
  ORDER_NUM       INTEGER not null
    primary key,
  CUSTOMER_ID     INTEGER not null
    constraint FOREIGNKEY_CUSTOMER_ID
    references CUSTOMER,
  PRODUCT_ID      INTEGER not null
    constraint FOREIGNKEY_PRODUCT_ID
    references PRODUCT,
  QUANTITY        SMALLINT,
  SHIPPING_COST   DECIMAL(12, 2),
  SALES_DATE      DATE,
  SHIPPING_DATE   DATE,
  FREIGHT_COMPANY VARCHAR(30)
);

INSERT INTO APP.PRODUCT_CODE (PROD_CODE, DISCOUNT_CODE, DESCRIPTION) VALUES ('SW', 'M', 'Software');
INSERT INTO APP.PRODUCT_CODE (PROD_CODE, DISCOUNT_CODE, DESCRIPTION) VALUES ('HW', 'H', 'Hardware');

INSERT INTO APP.MANUFACTURER (MANUFACTURER_ID, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, ZIP, PHONE, FAX, EMAIL, REP) VALUES (19985678, 'Happy End Searching', '5 81st Street', 'Suite 100', 'Mountain View', 'CA', '94043', '650-555-0102', '408-555-0103', 'happysearching@example.com', 'John Snow');
INSERT INTO APP.MANUFACTURER (MANUFACTURER_ID, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, ZIP, PHONE, FAX, EMAIL, REP) VALUES (19986982, 'Smith Bird Watching', '4000 Finch Circle', 'Building 14', 'Santa Clara', 'CA', '95051     ', '650-555-0111', '408-555-0112', 'www.sbw@example.com', 'Brian Washington');

INSERT INTO APP.PRODUCT (PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION) VALUES (980001, 19985678, 'SW', 1095.00, 800000, 8.25, 'TRUE', 'Identity Server');
INSERT INTO APP.PRODUCT (PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION) VALUES (980005, 19986982, 'SW', 11500.99, 500, 55.25, 'TRUE', 'Accounting Application');
