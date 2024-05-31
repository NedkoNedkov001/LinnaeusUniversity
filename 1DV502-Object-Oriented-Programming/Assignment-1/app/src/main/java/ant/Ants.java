package ant;

import java.util.Random;

public class Ants {
  /**
   * Main.
   * @param args input if available to method
   */


  public static void main(String[] args) {
    Random r = new Random();

    double totalSteps = 0;

    int[][] chessboard = new int[8][8];
    System.out.println("Ants\n=====\n");
    for (int i = 1; i <= 10; i++) {
      // Reset chessboard
      for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
          chessboard[row][col] = 0;
        }
      }

      int currSteps = 0;

      int currX = r.nextInt(8);
      int currY = r.nextInt(8);

      chessboard[currX][currY]++;
      int visitedSquares = 1;

      while (visitedSquares != 64) {
        int direction = r.nextInt(4);
        switch (direction) {
          case 0:
            if (currX < 7) {
              currX++;
              currSteps++;
            }
            break;
          case 1:
            if (currX > 0) {
              currX--;
              currSteps++;
            }
            break;
          case 2:
            if (currY < 7) {
              currY++;
              currSteps++;
            }
            break;
          case 3:
            if (currY > 0) {
              currY--;
              currSteps++;
            }
            break;
          default:
            break;
        }

        if (chessboard[currX][currY] == 0) {
          chessboard[currX][currY] = 1;
          visitedSquares++;
        }
      }
      System.out.println("Number of steps in simulation " + i + ": " + currSteps);
      totalSteps += currSteps;
    }
    System.out.println("Average amount of steps: " + totalSteps / 10);
  }
}