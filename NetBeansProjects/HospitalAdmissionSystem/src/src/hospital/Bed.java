package hospital;

/**
 * Feature 2: a single bed in the ward.
 * Numbered 1-20; displayed as "B01".."B20".
 */
public class Bed {

    private final int bedNumber;
    private boolean occupied;
    private Inpatient patient;

    public Bed(int bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patient = null;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public String getBedId() {
        return String.format("B%02d", bedNumber);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Inpatient getPatient() {
        return patient;
    }

    public void setPatient(Inpatient patient) {
        this.patient = patient;
    }
}
