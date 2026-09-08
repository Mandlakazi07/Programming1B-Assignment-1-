package hospital;

/**
 * Feature 4: the three categories the hospital treats.
 * Using an enum here (instead of a plain String) means the compiler
 * stops us from ever setting a category to something invalid like "Visitor".
 */
public enum PatientCategory {
    INPATIENT,
    OUTPATIENT,
    EMERGENCY
}
