package hospital;

/**
 * Feature 2: Bed Management.
 * Holds the 20 beds as a 4x5 2D array, as required by the spec:
 *   B01 B02 B03 B04 B05
 *   B06 B07 B08 B09 B10
 *   B11 B12 B13 B14 B15
 *   B16 B17 B18 B19 B20
 */
public class Ward {

    private static final int ROWS = 4;
    private static final int COLS = 5;

    private final Bed[][] beds;

    public Ward() {
        beds = new Bed[ROWS][COLS];
        int bedNumber = 1;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                beds[r][c] = new Bed(bedNumber);
                bedNumber++;
            }
        }
    }

    public int getTotalBeds() {
        return ROWS * COLS;
    }

    /**
     * Allocates the first free bed to the given inpatient.
     * Returns the bed id (e.g. "B07") on success, or null if the ward is full.
     */
    public String allocateBed(Inpatient patient) {
        for (Bed[] row : beds) {
            for (Bed bed : row) {
                if (!bed.isOccupied()) {
                    bed.setOccupied(true);
                    bed.setPatient(patient);
                    patient.setBedNumber(bed.getBedNumber());
                    return bed.getBedId();
                }
            }
        }
        return null; // no beds available
    }

    /**
     * Releases the bed with the given number (1-20).
     * Returns true if a bed was found and released, false otherwise.
     */
    public boolean releaseBed(int bedNumber) {
        Bed bed = findBed(bedNumber);
        if (bed != null && bed.isOccupied()) {
            if (bed.getPatient() != null) {
                bed.getPatient().setBedNumber(0);
            }
            bed.setOccupied(false);
            bed.setPatient(null);
            return true;
        }
        return false;
    }

    public boolean isBedOccupied(int bedNumber) {
        Bed bed = findBed(bedNumber);
        return bed != null && bed.isOccupied();
    }

    private Bed findBed(int bedNumber) {
        for (Bed[] row : beds) {
            for (Bed bed : row) {
                if (bed.getBedNumber() == bedNumber) {
                    return bed;
                }
            }
        }
        return null;
    }

    public void displayWardLayout() {
        System.out.println("\n--- Ward Layout (4 x 5) ---");
        for (Bed[] row : beds) {
            StringBuilder line = new StringBuilder();
            for (Bed bed : row) {
                line.append(bed.getBedId())
                    .append(bed.isOccupied() ? "[X] " : "[ ] ");
            }
            System.out.println(line.toString());
        }
        System.out.println("[ ] = available   [X] = occupied");
    }

    public void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        boolean any = false;
        for (Bed[] row : beds) {
            for (Bed bed : row) {
                if (!bed.isOccupied()) {
                    System.out.println(bed.getBedId());
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("No beds available.");
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        boolean any = false;
        for (Bed[] row : beds) {
            for (Bed bed : row) {
                if (bed.isOccupied()) {
                    Inpatient p = bed.getPatient();
                    System.out.println(bed.getBedId() + " - "
                            + (p != null ? p.getFirstName() + " " + p.getLastName() + " (" + p.getPatientId() + ")" : "Unknown"));
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("No beds occupied.");
        }
    }

    public int getOccupiedCount() {
        int count = 0;
        for (Bed[] row : beds) {
            for (Bed bed : row) {
                if (bed.isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    public double getOccupancyPercentage() {
        return (getOccupiedCount() * 100.0) / getTotalBeds();
    }
}
