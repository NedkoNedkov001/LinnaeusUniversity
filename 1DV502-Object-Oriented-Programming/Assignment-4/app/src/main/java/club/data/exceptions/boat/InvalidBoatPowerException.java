package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat power.
 */
public class InvalidBoatPowerException extends Exception {
  public InvalidBoatPowerException() {
    super(String.format(FailMessages.BOAT_INVALID_POWER));
  }
}