package club.strategies;

import club.data.models.members.BoatClub;
import club.data.models.members.Member;

/**
 * Set and execute strategy.
 */
public class Context {
  private FindMemberStrategy strategy;
  
  public void setStrategy(FindMemberStrategy newStrategy) {
    strategy = newStrategy;
  }

  public Member executeStrategy(BoatClub club, String condition) {
    return strategy.find(club, condition);
  }
}
