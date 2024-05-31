package club.data.exceptions.member;

import club.data.constants.FailMessages;

/**
 * Exception for invalid member.
 */
public class InexistentMemberException extends Exception {
  public InexistentMemberException() {
    super(String.format(FailMessages.MEMBER_INEXISTENT));
  }
}