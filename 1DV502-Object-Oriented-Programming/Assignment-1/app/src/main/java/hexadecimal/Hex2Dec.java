package hexadecimal;

import java.util.Scanner;

public class Hex2Dec {

  private static String hexToDecimal(String hex) {
    int decimalValue = 0;

    for (int i = 0; i < hex.length(); i++) {
      int currCharValue = 0;
      switch (hex.toUpperCase().charAt(i)) {
        case '1':
          currCharValue = 1;
          break;
        case '2':
          currCharValue = 2;
          break;
        case '3':
          currCharValue = 3;
          break;
        case '4':
          currCharValue = 4;
          break;
        case '5':
          currCharValue = 5;
          break;
        case '6':
          currCharValue = 6;
          break;
        case '7':
          currCharValue = 7;
          break;
        case '8':
          currCharValue = 8;
          break;
        case '9':
          currCharValue = 9;
          break;
        case 'A':
          currCharValue = 10;
          break;
        case 'B':
          currCharValue = 11;
          break;
        case 'C':
          currCharValue = 12;
          break;
        case 'D':
          currCharValue = 13;
          break;
        case 'E':
          currCharValue = 14;
          break;
        case 'F':
          currCharValue = 15;
          break;
        default:
          break;
      }

      decimalValue += currCharValue * Math.pow(16, (hex.length() - i - 1));
    }
    String result = "The decimal value for " + hex + " is " + decimalValue + ".";
    return result;
  }

  /**
   * Main.
   * 
   * @param args input if available to method
   */
  public static void main(String[] args) {

    System.out.print("Enter a hex number: ");
    Scanner s = new Scanner(System.in);
    String hexValue = s.next();
    s.close();

    System.out.println(hexToDecimal(hexValue));
  }
}
