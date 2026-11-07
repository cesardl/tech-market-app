"""Generate db/data.sql: a FK-consistent volume seed for the Tech Market schema.

Deterministic for a given --seed, additive to db/market-schema.sql, and safe to replay
through DatabaseTestContext (block comments only, no semicolon inside a literal).

    python tools/gen_data.py                            rewrites db/data.sql as committed
    python tools/gen_data.py --products 10000 --orders 50000
    python tools/gen_data.py --products 100000 --orders 500000 --out db/data-big.sql

Run it from the repo root. Defaults reproduce the committed db/data.sql byte for byte.
"""
import argparse
import random
from datetime import date, timedelta
from pathlib import Path

parser = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
parser.add_argument("--products", type=int, default=1200)
parser.add_argument("--orders", type=int, default=5000)
parser.add_argument("--customers", type=int, default=400)
parser.add_argument("--manufacturers", type=int, default=60)
parser.add_argument("--markets", type=int, default=40)
parser.add_argument("--seed", type=int, default=20261101)
parser.add_argument("--batch", type=int, default=100, help="rows per INSERT statement")
parser.add_argument("--out", default=None, help="output file (default: db/data.sql)")
args = parser.parse_args()

REPO = Path(__file__).resolve().parent.parent

R = random.Random(args.seed)
OUT = args.out or str(REPO / "db" / "data.sql")

N_MANUFACTURER = args.manufacturers
N_MARKET = args.markets
N_CUSTOMER = args.customers
N_PRODUCT = args.products
N_ORDER = args.orders
BATCH = args.batch

# PRODUCT ids come from the 6-digit range ProductBusiness draws from, so there has to
# be room left for generateIdentifier() to find a free one.
assert N_PRODUCT < 900000, "more products than the 6-digit identifier range holds"
assert N_ORDER < 90000000, "more orders than the 8-digit identifier range holds"

# ---------------------------------------------------------------- base seed (market-schema.sql)
BASE_DISCOUNT = ["N", "M"]
BASE_PRODCODE = ["SW", "HW"]
BASE_MANUFACTURER = [19985678, 19986982]
BASE_PRODUCT = [980001, 980005, 958889]
BASE_CUSTOMER = [1, 2]
BASE_MARKET = ["95117"]
BASE_ORDER = [10398001]

# ---------------------------------------------------------------- vocabularies (ASCII, no ' or ;)
BRANDS = ["Altair", "Borealis", "Cygnus", "Deneb", "Electra", "Fomalhaut", "Gemini", "Helios",
          "Izar", "Jupiter", "Kepler", "Lyra", "Meridian", "Nadir", "Orion", "Polaris",
          "Quasar", "Rigel", "Sirius", "Tucana", "Umbriel", "Vega", "Westerlund", "Xanthus"]
LINES = ["Core", "Edge", "Pro", "Studio", "Enterprise", "Lite", "Prime", "Vantage", "Summit",
         "Horizon", "Pulse", "Vertex", "Nova", "Atlas", "Forge"]
HARDWARE = ["Workstation", "Rack Server", "Blade Server", "Laptop", "Tablet", "Thin Client",
            "NAS Array", "SSD 2TB", "GPU Accelerator", "Mainboard", "Power Supply",
            "Switch 48 Port", "Router", "Firewall Appliance", "KVM Console"]
SOFTWARE = ["Identity Server", "Accounting Suite", "ERP Platform", "CRM Platform",
            "Data Warehouse", "Backup Manager", "Monitoring Agent", "Report Builder",
            "Workflow Engine", "Message Broker", "API Gateway", "Test Harness",
            "Container Runtime", "Log Collector", "Schema Migrator"]
