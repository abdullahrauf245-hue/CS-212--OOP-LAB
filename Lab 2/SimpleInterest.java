public class SimpleInterest {
    public static void main(String[] args) {
        // 1. Declare variables for principal, rate, and time
        double principal = 1000.0; // The initial amount of money
        double rate = 5.0;         // The interest rate (e.g., 5%)
        double time = 2.0;         // The time the money is invested or borrowed for (in years)

        // 2. Calculate simple interest using the formula
        double simpleInterest = (principal * rate * time) / 100;

        // 3. Print the result so the variable is used (avoiding the warning you saw earlier!)
        System.out.println("Principal Amount: $" + principal);
        System.out.println("Interest Rate: " + rate + "%");
        System.out.println("Time Period: " + time + " years");
        System.out.println("Calculated Simple Interest: $" + simpleInterest);
    }
}