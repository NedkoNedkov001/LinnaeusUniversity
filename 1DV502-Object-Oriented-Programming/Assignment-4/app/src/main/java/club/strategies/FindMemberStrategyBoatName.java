package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import java.util.ArrayList;

/**
 * Find member with boat name.
 */
public class FindMemberStrategyBoatName implements FindMemberStrategy {

  @Override
  public Member find(BoatClub club, String boatName) {
    ArrayList<Member> members = club.getMembers();
    for (Member member : members) {
      if (member.getBoat(boatName) != null) {
        return member;
      }
    }
    return null;
  }
}
