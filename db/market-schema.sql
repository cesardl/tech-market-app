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
