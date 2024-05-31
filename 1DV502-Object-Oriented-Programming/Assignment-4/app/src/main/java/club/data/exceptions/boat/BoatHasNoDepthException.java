package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat power.
 */
public class BoatHasNoDepthException extends Exception {
  public BoatHasNoDepthException(String boatType) {
    super(String.format(FailMessages.BOAT_HAS_NO_DEPTH, boatType));
  }
}