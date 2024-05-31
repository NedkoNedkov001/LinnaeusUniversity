package view;

import java.util.ArrayList;
import java.util.List;
import model.Contract;
import model.Item;
import model.Member;
import model.Time;
import model.enums.Category;
import view.enums.ContractCreateOption;
import view.enums.ContractMenuOption;
import view.enums.ItemEditMenuOption;
import view.enums.ItemMenuOption;
import view.enums.MainMenuOption;
import view.enums.MemberMenuOption;
import view.enums.TimeMenuOption;

/**
 * ConsoleUi interface for app.
 */
public interface ConsoleUi {
  // ----- INPUTS ENUMS -----

  // ----- MENUS -----

  /**
   * Shows main menu on console.
   *
   * @return user choice
   */
  MainMenuOption getMainMenuOption();

  /**
   * Shows member menu on console.
   *
   * @return user choice
   */
  MemberMenuOption getMemberMenuOption();

  /**
   * Shows item menu on console.
   *
   * @return user choice
   */
  ItemMenuOption getItemMenuOption();

  /**
   * Shows edit item menu on console.
   *
   * @return user choice
   */
  ItemEditMenuOption getItemEditMenuOption();

  /**
   * Shows category choice menu on console.
   *
   * @return user choice
   */
  Category getItemCategory();

  /**
   * Shows contract menu on console.
   *
   * @return user choice
   */
  ContractMenuOption getContractMenuOption();

  /**
   * Shows item menu on console.
   *
   * @return user choice
   */
  TimeMenuOption getTimeMenuOption();

  /**
   * Prints member menu.
   *
   * @param page        current page
   * @param pageMembers current page members
   * @return user choice
   */
  String printMemberMenu(Integer page, List<Member> pageMembers);

  /**
   * Prints item menu.
   *
   * @param page      current page
   * @param pageItems current page items
   * @return user choice
   */
  String printItemMenu(Integer page, List<Item> pageItems);

  /**
   * Shows a list as a menu.
   *
   * @param page      current page
   * @param listNames list items
   * @return user choice
   */
  String printListMenu(Integer page, ArrayList<String> listNames);

  Member getMember(ArrayList<Member> allMembers);

  Item getItem(ArrayList<Item> allItems);

  ConsoleUi switchUi();

  // ----- INPUTS -----
  String createMemberName();

  String createMemberEmail();

  String createMemberPhoneNum();

  String createItemName();

  String createItemDescription();

  String createItemCost();

  void chooseMemberToLend();

  void chooseItemToLend();

  String getStartDay();

  /**
   * Get number of days.
   *
   * @return user input
   */
  String getDays();

  /**
   * Get user confirmation about a contract.
   *
   * @param member member to lend to
   * @param item   item to lend
   * @param days   total days
   * @return user choice
   */
  ContractCreateOption getConractCreateOption(Member member, Item item, Integer days);

  // ----- SUCCESS MESSAGES -----
  void printMemberCreated();

  void printCreateMemberCancel();

  void printMemberDeleted();

  void printMemberEdited();

  void printItemCreated();

  void printCreateItemCancel();

  void printItemDeleted();

  void printItemEdited();

  void printEditItemCancel();

  void printContractCreated();

  void printCreateContractCancel();

  void printTimeAdvanced();

  // ----- ERROR MESSAGES -----
  void printMemberNotCreated();

  /**
   * Prints a message when given member name is not unique or length not in range.
   */
  void printInvalidMemberName();

  void printInvalidEmail();

  void printInvalidPhoneNum();

  void printMemberNotEdited();

  void printItemNotCreated();

  void printItemNotEdited();

  /**
   * Prints a message when given item name is not unique or length not in range.
   *
   */
  void printInvalidItemName();

  /**
   * Prints a message when given item description length not in range.
   *
   */
  void printInvalidItemDesc();

  void printInvalidItemCostFormat();

  /**
   * Prints a message when given item cost is not in range.
   *
   */
  void printInvalidItemCost();

  void printInvalidCategory();

  void printInvalidDays();

  void printInvalidStartDay();

  void printInvalidDuration();

  /**
   * Prints the item is already taken.
   *
   */
  void printItemAlreadyTaken();

  void printNotEnoughCredits();

  // ----- VIEWS -----
  /**
   * Shows member details on console.
   *
   * @param memberToView member to view
   */
  void viewMember(Member memberToView);

  /**
   * Shows simple member details on console.
   *
   * @param members members to list
   */
  void listMembersSimple(ArrayList<Member> members);

  /**
   * Shows verbose member details on console.
   *
   * @param members members to list
   */
  void listMembersVerbose(ArrayList<Member> members);

  /**
   * Shows item details on console.
   *
   * @param item item to view
   */
  void viewItem(Item item);

  /**
   * Shows contract details on console.
   *
   * @param contract contract to view
   */
  void viewContract(Contract contract);

  void printCurrDay(Time time);

  // ----- MISC -----

}