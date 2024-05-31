package controller;

import model.Game;
import model.PlayerObserver;
import model.variables.Variables;
import view.View;

/**
 * Scenario controller for playing the game.
 */
public class Player implements PlayerObserver {
  View view;
  Game game;

  /**
   * Initializing constructor.
   */
  Player(Game g, View v) {
    view = v;
    game = g;
    game.getDealer().addSubscriber(this);
    game.getPlayer().addSubscriber(this);
  }

  /**
   * Runs the play use case.
   */
  public boolean play() {
    view.displayWelcomeMessage();
    view.displayDealerHand(game.getDealerHand(), game.getDealerScore());
    view.displayPlayerHand(game.getPlayerHand(), game.getPlayerScore());

    if (game.isGameOver()) {
      view.displayGameOver(game.isDealerWinner());
    }

    int input = view.getInput();
    if (input == Variables.PLAY) {
      game.newGame();
    } else if (input == Variables.HIT) {
      game.hit();
    } else if (input == Variables.STAND) {
      game.stand();
    }

    return input != Variables.QUIT;
  }

  /**
   * pause for some time.
   */
  private void pause(Integer pauseTime) {
    try {
      Thread.sleep(pauseTime);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
  }

  /**
   * player has new card.
   */
  public void playerHasNewCard() {
    pause(Variables.PAUSE_TIME);
    view.displayWelcomeMessage();
    view.displayDealerHand(game.getDealerHand(), game.getDealerScore());
    view.displayPlayerHand(game.getPlayerHand(), game.getPlayerScore());
    pause(Variables.PAUSE_TIME);
  }
}