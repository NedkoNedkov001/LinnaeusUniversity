package model.rules;

import model.Player;

/**
 * player win strategy.
 */
public class PlayerWinStrategy implements WinStrategy {
  /**
   * dealer wins.
   */
  public boolean dealerWins(Player dealer, Player player) {
    if (dealer.calcScore() > player.calcScore()) {
      return true;
    }
    return false;
  }
}
