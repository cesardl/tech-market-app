# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository. `AGENTS.md` sits beside it with the general engineering standards for the Java Swing projects; this file holds what is true of *this* repository, and its **Stack and Project Context** section points back here.

## Project

Java 21 Swing desktop app over an H2 file database — originally a UI for the old NetBeans sample database, now the training codebase described below. Maven build, JUnit 4, hibernate-validator (Bean Validation), slf4j + log4j. UI strings, log messages and some legacy method names are in Spanish; match the surrounding file rather than renaming.

## Purpose and constraints

This is a **teaching codebase**, not an application to modernize. Its point is to reimplement in miniature what Hibernate and Spring do — row mapping, identifier generation, a validation pass, connection handling, a service layer — so that the team hits those problems directly instead of letting a framework hide them.

Consequences for any change or review here:

- **Do not propose adopting a framework** (Spring, Spring Boot, Hibernate/JPA, MyBatis, Lombok, DI containers). The hand-written layer *is* the deliverable. Patterns that imitate a framework feature in a lightweight way — `TemplateBusiness` as a generic repository, per-DAO row mapping, the singleton "context", `TechMarketValidator` over the Bean Validation API — are deliberate.
- Hand-rolled algorithms are course material: `ProductInMemoryDAO`'s insertion sort and binary search are the lesson, not duplication to replace with `List.sort`.
- Duplication is still worth collapsing when the exercise is *how a framework would remove it* (e.g. one generic lookup DAO taking a SQL string + a row mapper = what `JdbcTemplate` gives you). Prefer refactors that teach the framework's job over refactors that just shorten the file.
- SOLID violations in the existing code are fair game to name — the fat `CompleteCrudDAO` with `UnsupportedOperationException` stubs is a live ISP example.
- **H2 is the only supported database.** Apache Derby came first and is gone: its developers voted the project into a read-only state on 2025-10-10 — no further releases, frozen site and issue tracker, last release 10.17.1.0 from November 2023. H2 took over on 2026-11-01. The short-lived dual-vendor experiment in `9e8ba98` had been reverted earlier that same day, so there is no vendor abstraction to preserve and H2-specific SQL is fine.

## Commands

```bash
mvn clean verify                                      # compile + tests + JaCoCo report
mvn test                                              # tests only (also writes the JaCoCo report)
mvn test -Dtest=CustomerBusinessTest                  # single class
mvn test -Dtest=CustomerBusinessTest#getDiscountCodesTest   # single method
sonar-scanner                                         # SonarCloud; nothing runs it automatically

# rebuild a database from the APP_*.sql backup, then add db/data.sql on top
mvn -q compile exec:java -Dexec.mainClass=org.h2.tools.RunScript \
    -Dexec.args="-url jdbc:h2:file:./target/market-db -user app -password app -script db/restore.sql"

# browse either database in H2's web console
mvn -q compile exec:java -Dexec.mainClass=org.h2.tools.Console
```

- Always run Maven from the repo root: `DatabaseTestContext` reads `db/market-schema.sql` through a relative path.
- `ViewsTest` instantiates real Swing windows, so the build needs a display; on a headless machine wrap it as `xvfb-run mvn clean verify`.
- Main class is `pe.edu.unmsm.fisi.market.TechMarketApp`. Run it from the IDE, or `mvn compile exec:java -Dexec.mainClass=pe.edu.unmsm.fisi.market.TechMarketApp` (exec-maven-plugin is not declared in the pom; Maven resolves it on the fly). It opens `~/ansel/market-db` straight off the filesystem — no server to start — but that file needs its tables first, so run `db/restore.sql` against it once.
- Targets Java 21 through a single `maven.compiler.release` property (no `source`/`target` pair). H2 2.5.252 ships Java 11 bytecode, so 21 is the project's own choice now rather than a driver requirement (Derby 10.17 did force it). Verified toolchain: JDK 21.0.8 + Maven 3.6.3. **The machine's `JAVA_HOME` points at JDK 11.0.30**, so Maven has to be run with `JAVA_HOME` set to a 21 JDK (`C:\Program Files\Java\jdk-21` locally) or the build fails at compile.

