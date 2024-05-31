package model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.UUID;
import model.constants.Variables;
import model.enums.Category;

/**
 * Register class.
 *
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Model setters are package protected.")
public class Register {
  private ArrayList<Member> members;
  private Time time;

  /**
   * Register class.
   * 
   */
  public Register() {
    this.members = new ArrayList<Member>();
    this.time = new Time();

    populateRegister();
  }

  // ----------------- Getters
  /**
   * Gets a copy of list of members.
   *
   * @return members
   */
  public ArrayList<Member> getMembers() {
    ArrayList<Member> members = new ArrayList<>();
    for (Member member : this.members) {
      members.add(member);
    }
    return members;
  }

  public Time getTime() {
    return time;
  }

  /**
   * Returns a member with given id.
   *
   * @param id id of member to return
   * @return member
   */
  public Member getMemberById(String id) {
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return member;
      }
    }
    return null;
  }

  /**
   * Returns a member with given name.
   *
   * @param name name of member to return
   * @return member
   */
  private Member getMemberByName(String name) {
    for (Member member : members) {
      if (member.getName().equals(name)) {
        return member;
      }
    }
    return null;
  }

  private Member getMemberByEmail(String email) {
    for (Member member : members) {
      if (member.getEmail().equals(email)) {
        return member;
      }
    }
    return null;
  }

  private Member getMemberByPhoneNum(String memberPhoneNum) {
    for (Member member : members) {
      if (member.getPhoneNum().equals(memberPhoneNum)) {
        return member;
      }
    }
    return null;
  }

  /**
   * Returns an item with a given name and owner.
   *
   * @param owner    owner member
   * @param itemName item name
   * @return item
   */
  public Item getItem(Member owner, String itemName) {
    for (Item i : owner.getItems()) {
      if (i.getName().equals(itemName)) {
        return i;
      }
    }
    return null;
  }

  /**
   * Get a list of all items for all members.
   *
   * @return a list of all items for all members
   */
  public ArrayList<Item> getAllItems() {
    ArrayList<Item> items = new ArrayList<>();
    for (Member member : members) {
      for (Item item : member.getItems()) {
        items.add(item);
      }
    }
    return items;
  }

  /**
   * Gets items for a given page.
   *
   * @param page current page
   * @return a list of items for a page
   */
  public ArrayList<Item> getPageItems(Integer page) {
    ArrayList<Item> allItems = getAllItems();
    ArrayList<Item> pageItems = new ArrayList<>();
    if (allItems.size() > 0) {
      for (int i = 0; i < Variables.ITEMS_PER_PAGE; i++) {
        pageItems.add(allItems.get(i + (page * Variables.MEMBERS_PER_PAGE)));
        if (i + (page * Variables.ITEMS_PER_PAGE) == allItems.size() - 1) {
          break;
        }
      }
    }
    return pageItems;
  }

  /**
   * Gets members for a given page.
   *
   * @param page current page
   * @return a list of members for a page
   */
  public ArrayList<Member> getPageMembers(Integer page) {
    ArrayList<Member> members = new ArrayList<Member>();
    if (this.members.size() > 0) {
      for (int i = 0; i < Variables.MEMBERS_PER_PAGE; i++) {
        members.add(this.members.get(i + (page * Variables.MEMBERS_PER_PAGE)));
        if (i + (page * Variables.MEMBERS_PER_PAGE) == this.members.size() - 1) {
          break;
        }
      }
    }
    return members;
  }

  // ----------------- Operations
  /**
   * Add a new member.
   *
   * @param memberName     name
   * @param memberEmail    email
   * @param memberPhoneNum phone number
   */
  public Boolean addMember(String memberName, String memberEmail, String memberPhoneNum) {
    if (!isValidName(memberName)) {
      return false;
    }
    if (!isValidEmail(memberEmail)) {
      return false;
    }
    if (!isValidPhoneNum(memberPhoneNum)) {
      return false;
    }

    Member member = new Member(generateMemberId(), memberName, memberEmail,
        memberPhoneNum, time.getDay(), 0);
    members.add(member);
    return true;
  }

  /**
   * Add a new member.
   *
   * @param memberId       id
   * @param memberName     name
   * @param memberEmail    email
   * @param memberPhoneNum phone number
   * @param dayAdded       day added
   * @param credits        credits
   */
  public Boolean addMember(String memberId, String memberName,
      String memberEmail, String memberPhoneNum, Integer dayAdded, Integer credits) {
    if (!isValidMemberId(memberId)) {
      return false;
    }
    if (!isValidName(memberName)) {
      return false;
    }
    if (!isValidEmail(memberEmail)) {
      return false;
    }
    if (!isValidPhoneNum(memberPhoneNum)) {
      return false;
    }
    if (!isValidDay(dayAdded)) {
      return false;
    }
    if (!isValidCredits(credits)) {
      return false;
    }
    Member member = new Member(memberId, memberName,
        memberEmail, memberPhoneNum, dayAdded, credits);
    members.add(member);
    return true;
  }

  /**
   * Removes member.
   *
   * @param memberToDelete member
   * @return boolean
   */
  public Boolean removeMember(Member memberToDelete) {
    if (members.remove(memberToDelete)) {
      return true;
    }
    return false;
  }

  /**
   * Edit a member.
   *
   * @param memberToEdit member to edit
   * @param newName      new name
   * @param newEmail     new email
   * @param newPhoneNum  new phone number
   */
  public Boolean editMember(Member memberToEdit, String newName,
      String newEmail, String newPhoneNum) {
    if (!isValidName(newName)) {
      return false;
    }
    if (!isValidEmail(newEmail)) {
      return false;
    }
    if (!isValidPhoneNum(newPhoneNum)) {
      return false;
    }
    if (memberToEdit == null) {
      return false;
    }
    memberToEdit.edit(newName, newEmail, newPhoneNum);
    return true;
  }

  /**
   * Adding a new item to a member.
   */
  public Boolean addItemToMember(Member owner, String itemName, String itemDesc,
      Integer itemCost, Category itemCategory) {
    if (owner == null) {
      return false;
    }
    if (!isValidItemName(owner, itemName)) {
      return false;
    }
    if (!isValidDescription(itemDesc)) {
      return false;
    }
    if (!isValidCost(itemCost)) {
      return false;
    }
    if (itemCategory == null) {
      return false;
    }

    owner.addItem(new Item(itemName, itemDesc, itemCost, itemCategory, owner, time.getDay()));
    return true;
  }

  /**
   * Remove an item.
   *
   * @param itemToRemove item to remove
   */
  public Boolean removeItem(Item itemToRemove) {
    if (itemToRemove != null) {
      return itemToRemove.getOwner().removeItem(itemToRemove);
    }
    return false;
  }

  /**
   * Edit an item name.
   *
   * @param itemToEdit item to edit
   * @param newName    new name
   */
  public Boolean editItemName(Item itemToEdit, String newName) {
    if (itemToEdit == null) {
      return false;
    }
    if (!isValidItemName(itemToEdit.getOwner(), newName)) {
      return false;
    }
    itemToEdit.renameItem(newName);
    return true;
  }

  /**
   * Edit an item description.
   *
   * @param itemToEdit item to edit
   * @param newDesc    new description
   */
  public Boolean editItemDesc(Item itemToEdit, String newDesc) {
    if (!isValidDescription(newDesc)) {
      return false;
    }
    if (itemToEdit == null) {
      return false;
    }
    itemToEdit.editItemDesc(newDesc);
    return true;
  }

  /**
   * Edit an item cost.
   *
   * @param itemToEdit item to edit
   * @param newCost    new cost
   */
  public Boolean editItemCost(Item itemToEdit, Integer newCost) {
    if (!isValidCost(newCost)) {
      return false;
    }

    if (itemToEdit == null) {
      return false;
    }
    itemToEdit.editDailyCost(newCost);
    return true;
  }

  /**
   * Edit an item category.
   *
   * @param itemToEdit  item to edit
   * @param newCategory new category
   */
  public Boolean editItemCategory(Item itemToEdit, Category newCategory) {
    if (newCategory == null) {
      return false;
    }

    if (itemToEdit == null) {
      return false;
    }
    itemToEdit.editCategory(newCategory);
    return true;
  }

  /**
   * Create a contract.
   *
   * @param itemToLend   item
   * @param memberToLend member
   * @param startDay     start day
   * @param days         days
   */
  public Boolean addContract(Item itemToLend, Member memberToLend,
      Integer startDay, Integer days) {

    if (itemToLend == null) {
      return false;
    }
    if (memberToLend == null) {
      return false;
    }
    if (!isValidStartDay(startDay)) {
      return false;
    }

    Integer endDay = startDay + days;
    if (isValidDuration(endDay - startDay)) {
      return false;
    }
    if (isAvailableItemAtTime(itemToLend, startDay, endDay)) {
      return false;
    }
    if (isEnoughCredits(memberToLend, itemToLend, days)) {
      return false;
    }
    return time.addSubscriber(new Contract(itemToLend, memberToLend,
        startDay, endDay, time.getDay()));
  }

  public void dayUp() {
    time.dayUp();
  }

  // ----------------- Validations

  private Boolean isValidMemberId(String id) {
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validates name.
   *
   * @param name name
   * @return boolean
   */
  public Boolean isValidName(String name) {
    if (name == null) {
      return false;
    }

    if (name.equals("")) {
      return true;
    }

    if (name.length() < Variables.MEMBER_MIN_NAME_LEN
        || name.length() > Variables.MEMBER_MAX_NAME_LEN) {
      return false;
    }

    return (getMemberByName(name) == null);
  }

  /**
   * Validates email.
   *
   * @param email email
   * @return boolean
   */
  public Boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }

    if (email.equals("")) {
      return true;
    }

    String stricterFilterString = "[0-9a-z]+@[a-z0-9]+\\.[a-z]{2,4}";
    java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
    java.util.regex.Matcher m = p.matcher(email);
    return (m.matches() == true && getMemberByEmail(email) == null);
  }

  private Boolean isValidCredits(Integer credits) {
    return credits >= 0;
  }

  private Boolean isValidDay(Integer dayAdded) {
    return dayAdded >= 0;
  }

  /**
   * Validates phone number.
   *
   * @param memberPhoneNum phone number
   * @return boolean
   */
  public Boolean isValidPhoneNum(String memberPhoneNum) {
    if (memberPhoneNum == null) {
      return false;
    }
    if (memberPhoneNum.equals("")) {
      return true;
    }
    return (memberPhoneNum.length() >= Variables.MEMBER_MIN_PHONE_NUM_LEN
        && memberPhoneNum.length() <= Variables.MEMBER_MAX_PHONE_NUM_LEN
        && getMemberByPhoneNum(memberPhoneNum) == null);
  }

  /**
   * Validates item name.
   *
   * @param name name
   * @return boolean
   */
  public Boolean isValidItemName(Member owner, String name) {
    if (name == null) {
      return false;
    }

    if (name.equals("")) {
      return true;
    }

    if (name.length() < Variables.ITEM_MIN_NAME_LEN
        || name.length() > Variables.ITEM_MAX_NAME_LEN) {
      return false;
    }

    return !memberHasItem(owner, name);
  }

  /**
   * Checks if a member has an item with name.
   *
   * @param member   member to check
   * @param itemName item name to compare
   * @return true if member has an item with such name
   */
  private Boolean memberHasItem(Member member, String itemName) {
    for (Item item : member.getItems()) {
      if (item.getName().equals(itemName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Validates description.
   *
   * @param desc desc
   * @return boolean
   */
  public Boolean isValidDescription(String desc) {
    if (desc == null) {
      return false;
    }
    if (desc.equals("")) {
      return false;
    }

    return desc.length() >= Variables.ITEM_DESC_MIN_LEN
        && desc.length() <= Variables.ITEM_DESC_MAX_LEN;
  }

  /**
   * Validates cost.
   *
   * @param cost cost
   * @return boolean
   */
  public Boolean isValidCost(Integer cost) {
    if (cost == null) {
      return false;
    }
    return (cost >= Variables.ITEM_MIN_COST && cost <= Variables.ITEM_MAX_COST);
  }

  /**
   * Validate contract start day.
   *
   * @param startDay start day
   * @return true if start day is valid, otherwise false
   */
  public Boolean isValidStartDay(Integer startDay) {
    if (startDay == null) {
      return false;
    }
    return startDay >= time.getDay();
  }

  /**
   * Validate contract duration.
   *
   * @param days days
   * @return true if duration is valid, otherwise false
   */
  public Boolean isValidDuration(Integer days) {
    if (days == null) {
      return false;
    }
    return days >= Variables.CONTRACT_MIN_DURATION;
  }

  /**
   * Checks if a given day is already in use by the contract.
   *
   * @param item     item
   * @param startDay start day
   * @return Boolean
   */
  public Boolean isAvailableItemAtTime(Item item, Integer startDay, Integer days) {
    Integer endDay = startDay + days;
    for (Contract contract : item.getContracts()) {
      if ((startDay >= contract.getStartDay() && startDay < contract.getEndDay())
          || (endDay > contract.getStartDay() && endDay <= contract.getEndDay())
          || startDay < contract.getStartDay() && endDay > contract.getEndDay()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if member has enough credits to start a contract.
   *
   * @param member member
   * @param item   item
   * @param days   days
   * @return Boolean
   */
  public Boolean isEnoughCredits(Member member, Item item, Integer days) {
    int totalCost = 0;
    if (item.getOwner().equals(member)) {
      totalCost = item.getDailyCost() * days;
    }
    return member.getCredits() < totalCost;
  }

  // ----------------- Supporting methods
  private String generateMemberId() {
    String memberId = UUID.randomUUID().toString().substring(0, 5);
    while (!isValidMemberId(memberId)) {
      memberId = UUID.randomUUID().toString().substring(0, 5);
    }
    return memberId;
  }

  private void populateRegister() {
    addMember("000001", "MemberM1", "m1@gmail.com", "+46 123456781", 0, 500);
    addMember("000002", "MemberM2", "m2@gmail.com", "+46 123456782", 0, 100);
    addMember("000003", "MemberM3", "m3@gmail.com", "+46 123456783", 0, 100);

    addItemToMember(getMemberByName("MemberM1"), "ItemI1", "I1 description", 50, Category.Tool);
    addItemToMember(getMemberByName("MemberM1"), "ItemI2", "I2 description", 10, Category.Other);

    addContract(getItem(getMemberByName("MemberM1"), "ItemI2"), getMemberByName("MemberM3"), 5, 7);
  }

}