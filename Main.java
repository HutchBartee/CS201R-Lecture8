import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

      	System.out.print("EXAMPLE 1: No Check ");
        //EXAMPLE 1: No editing for entering 0
        // Prompt the user to enter two integers
      	System.out.print("Enter two integers: ");
        int number1 = input.nextInt();
	    int number2 = input.nextInt();
	    System.out.println(number1 + " / " + number2 + " is " + (number1 / number2));
    
        // Add a simple test to verify denominator is not 0
        // Prompt the user to enter two integers
      	System.out.println("EXAMPLE 1: Check for Divide by 0 ");
      	System.out.print("Enter two integers: ");
        number1 = input.nextInt();
	    number2 = input.nextInt();
        if (number2 != 0)
	        System.out.println(number1 + " / " + number2 + " is " + (number1 / number2));
        else
            System.out.println("Cannot divide by 0");

        //EXAMPLE 2: Out of Bounds 
      	System.out.println("\n\nEXAMPLE 2: Check indexes");
        int[] myNumbers = {1, 2, 3};
      	System.out.print("Enter the index to display: ");
        int idx1 = input.nextInt();

        // test before using (NOTE: this does not catch invalid input)
        while (idx1 < 0 || idx1 >= myNumbers.length){
            System.out.print("Sorry. That is invalid. Please enter a valid index to display: ");
            idx1 = input.nextInt();            
        }
        System.out.println(myNumbers[idx1]);

      	System.out.println("\n\nEXAMPLE 2: Check indexes (add loop to continue prompting user)");
      	System.out.print("Enter the index to display: ");
        // use try/catch/finally  
        try{
            idx1 = input.nextInt();
            System.out.println(myNumbers[idx1]);
        }
        catch (Exception e){
            System.out.println("EXCEPTION: something went wrong");
        }
        finally {
            System.out.println("FINALLY: the 'try catch' is finished.");
        }
        System.out.println("AFTER: statement after catch\n");


        //EXAMPLE 3: programmer throw from function
        //           add try/catch
      	System.out.println("\n\nEXAMPLE 3: Call Method that throws exception");
        try{
            checkAge(18);
            checkAge(15);
        }
        catch (Exception e){
            System.out.println("oops - something went wrong: \n" + e);
        }

        input.close();

        //EXAMPLE 4: File & Programmer Defined Exceptions
      	System.out.println("\n\nEXAMPLE 4: Exceptions for File Handling & verifying input");
        ArrayList<Person> people = new ArrayList<>();

        //writing this outside a try block will produce a compiler error
        //Scanner scan2 = new Scanner(new File("peo.txt"));
 
        try{
            //writing this way int the try block 'handles' the FileNotFoundException
            File inFile = new File("people.txt");
            Scanner scanner = new Scanner(inFile);
            //Scanner scanner = new Scanner(new File("people.txt"));

            String inputLine;
            while (scanner.hasNextLine()){
                inputLine = scanner.nextLine();
                String[] tokens = inputLine.split(",");
                try{
                    int tempAge = Integer.parseInt(tokens[3]);
                    char tempType = tokens[0].toUpperCase().charAt(0);
                    if (tempType != 'P' && tempType != 'T' && tempType != 'E' && tempType != 'S')
                        throw new TypeException(tempType);
                    //YOUR TURN:  add test & throw exception for age (age > 0 and < 110)


                    Person tempP = new Person(tokens[0].charAt(0), tokens[1], tokens[2], tempAge);
                    people.add(tempP);
                }
                //add catch for TypeException
                catch (TypeException e){
                    System.out.println("EXAMPLE 4: Type exception: " + e.getMessage());
                }
                catch(NumberFormatException e){
                    System.out.println("EXAMPLE 4: Number format exception: " + e.getMessage());
                }
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("EXAMPLE 4: Not enough tokens in input file: " + e.getMessage());
                }
                catch(Exception e){
                    System.out.println("EXAMPLE 4: Oops...: " + e.getMessage());
                }
            }
            System.out.println("closing scanner");
            scanner.close();
        }
        catch(IOException e){
            System.out.println("Error opening the file: " + e.getMessage());
        }


        System.out.println("printing people");
        for (Person p : people){
            System.out.printf("%-4c %-15s %-15s %5d \n",p.type, p.fname, p.lname, p.age);
        }

         //EXAMPLE 5: Nested method calls
      	System.out.println("\n\nEXAMPLE 5: Nested method calls (method throws exception)");
        try{
            method1("0");
            method1("-1");
            method1("abc");
        }
        catch(Exception e){
            System.out.println("EXAMPLE 5: error discovered in main: " + e);
        }

    }

    static void checkAge(int age) {
        if (age < 18) 
            throw new ArithmeticException("Access denied - You must be at least 18 years old.");
        else 
            System.out.println("Access granted - You are old enough!");
    }

    static void method1(String str) {
        try{
            int value = Integer.parseInt(str);
            if (value < 0) 
                throw new Exception("\tEXAMPLE 5: Method 1 THROW: Value entered is less than 0.");
            method2(value);
            System.out.println("\tEXAMPLE 5: Method 1: after method 2 call. " );
        }
        catch (Exception e){
            System.out.println("\tEXAMPLE 5: Method 1 CATCH: issue found: " + e);
            //e.printStackTrace();
        }

    }
    static void method2(int value) {
        try{
            if (value == 0) 
                throw new ArithmeticException("\tEXAMPLE 5: Method 2 THROW: Value entered is 0");
            System.out.println("5 / " + value + " = " + (5/value));
        }
        catch(ArithmeticException e){
            System.out.println("\tEXAMPLE 5: Method 2 CATCH: issue found: " + e);            
        }
    }
}

