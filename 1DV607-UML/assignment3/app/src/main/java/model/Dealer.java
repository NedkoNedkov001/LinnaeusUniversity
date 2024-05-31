package model;

import model.rules.HitStrategy;
import model.rules.NewGameStrategy;
import model.rules.RulesFactory;
import model.rules.WinStrategy;

/**
 * Represents a dealer player that handles the deck of cards and runs the game
 * using rules.
 */
public class Dealer extends Player {

  private Deck deck;
  private NewGameStrategy newGameRule;
  private HitStrategy hitRule;
  private WinStrategy winRule;

  /**
   * Initializing constructor.
   */
  public Dealer(RulesFactory rulesFactory) {

    newGameRule = rulesFactory.getNewGameRule();
    hitRule = rulesFactory.getHitRule();
    winRule = rulesFactory.getWinRule();
  }

  /**
   * Starts a new game if the game is not currently under way.
   */
  public boolean newGame(Player player) {
    if (deck == null || isGameOver(player)) {
      deck = new Deck();
      clearHand();
      player.clearHand();
      return newGameRule.newGame(deck, this, player);
    }
    return false;
  }

  /**
   * Gives the player one more card if possible. I.e. the player hits.
   */
  public boolean hit(Player player) {

    if (deck != null && player.calcScore() < maxScore && !isGameOver(player)) {
      player.dealCard(deck.getCard(), true);
      return true;
    }

    return false;
  }

  /**
   * Checks if the dealer is the winner compared to a player.
   */
  public boolean isDealerWinner(Player player) {
    if (player.calcScore() > maxScore) {
      return true;
    } else if (calcScore() > maxScore) {
      return false;
    }
    // return calcScore() >= player.calcScore();
    return winRule.dealerWins(this, player);
  }

  /**
   * Checks if the game is over, i.e. the dealer can take no more cards.
   */
  public boolean isGameOver(Player player) {
    if (player.calcScore() > player.maxScore) {
      return true;
    }
    if (deck != null && hitRule.doHit(this) != true) {
      return true;
    }
    return false;
  }

  /**
   * The player has choosen to take no more cards, it is the dealers turn.
   */
  public boolean stand() {
    if (deck != null) {
      showHand();
      while (hitRule.doHit(this)) {
        dealCard(deck.getCard(), true);
      }
      return true;
    }
    return false;
  }
}