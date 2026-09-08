package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feature 5: CRUD Operation Tests [5 marks].
 * Covers: register, search, update, delete a patient.
 *
 * @BeforeEach runs before EVERY @Test method below, giving each test a
 * fresh HospitalSystem so tests can't affect one another.
 */
public class PatientCrudTest {

    private HospitalSystem system;

    @BeforeEach
    void setUp() {
        system = new HospitalSystem();
    }

    @Test
    void testRegisterPatient() {
        Patient p = new Patient("P001", "Thandi", "Zulu", 34, "Female", "Flu", PatientCategory.OUTPATIENT);
        boolean result = system.registerPatient(p);

        assertTrue(result, "Registering a new, unique patient should succeed");
        assertEquals(1, system.getTotalPatients());
    }

    @Test
    void testSearchForPatient() {
        Patient p = new Patient("P002", "Sipho", "Mahlangu", 45, "Male", "Diabetes", PatientCategory.OUTPATIENT);
        system.registerPatient(p);

        Patient found = system.findPatient("P002");

        assertNotNull(found, "Should find a patient that was registered");
        assertEquals("Mahlangu", found.getLastName());
    }

    @Test
    void testUpdatePatientDetails() {
        Patient p = new Patient("P003", "Lindiwe", "Nkosi", 29, "Female", "Migraine", PatientCategory.OUTPATIENT);
        system.registerPatient(p);

        Patient found = system.findPatient("P003");
        found.setMedicalCondition("Recovered");
        found.setAge(30);

        Patient updated = system.findPatient("P003");
        assertEquals("Recovered", updated.getMedicalCondition());
        assertEquals(30, updated.getAge());
    }

    @Test
    void testDeletePatient() {
        Patient p = new Patient("P004", "Bongani", "Dube", 51, "Male", "Fracture", PatientCategory.EMERGENCY);
        system.registerPatient(p);

        boolean deleted = system.deletePatient("P004");

        assertTrue(deleted, "Deleting an existing patient should succeed");
        assertNull(system.findPatient("P004"), "Patient should no longer be found after deletion");
        assertEquals(0, system.getTotalPatients());
    }
}
