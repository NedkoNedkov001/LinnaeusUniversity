package weekday;

import java.util.Scanner;

public class DayOfWeek {
  /**
   * Main.
   * @param args input if available to method
   */
  public static void main(String[] args) {

    System.out.print("Enter year: ");
    Scanner s = new Scanner(System.in);
    int y = s.nextInt();
    System.out.print("Enter month (1-12): ");
    int m = s.nextInt();
    if (m == 1) {
      y--;
      m = 13;
    } else if (m == 2) {
      y--;
      m = 14;
    }
    System.out.print("Enter day of the month (1-31): ");
    int dm = s.nextInt();
    s.close();

    int yc = y % 100;
    y /= 100;

    int dw = (dm + ((26 * (m + 1)) / 10) + yc + (yc / 4) + (y / 4) + (5 * y)) % 7;

    System.out.print("Day of the week is ");
    switch (dw) {
      case 0:
        System.out.print("Saturday");
        break;
      case 1:
        System.out.print("Sunday");
        break;
      case 2:
        System.out.print("Monday");
        break;
      case 3:
        System.out.print("Tuesday");
        break;
      case 4:
        System.out.print("Wednesday");
        break;
      case 5:
        System.out.print("Thursday");
        break;
      case 6:
        System.out.print("Friday");
        break;
      default:
        break;
    }

  }
}
