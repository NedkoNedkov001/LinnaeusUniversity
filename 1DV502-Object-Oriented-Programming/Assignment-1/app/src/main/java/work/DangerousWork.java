package work;

import java.util.Scanner;

public class DangerousWork {
  /**
   * Main method.
   * @param args input if available to method
   */
  public static void main(String[] args) {

    System.out.print("How much would you like to earn? ");
    
    Scanner s = new Scanner(System.in);
    int desiredSalary = s.nextInt();
    s.close();

    double currentSalary = 0.01;
    int days = 0;
    while (currentSalary < desiredSalary) {
      days++;
      currentSalary *= 2;
    }

    System.out.println("You will have your money in " + days + " days.");

    if (days > 30) {
      System.out.println("It is not recommended to work more than 30 days!");
    }
  }
}
