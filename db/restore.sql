/*
 * restore.sql - rebuild the market database from the original sample backup.
 *
 * This is an ij script, not plain SQL: connect, run and exit are ij commands,
 * so DatabaseTestContext cannot replay this file the way it replays the others.
 * Run it from the REPO ROOT, because every path below is relative to it:
 *
 *   mvn -q clean compile exec:java -Dexec.classpathScope=test
 *       -Dexec.mainClass=org.apache.derby.tools.ij -Dexec.args=db/restore.sql
 *
 * Keep the clean: it removes target/, the database included, so the restore
 * always starts from nothing and runs without a single error.
 *
 * What it does, in order:
 *   1. connects to target/market-derby-db, creating it when absent
 *   2. runs db/market-schema.sql for the DDL
 *   3. empties every table, which drops the small test seed that file carries,
 *      so the backup below lands as the only content
 *   4. loads the seven APP_*.sql backup files in foreign-key order (109 rows)
 *   5. loads db/data.sql on top of it for volume (6,711 rows)
 *
 * For the backup WITHOUT the generated volume, drop the db/data.sql line at the
 * end. To restore somewhere else, edit the connect line - the commented one is
 * the network server that db/jdbc.properties points the app at.
 *
 * Run against a database that already holds a restore (no clean), step 2 is noisy
 * but harmless: X0Y32 "already exists" for each of the 7 tables, then 23505 for
 * each of the 13 rows in its seed, which the previous restore already covers.
 * Step 3 empties the tables regardless, so the end state is identical - verified
 * by running this twice and comparing the counts below.
 */

connect 'jdbc:derby:target/market-derby-db;create=true;user=app;password=app';
-- connect 'jdbc:derby://localhost:1527/market-derby-db;user=app;password=app';

/* 2. schema. Brings its own small test seed, which step 3 removes. */
run 'db/market-schema.sql';

/* 3. clear, children before parents */
DELETE FROM APP.PURCHASE_ORDER;
DELETE FROM APP.PRODUCT;
DELETE FROM APP.CUSTOMER;
DELETE FROM APP.PRODUCT_CODE;
DELETE FROM APP.MANUFACTURER;
DELETE FROM APP.MICRO_MARKET;
DELETE FROM APP.DISCOUNT_CODE;

/* 4. the original backup, parents before children */
run 'APP_DISCOUNT_CODE.sql';
run 'APP_MICRO_MARKET.sql';
run 'APP_MANUFACTURER.sql';
run 'APP_PRODUCT_CODE.sql';
run 'APP_CUSTOMER.sql';
run 'APP_PRODUCT.sql';
run 'APP_PURCHASE_ORDER.sql';

/* 5. generated volume on top. Delete this line for a backup-only restore. */
run 'db/data.sql';

/* what landed */
select 'DISCOUNT_CODE ' as TABLE_LOADED, count(*) as ROW_COUNT from APP.DISCOUNT_CODE
union all select 'MICRO_MARKET  ', count(*) from APP.MICRO_MARKET
union all select 'MANUFACTURER  ', count(*) from APP.MANUFACTURER
union all select 'PRODUCT_CODE  ', count(*) from APP.PRODUCT_CODE
union all select 'CUSTOMER      ', count(*) from APP.CUSTOMER
union all select 'PRODUCT       ', count(*) from APP.PRODUCT
union all select 'PURCHASE_ORDER', count(*) from APP.PURCHASE_ORDER;

disconnect;
exit;
