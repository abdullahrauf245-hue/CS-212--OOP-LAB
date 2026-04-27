class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    String name;
    int age;
    int marks;

    Student(String name, int age, int marks) {
        this.name = name;
      this.age = age;
        this.marks = marks;
    }
}

public class UniversityAdmissionSystem {

    public static void validateStudent(Student student) throws InvalidAgeException, InvalidMarksException {
        if (student.age < 18) {
            throw new InvalidAgeException(
                "Admission failed for " + student.name + ": age must be 18 or above."
            );
        }

        if (student.marks < 0 || student.marks > 100) {
            throw new InvalidMarksException(
                "Admission failed for " + student.name + ": marks must be between 0 and 100."
            );
        }
    }

    public static void main(String[] args) {
        Student[] students = {
            new Student("Ali", 17, 82),
            new Student("Sara", 19, 110),
            new Student("Ayesha", 20, 88),
          new Student("Hassan", 18, 73)
        };

        System.out.println("=== University Admission System ===");

        for (Student s : students) {
            try {
                validateStudent(s);
                System.out.println("Admission approved for " + s.name + ". You can proceed.");
            } catch (InvalidAgeException | InvalidMarksException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("Process completed.");
    }
}
