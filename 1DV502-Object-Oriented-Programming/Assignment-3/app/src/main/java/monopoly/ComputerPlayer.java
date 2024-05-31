package monopoly;

/**
 * Represents an automated computer player.
 */
public class ComputerPlayer extends Player {

  public ComputerPlayer(Tile startTile, String name, ConsoleUi ui) {
    super(startTile, name, ui);

  }

  @Override
  public boolean yourTurn(Dice d1, Dice d2) {
    if (getFunds() >= 200) {
      buyCurrentTile();
    }
    d1.roll();
    d2.roll();
    final int steps1 = d1.getValue();
    final int steps2 = d2.getValue();
    ui.playerMoves(getName(), steps1, steps2);
    move(steps1 + steps2);

    return true;
  }

}
