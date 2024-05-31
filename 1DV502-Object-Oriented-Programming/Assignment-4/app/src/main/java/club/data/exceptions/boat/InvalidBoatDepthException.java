package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat power.
 */
public class InvalidBoatDepthException extends Exception {
  public InvalidBoatDepthException() {
    super(String.format(FailMessages.BOAT_INVALID_DEPTH));
  }
}