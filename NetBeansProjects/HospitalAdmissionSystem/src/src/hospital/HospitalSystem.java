package hospital;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The "glue" class: holds the in-memory list of all patients and the one
 * Ward, and exposes the operations the menu (or the JUnit tests) call into.
 * Keeping this logic separate from Main means we can unit-test it without
 * needing a Scanner or console input at all.
 */
public class HospitalSystem {

    private final List<Patient> patients;
    private final Ward ward;

    public HospitalSystem() {
        this.patients = new ArrayList<>();
        this.ward = new Ward();
    }

    public Ward getWard() {
        return ward;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Registers a new patient. Returns false (and registers nobody) if the
     * patientId is already in use - IDs must be unique.
     */
    public boolean registerPatient(Patient patient) {
        if (findPatient(patient.getPatientId()) != null) {
            return false; // duplicate ID
        }
        patients.add(patient);
        return true;
    }

    public Patient findPatient(String patientId) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Deletes a patient by ID. If they were an inpatient occupying a bed,
     * the bed is released first so it doesn't stay "stuck" as occupied.
     */
    public boolean deletePatient(String patientId) {
        Patient p = findPatient(patientId);
        if (p == null) {
            return false;
        }
        if (p instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) p;
            if (inpatient.hasBed()) {
                ward.releaseBed(inpatient.getBedNumber());
            }
        }
        patients.remove(p);
        return true;
    }

    public void displayAllPatients() {
        System.out.println("\n--- All Registered Patients ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient p : patients) {
            p.displayDetails();
            System.out.println("--------------------------------");
        }
    }

    public int getTotalPatients() {
        return patients.size();
    }

    /**
     * Feature 2: allocates a bed to an existing inpatient, looked up by ID.
     * Returns a result code so Main can show the right message:
     *   "OK"        - allocated successfully
     *   "NOT_FOUND" - no patient with that ID
     *   "NOT_INPATIENT" - patient exists but isn't an Inpatient
     *   "ALREADY_HAS_BED" - inpatient already occupies a bed
     *   "WARD_FULL" - no beds available
     */
    public String allocateBedToPatient(String patientId) {
        Patient p = findPatient(patientId);
        if (p == null) {
            return "NOT_FOUND";
        }
        if (!(p instanceof Inpatient)) {
            return "NOT_INPATIENT";
        }
        Inpatient inpatient = (Inpatient) p;
        if (inpatient.hasBed()) {
            return "ALREADY_HAS_BED";
        }
        String bedId = ward.allocateBed(inpatient);
        return bedId != null ? "OK" : "WARD_FULL";
    }

    public void sortPatientsBySurname() {
        patients.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortPatientsById() {
        patients.sort(Comparator.comparing(Patient::getPatientId, String.CASE_INSENSITIVE_ORDER));
    }
}
