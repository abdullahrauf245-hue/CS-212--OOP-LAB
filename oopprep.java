// import java.util.Scanner;
// public class oopprep{


// public static void main (String [] args){
// // // double lenght ;
// // // double breadth ;
// // // double area ;

// // // lenght = 10.0 ;
// // // breadth = 5.0 ;


// // // area = lenght * breadth ;


// // // System.out.println("The area of the rectangle is: " + area);

// // Scanner hello = new Scanner(System.in);

// // System.out.println("Enter your name: ");

// // String name  = hello.nextLine();

// // System.out.println("Hello, " + name + "! Welcome to Java programming.");

// // int number1 = 2;
// // int number2 = 3;

// // double average = (number1 + number2) / 2.0;

// // System.out.println("The average of " + number1 + " and " + number2 + " is: " + average);


// Scanner hello = new Scanner(System.in);


// System.out.print("Enter principal amount:" );
// int principal = hello.nextInt();


// System.out.print("Enter rate of interest:" );
// double rate = hello.nextDouble();

// System.out.print("Enter time in years:" );
// int time = hello.nextInt();

// double simpleInterest = (principal * rate * time) / 100;

// System.out.println("The simple interest is: " + simpleInterest);

// }



// }

import java.util.Scanner;

public class oopprep {
    public static void main(String[] args) {
        // 1. Generate two random single-digit integers
        int number1 = (int)(Math.random() * 10);
        int number2 = (int)(Math.random() * 10);

        // 2. If number1 < number2, swap number1 with number2
        if (number1 < number2) {
            int temp = number1;
            number1 = number2;
            number2 = temp;
        }

        // 3. Prompt the student to answer "What is number1 - number2?"
        System.out.println
            ("What is " + number1 + " - " + number2 + "? ");
        Scanner input = new Scanner(System.in);
        int answer = input.nextInt();

        // 4. Grade the answer and display the result
        if (number1 - number2 == answer)
            System.out.println("You are correct!");
        else {
            System.out.println("Your answer is wrong.");
            System.out.println(number1 + " - " + number2 +
                " should be " + (number1 - number2));
        }
    }
}