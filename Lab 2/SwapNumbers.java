public class SwapNumbers {
    public static void main(String[] args) {
        // 1. Declare and initialize two integer variables
        int num1 = 15;
        int num2 = 27;

        // Display the values before swapping
        System.out.println("--- Before Swapping ---");
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);

        // 2. Swap their values using a temporary variable
        int temp = num1; // Step A: Pour num1 into the empty 'temp' glass
        num1 = num2;     // Step B: Pour num2 into the now-empty 'num1' glass
        num2 = temp;     // Step C: Pour the original num1 (from 'temp') into the 'num2' glass

        // 3. Display the values after swapping
        System.out.println("\n--- After Swapping ---");
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }
}