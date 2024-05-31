package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import model.enums.Category;

/**
 * Item class.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Model setters are package protected.")
public class Item {
  private String name;
  private String desc;
  private final Integer dayAdded;
  private Integer dailyCost;
  private Category category;
  private final Member owner;
  private Member currUser;
  private ArrayList<Contract> contracts;

  /**
   * Constructor for item.
   *
   * @param name     name of item
   * @param desc     description of item
   * @param cost     daily cost of item
   * @param category category of item
   * @param owner    owner of item
   * @param dayAdded day added of item
   */
  public Item(String name, String desc, Integer cost, Category category,
      Member owner, Integer dayAdded) {
    this.owner = owner;
    this.dayAdded = dayAdded;
    setName(name);
    setDesc(desc);
    setDailyCost(dailyCost);
    setCategory(category);
    setCurrUser(null);
    this.contracts = new ArrayList<Contract>();
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  void renameItem(String name) {
    setName(name);
  }

  public String getDesc() {
    return desc;
  }

  private void setDesc(String desc) {
    this.desc = desc;
  }

  void editItemDesc(String desc) {
    setDesc(desc);
  }

  public Integer getDayAdded() {
    return dayAdded;
  }

  public Integer getDailyCost() {
    return dailyCost;
  }

  private void setDailyCost(Integer dailyCost) {
    this.dailyCost = dailyCost;
  }

  void editDailyCost(Integer dailyCost) {
    setDailyCost(dailyCost);
  }

  public Category getCategory() {
    return category;
  }

  private void setCategory(Category category) {
    this.category = category;
  }

  void editCategory(Category category) {
    setCategory(category);
  }

  public Member getOwner() {
    return owner;
  }

  /**
   * Get current user.
   *
   * @return current user or null
   */
  public Member getCurrUser() {
    if (currUser == null) {
      return null;
    }
    return currUser;
  }

  private void setCurrUser(Member currUser) {
    this.currUser = currUser;
  }

  /**
   * Get a copy of the list of contracts.
   *
   * @return list of contracts
   */
  public ArrayList<Contract> getContracts() {
    ArrayList<Contract> contracts = new ArrayList<>();
    for (Contract contract : this.contracts) {
      contracts.add(contract);
    }
    return contracts;
  }

  void addContract(Contract contract) {
    this.contracts.add(contract);
  }

  void changeUser(Member member) {
    setCurrUser(member);
  }
}