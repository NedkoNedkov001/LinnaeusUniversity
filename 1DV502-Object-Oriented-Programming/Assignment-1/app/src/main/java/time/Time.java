package time;

import java.util.Scanner;

public class Time {
  /**
   * Main method.
   * 
   * @param args input if available to method
   */
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);

    System.out.print("Give a number of seconds: ");
    int totalSeconds = s.nextInt();

    int hours = totalSeconds / 3600;

    int minutes = (totalSeconds % 3600) / 60;

    int seconds = (totalSeconds % 3600) % 60;

    System.out
        .println("This corresponds to: " + hours + " hours, " + minutes
                 + " minutes and " + seconds + " seconds.");
    s.close();
  }

}
