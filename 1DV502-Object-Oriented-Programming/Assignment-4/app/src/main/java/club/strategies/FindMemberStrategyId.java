package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import java.util.ArrayList;

/**
 * Find member with id.
 */
public class FindMemberStrategyId implements FindMemberStrategy {
  @Override
  public Member find(BoatClub club, String id) {
    ArrayList<Member> members = club.getMembers();
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return member;
      }
    }
    return null;
  }
}
