package monopoly;

/**
 * Represents a player and offers human interaction via ConsoleUi.
 */
public class HumanPlayer extends Player {
  /**
   * Creates a new Player.
   *
   * @param startTile The tile the player should start on.
   * @param name      The name of the player.
   * @param ui        The user interface that the player uses to send messages.
   */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = "EI_EXPOSE_REP2",
      justification = "The tile objects are shared between all player and the board.")
  public HumanPlayer(Tile startTile, String name, ConsoleUi ui) {
    super(startTile, name, ui);
  }
}
