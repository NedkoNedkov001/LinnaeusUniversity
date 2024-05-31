package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import java.util.ArrayList;

/**
 * Find member with email.
 */
public class FindMemberStrategyEmail implements FindMemberStrategy {
  @Override
  public Member find(BoatClub club, String email) {
    ArrayList<Member> members = club.getMembers();
    for (Member member : members) {
      if (member.getEmail() == null) {
        continue;
      } else if (member.getEmail().equals(email)) {
        return member;
      }
    }
    return null;
  }
}
