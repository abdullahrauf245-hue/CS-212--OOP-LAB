import java.util.Scanner;

public class ZellersCongruence {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1. Prompt the user for year, month, and day
        System.out.print("Enter year (e.g., 2012): ");
        int year = input.nextInt();

        System.out.print("Enter month (1-12): ");
        int month = input.nextInt();

        System.out.print("Enter the day of the month (1-31): ");
        int day = input.nextInt();

        // 2. Adjust for Zeller's Congruence rules
        // January and February count as months 13 and 14 of the previous year
        if (month == 1 || month == 2) {
            month += 12;
            year -= 1;
        }

        // 3. Assign the variables based on the algorithm
        int q = day;
        int m = month;
        int j = year / 100; // Zero-based century
        int k = year % 100; // Year of the century

        // 4. Calculate 'h' using the formula
        // Note: integer division in Java automatically handles the floor operation
        int h = (q + (13 * (m + 1)) / 5 + k + (k / 4) + (j / 4) + 5 * j) % 7;

        // 5. Map the result (0-6) to the corresponding day of the week
        String dayOfWeek = "";
        switch (h) {
            case 0: dayOfWeek = "Saturday"; break;
            case 1: dayOfWeek = "Sunday"; break;
            case 2: dayOfWeek = "Monday"; break;
            case 3: dayOfWeek = "Tuesday"; break;
            case 4: dayOfWeek = "Wednesday"; break;
            case 5: dayOfWeek = "Thursday"; break;
            case 6: dayOfWeek = "Friday"; break;
        }

        // 6. Display the final result
        System.out.println("Day of the week is " + dayOfWeek);

        input.close();
    }
}
