# Hospital Patient Admission System - PROG6112 Practical Assignment 1

## How to add this to NetBeans

1. Open (or create) your NetBeans Java project for this assignment.
2. Copy the `src/hospital/` folder's contents into your project's
   `Source Packages` (usually the `src` folder), keeping the `hospital`
   package folder.
3. Copy the `test/hospital/` folder's contents into your project's
   `Test Packages` folder (NetBeans creates this automatically, or:
   right-click project -> Properties -> Sources -> check "Test Package
   Folders").
4. Add JUnit 5 to the project:
   - Right-click the project -> Properties -> Libraries -> Add Library
   - Choose "JUnit" (pick the JUnit 5 / Jupiter version if NetBeans
     offers a choice - some NetBeans installs only list JUnit 4 by
     default, in which case see the note below).
5. Right-click `Main.java` -> Run File to launch the console menu.
6. Right-click the `test` package -> Test Package to run all JUnit tests.

## If your NetBeans only has JUnit 4

These tests use JUnit 5 (Jupiter) annotations (`@BeforeEach`, `@Test` from
`org.junit.jupiter.api`). If your project's library manager only offers
JUnit 4, either:
- add the JUnit 5 jars manually (Libraries -> Add JAR/Folder, pointing at
  junit-jupiter jars), or
- tell me and I'll convert the three test classes to JUnit 4 syntax
  (`@Before` instead of `@BeforeEach`, `org.junit.Test` instead of
  `org.junit.jupiter.api.Test`, `org.junit.Assert.*` instead of
  `org.junit.jupiter.api.Assertions.*`) - it's a small, mechanical change.

## Design notes worth knowing for your own understanding

- **Bed allocation is a separate step from registration.** Registering an
  Inpatient creates them with a ward number but bedNumber = 0. You then use
  Bed Management -> "Allocate a bed" to actually assign them a bed. This
  matches the spec listing Feature 1 (patient management) and Feature 2
  (bed management) separately.
- **Deleting an inpatient releases their bed automatically**, so beds never
  get "stuck" as occupied by a patient who no longer exists.
- **HospitalSystem has zero Scanner/console code in it.** All the actual
  logic (register, search, allocate, etc.) lives there and returns simple
  values/strings. `Main` only reads input and prints results. That's what
  lets the JUnit tests call `HospitalSystem` methods directly without
  needing to simulate console input.
