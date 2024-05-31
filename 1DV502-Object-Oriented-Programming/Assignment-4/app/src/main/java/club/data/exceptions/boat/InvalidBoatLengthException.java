package club.data.exceptions.boat;

import club.data.constants.FailMessages;

/**
 * Exception for invalid boat length.
 */
public class InvalidBoatLengthException extends Exception {
  public InvalidBoatLengthException() {
    super(String.format(FailMessages.BOAT_INVALID_LENGTH));
  }

}
