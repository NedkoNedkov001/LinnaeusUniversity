package model.rules;

import model.Card;
import model.Player;
import model.variables.Variables;

/**
 * soft 17 strategy.
 */
public class Soft17HitStrategy implements HitStrategy {
  private static final int[] cardScores = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };

  /**
   * hit.
   */
  public boolean doHit(Player dealer) {
    if (dealer.calcScore() < Variables.HIT_LIMIT) {
      return true;
    }

    if (dealer.calcScore() == Variables.HIT_LIMIT) {
      int cardsWithoutAce = 0;
      for (Card card : dealer.getHand()) {
        if (card.getValue() != Card.Value.Ace) {
          cardsWithoutAce += cardScores[card.getValue().ordinal()];
        }
      }

      if (cardsWithoutAce == Variables.SOFT_LIMIT) {
        for (Card card : dealer.getHand()) {
          if (card.getValue() == Card.Value.Ace) {
            return true;
          }
        }
      }
    }

    return false;
  }
}
