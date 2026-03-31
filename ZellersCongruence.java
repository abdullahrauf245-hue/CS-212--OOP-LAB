// public class ProductTask {
//     public static void main(String[] args) {
       
//         int num1 = 5;
//         int num2 = 10;
//         int num3 = 2;

       
//         int product = num1 * num2 * num3;

      
//         System.out.println("The product of " + num1 + ", " + num2 + ", and " + num3 + " is: " + product);
//     }
// }

// public class FactorialTask {
//     public static void main(String[] args) {
//         int number = 5;
//         long factorial = 1;

        
//         for (int i = 1; i <= number; i++) {
//             factorial *= i;
//         }

       
//         System.out.println("Factorial of " + number + " is: " + factorial);
//     }}

// public class SimpleInterest {
//     public static void main(String[] args) {
        
//         double principal = 1000.0;
//         double rate = 5.0;
//         double time = 2.0;

        
//         double interest = (principal * rate * time) / 100;

        
//         System.out.println("The Simple Interest is: " + interest);
//     }
// }

// public class SwapNumbers {
//     public static void main(String[] args) {
//         int a = 15;
//         int b = 30;

//         System.out.println("Before swapping: a = " + a + ", b = " + b); 

        
//         int temp = a; 
//         a = b;
//         b = temp;

//         System.out.println("After swapping: a = " + a + ", b = " + b); 
//     }
// }

// public class SquareNumber {
//     public static void main(String[] args) {
//         int num = 8; // [cite: 57]
//         int square = num * num; // [cite: 58]

//         System.out.println("The square of " + num + " is: " + square); // [cite: 59]
//     }
// }

// public class TemperatureConverter {
//     public static void main(String[] args) {
//         double celsius = 25.0; 

    
//         double fahrenheit = (celsius * 9 / 5) + 32;

//         System.out.println(celsius + " Celsius is equal to " + fahrenheit + " Fahrenheit"); // [cite: 65]
//     }
// }

public class ZellersCongruence {
    public static void main(String[] args) {
        
        int day = 15;
        int month = 8;
        int year = 2023;

        int q = day;
        int m = month;
        int y = year;

        if (m == 1 || m == 2) {
            m += 12; 
            y -= 1;  
        }
        int j = y / 100; 
        int k = y % 100; 
        int h = (q + (26 * (m + 1)) / 10 + k + k / 4 + j / 4 + 5 * j) % 7; 
        String dayName = "";
        if (h == 0) dayName = "Saturday"; 
        else if (h == 1) dayName = "Sunday";
        else if (h == 2) dayName = "Monday";
        else if (h == 3) dayName = "Tuesday";
        else if (h == 4) dayName = "Wednesday";
        else if (h == 5) dayName = "Thursday";
        else if (h == 6) dayName = "Friday";

        System.out.println("Given Date: " + day + " / " + month + " / " + year); 
        System.out.println("Day of the Week: " + dayName); 
    }}

