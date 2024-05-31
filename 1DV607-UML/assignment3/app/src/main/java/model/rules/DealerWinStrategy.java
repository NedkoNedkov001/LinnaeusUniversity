package model.rules;

import model.Player;

/**
 * dealer win strategy.
 */
public class DealerWinStrategy implements WinStrategy {
  /**
   * dealer wins.
   */
  public boolean dealerWins(Player dealer, Player player) {
    if (dealer.calcScore() >= player.calcScore()) {
      return true;
    }
    return false;
  }
}
