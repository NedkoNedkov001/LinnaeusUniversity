package model.rules;

import model.Player;

/**
 * WinStrategy.
 */
public interface WinStrategy {
  boolean dealerWins(Player dealer, Player player);
}
