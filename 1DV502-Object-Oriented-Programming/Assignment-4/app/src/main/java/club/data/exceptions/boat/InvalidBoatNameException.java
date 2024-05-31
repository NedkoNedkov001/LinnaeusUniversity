package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat name.
 */
public class InvalidBoatNameException extends Exception {
  public InvalidBoatNameException() {
    super(String.format(FailMessages.BOAT_INVALID_NAME));
  }
}
