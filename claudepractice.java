import java.util.Scanner;
public class claudepractice {

    public static void main(String[] args) {
        Scanner hello = new Scanner(System.in);

    // int temperature; 

    // System.out.print("Enter the temperature in fahrenheit: ");
    // temperature = hello.nextInt();


    // //converting to celsius
  
    // double celsius = (temperature - 32) * 5.0 / 9.0;

    // System.out.println("The temperature in celsius is: " + celsius);

    // if (celsius < 0)
    // {
    //     System.out.println("The temperature is below freezing point.");

    // }
    // else if (celsius >= 0 && celsius < 15)
    // {
    //     System.out.println("The temperature is cold.");

    // }
    // else if (celsius >= 15 && celsius < 25)
    // {
    //     System.out.println("The temperature is comfortable.");

    // }
    // else if (celsius >= 25 && celsius < 35)
    // {
    //     System.out.println("The temperature is hot.");

    // }
    // else if (celsius >= 35)
    // {
    //     System.out.println("The temperature is very hot.");



    // }
    int size = hello.nextInt();
    int [] numbers =new int [size] ;

    // inputting values into the array
    for (int i = 0; i < size; i++)
    {
        numbers[i] = hello.nextInt();
       
    }

    // printing the array

    for (int i = 0; i < size; i++)
    {
        System.out.println("The numbers are: " + numbers[i]);
    }


    int x = hello.nextInt();

    for (int i = 0; i < size; i++)
    {
        if (numbers[i] == x)
        {
            System.out.println("The number " + x + " is found at index " + i);
            break;
        }
    }

}}