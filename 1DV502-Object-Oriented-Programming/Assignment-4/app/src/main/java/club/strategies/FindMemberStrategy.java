package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;

/**
 * Find members with condition.
 */
public interface FindMemberStrategy {
  public Member find(BoatClub club, String condition);
}