CITIES = [("Mountain View", "CA"), ("Santa Clara", "CA"), ("San Jose", "CA"), ("Fremont", "CA"),
          ("Portland", "OR"), ("Seattle", "WA"), ("Boise", "ID"), ("Denver", "CO"),
          ("Austin", "TX"), ("Dallas", "TX"), ("Houston", "TX"), ("Phoenix", "AZ"),
          ("Miami", "FL"), ("Orlando", "FL"), ("Tampa", "FL"), ("Atlanta", "GA"),
          ("Charlotte", "NC"), ("Nashville", "TN"), ("Columbus", "OH"), ("Chicago", "IL"),
          ("Madison", "WI"), ("Minneapolis", "MN"), ("Saint Louis", "MO"), ("Omaha", "NE"),
          ("Boston", "MA"), ("Providence", "RI"), ("Hartford", "CT"), ("Albany", "NY"),
          ("Newark", "NJ"), ("Philadelphia", "PA"), ("Baltimore", "MD"), ("Richmond", "VA")]
STREETS = ["Oak Avenue", "Finch Circle", "Las Olivas Blvd", "Main Street", "Harbor Drive",
           "Juniper Lane", "Sunset Boulevard", "Industrial Parkway", "Lakeview Road",
           "Commerce Way", "Cedar Court", "Mission Street", "Union Square", "Baker Street"]
UNITS = ["Suite 100", "Suite 240", "Building 14", "Floor 3", "Unit B", "P.O. Box 567",
         "Dock 9", "Mail Stop 42", "Annex 2", "Hangar 7"]
ORG_HEAD = ["Jumbo", "New", "Pacific", "Atlantic", "Summit", "Keystone", "Granite", "Harbor",
            "Lakeside", "Redwood", "Ironside", "Brightwater", "Northwind", "Southgate",
            "Copperfield", "Silverline", "Evergreen", "Clearpoint"]
ORG_TAIL = ["Eagle Corp", "Enterprises", "Industries", "Systems", "Logistics", "Holdings",
            "Partners", "Technologies", "Supply Co", "Group", "Labs", "Works", "Trading",
            "Distribution", "Solutions"]
FIRST = ["John", "Brian", "Maria", "Luis", "Anna", "Peter", "Sofia", "Carlos", "Diana",
         "Robert", "Elena", "Victor", "Nora", "Hugo", "Clara", "Marcus"]
LAST = ["Snow", "Washington", "Reyes", "Castillo", "Fischer", "Novak", "Moreau", "Lindqvist",
        "Okafor", "Tanaka", "Silva", "Brandt", "Kowalski", "Ferreira", "Dumont", "Vargas"]
FREIGHT = ["Poney Express", "Blue Dart Freight", "Continental Cargo", "Harbor Line Shipping",
           "Rapid Mile Logistics", "Union Freightways", "Cascade Transport", "Ironhorse Rail",
           "Skyward Air Cargo", "Delta Ground Services"]

# ---------------------------------------------------------------- helpers
WIDTH = {}


def lit(value, column=None, width=None):
    """SQL string literal, asserting the column width is respected."""
    assert "'" not in value and ";" not in value, value
    if width is not None:
        assert len(value) <= width, "%s too long (%d > %d): %r" % (column, len(value), width, value)
        WIDTH[column] = max(WIDTH.get(column, 0), len(value))
    return "'%s'" % value


def phone():
    """Exactly 12 characters, so the CHAR(12) columns need no padding."""
    p = "%03d-555-%04d" % (R.randint(201, 989), R.randint(100, 9999))
    assert len(p) == 12
    return p


def slug(text):
    return "".join(c for c in text.lower() if c.isalnum())


def pick_ids(count, low, high, exclude):
    taken = set(exclude)
    out = []
    while len(out) < count:
        candidate = R.randrange(low, high)
        if candidate not in taken:
            taken.add(candidate)
            out.append(candidate)
    return sorted(out)


def rows_block(table, columns, rows):
    """Multi-row INSERTs in batches; one row per line, nothing split across a ';'."""
    out = []
    for start in range(0, len(rows), BATCH):
        chunk = rows[start:start + BATCH]
        out.append("INSERT INTO APP.%s (%s) VALUES" % (table, ", ".join(columns)))
        for i, row in enumerate(chunk):
            out.append("  (%s)%s" % (", ".join(row), "," if i < len(chunk) - 1 else ";"))
        out.append("")
    return out


