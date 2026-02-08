# Tax Calculator CLI

Command-line application that reads stock operations from **stdin** and outputs per-operation tax amounts based on weighted-average price, loss carry-over rules, and the 20,000.00 (currency units) exemption threshold.

## Design and Implementation

- ***com.vidayko.tax.Application*** — the application entry point.
- ***com.vidayko.tax.logic.Calculator*** — the main stateful logic component exposing a synchronized API method `calculate()`.  
  Each call resets internal state (Quantity Held, Weighted Average Price, Accumulated Loss), ensuring deterministic and isolated calculations.
- ***com.vidayko.tax.logic.CompoundBigDecimal*** — a wrapper around `BigDecimal` used to centralize rounding, scale, and precision rules. Provides proxy arithmetic methods and enforces consistent numeric handling across all calculations.
- ***package com.vidayko.tax.model*** — contains internal domain objects used in business logic (e.g., `Operation`, `TaxRecord`).

### Architectural Notes

- The calculator is intentionally **stateful** per invocation to simplify weighted-average calculations and loss tracking across sequential operations.
- Parsing and serialization are isolated from the core logic, keeping calculation code free of I/O concerns.
- The CLI interface supports UNIX-style pipelines:  
  `cat input.txt | capital-gains` for easy automation and scripting.

## Frameworks & Libraries
- ***jackson-databind*** used to parse input JSON arrays into Operation objects and serialize calculated TaxRecord results back to JSON.
- ***lombok*** used to reduce boilerplate in model classes (e.g., getters, constructors).
- ***junit-jupiter*** used as the main testing framework to write and run unit and integration tests.
- ***commons-io*** used in tests to simplify working with test input/output files and stream utilities.
- ***assertj-core*** used in tests because it provides assertions and recursive comparison/equality support for complex objects, which *junit-jupiter* does not offer.

## Build

Requires **Java 11+** and **Maven**.

```bash
mvn clean install
```

This produces the executable CLI JAR under:

**Note:** The resulting JAR is *fat-packaged* (uber-jar), containing all required libraries.

```
./target/tax-calculator-cli-1.0-SNAPSHOT.jar
```

## Tests

*Unit* and *Integration* tests run automatically during `mvn package`.  
No separate test execution is required.

To run tests manually:

```bash
mvn test
```

## Run

### Running as Linux/Unix Command

Requires **Linux/Unix** and **Java 11+** (JRE or JDK).

#### Install

```bash
chmod +x linux-install.sh
sudo ./linux-install.sh
```

This installs the JAR into `/opt/capital-gains/` and configures the `capital-gains` command wrapper.

#### Run

```bash
capital-gains < ./src/test/resources/testcases/com/vidayko/tax/ApplicationTest/input.txt
```
#### Uninstall

```bash
chmod +x linux-uninstall.sh
sudo ./linux-uninstall.sh
```

This uninstalls the `/opt/capital-gains/` and deletes the `capital-gains` command wrapper.

### Running Platform Independently

Requires **Java 11+** (JRE or JDK).

```bash
java -jar ./target/tax-calculator-cli-1.0-SNAPSHOT.jar < /home/user/projects/idea/tax-calculator-cli/src/test/resources/testcases/com/vidayko/tax/ApplicationTest/input.txt
```

## Additional Notes

- Input must be a JSON array of operations, each containing `operation`, `unit-cost`, and `quantity`.
- Output is a JSON array of `{ "tax": ... }` objects, aligned 1:1 with input operations.
- The application preserves floating-point precision using `BigDecimal` with controlled rounding logic.