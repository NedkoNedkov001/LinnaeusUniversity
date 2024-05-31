package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat power.
 */
public class BoatHasNoPowerException extends Exception {
  public BoatHasNoPowerException(String boatType) {
    super(String.format(FailMessages.BOAT_HAS_NO_POWER, boatType));
  }
}