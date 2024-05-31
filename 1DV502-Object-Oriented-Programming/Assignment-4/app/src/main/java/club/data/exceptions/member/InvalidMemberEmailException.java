package club.data.exceptions.member;

import club.data.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidMemberEmailException extends Exception {
  public InvalidMemberEmailException() {
    super(String.format(FailMessages.MEMBER_INVALID_EMAIL));
  }
}