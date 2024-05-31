package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat name.
 */
public class InvalidBoatTypeException extends Exception {
  public InvalidBoatTypeException() {
    super(String.format(FailMessages.BOAT_INVALID_TYPE));
  }
}
