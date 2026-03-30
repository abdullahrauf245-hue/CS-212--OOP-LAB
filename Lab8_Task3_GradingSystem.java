/* Lab 8 Task 3: Smart University Grading Ecosystem - Hierarchical Inheritance
UML:
Student (base: calculateGrade)
├── ExamStudent (grade = examScore)
└── ProjectStudent (grade = (project + quiz + presentation)/3)
*/

abstract class Student {
    protected String name;
    protected String category;
    
    public Student(String n, String cat) {
        name = n;
        category = cat;
    }
    
    public abstract double calculateGrade();
    
    public String getPerformanceCategory() {
        double grade = calculateGrade();
        if (grade > 90) return "Excellent";
        else if (grade >= 80) return "Good";
        else return "Needs Improvement";
    }
    
    public String toString() {
        return name + " (" + category + "): Grade=" + calculateGrade() + " [" + getPerformanceCategory() + "]";
    }
    
    public String getName() {
        return name;
    }
    
    public double getGrade() {
        return calculateGrade();
    }
}

class ExamStudent extends Student {
    private double examScore;
    
    public ExamStudent(String n, double score) {
        super(n, "Exam");
        examScore = score;
    }
    
    public double calculateGrade() {
        return examScore;
    }
}

class ProjectStudent extends Student {
    private double project, quiz, presentation;
    
    public ProjectStudent(String n, double p, double q, double pres) {
        super(n, "Project");
        project = p;
        quiz = q;
        presentation = pres;
    }
    
    public double calculateGrade() {
        return (project + quiz + presentation) / 3.0;
    }
}

public class Lab8_Task3_GradingSystem {
    public static void main(String[] args) {
        Student[] students = {
            new ExamStudent("Alice", 92),
            new ExamStudent("Bob", 78),
            new ProjectStudent("Charlie", 88, 82, 90),
            new ProjectStudent("Diana", 75, 80, 72)
        };
        
        double topGrade = 0;
        Student topStudent = null;
        
        System.out.println("=== University Grading Summary ===");
        for (Student s : students) {
            System.out.println(s);
            double grade = s.getGrade();
            if (grade > topGrade) {
                topGrade = grade;
                topStudent = s;
            }
        }
        
        System.out.println("\nTop Performer: " + topStudent);
        System.out.println("All categories processed. System supports new types via inheritance.");
    }
}
