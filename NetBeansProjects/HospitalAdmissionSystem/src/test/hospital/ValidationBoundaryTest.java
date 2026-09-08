package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feature 5: Validation and Boundary Tests [5 marks].
 * Covers: prevent duplicate Patient IDs, sort patients by surname or Patient ID.
 */
public class ValidationBoundaryTest {

    private HospitalSystem system;

    @BeforeEach
    void setUp() {
        system = new HospitalSystem();
    }

    @Test
    void testPreventDuplicatePatientIds() {
        Patient first = new Patient("P020", "Ayanda", "Sithole", 25, "Female", "Cold", PatientCategory.OUTPATIENT);
        Patient duplicate = new Patient("P020", "Someone", "Else", 40, "Male", "Cough", PatientCategory.OUTPATIENT);

        boolean firstResult = system.registerPatient(first);
        boolean duplicateResult = system.registerPatient(duplicate);

        assertTrue(firstResult, "First registration with a new ID should succeed");
        assertFalse(duplicateResult, "Second registration with the same ID should be rejected");
        assertEquals(1, system.getTotalPatients(), "Only the first patient should have been added");
    }

    @Test
    void testSortPatientsBySurname() {
        system.registerPatient(new Patient("P030", "A", "Zulu", 20, "Female", "-", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P031", "B", "Adams", 20, "Female", "-", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P032", "C", "Mokoena", 20, "Female", "-", PatientCategory.OUTPATIENT));

        system.sortPatientsBySurname();
        List<Patient> patients = system.getPatients();

        assertEquals("Adams", patients.get(0).getLastName());
        assertEquals("Mokoena", patients.get(1).getLastName());
        assertEquals("Zulu", patients.get(2).getLastName());
    }

    @Test
    void testSortPatientsById() {
        system.registerPatient(new Patient("P099", "A", "Last", 20, "Female", "-", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P001", "B", "Last", 20, "Female", "-", PatientCategory.OUTPATIENT));
        system.registerPatient(new Patient("P050", "C", "Last", 20, "Female", "-", PatientCategory.OUTPATIENT));

        system.sortPatientsById();
        List<Patient> patients = system.getPatients();

        assertEquals("P001", patients.get(0).getPatientId());
        assertEquals("P050", patients.get(1).getPatientId());
        assertEquals("P099", patients.get(2).getPatientId());
    }
}