# ---------------------------------------------------------------- DISCOUNT_CODE
# S, P and X clash with neither base: market-schema.sql seeds N and M, the APP_*
# backup holds H, M, L and N. That lets this file load on top of either one.
new_discounts = [("S", "22.00"), ("P", "14.75"), ("X", "3.50")]
all_discounts = BASE_DISCOUNT + [c for c, _ in new_discounts]

# ---------------------------------------------------------------- MICRO_MARKET
new_markets = []
market_zips = []
for i in range(N_MARKET):
    zip_code = "%05d" % (90000 + i * 137)
    market_zips.append(zip_code)
    new_markets.append((lit(zip_code, "ZIP_CODE", 10),
                        "%.3f" % R.uniform(120.0, 980.0),
                        "%.3f" % R.uniform(100.0, 700.0),
                        "%.3f" % R.uniform(100.0, 700.0)))
all_market_zips = BASE_MARKET + market_zips

# ---------------------------------------------------------------- MANUFACTURER
manufacturer_ids = pick_ids(N_MANUFACTURER, 20000000, 29999999, BASE_MANUFACTURER)
new_manufacturers = []
for mid in manufacturer_ids:
    name = "%s %s" % (R.choice(ORG_HEAD), R.choice(ORG_TAIL))
    city, state = R.choice(CITIES)
    rep = "%s %s" % (R.choice(FIRST), R.choice(LAST))
    new_manufacturers.append((
        str(mid),
        lit(name, "MANUFACTURER.NAME", 30),
        lit("%d %s" % (R.randint(1, 9999), R.choice(STREETS)), "MANUFACTURER.ADDRESSLINE1", 30),
        lit(R.choice(UNITS), "MANUFACTURER.ADDRESSLINE2", 30),
        lit(city, "MANUFACTURER.CITY", 25),
        lit(state, "MANUFACTURER.STATE", 2),
        lit(R.choice(all_market_zips), "MANUFACTURER.ZIP", 10),
        lit(phone(), "MANUFACTURER.PHONE", 12),
        lit(phone(), "MANUFACTURER.FAX", 12),
        lit("%s@example.com" % slug(name)[:27], "MANUFACTURER.EMAIL", 40),
        lit(rep, "MANUFACTURER.REP", 30),
    ))
all_manufacturer_ids = BASE_MANUFACTURER + manufacturer_ids

# ---------------------------------------------------------------- PRODUCT_CODE
new_codes = [("AC", "Accessory"), ("NW", "Network"), ("ST", "Storage"), ("SV", "Services"),
             ("PR", "Peripheral"), ("MB", "Mainboard"), ("GP", "Graphics"), ("OS", "OS")]
new_prodcodes = []
for code, description in new_codes:
    new_prodcodes.append((lit(code, "PROD_CODE", 2),
                          lit(R.choice(all_discounts), "PRODUCT_CODE.DISCOUNT_CODE", 1),
                          lit(description, "PRODUCT_CODE.DESCRIPTION", 10)))
all_prodcodes = BASE_PRODCODE + [c for c, _ in new_codes]
SOFTWARE_CODES = {"SW", "SV", "OS"}

# ---------------------------------------------------------------- CUSTOMER
customer_ids = pick_ids(N_CUSTOMER, 10, 1000000, BASE_CUSTOMER)
new_customers = []
for cid in customer_ids:
    name = "%s %s" % (R.choice(ORG_HEAD), R.choice(ORG_TAIL))
    city, state = R.choice(CITIES)
    new_customers.append((
        str(cid),
        lit(R.choice(all_discounts), "CUSTOMER.DISCOUNT_CODE", 1),
        lit(R.choice(all_market_zips), "CUSTOMER.ZIP", 10),
        lit(name, "CUSTOMER.NAME", 30),
        lit("%d %s" % (R.randint(1, 9999), R.choice(STREETS)), "CUSTOMER.ADDRESSLINE1", 30),
        lit(R.choice(UNITS), "CUSTOMER.ADDRESSLINE2", 30),
        lit(city, "CUSTOMER.CITY", 25),
        lit(state, "CUSTOMER.STATE", 2),
        lit(phone(), "CUSTOMER.PHONE", 12),
        lit(phone(), "CUSTOMER.FAX", 12),
        lit("%s@example.com" % slug(name)[:27], "CUSTOMER.EMAIL", 40),
        str(R.randrange(10000, 500001, 5000)),
    ))
