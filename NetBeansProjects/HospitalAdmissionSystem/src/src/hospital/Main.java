package hospital;

import java.util.Scanner;

/**
 * Console entry point. All the real logic lives in HospitalSystem/Ward -
 * this class is deliberately "dumb": read input, call a method, print a
 * result. That separation is what makes HospitalSystem testable with JUnit
 * without a Scanner involved.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalSystem system = new HospitalSystem();
    private static final String WARD_NUMBER = "W1"; // only one ward, per the assumptions

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MediCare Hospital Admission System =====");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    bedMenu();
                    break;
                case 3:
                    reportsMenu();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }

    // ------------------------------------------------------------------
    // Feature 1: Patient Management
    // ------------------------------------------------------------------
    private static void patientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Register a new patient");
            System.out.println("2. Search for a patient");
            System.out.println("3. Update patient details");
            System.out.println("4. Delete a patient");
            System.out.println("5. Display all patients");
            System.out.println("6. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    searchPatient();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    system.displayAllPatients();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void registerPatient() {
        System.out.print("Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Age: ");
        int age = readInt();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Medical condition: ");
        String condition = scanner.nextLine();

        System.out.println("Category - 1: Inpatient  2: Outpatient  3: Emergency");
        System.out.print("Choose category: ");
        int categoryChoice = readInt();

        Patient patient;
        if (categoryChoice == 1) {
            patient = new Inpatient(id, firstName, lastName, age, gender, condition, WARD_NUMBER);
        } else if (categoryChoice == 2) {
            patient = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.OUTPATIENT);
        } else {
            patient = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.EMERGENCY);
        }

        if (system.registerPatient(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("A patient with ID " + id + " already exists.");
        }
    }

    private static void searchPatient() {
        System.out.print("Enter Patient ID to search: ");
        String id = scanner.nextLine();
        Patient p = system.findPatient(id);
        if (p == null) {
            System.out.println("No patient found with ID " + id);
        } else {
            p.displayDetails();
        }
    }

    private static void updatePatient() {
        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine();
        Patient p = system.findPatient(id);
        if (p == null) {
            System.out.println("No patient found with ID " + id);
            return;
        }
        System.out.print("New first name (" + p.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        if (!firstName.isBlank()) {
            p.setFirstName(firstName);
        }

        System.out.print("New last name (" + p.getLastName() + "): ");
        String lastName = scanner.nextLine();
        if (!lastName.isBlank()) {
            p.setLastName(lastName);
        }

        System.out.print("New age (" + p.getAge() + "): ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isBlank()) {
            p.setAge(Integer.parseInt(ageInput));
        }

        System.out.print("New medical condition (" + p.getMedicalCondition() + "): ");
        String condition = scanner.nextLine();
        if (!condition.isBlank()) {
            p.setMedicalCondition(condition);
        }

        System.out.println("Patient updated successfully.");
    }

    private static void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine();
        if (system.deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("No patient found with ID " + id);
        }
    }

    // ------------------------------------------------------------------
    // Feature 2: Bed Management
    // ------------------------------------------------------------------
    private static void bedMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Bed Management ---");
            System.out.println("1. Allocate a bed to an inpatient");
            System.out.println("2. Release a bed");
            System.out.println("3. Display ward layout");
            System.out.println("4. Display available beds");
            System.out.println("5. Display occupied beds");
            System.out.println("6. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    allocateBed();
                    break;
                case 2:
                    releaseBed();
                    break;
                case 3:
                    system.getWard().displayWardLayout();
                    break;
                case 4:
                    system.getWard().displayAvailableBeds();
                    break;
                case 5:
                    system.getWard().displayOccupiedBeds();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void allocateBed() {
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        String result = system.allocateBedToPatient(id);
        switch (result) {
            case "OK":
                System.out.println("Bed allocated successfully.");
                break;
            case "NOT_FOUND":
                System.out.println("No patient found with ID " + id);
                break;
            case "NOT_INPATIENT":
                System.out.println("Only Inpatients may be allocated a bed.");
                break;
            case "ALREADY_HAS_BED":
                System.out.println("This patient already has a bed.");
                break;
            case "WARD_FULL":
                System.out.println("No beds available - the ward is full.");
                break;
            default:
                System.out.println("Unexpected result: " + result);
        }
    }

    private static void releaseBed() {
        System.out.print("Enter bed number to release (1-20): ");
        int bedNumber = readInt();
        if (system.getWard().releaseBed(bedNumber)) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("That bed is not currently occupied (or doesn't exist).");
        }
    }

    // ------------------------------------------------------------------
    // Feature 3: Reports
    // ------------------------------------------------------------------
    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. All registered patients");
            System.out.println("2. All available beds");
            System.out.println("3. All occupied beds");
            System.out.println("4. Total registered patients");
            System.out.println("5. Total occupied beds");
            System.out.println("6. Ward occupancy percentage");
            System.out.println("7. Back to main menu");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    system.displayAllPatients();
                    break;
                case 2:
                    system.getWard().displayAvailableBeds();
                    break;
                case 3:
                    system.getWard().displayOccupiedBeds();
                    break;
                case 4:
                    System.out.println("Total registered patients: " + system.getTotalPatients());
                    break;
                case 5:
                    System.out.println("Total occupied beds: " + system.getWard().getOccupiedCount());
                    break;
                case 6:
                    System.out.printf("Ward occupancy: %.1f%%%n", system.getWard().getOccupancyPercentage());
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Reads an integer safely; returns -1 on bad input instead of crashing.
    private static int readInt() {
        String line = scanner.nextLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
