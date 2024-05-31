package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Contract class.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Model setters are package protected.")
public class Contract implements TimeChangedObserver {
  private final Integer establishDay;
  private final Integer startDay;
  private final Integer endDay;
  private final Item item;
  private final Member lender;
  private final Integer cost;
  private Boolean isExpired;

  /**
   * Constructor for contract.
   *
   * @param item     the lended item
   * @param lender   the lending member
   * @param startDay the start day
   * @param endDay   the end day
   */
  public Contract(Item item, Member lender, Integer startDay,
      Integer endDay, Integer establishDay) {
    this.establishDay = establishDay;
    this.startDay = startDay;
    if (establishDay.equals(startDay)) {
      item.changeUser(lender);
    }
    this.endDay = endDay;
    this.item = item;
    this.lender = lender;
    this.cost = (endDay - startDay + 1) * item.getDailyCost();
    setIsExpired(false);
    item.getOwner().addCredits(cost);
    lender.removeCredits(cost);
    item.addContract(this);
  }

  public Integer getEstablishDay() {
    return establishDay;
  }

  public Boolean getIsExpired() {
    return isExpired;
  }

  private void setIsExpired(Boolean isExpired) {
    this.isExpired = isExpired;
  }

  public Integer getStartDay() {
    return startDay;
  }

  public Integer getEndDay() {
    return endDay;
  }

  public Item getItem() {
    return item;
  }

  public Member getLender() {
    return lender;
  }

  public Integer getCost() {
    return cost;
  }

  /**
   * Checks for member credit eligibality.
   *
   * @param lender is the object to be checked having the credits.
   * @param item   is the object to be checked having the cost to be substracted.
   * @param days   is the multiplier to the cost.
   * @return boolean values.
   */
  public boolean isEnoughCredits(Member lender, Item item, Integer days) {
    if (lender.getCredits() > (item.getDailyCost() * days)
        || lender.getId().equals(item.getOwner().getId())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if a contract is expired.
   *
   * @param currDay the current day of the system
   * @return whether a contract has expired
   */
  public boolean isExpiredContract(Integer currDay) {
    if (endDay <= currDay) {
      item.changeUser(null);
      setIsExpired(true);

      return true;
    }
    return false;
  }

  @Override
  public Boolean timeAdvanced(Integer newDay) {
    if (newDay.equals(startDay)) {
      item.changeUser(lender);
    } else if (newDay.equals(endDay + 1)) {
      setIsExpired(true);
      item.changeUser(null);
    }
    return newDay == endDay + 1;
  }
}