package nine;

import java.util.Random;
import java.util.Scanner;

public class Nine {
  /**
   * Main method.
   * 
   * @param args input if available to method
   */
  public static void main(String[] args) {
    System.out.println("Playing a game \n=================\n");

    char ready;
    
    Scanner s = new Scanner(System.in);
    do {
      System.out.print("Ready to play? (Y/N) ");
      ready = s.next().charAt(0);
    } while (ready != 'Y');

    int playerTotal = 0;
    Random r = new Random();
    int playerNumberOne = r.nextInt((6 - 1) + 1) + 1;
    playerTotal += playerNumberOne;
    System.out.println("You rolled " + playerNumberOne);

    System.out.print("Would you like to roll again? (Y/N) ");
    char reroll = s.next().charAt(0);
    s.close();
    if (reroll == 'Y') {
      int playerNumberTwo = r.nextInt((6 - 1) + 1) + 1;
      playerTotal += playerNumberTwo;
      System.out.println("You rolled " + playerNumberTwo + " and in total you have " + playerTotal);

      if (playerTotal > 9) {
        System.out.println("Computer won!");
        // s.close();
        return;
      }
    }

    int computerTotal = 0;
    int computerNumberOne = r.nextInt((6 - 1) + 1) + 1;
    computerTotal += computerNumberOne;
    System.out.println("The computer rolled " + computerNumberOne);

    if (computerTotal <= 4) {
      int computerNumberTwo = r.nextInt((6 - 1) + 1) + 1;
      computerTotal += computerNumberTwo;
      System.out.println("The computer rolls again and gets " + computerNumberTwo 
                          + " in total " + computerTotal);

      if (computerTotal > 9) {
        System.out.println("Player won!");
        // s.close();
        return;
      }
    }
    if (playerTotal > computerTotal) {
      System.out.println("You won!");
    } else if (playerTotal < computerTotal) {
      System.out.println("Computer won!");
    } else {
      System.out.println("Draw!");
    }
  }
}
