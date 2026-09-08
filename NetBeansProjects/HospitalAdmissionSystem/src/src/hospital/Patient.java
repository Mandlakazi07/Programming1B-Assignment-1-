package hospital;

/**
 * Feature 1 & 4: the base Patient class.
 *
 * Encapsulation: every field is private. Nothing outside this class can
 * touch patientId, firstName, etc. directly - they must go through the
 * getters/setters below. That's what earns the "Encapsulation" marks.
 *
 * Outpatients and Emergency patients are represented directly by this
 * class (no bed/ward info needed). Inpatients use the Inpatient subclass.
 */
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    public Patient(String patientId, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // ----- Getters -----
    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public PatientCategory getCategory() {
        return category;
    }

    // ----- Setters (used by "Update patient details") -----
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    // Deliberately no setPatientId() and no setCategory():
    // an ID shouldn't change once issued, and changing category after
    // registration would leave Inpatient-only data in an inconsistent state.

    /**
     * Prints this patient's details to the console.
     * Inpatient overrides this to ADD ward/bed info on top of what's here
     * (via super.displayDetails()) rather than replacing it.
     */
    public void displayDetails() {
        System.out.println("Patient ID       : " + patientId);
        System.out.println("Name             : " + firstName + " " + lastName);
        System.out.println("Age              : " + age);
        System.out.println("Gender           : " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category         : " + category);
    }

    @Override
    public String toString() {
        return patientId + " - " + firstName + " " + lastName + " (" + category + ")";
    }
}
