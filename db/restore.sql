/*
 * restore.sql - rebuild the market database from the original sample backup.
 *
 * Plain SQL for H2's RunScript tool, which takes the target database as an
 * argument, so this file never says WHERE it lands. Run it from the REPO ROOT:
 * every RUNSCRIPT path below is relative to the working directory.
 *
 *   mvn -q compile exec:java -Dexec.mainClass=org.h2.tools.RunScript
 *       -Dexec.args="-url jdbc:h2:file:./target/market-db -user app -password app -script db/restore.sql"
 *
 * Point -url at the main profile to restore the database the app itself reads:
 *
 *       -url jdbc:h2:file:~/ansel/market-db
 *
 * DESTRUCTIVE, and meant to be: step 1 drops the whole APP schema, so whatever
 * that database already held is gone. That is also what makes it idempotent -
 * no mvn clean needed, and the tenth run ends exactly where the first one did.
 *
 * Only one JVM at a time may open an H2 file database, so close the app before
 * restoring into ~/ansel (or add AUTO_SERVER=TRUE to both URLs to share one).
 *
 * What it does, in order:
 *   1. drops and recreates the APP schema
 *   2. runs db/market-schema.sql for the DDL
 *   3. empties every table, which drops the small test seed that file carries,
 *      so the backup below lands as the only content
 *   4. loads the seven db/backup/APP_*.sql files in foreign-key order (109 rows)
 *   5. loads db/data.sql on top of it for volume (6,711 rows)
 *
 * For the backup WITHOUT the generated volume, drop the db/data.sql line.
 *
 * It ends at 7 discount codes, 51 micro markets, 90 manufacturers, 14 product
 * codes, 413 customers, 1230 products and 5015 purchase orders. RunScript
 * discards query results, so to read anything back afterwards open the database
 * in H2's browser console: mvn -q compile exec:java -Dexec.mainClass=org.h2.tools.Console
 */

/* 1. a clean APP schema, whatever this database held before */
DROP SCHEMA IF EXISTS APP CASCADE;
CREATE SCHEMA APP;

/* 2. the DDL. Brings its own small test seed, which step 3 removes. */
RUNSCRIPT FROM 'db/market-schema.sql';

/* 3. clear, children before parents. PRODUCT_CODE before DISCOUNT_CODE is
      required, not tidiness: it references it. */
DELETE FROM APP.PURCHASE_ORDER;
DELETE FROM APP.PRODUCT;
DELETE FROM APP.CUSTOMER;
DELETE FROM APP.PRODUCT_CODE;
DELETE FROM APP.MANUFACTURER;
DELETE FROM APP.MICRO_MARKET;
DELETE FROM APP.DISCOUNT_CODE;

/* 4. the original backup, parents before children */
RUNSCRIPT FROM 'db/backup/APP_DISCOUNT_CODE.sql';
RUNSCRIPT FROM 'db/backup/APP_MICRO_MARKET.sql';
RUNSCRIPT FROM 'db/backup/APP_MANUFACTURER.sql';
RUNSCRIPT FROM 'db/backup/APP_PRODUCT_CODE.sql';
RUNSCRIPT FROM 'db/backup/APP_CUSTOMER.sql';
RUNSCRIPT FROM 'db/backup/APP_PRODUCT.sql';
RUNSCRIPT FROM 'db/backup/APP_PURCHASE_ORDER.sql';

/* 5. generated volume on top. Delete this line for a backup-only restore. */
RUNSCRIPT FROM 'db/data.sql';
