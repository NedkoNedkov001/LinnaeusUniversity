package controller;

import view.EnglishUi;

/**
 * Responsible for staring the application.
 */
public class App {
  /**
   * Application starting point.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    System s = new System(new EnglishUi());
    s.run();
  }
}