/* Lab 8 Task 2: Smart Healthcare Monitoring System - Hierarchical Inheritance
UML:
Patient (base: vitals, severity, status)
├── CriticalPatient (high freq monitor, immediate attention)
└── StablePatient (normal monitor)
*/

class Patient {
    protected String name;
    protected double vitalsLevel; // lower = worse
    protected String severity;
    
    public Patient(String n, double v, String s) {
        name = n;
     vitalsLevel = v;
        severity = s;
    }
    
    public void updateVitals(double newVitals) {
        vitalsLevel = newVitals;
    }
    
    public String getStatus() {
        return severity + " - Vitals: " + vitalsLevel;
    }
    
    public boolean needsAttention() {
        return vitalsLevel < 5.0;
    }
    
    public String getName() {
        return name;
    }
    
    public double getVitals() {
        return vitalsLevel;
    }
    
    public String toString() {
        return name + ": " + getStatus();
    }
}

class CriticalPatient extends Patient {
    public CriticalPatient(String n, double v) {
        super(n, v, "CRITICAL");
    }
    
    public String getStatus() {
        if (vitalsLevel < 3.0) return "EMERGENCY IMMEDIATE ATTENTION";
        return super.getStatus();
    }
    
    public boolean needsAttention() {
        return true; // always
    }
}

class StablePatient extends Patient {
    public StablePatient(String n, double v) {
        super(n, v, "STABLE");
    }
    
    public boolean needsAttention() {
        return vitalsLevel < 4.0;
    }
}

public class Lab8_Task2_HealthcareSystem {
    public static void main(String[] args) {
        Patient[] patients = {
            new CriticalPatient("PatientA", 2.5),
            new CriticalPatient("PatientB", 4.2),
            new StablePatient("PatientC", 6.8),
            new StablePatient("PatientD", 3.9)
        };
        
        // Update some vitals (simulate monitoring)
        patients[0].updateVitals(1.8);
        patients[3].updateVitals(4.5);
        
        int criticalCount = 0;
        System.out.println("=== Patient Monitoring Summary ===");
        for (Patient p : patients) {
            System.out.println(p);
            if (p.needsAttention()) {
                criticalCount++;
                System.out.println("  -> NEEDS IMMEDIATE ATTENTION!");
            }
        }
        
        System.out.println("\nCritical cases requiring attention: " + criticalCount);
        System.out.println("Health Status Summary: Process complete.");
    }
}
