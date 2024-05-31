package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.variables.Variables;

/**
 * Represents a player in the Black Jack game. A Player has a hand of cards.
 */
public class Player {

  private List<Card.Mutable> hand;
  private ArrayList<PlayerObserver> subscribers;
  protected final int maxScore = Variables.MAX_SCORE;

  public Player() {
    hand = new LinkedList<Card.Mutable>();
    subscribers = new ArrayList<>();
  }

  /**
   * Adds a card to the Player's hand.
   *
   * @param addToHand The card to add to the hand.
   */
  public void dealCard(Card.Mutable addToHand, boolean visible) {
    addToHand.show(visible);
    hand.add(addToHand);
    notifySubscribers();
  }

  /**
   * Returns the cards in the hand.
   *
   * @return the cards in the Player's hand
   */
  public Iterable<Card> getHand() {
    return new LinkedList<Card>(hand);
  }

  /**
   * Removes all cards from the hand.
   */
  public void clearHand() {
    hand.clear();
  }

  /**
   * Shows all cards in the hand.
   */
  public void showHand() {
    for (Card.Mutable c : hand) {
      c.show(true);
      notifySubscribers();
    }
  }

  /**
   * Calculates the score of the hand according to Black Jack rules.
   *
   * @return The score.
   */
  public int calcScore() {
    // the number of scores is dependant on the number of scorable values
    // as it seems there is no way to do this check at compile time in java ?!
    // cardScores[13] = {...};
    int[] cardScores = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };
    assert (cardScores.length == Card.Value.Count.ordinal())
        : "Card Scores array size does not match number of card values";

    int score = 0;

    for (Card c : getHand()) {
      if (c.getValue() != Card.Value.Hidden) {
        score += cardScores[c.getValue().ordinal()];
      }
    }

    if (score > maxScore) {
      for (Card c : getHand()) {
        if (c.getValue() == Card.Value.Ace && score > maxScore) {
          score -= 10;
        }
      }
    }

    return score;
  }

  /**
   * notify subscribers.
   */
  private void notifySubscribers() {
    for (PlayerObserver s : subscribers) {
      s.playerHasNewCard();
    }
  }

  /**
   * add subscriber.
   */
  public void addSubscriber(PlayerObserver s) {
    subscribers.add(s);
  }

  /**
   * remove subscribers.
   */
  public void removeSubscriber(PlayerObserver s) {
    subscribers.remove(s);
  }
}
