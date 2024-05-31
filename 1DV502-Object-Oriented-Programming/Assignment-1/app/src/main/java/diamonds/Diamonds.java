package diamonds;

import java.util.Scanner;

public class Diamonds {

  private static String printDiamond(int height) {
    String string = "";

    int currWhitespaces = height;
    int currStars = -1;
    for (int i = 0; i < height; i++) {
      currWhitespaces--;
      currStars += 2;
      for (int j = 0; j < currWhitespaces; j++) {
        string += ' ';
      }
      for (int k = 0; k < currStars; k++) {
        string += '*';
      }
      string += '\n';

    }
    for (int i = height - 1; i > 0; i--) {
      currWhitespaces++;
      currStars -= 2;
      for (int j = 0; j < currWhitespaces; j++) {
        string += ' ';
      }
      for (int k = 0; k < currStars; k++) {
        string += '*';
      }
      string += '\n';

    }

    return string;
  }

  /**
   * Main method.
   * @param args input if available to method
   */
  public static void main(String[] args) {

    System.out.print("Give a positive number: ");
    Scanner s = new Scanner(System.in);
    int height = s.nextInt();
    while (height > 0) {
      System.out.println(printDiamond(height));
      System.out.print("Give a positive number: ");
      height = s.nextInt();
    }
    s.close();
  }
}
