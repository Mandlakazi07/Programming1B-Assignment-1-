package hospital;

/**
 * Feature 4: Inpatient extends Patient.
 *
 * Constructor chaining: our constructor's FIRST line calls super(...),
 * which runs Patient's constructor to set up all the shared fields
 * (id, name, age, gender, condition, category). We then only set the
 * two EXTRA fields that are specific to inpatients.
 *
 * bedNumber is 0 until a bed is actually allocated via Ward.allocateBed().
 * We keep registration (Feature 1) and bed allocation (Feature 2) as
 * separate steps, matching the spec's separate feature list.
 */
public class Inpatient extends Patient {

    private String wardNumber;
    private int bedNumber; // 0 means "not yet allocated a bed"

    public Inpatient(String patientId, String firstName, String lastName, int age,
                      String gender, String medicalCondition, String wardNumber) {
        // super() must be the first statement in the constructor.
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = 0;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public boolean hasBed() {
        return bedNumber != 0;
    }

    /**
     * Method overriding: this REPLACES Patient's version when called on
     * an Inpatient object, but we start by calling super.displayDetails()
     * so we don't have to repeat all the shared-field printing logic.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number      : " + wardNumber);
        System.out.println("Bed Number       : " + (hasBed() ? String.format("B%02d", bedNumber) : "Not allocated"));
    }
}