## Database

H2 **2.5.252** (released 2026-09-26), a single compile-scoped `com.h2database:h2` dependency that serves the app, the tests and the `org.h2.tools.*` command-line tools alike — Derby needed three artifacts and a separate server process for the same job. Both profiles are **file-based**, so there is no server process anywhere; the flip side is that **one JVM at a time** may hold a given database file. Close the app before restoring into the database it reads, or add `AUTO_SERVER=TRUE` to both URLs to share one.

JDBC settings come from a resource bundle named `db.jdbc`, so the test copy shadows the main one on the test classpath. Both use driver `org.h2.Driver`, user/password `app`/`app` and schema `APP`, which `ConnectionUtils` applies with `conn.setSchema(...)` after `Class.forName(driver)`:

- `src/main/resources/db/jdbc.properties` — `jdbc:h2:file:~/ansel/market-db`, the database the app reads. H2 expands `~` to the user home itself; no code does it.
- `src/test/resources/db/jdbc.properties` — `jdbc:h2:file:./target/market-db`, relative to the repo root, so `mvn clean` wipes it.

Both URLs then carry the same two settings, and both are load-bearing:

- `INIT=CREATE SCHEMA IF NOT EXISTS APP` — H2's default schema is `PUBLIC`, and `openConnection()` calls `setSchema("APP")` on *every* connection, the first one against a brand-new file included. Without `INIT` that call fails before anything has had a chance to create the schema, and `main` exits with code 1. Derby needed none of this: a new Derby database already contained `APP`.
- `DB_CLOSE_DELAY=-1` — each DAO method opens and closes its own connection, and H2 closes a file database as soon as its last connection goes, so without this the whole store is reopened per DAO call. Derby kept the database booted instead.

A `market-db.trace.db` file appears next to the database: H2 writes failing statements there. Since the DAOs log and swallow `SQLException`, it is often the quickest way to see what the UI dropped silently.

`db/market-schema.sql` stays in `db/` — `DatabaseTestContext` replays it to build the embedded database under `target/` during the test phase. It holds the DDL plus a minimal seed (2 customers, 3 products, 2 manufacturers, 2 discount codes, 1 micro market, 1 purchase order), opens with `CREATE SCHEMA IF NOT EXISTS APP;` then `SET SCHEMA APP;` and declares `PRODUCT.AVAILABLE` as `BOOLEAN` with unquoted `TRUE`/`FALSE` literals. `DatabaseTestContext.init()` splits the file on `;` and replays every statement, swallowing only SQL states `42S01` (table already exists) and `23505` (duplicate key); **any other state aborts all four DB-backed test classes in `@BeforeClass`**. A `mvn test` without `clean` takes exactly that path and is meant to: 7 tables already there, then 13 duplicate seed rows, all tolerated. DB-backed tests assert exact row counts from this seed, so changing it breaks them.

`db/data.sql` is a second, generated seed meant for performance work: 1,200 products (1,107 in stock), 400 customers, 5,000 purchase orders, 60 manufacturers, 40 micro markets and 8 extra product codes. It is **additive** — it runs after `market-schema.sql`, reuses none of that file's identifiers, and picks its three discount codes (`S`, `P`, `X`) to clash with neither `market-schema.sql` (`N`, `M`) nor the `APP_*` backup (`H`, `M`, `L`, `N`), so it loads on top of either base. Nothing enforces `PRODUCT_CODE.DISCOUNT_CODE` as a foreign key, so the base seed's `HW` row still points at an `H` code that only the backup supplies. Nothing in the build loads it, so the row-count assertions in the DB-backed tests are untouched. Regenerate it with `python tools/gen_data.py` from the repo root — the defaults reproduce the committed file byte for byte, and `--products` / `--orders` / `--customers` / `--seed` scale it for a heavier run. It holds the business layer's own invariants (`AVAILABLE = (QUANTITY_ON_HAND <> 0)`, `SHIPPING_DATE = SALES_DATE + 15 days`, `QUANTITY <= QUANTITY_ON_HAND`), and it carries block comments only with every `;` outside string literals, so `DatabaseTestContext` can replay it unchanged when you want that volume in a test run.
The seven `APP_*.sql` files in `db/backup/` are the original NetBeans sample data, dumped one file per table: 109 rows, a superset of the seed inside `market-schema.sql`, so loading them over that seed collides on duplicate keys (and customer 2 carries a different ZIP in the dump). Nothing in the build reads them; `db/restore.sql` loads them in foreign-key order (DISCOUNT_CODE, MICRO_MARKET, MANUFACTURER, PRODUCT_CODE, CUSTOMER, PRODUCT, PURCHASE_ORDER). They predate `PRODUCT.AVAILABLE` becoming `BOOLEAN` and still write it as the quoted string `'TRUE'`/`'FALSE'`, which H2 coerces into the `BOOLEAN` column without complaint, as Derby did. None of their identifiers overlap `db/data.sql`, so both can be loaded into the same database.

