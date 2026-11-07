# Tech Market — Swing application

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=pe.edu.unmsm.fisi.tech-market-app&metric=alert_status)](https://sonarcloud.io/project/issues?id=pe.edu.unmsm.fisi.tech-market-app&resolved=false) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=pe.edu.unmsm.fisi.tech-market-app&metric=coverage)](https://sonarcloud.io/component_measures?id=pe.edu.unmsm.fisi.tech-market-app&metric=coverage)

A desktop application for a small distribution business: customers, products and purchase
orders over the old NetBeans sample database. It is the sample project for the Algorithmics
course at UNMSM FISI, and it is deliberately framework-free — row mapping, identifier
generation, validation, connection handling and a service layer are all written by hand, so
the problems a framework hides are visible.

Java 21, Swing with NetBeans Matisse forms, plain JDBC on an H2 file database, Maven,
JUnit 4, Bean Validation (hibernate-validator), slf4j over log4j. The user interface is in
Spanish.

## Build and run

```bash
mvn clean verify    # compile, test, JaCoCo coverage report
mvn test            # tests only
```

Run Maven from the repository root — the test bootstrap reads `db/market-schema.sql`
through a relative path. The build needs a display, because the view tests open real Swing
windows; on a headless machine use `xvfb-run mvn clean verify`.

Both commands need `JAVA_HOME` pointing at a Java 21 JDK. If the machine's `JAVA_HOME` is
older, set it for the call.

The main class is `pe.edu.unmsm.fisi.market.TechMarketApp`. Run it from the IDE, or:

```bash
mvn compile exec:java -Dexec.mainClass=pe.edu.unmsm.fisi.market.TechMarketApp
```

It opens `~/ansel/market-db` straight off the filesystem — there is no server to start —
but that file needs its tables first, so restore it once before the first run.

## Database

Seven tables in the `APP` schema: `DISCOUNT_CODE`, `MICRO_MARKET`, `MANUFACTURER`,
`PRODUCT_CODE`, `CUSTOMER`, `PRODUCT` and `PURCHASE_ORDER`. Customers in ZIP-coded sales
territories place single-line orders for products that a manufacturer makes and a
two-letter code classifies; discount rates attach both to the customer and to the product
category. Only `CUSTOMER`, `PRODUCT` and `PURCHASE_ORDER` can be written from the
application — the other four are read-only lookups, and new rows in them come from the SQL
files.

Three files feed a database, and a fourth composes them:

- `db/market-schema.sql` — the DDL plus a small seed. The test run replays it to build
  `target/market-db`.
- `db/backup/APP_*.sql` — the original NetBeans sample data, 109 rows, one file per table.
- `db/data.sql` — 6,711 generated rows for performance work, reproducible with
  `python tools/gen_data.py`.
- `db/restore.sql` — rebuilds a database from all three, parents first. Destructive and
  idempotent:

```bash
mvn -q compile exec:java -Dexec.mainClass=org.h2.tools.RunScript -Dexec.args="-url jdbc:h2:file:./target/market-db -user app -password app -script db/restore.sql"
```

Point `-url` at `jdbc:h2:file:~/ansel/market-db` to restore the database the application
itself reads. Only one JVM at a time may hold an H2 file database, so close the application
first.

**[docs/data-dictionary.md](docs/data-dictionary.md) is the reference for all of this** —
every table and column, which relationships the database enforces and which are convention
only, who allocates each primary key, and what makes a new row consistent. Read it before
adding data by hand.

## Documentation

- [docs/data-dictionary.md](docs/data-dictionary.md) — the schema, column by column, and
  the rules for writing rows.
- `CLAUDE.md` — architecture, build, database mechanics and the gotchas that bite.
- `AGENTS.md` — the engineering standards this project is held to.

## License

See [LICENSE](LICENSE).