all_customer_ids = BASE_CUSTOMER + customer_ids

# ---------------------------------------------------------------- PRODUCT
product_ids = pick_ids(N_PRODUCT, 100000, 1000000, BASE_PRODUCT)
new_products = []
stock = {}
for index, pid in enumerate(product_ids):
    code = R.choice(all_prodcodes)
    if code in SOFTWARE_CODES:
        description = "%s %s %s" % (R.choice(BRANDS), R.choice(SOFTWARE), R.choice(LINES))
        cost = R.uniform(199.0, 29999.0)
    else:
        description = "%s %s %s" % (R.choice(BRANDS), R.choice(HARDWARE), R.choice(LINES))
        cost = R.uniform(19.0, 8999.0)
    # ~8% out of stock; AVAILABLE mirrors ProductBusiness: available == (quantityOnHand != 0)
    quantity = 0 if index % 13 == 0 else R.randint(1, 9999)
    stock[pid] = quantity
    new_products.append((
        str(pid),
        str(R.choice(all_manufacturer_ids)),
        lit(code, "PRODUCT.PRODUCT_CODE", 2),
        "%.2f" % cost,
        str(quantity),
        "%.2f" % R.uniform(1.0, 85.0),          # DECIMAL(4,2) -> must stay under 100
        "TRUE" if quantity != 0 else "FALSE",
        lit(description, "PRODUCT.DESCRIPTION", 50),
    ))
in_stock = [pid for pid, quantity in stock.items() if quantity > 0]

# ---------------------------------------------------------------- PURCHASE_ORDER
order_ids = pick_ids(N_ORDER, 10000000, 100000000, BASE_ORDER)
FIRST_SALE = date(2024, 1, 1)
SALE_WINDOW = (date(2026, 10, 31) - FIRST_SALE).days
new_orders = []
for oid in order_ids:
    pid = R.choice(in_stock)                     # never order what is out of stock
    quantity = R.randint(1, min(stock[pid], 32767))   # never order more than is on hand
    sales_date = FIRST_SALE + timedelta(days=R.randint(0, SALE_WINDOW))
    shipping_date = sales_date + timedelta(days=15)   # PurchaseOrderBusiness: now + 15 days
    new_orders.append((
        str(oid),
        str(R.choice(all_customer_ids)),
        str(pid),
        str(quantity),
        "%.2f" % R.uniform(9.99, 1999.99),
        lit(sales_date.isoformat(), "SALES_DATE", 10),
        lit(shipping_date.isoformat(), "SHIPPING_DATE", 10),
        lit(R.choice(FREIGHT), "FREIGHT_COMPANY", 30),
    ))

# ---------------------------------------------------------------- emit
available_count = sum(1 for p in new_products if p[6] == "TRUE")
header = """/*
 * data.sql - performance-testing seed for the Tech Market schema.
 *
 * Runs AFTER db/market-schema.sql, and only adds rows: it never touches the
 * identifiers that file already seeds, so the two can be replayed back to back.
 *
 *   load order     DISCOUNT_CODE -> MICRO_MARKET -> MANUFACTURER -> PRODUCT_CODE
 *                  -> CUSTOMER -> PRODUCT -> PURCHASE_ORDER  (foreign keys first)
 *
 *   %5d discount codes (S, P, X - chosen to clash with neither base)
 *   %5d micro markets
 *   %5d manufacturers
 *   %5d product codes
 *   %5d customers
 *   %5d products       (%d available, %d out of stock)
 *   %5d purchase orders
 *
 * Invariants held on purpose, so the data agrees with the business layer:
 *   AVAILABLE = (QUANTITY_ON_HAND <> 0)            as ProductBusiness derives it
 *   SHIPPING_DATE = SALES_DATE + 15 days           as PurchaseOrderBusiness stamps it
 *   QUANTITY <= the matching QUANTITY_ON_HAND      nothing is ordered beyond stock
 *   every order references a product in stock, and every foreign key resolves
 *   CHAR(12) phone and fax values are exactly 12 characters, so nothing is blank padded
 *
 * Generated deterministically (seed 20261101). Block comments only, and no
 * semicolon inside any literal, so DatabaseTestContext can replay it as it stands.
 */
""" % (len(new_discounts), len(new_markets), len(new_manufacturers), len(new_prodcodes),
       len(new_customers), len(new_products), available_count,
       len(new_products) - available_count, len(new_orders))