`db/restore.sql` is the one-command rebuild, and the only intended way to get the backup into a database. It is plain SQL driven by H2's `RunScript` tool, which takes the target as a `-url` argument, so the file itself never names one — point it at `./target/market-db` or at `~/ansel/market-db` with the same script. It drops and recreates the `APP` schema, runs the DDL, empties every table to drop the test seed that `market-schema.sql` carries, loads the seven backup files parents-first, then loads `db/data.sql` on top: 109 backup rows plus 6,711 generated, ending at 1,230 products, 5,015 orders and 413 customers. Dropping the schema first is what makes it **destructive and idempotent** — no `clean` needed, and the tenth run ends where the first one did. That matters because `RunScript` aborts on the first error instead of logging it and carrying on the way Derby's `ij` did, so the script cannot lean on an "already exists" being survivable. Delete the final `RUNSCRIPT` line for a backup-only restore. Being ordinary SQL, every statement in it can also be run from any JDBC connection; `RunScript` is just the convenient driver.

### Coverage

`jacoco-maven-plugin` 0.8.16 runs `prepare-agent` plus a `report` execution bound to the **test** phase, so plain `mvn test` is enough to produce `target/site/jacoco/` (HTML, `jacoco.xml`, `jacoco.csv`). `sonar-project.properties` points `sonar.coverage.jacoco.xmlReportPaths` at that XML. No threshold is enforced — add a `check` execution with rules if the build should fail under a floor. Baseline after the purchase-order sprint: 71% line, 32% branch, 30 tests, up from 71%/28%/26 at the H2 switch.

## Architecture

`view` (Swing) → `business` (singletons) → `dao` (plain JDBC) → H2, with `model` POJOs carrying the validation constraints. `util` holds the cross-cutting helpers.

**dao** — interface chain `MasterCrudDAO<T>` (`getAll`) → `SimpleCrudDAO<T>` (`findById`) → `CompleteCrudDAO<T>` (`save`/`update`/`delete`/`buscarNombre`). Every DAO method opens its own connection with `ConnectionUtils.openConnection()` inside try-with-resources: no pool, no transactions, no unit of work spanning two DAOs. DAOs log and swallow `SQLException`, returning `false` / `null` / `Collections.emptyList()`; callers branch on those values and nothing propagates to the UI as an exception. `PurchaseOrderDAO` is the one that also answers `false` when the statement affected **no rows**, so updating or deleting an order that is already gone reports failure; the other DAOs still answer `true` whenever the statement merely ran. `ProductInMemoryDAO` is a `List`-backed implementation (insertion sort + binary search, course material) used only from tests.

**business** — `TemplateBusiness<T>` is the abstract base holding the DAO reference and identifier generation. Each concrete business class is an eagerly initialized singleton (private constructor, `static INSTANCE`, `getInstance()`), wires its own DAOs in the constructor and calls `setIdentifierSize(6|8)` to choose the random-id range. **Primary keys are generated in the application, not by the database**: `generateIdentifier()` draws random ints until `dao.findById` returns `null`. Cross-entity lookups that feed combo boxes also live here (`ProductBusiness.getProductCodes/getManufacturers`, `CustomerBusiness.getDiscountCodes/getMicroMarkets`). Domain rules in this layer: `ProductBusiness` derives `available` from `quantityOnHand != 0`; `PurchaseOrderBusiness` stamps `salesDate = now` on insert, and `shippingDate = now + 15 days` only when the caller supplied none — the order form's date chooser would otherwise be overwritten by the default it was meant to replace.

