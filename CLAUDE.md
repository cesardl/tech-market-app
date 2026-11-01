# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Java 21 Swing desktop app over an Apache Derby database — originally a UI for the old NetBeans sample database, now the training codebase described below. Maven build, JUnit 4, hibernate-validator (Bean Validation), slf4j + log4j. UI strings, log messages and some legacy method names are in Spanish; match the surrounding file rather than renaming.

## Purpose and constraints

This is a **teaching codebase**, not an application to modernize. Its point is to reimplement in miniature what Hibernate and Spring do — row mapping, identifier generation, a validation pass, connection handling, a service layer — so that the team hits those problems directly instead of letting a framework hide them.

Consequences for any change or review here:

- **Do not propose adopting a framework** (Spring, Spring Boot, Hibernate/JPA, MyBatis, Lombok, DI containers). The hand-written layer *is* the deliverable. Patterns that imitate a framework feature in a lightweight way — `TemplateBusiness` as a generic repository, per-DAO row mapping, the singleton "context", `TechMarketValidator` over the Bean Validation API — are deliberate.
- Hand-rolled algorithms are course material: `ProductInMemoryDAO`'s insertion sort and binary search are the lesson, not duplication to replace with `List.sort`.
- Duplication is still worth collapsing when the exercise is *how a framework would remove it* (e.g. one generic lookup DAO taking a SQL string + a row mapper = what `JdbcTemplate` gives you). Prefer refactors that teach the framework's job over refactors that just shorten the file.
- SOLID violations in the existing code are fair game to name — the fat `CompleteCrudDAO` with `UnsupportedOperationException` stubs is a live ISP example.
- **Derby is the only supported database.** H2 was added in commit `9e8ba98` and taken back out on 2026-11-01: keeping the SQL inside the subset both vendors accept cost more than it taught. There is no vendor abstraction left to preserve, so Derby-specific SQL is fine.

## Commands

```bash
mvn clean verify                                      # compile + tests + JaCoCo report
mvn test                                              # tests only (also writes the JaCoCo report)
mvn test -Dtest=CustomerBusinessTest                  # single class
mvn test -Dtest=CustomerBusinessTest#getDiscountCodesTest   # single method
sonar-scanner                                         # SonarCloud; nothing runs it automatically
```

- Always run Maven from the repo root: `DatabaseTestContext` reads `db/market-schema.sql` through a relative path.
- `ViewsTest` instantiates real Swing windows, so the build needs a display; on a headless machine wrap it as `xvfb-run mvn clean verify`.
- Main class is `pe.edu.unmsm.fisi.market.TechMarketApp`. Run it from the IDE, or `mvn compile exec:java -Dexec.mainClass=pe.edu.unmsm.fisi.market.TechMarketApp` (exec-maven-plugin is not declared in the pom; Maven resolves it on the fly). The app needs a Derby network server already running — see below.
- Targets Java 21 through a single `maven.compiler.release` property (no `source`/`target` pair). Derby 10.17 requires Java 21, so 21 is a hard floor, not a preference. Verified toolchain: JDK 21.0.8 + Maven 3.6.3. **The machine's `JAVA_HOME` points at JDK 11.0.30**, so Maven has to be run with `JAVA_HOME` set to a 21 JDK (`C:\Program Files\Java\jdk-21` locally) or the build fails at compile.

## Database

Apache Derby **10.17.1.0**, nothing else. The artifact split matters: `derbyclient` is compile-scoped for the app, while `derby` (engine) *and* `derbytools` are test-scoped for the embedded profile — since Derby 10.15 `org.apache.derby.jdbc.EmbeddedDriver` lives in **derbytools.jar**, not `derby.jar`, so dropping `derbytools` breaks every DB-backed test. `derbyshared` arrives transitively from both.

JDBC settings come from a resource bundle named `db.jdbc`, so the test copy shadows the main one on the test classpath:

- `src/main/resources/db/jdbc.properties` — **network client**: `org.apache.derby.jdbc.ClientDriver`, `jdbc:derby://localhost:1527/market-derby-db`. Only `derbyclient` ships at runtime, so **a Derby network server must already be running separately**; `ConnectionUtils.initialize()` failing makes `main` exit with code 1.
- `src/test/resources/db/jdbc.properties` — **embedded**: `org.apache.derby.jdbc.EmbeddedDriver`, `jdbc:derby:target/market-derby-db;create=true`, so tests create their own database under `target/` and `mvn clean` wipes it.
- Both use user/password `app`/`app` and schema `APP`; `ConnectionUtils` applies it with `conn.setSchema(...)` after `Class.forName(driver)`. A new Derby database already contains the `APP` schema, so no statement has to create it.

