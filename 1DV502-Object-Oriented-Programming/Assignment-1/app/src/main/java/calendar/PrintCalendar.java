package calendar;

import java.util.Scanner;

public class PrintCalendar {

  private static int getYear(Scanner s) {
    System.out.print("Enter a year after 1800: ");
    int year = s.nextInt();
    while (!validateYear(year)) {
      System.out.print("Enter a year after 1800: ");
      year = s.nextInt();
    }
    return year;
  }

  private static boolean validateYear(int year) {
    return (year >= 1800);
  }

  private static int getMonth(Scanner s) {
    System.out.print("Enter a month (1-12): ");
    int month = s.nextInt();
    while (!validateMonth(month)) {
      System.out.print("Enter a month (1-12): ");
      month = s.nextInt();
    }
    return month;
  }

  private static boolean validateMonth(int month) {
    return (month >= 1 && month <= 12);
  }

  private static int getFirstDay(int year, int month) {
    if (month == 1) {
      year--;
      month = 13;
    } else if (month == 2) {
      year--;
      month = 14;
    }
    int yc = year % 100;
    year /= 100;
    int dm = 1;

    int dayOfWeek = ((dm + ((26 * (month + 1)) / 10) + yc + (yc / 4) + (year / 4)
        + (5 * year)) - 2) % 7;

    return dayOfWeek;
  }

  private static int getNumberOfDays(int year, int month) {
    int numberOfDays;

    if (month == 1
        || month == 3
        || month == 5
        || month == 7
        || month == 8
        || month == 10
        || month == 12) {
      numberOfDays = 31;
    } else if (month == 4
        || month == 6
        || month == 9
        || month == 11) {
      numberOfDays = 30;
    } else {
      if (year % 4 == 0) {
        numberOfDays = 29;
      } else {
        numberOfDays = 28;
      }
    }
    return numberOfDays;
  }

  private static String printCalendar(int year, int month) {
    StringBuilder sb = new StringBuilder();
    sb.append("-----------------------------\n Mon Tue Wed Thu Fri Sat Sun \n");

    int firstDayOfMonth = getFirstDay(year, month);
    int printedDays = 0;
    sb.append("   ");
    for (int i = 0; i < firstDayOfMonth; i++) {
      sb.append("    ");
    }
    for (int i = 1; i <= 7 - firstDayOfMonth; i++) {
      sb.append(i + "   ");
      printedDays++;
    }
    sb.append('\n');
    int numberOfDays = getNumberOfDays(year, month);
    while (printedDays < numberOfDays) {
      for (int i = 0; i < 7; i++) {
        if (printedDays < 9) {
          sb.append("   " + ++printedDays);
        } else {
          sb.append("  " + ++printedDays);
        }
        if (printedDays == numberOfDays) {
          break;
        }
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Main method.
   * 
   * @param args input if available to method
   */
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int year = getYear(s);
    int month = getMonth(s);
    s.close();

    String[] monthNames = { "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December" };

    System.out.println(monthNames[month - 1] + " " + year);

    System.out.println(printCalendar(year, month));

  }
}