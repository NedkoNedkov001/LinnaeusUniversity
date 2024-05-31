package game;

import java.util.Random;
import java.util.Scanner;

public class GameSRP {
  /**
   * Main.
   * @param args input if available to method
   */
  public static void main(String[] args) {

    int playerWins = 0;
    int computerWins = 0;
    int draws = 0;

    System.out.print("Scissor (1), rock (2), paper (3) or 0 to quit: ");
    Scanner s = new Scanner(System.in);
    int playerChoice = s.nextInt();
    Random r = new Random();
    while (playerChoice != 0) {
      int computerChoice = r.nextInt((3 - 1) + 1) + 1;
      switch (playerChoice) {
        case 1:
          switch (computerChoice) {
            case 1:
              draws++;
              System.out.println("It's a draw!");
              break;
            case 2:
              computerWins++;
              System.out.println("You lost, computer had rock!");
              break;
            default:
              playerWins++;
              System.out.println("You won, computer had paper!");
              break;
          }
          break;
        case 2:
          switch (computerChoice) {
            case 1:
              playerWins++;
              System.out.println("You won, computer had scissor!");
              break;
            case 2:
              draws++;
              System.out.println("It's a draw!");
              break;
            default:
              computerWins++;
              System.out.println("You lost, computer had paper!");
              break;
          }
          break;
        case 3:
          switch (computerChoice) {
            case 1:
              computerWins++;
              System.out.println("You lost, computer had scissor!");
              break;
            case 2:
              playerWins++;
              System.out.println("You won, computer had rock!");
              break;
            default:
              draws++;
              System.out.println("It's a draw!");
              break;
          }
          break;
        default:
          break;
      }
      System.out.print("Scissor (1), rock (2), paper (3) or 0 to quit: ");
      playerChoice = s.nextInt();
    }
    s.close();

    System.out.println("Score: " + playerWins + " (you) " + computerWins
                       + " (computer) " + draws + " (draw).");
  }

}
