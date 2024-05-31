package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;

/**
 * Member class.
 * 
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Model setters are package protected.")
public class Member {
  private final String id;
  private final Integer dayAdded;
  private String name;
  private String email;
  private String phoneNum;
  private Integer credits;
  private ArrayList<Item> items;

  /**
   * Constructor for member.
   *
   * @param id       id
   * @param name     name of member
   * @param email    email of member
   * @param phoneNum phone number of member
   * @param credits  credits
   * @param dayAdded day added of member
   */
  public Member(String id, String name, String email,
      String phoneNum, Integer dayAdded, Integer credits) {
    this.id = id;
    this.dayAdded = dayAdded;
    setName(name);
    setEmail(email);;
    setPhoneNum(phoneNum);;
    setCredits(credits);
    this.items = new ArrayList<Item>();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  private void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  private void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public Integer getDayAdded() {
    return dayAdded;
  }

  public Integer getCredits() {
    return credits;
  }

  private void setCredits(Integer credits) {
    this.credits = credits;
  }

  /**
   * Get a copy of the list of items.
   *
   * @return list of items
   */
  public ArrayList<Item> getItems() {
    ArrayList<Item> items = new ArrayList<>();
    for (Item item : this.items) {
      items.add(item);
    }
    return items;
  }

  // -----------------------------------------------------------------------------

  /**
   * Edit information for member.
   *
   * @param name     new name (empty to keep)
   * @param email    new email (empty to keep)
   * @param phoneNum new email (empty to keep)
   */
  void edit(String name, String email, String phoneNum) {
    if (!name.equals("")) {
      setName(name);
    }
    if (!email.equals("")) {
      setEmail(email);
    }
    if (!phoneNum.equals("")) {
      setPhoneNum(phoneNum);
    }
  }

  void addItem(Item item) {
    items.add(item);
    addCredits(100);
  }

  Boolean removeItem(Item item) {
    return items.remove(item);
  }

  void addCredits(Integer amount) {
    setCredits(credits + amount);
  }

  void removeCredits(Integer amount) {
    setCredits(credits - amount);
  }
}