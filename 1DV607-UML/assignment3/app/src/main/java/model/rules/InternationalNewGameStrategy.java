package model.rules;

import model.Dealer;
import model.Deck;
import model.Player;

/**
 * InternationalNewGameStrategy.
 */
class InternationalNewGameStrategy implements NewGameStrategy {

  /**
  * NewGame.
  */
  public boolean newGame(Deck deck, Dealer dealer, Player player) {
    player.dealCard(deck.getCard(), true);
    dealer.dealCard(deck.getCard(), true);
    player.dealCard(deck.getCard(), true);
    return true;
  }
}