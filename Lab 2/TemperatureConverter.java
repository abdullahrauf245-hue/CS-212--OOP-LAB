public class TemperatureConverter {
    public static void main(String[] args) {
        // 1. Declare a temperature in Celsius
        double celsius = 25.0; // You can change this value to test other temperatures

        // 2. Convert it to Fahrenheit
        // We use 9.0 / 5.0 to ensure Java calculates the decimal correctly
        double fahrenheit = (celsius * 9.0 / 5.0) + 32;

        // 3. Display the converted temperature
        System.out.println("--- Temperature Conversion ---");
        System.out.println("Celsius: " + celsius + "°C");
        System.out.println("Fahrenheit: " + fahrenheit + "°F");
    }
}