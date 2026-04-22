// Task#3: HealthProfile Test Application
// File: Task3_HealthProfileTest.java

import java.util.Scanner;

public class Task3_HealthProfileTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Prompt for person's information
        System.out.println("=== Health Profile Test Application ===");
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        
        System.out.print("Enter gender (Male/Female): ");
        String gender = input.nextLine();
        
        System.out.print("Enter birth month (1-12): ");
        int birthMonth = input.nextInt();
        
        System.out.print("Enter birth day: ");
        int birthDay = input.nextInt();
        
        System.out.print("Enter birth year: ");
        int birthYear = input.nextInt();
        
        System.out.print("Enter height in inches: ");
        double height = input.nextDouble();
        
        System.out.print("Enter weight in pounds: ");
        double weight = input.nextDouble();
        
        // Create HealthProfile object
        Task3_HealthProfile profile = new Task3_HealthProfile(
            firstName, lastName, gender, 
            birthMonth, birthDay, birthYear,
            height, weight
        );
        
        // Display person's information
        System.out.println("\n=== Person's Information ===");
        System.out.println("First Name: " + profile.getFirstName());
        System.out.println("Last Name: " + profile.getLastName());
        System.out.println("Gender: " + profile.getGender());
        System.out.println("Date of Birth: " + profile.getBirthMonth() + "/" + 
                           profile.getBirthDay() + "/" + profile.getBirthYear());
        System.out.println("Height: " + profile.getHeightInches() + " inches");
        System.out.println("Weight: " + profile.getWeightPounds() + " pounds");
        
        // Calculate and display age in years
        System.out.println("\n=== Age Information ===");
        System.out.println("Age in years: " + profile.calculateAge());
        
        // Calculate and display maximum heart rate
        System.out.println("\n=== Heart Rate Information ===");
        System.out.println("Maximum Heart Rate: " + profile.calculateMaxHeartRate() + " bpm");
        System.out.println("Target Heart Rate Range: " + profile.calculateTargetHeartRate() + " bpm");
        
        // Calculate and display BMI
        System.out.println("\n=== BMI Information ===");
        System.out.printf("BMI: %.2f%n", profile.calculateBMI());
        System.out.println("BMI Category: " + profile.getBMICategory());
        
        // Display BMI values chart
        profile.displayBMIChart();
        
        input.close();
    }

    // Keep test self-contained to avoid cross-file default-package resolution issues.
    static class Task3_HealthProfile {
        private String firstName;
        private String lastName;
        private String gender;
        private int birthMonth;
        private int birthDay;
        private int birthYear;
        private double heightInches;
        private double weightPounds;

        Task3_HealthProfile(String firstName, String lastName, String gender,
                            int birthMonth, int birthDay, int birthYear,
                            double heightInches, double weightPounds) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthMonth = birthMonth;
            this.birthDay = birthDay;
            this.birthYear = birthYear;
            this.heightInches = heightInches;
            this.weightPounds = weightPounds;
        }

        String getFirstName() { return firstName; }
        String getLastName() { return lastName; }
        String getGender() { return gender; }
        int getBirthMonth() { return birthMonth; }
        int getBirthDay() { return birthDay; }
        int getBirthYear() { return birthYear; }
        double getHeightInches() { return heightInches; }
        double getWeightPounds() { return weightPounds; }

        int calculateAge() {
            int currentYear = 2026;
            int currentMonth = 4;
            int currentDay = 22;
            int age = currentYear - birthYear;
            if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDay > currentDay)) {
                age--;
            }
            return age;
        }

        int calculateMaxHeartRate() {
            return 220 - calculateAge();
        }

        String calculateTargetHeartRate() {
            int maxHR = calculateMaxHeartRate();
            int minTarget = (int) (maxHR * 0.50);
            int maxTarget = (int) (maxHR * 0.85);
            return minTarget + " - " + maxTarget;
        }

        double calculateBMI() {
            return (weightPounds / (heightInches * heightInches)) * 703;
        }

        String getBMICategory() {
            double bmi = calculateBMI();
            if (bmi < 18.5) return "Underweight";
            if (bmi < 25) return "Normal weight";
            if (bmi < 30) return "Overweight";
            return "Obesity";
        }

        void displayBMIChart() {
            System.out.println("\n=== BMI Values Chart ===");
            System.out.println("Underweight:    BMI < 18.5");
            System.out.println("Normal weight:  BMI = 18.5 - 24.9");
            System.out.println("Overweight:     BMI = 25 - 29.9");
            System.out.println("Obesity:        BMI >= 30");
        }
    }
}