lines = [header.rstrip("\n"), "", "SET SCHEMA APP;", ""]
lines += rows_block("DISCOUNT_CODE", ["DISCOUNT_CODE", "RATE"],
                    [(lit(c, "DISCOUNT_CODE", 1), r) for c, r in new_discounts])
lines += rows_block("MICRO_MARKET", ["ZIP_CODE", "RADIUS", "AREA_LENGTH", "AREA_WIDTH"], new_markets)
lines += rows_block("MANUFACTURER", ["MANUFACTURER_ID", "NAME", "ADDRESSLINE1", "ADDRESSLINE2",
                                     "CITY", "STATE", "ZIP", "PHONE", "FAX", "EMAIL", "REP"],
                    new_manufacturers)
lines += rows_block("PRODUCT_CODE", ["PROD_CODE", "DISCOUNT_CODE", "DESCRIPTION"], new_prodcodes)
lines += rows_block("CUSTOMER", ["CUSTOMER_ID", "DISCOUNT_CODE", "ZIP", "NAME", "ADDRESSLINE1",
                                 "ADDRESSLINE2", "CITY", "STATE", "PHONE", "FAX", "EMAIL",
                                 "CREDIT_LIMIT"], new_customers)
lines += rows_block("PRODUCT", ["PRODUCT_ID", "MANUFACTURER_ID", "PRODUCT_CODE", "PURCHASE_COST",
                                "QUANTITY_ON_HAND", "MARKUP", "AVAILABLE", "DESCRIPTION"],
                    new_products)
lines += rows_block("PURCHASE_ORDER", ["ORDER_NUM", "CUSTOMER_ID", "PRODUCT_ID", "QUANTITY",
                                       "SHIPPING_COST", "SALES_DATE", "SHIPPING_DATE",
                                       "FREIGHT_COMPANY"], new_orders)

# ---------------------------------------------------------------- self-checks
assert len(set(product_ids)) == N_PRODUCT and not set(product_ids) & set(BASE_PRODUCT)
assert len(set(order_ids)) == N_ORDER and not set(order_ids) & set(BASE_ORDER)
assert len(set(customer_ids)) == N_CUSTOMER and not set(customer_ids) & set(BASE_CUSTOMER)
assert len(set(manufacturer_ids)) == N_MANUFACTURER
assert 0 < available_count < N_PRODUCT, "availability should be a mix of in and out of stock"
body = "\n".join(lines) + "\n"
assert "--" not in body, "'--' comments break DatabaseTestContext line joining"
for statement in body.split(";"):
    assert statement.count("'") % 2 == 0, "unbalanced quote around a ';'"

with open(OUT, "w", newline="\r\n", encoding="utf-8") as handle:
    handle.write(body)

print("wrote %s" % OUT)
print("rows: %d discount, %d market, %d manufacturer, %d prodcode, %d customer, %d product, %d order"
      % (len(new_discounts), len(new_markets), len(new_manufacturers), len(new_prodcodes),
         len(new_customers), len(new_products), len(new_orders)))
print("products available=%d out_of_stock=%d" % (available_count, N_PRODUCT - available_count))
print("widest values seen: %s" % sorted(WIDTH.items(), key=lambda kv: -kv[1])[:6])
