package club.data.exceptions.member;

import club.data.constants.FailMessages;

/**
 * Exception for invalid member name.
 */
public class InvalidMemberNameException extends Exception {
  public InvalidMemberNameException() {
    super(String.format(FailMessages.BOAT_INVALID_NAME));
  }
}