`db/market-schema.sql` stays at the repo root — `DatabaseTestContext` replays it to build the embedded database under `target/` during the test phase. It holds the DDL plus a minimal seed (2 customers, 3 products, 2 manufacturers, 2 discount codes, 1 micro market, 1 purchase order), opens with `SET SCHEMA APP;` and declares `PRODUCT.AVAILABLE` as `BOOLEAN` with unquoted `TRUE`/`FALSE` literals. `DatabaseTestContext.init()` splits the file on `;` and replays every statement, swallowing only SQL states `X0Y32` (object already exists) and `23505` (duplicate key); **any other state aborts all four DB-backed test classes in `@BeforeClass`**, so keep the DDL inside what Derby parses — `CREATE SCHEMA IF NOT EXISTS` is a syntax error (`42X01`) and was exactly how the H2 experiment broke the suite. DB-backed tests assert exact row counts from this seed, so changing it breaks them.

`db/data.sql` is a second, generated seed meant for performance work: 1,200 products (1,107 in stock), 400 customers, 5,000 purchase orders, 60 manufacturers, 40 micro markets and 8 extra product codes. It is **additive** — it runs after `market-schema.sql`, reuses none of that file's identifiers, and adds the `H` discount code that the base seed's `HW` product code dangles on. Nothing in the build loads it, so the row-count assertions in the DB-backed tests are untouched. Regenerate it with `python tools/gen_data.py` from the repo root — the defaults reproduce the committed file byte for byte, and `--products` / `--orders` / `--customers` / `--seed` scale it for a heavier run. It holds the business layer's own invariants (`AVAILABLE = (QUANTITY_ON_HAND <> 0)`, `SHIPPING_DATE = SALES_DATE + 15 days`, `QUANTITY <= QUANTITY_ON_HAND`), and it carries block comments only with every `;` outside string literals, so `DatabaseTestContext` can replay it unchanged when you want that volume in a test run.
The seven `APP_*.sql` files at the repo root are the original NetBeans sample data, dumped one file per table: 109 rows, a superset of the seed inside `market-schema.sql`, so loading them over that seed collides on duplicate keys (and customer 2 carries a different ZIP in the dump). Nothing in the build reads them — load them by hand into a schema-only database in foreign-key order: DISCOUNT_CODE, MICRO_MARKET, MANUFACTURER, PRODUCT_CODE, CUSTOMER, PRODUCT, PURCHASE_ORDER. They predate `PRODUCT.AVAILABLE` becoming `BOOLEAN` and still write it as the quoted string `'TRUE'`/`'FALSE'`, which Derby coerces without complaint. None of their identifiers overlap `db/data.sql`, so both can be loaded into the same database.

### Coverage

`jacoco-maven-plugin` 0.8.16 runs `prepare-agent` plus a `report` execution bound to the **test** phase, so plain `mvn test` is enough to produce `target/site/jacoco/` (HTML, `jacoco.xml`, `jacoco.csv`). `sonar-project.properties` points `sonar.coverage.jacoco.xmlReportPaths` at that XML. No threshold is enforced — add a `check` execution with rules if the build should fail under a floor. Baseline at the Derby-only cutover: 71% line, 28% branch, 26 tests.

## Architecture

`view` (Swing) → `business` (singletons) → `dao` (plain JDBC) → Derby, with `model` POJOs carrying the validation constraints. `util` holds the cross-cutting helpers.

**dao** — interface chain `MasterCrudDAO<T>` (`getAll`) → `SimpleCrudDAO<T>` (`findById`) → `CompleteCrudDAO<T>` (`save`/`update`/`delete`/`buscarNombre`). Every DAO method opens its own connection with `ConnectionUtils.openConnection()` inside try-with-resources: no pool, no transactions, no unit of work spanning two DAOs. DAOs log and swallow `SQLException`, returning `false` / `null` / `Collections.emptyList()`; callers branch on those values and nothing propagates to the UI as an exception. `ProductInMemoryDAO` is a `List`-backed implementation (insertion sort + binary search, course material) used only from tests.

**business** — `TemplateBusiness<T>` is the abstract base holding the DAO reference and identifier generation. Each concrete business class is an eagerly initialized singleton (private constructor, `static INSTANCE`, `getInstance()`), wires its own DAOs in the constructor and calls `setIdentifierSize(6|8)` to choose the random-id range. **Primary keys are generated in the application, not by the database**: `generateIdentifier()` draws random ints until `dao.findById` returns `null`. Cross-entity lookups that feed combo boxes also live here (`ProductBusiness.getProductCodes/getManufacturers`, `CustomerBusiness.getDiscountCodes/getMicroMarkets`). Domain rules in this layer: `ProductBusiness` derives `available` from `quantityOnHand != 0`; `PurchaseOrderBusiness` stamps `salesDate = now` and `shippingDate = now + 15 days` on insert.

**view** — NetBeans Matisse forms: every screen is a `.form` (editor model) + `.java` pair. The code inside `//GEN-BEGIN` … `//GEN-LAST` markers and `initComponents()` is regenerated from the `.form`, so change layout through the NetBeans form editor and keep hand-written code outside those markers; editing one file of the pair without the other desynchronizes them. `JFrameMain` is a `JDesktopPane` shell whose menu items call `AppUtils.displayInternalFrame` to add and show each internal frame once. Each list screen (`JInternalFrame*`) fills a `DefaultTableModel` from `business.all()` and opens a modal `JDialog*Form` for detail/edit; the dialog exposes `isActionPerformed()` so the list knows whether to reload.

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