**view** — NetBeans Matisse forms: every screen is a `.form` (editor model) + `.java` pair. The code inside `//GEN-BEGIN` … `//GEN-LAST` markers and `initComponents()` is regenerated from the `.form`, so change layout through the NetBeans form editor and keep hand-written code outside those markers; editing one file of the pair without the other desynchronizes them. `JFrameMain` is a `JDesktopPane` shell whose menu items call `AppUtils.displayInternalFrame` to add and show each internal frame once. Each list screen (`JInternalFrame*`) fills a `DefaultTableModel` from `business.all()` and opens a modal `JDialog*Form` for detail/edit; the dialog exposes `isActionPerformed()` so the list knows whether to reload. Pressing **Delete** on a list table deletes the selected row after a confirm, on all three screens. Because the dialogs are modal, `JDialogPurchaseOrderForm` keeps its two halves (`showPurchaseOrder()` filling the widgets, `catchingData()` reading them back) package-private, which is the only way a test can drive them.

**validation** — driven from the dialogs, not the business layer. `catchingData()` copies widget values into the POJO (via `AppUtils.clean` / `toLong` / `toInteger`), then `TechMarketValidator.validate` returns `Map<propertyPath, message>`. `processIfHasError("fieldName", errors, textField)` looks violations up **by string property name** and shows only the first one, so renaming a model field means updating those string literals in the form.

`ConnectionUtils.initialize()` must run before any DAO call: `TechMarketApp.main` does it for the app, tests do it through `DatabaseTestContext.init()`.

Adding an entity means touching all four layers: POJO with constraints in `model`, a `CompleteCrudDAO` implementation in `dao/impl`, a `TemplateBusiness` subclass singleton in `business/impl`, and a `JInternalFrame*` + `JDialog*Form` pair wired into `JFrameMain`'s menu.

## Gotchas

- `AppUtils.toInteger/toDouble/toLong` return the sentinel `ERROR_NUMBER` (-99999) instead of throwing; call sites must compare against it (see `JInternalFrameProducts`).
- The business singletons are shared JVM state across test classes. `ProductInMemoryBusinessTest` calls `setDao(new ProductInMemoryDAO())` on the same `ProductBusiness.INSTANCE` that the DB-backed `ProductBusinessTest` uses, and `ProductBusinessTest` never resets it — the suite only passes because Surefire happens to run them in that order. Re-set the DAO in `@Before` when adding tests around `ProductBusiness`.
- `PRODUCT.AVAILABLE` is a real `BOOLEAN` column, but `ProductDAO` writes it with `setBoolean` and reads it back with `Boolean.parseBoolean(rs.getString(...))`, which silently yields `false` for anything it does not recognise.
- `ProductDAO.getAll()` returns only rows with `QUANTITY_ON_HAND <> 0 AND AVAILABLE = TRUE`, and projects just id + description; the full object graph comes from `findById`/`buscarNombre`, which join `MANUFACTURER` and `PRODUCT_CODE`.
- `PurchaseOrderDAO.getAll()` returns partially populated graphs (customer with name only, product with description only) because it projects just what the table shows.
- Line endings are per-file here and `core.autocrlf=true`: `pom.xml` and `db/market-schema.sql` are CRLF, `sonar-project.properties` is LF. MSYS `sed -i` / `awk` rewrite in text mode and strip the CRs, turning a 6-line edit into a whole-file diff, and `grep -c $'\r'` miscounts in this shell. Edit these files through PowerShell (`[IO.File]::ReadAllText` / `WriteAllText`) and check `git diff --stat` after any scripted edit.
- There is no CI. `.travis.yml` was deleted on 2026-11-01 (travis-ci.org shut down in 2021) along with the README's build badge and the `sonar.links.ci` property; `sonar-scanner` has to be run by hand.
