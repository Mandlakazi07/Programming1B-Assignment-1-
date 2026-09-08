package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feature 5: Bed Management Tests [5 marks].
 * Covers: allocate a bed, release a bed, prevent allocating an occupied bed,
 * prevent bed allocation when all beds are occupied.
 */
public class BedManagementTest {

    private HospitalSystem system;

    @BeforeEach
    void setUp() {
        system = new HospitalSystem();
    }

    @Test
    void testAllocateBed() {
        Inpatient inpatient = new Inpatient("P010", "Nomvula", "Cele", 60, "Female", "Pneumonia", "W1");
        system.registerPatient(inpatient);

        String result = system.allocateBedToPatient("P010");

        assertEquals("OK", result);
        assertTrue(inpatient.hasBed());
        assertEquals(1, system.getWard().getOccupiedCount());
    }

    @Test
    void testReleaseBed() {
        Inpatient inpatient = new Inpatient("P011", "Mandla", "Ngcobo", 40, "Male", "Appendicitis", "W1");
        system.registerPatient(inpatient);
        system.allocateBedToPatient("P011");
        int bedNumber = inpatient.getBedNumber();

        boolean released = system.getWard().releaseBed(bedNumber);

        assertTrue(released);
        assertEquals(0, system.getWard().getOccupiedCount());
    }

    @Test
    void testPreventAllocatingAnAlreadyOccupiedBedToSamePatient() {
        Inpatient inpatient = new Inpatient("P012", "Zanele", "Khumalo", 33, "Female", "Malaria", "W1");
        system.registerPatient(inpatient);
        system.allocateBedToPatient("P012"); // first allocation succeeds

        String secondAttempt = system.allocateBedToPatient("P012");

        assertEquals("ALREADY_HAS_BED", secondAttempt,
                "A patient who already has a bed shouldn't be allocated a second one");
        assertEquals(1, system.getWard().getOccupiedCount(), "Occupied count shouldn't change");
    }

    @Test
    void testPreventBedAllocationWhenAllBedsOccupied() {
        // Fill all 20 beds first.
        for (int i = 1; i <= 20; i++) {
            Inpatient inpatient = new Inpatient("P0" + i, "First" + i, "Last" + i, 30, "Male", "Condition", "W1");
            system.registerPatient(inpatient);
            system.allocateBedToPatient("P0" + i);
        }
        assertEquals(20, system.getWard().getOccupiedCount(), "All 20 beds should be full");

        // The 21st inpatient should be turned away.
        Inpatient extraPatient = new Inpatient("P099", "Extra", "Patient", 30, "Male", "Condition", "W1");
        system.registerPatient(extraPatient);
        String result = system.allocateBedToPatient("P099");

        assertEquals("WARD_FULL", result, "No bed should be allocated once the ward is full");
    }
}
