package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import java.util.ArrayList;

/**
 * Find member with boat name.
 */
public class FindMemberStrategyName implements FindMemberStrategy {

  @Override
  public Member find(BoatClub club, String name) {
    ArrayList<Member> members = club.getMembers();
    for (Member member : members) {
      if (member.getName().equals(name)) {
        return member;
      }
    }
    return null;
  }
}
