package club.data.exceptions.member;

import club.data.constants.FailMessages;

/**
 * Exception for invalid member id.
 */
public class InvalidMemberIdException extends Exception {
  public InvalidMemberIdException() {
    super(String.format(FailMessages.MEMBER_INVALID_ID));
  }
}