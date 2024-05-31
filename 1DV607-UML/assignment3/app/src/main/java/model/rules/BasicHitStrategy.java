package model.rules;

import model.Player;
import model.variables.Variables;

/**
 * BasicHitStrategy.
 */
class BasicHitStrategy implements HitStrategy {
  private static final int hitLimit = Variables.HIT_LIMIT;

  public boolean doHit(Player dealer) {
    return dealer.calcScore() < hitLimit;
  }
}