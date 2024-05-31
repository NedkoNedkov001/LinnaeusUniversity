package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.Contract;
import model.Item;
import model.Member;
import model.Time;
import model.constants.Variables;
import model.enums.Category;
import view.constants.EnglishMenuConstants;
import view.enums.ContractCreateOption;
import view.enums.ContractMenuOption;
import view.enums.ItemEditMenuOption;
import view.enums.ItemMenuOption;
import view.enums.MainMenuOption;
import view.enums.MemberMenuOption;
import view.enums.TimeMenuOption;

/**
 * English view.
 * 
 */
public class EnglishUi implements ConsoleUi {
  private Scanner scanner = new Scanner(System.in, "UTF-8");

  // ----- MENUS -----

  /**
   * Shows main menu on console.
   *
   * @return user choice
   */
  public MainMenuOption getMainMenuOption() {
    System.out.println(EnglishMenuConstants.MAIN_MEMBERS + ". Members");
    System.out.println(EnglishMenuConstants.MAIN_ITEMS + ". Items");
    System.out.println(EnglishMenuConstants.MAIN_CONTRACTS + ". Contracts");
    System.out.println(EnglishMenuConstants.MAIN_TIME + ". Time");
    System.out.println(EnglishMenuConstants.MAIN_SWITCH_UI + ". Switch UI");
    System.out.println(EnglishMenuConstants.MAIN_EXIT + ". Quit");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.MAIN_MEMBERS:
        return MainMenuOption.MEMBERS;
      case EnglishMenuConstants.MAIN_ITEMS:
        return MainMenuOption.ITEMS;
      case EnglishMenuConstants.MAIN_CONTRACTS:
        return MainMenuOption.CONTRACTS;
      case EnglishMenuConstants.MAIN_TIME:
        return MainMenuOption.TIME;
      case EnglishMenuConstants.MAIN_SWITCH_UI:
        return MainMenuOption.SWITCH_UI;
      case EnglishMenuConstants.MAIN_EXIT:
        return MainMenuOption.EXIT;
      default:
        return MainMenuOption.ERROR;
    }

  }

  /**
   * Shows member menu on console.
   *
   * @return user choice
   */
  public MemberMenuOption getMemberMenuOption() {
    System.out.println(EnglishMenuConstants.MEMBER_CREATE + ". Create member");
    System.out.println(EnglishMenuConstants.MEMBER_DELETE + ". Delete member");
    System.out.println(EnglishMenuConstants.MEMBER_EDIT + ". Edit member");
    System.out.println(EnglishMenuConstants.MEMBER_VIEW + ". View member");
    System.out.println(EnglishMenuConstants.MEMBER_LIST_SIMPLE + ". (Simple) list members");
    System.out.println(EnglishMenuConstants.MEMBER_LIST_VERBOSE + ". (Complex) list members");
    System.out.println(EnglishMenuConstants.MEMBER_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.MEMBER_CREATE:
        return MemberMenuOption.CREATE;
      case EnglishMenuConstants.MEMBER_DELETE:
        return MemberMenuOption.DELETE;
      case EnglishMenuConstants.MEMBER_EDIT:
        return MemberMenuOption.EDIT;
      case EnglishMenuConstants.MEMBER_VIEW:
        return MemberMenuOption.VIEW;
      case EnglishMenuConstants.MEMBER_LIST_SIMPLE:
        return MemberMenuOption.SIMPLE_LIST;
      case EnglishMenuConstants.MEMBER_LIST_VERBOSE:
        return MemberMenuOption.COMPLEX_LIST;
      case EnglishMenuConstants.MEMBER_BACK:
        return MemberMenuOption.BACK;
      default:
        return MemberMenuOption.ERROR;
    }
  }

  /**
   * Shows item menu on console.
   *
   * @return user choice
   */
  public ItemMenuOption getItemMenuOption() {
    System.out.println(EnglishMenuConstants.ITEMS_CREATE + ". Create item");
    System.out.println(EnglishMenuConstants.ITEMS_DELETE + ". Delete item");
    System.out.println(EnglishMenuConstants.ITEMS_EDIT + ". Edit item");
    System.out.println(EnglishMenuConstants.ITEMS_VIEW + ". View item");
    System.out.println(EnglishMenuConstants.ITEMS_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.ITEMS_CREATE:
        return ItemMenuOption.CREATE;
      case EnglishMenuConstants.ITEMS_DELETE:
        return ItemMenuOption.DELETE;
      case EnglishMenuConstants.ITEMS_EDIT:
        return ItemMenuOption.EDIT;
      case EnglishMenuConstants.ITEMS_VIEW:
        return ItemMenuOption.VIEW;
      case EnglishMenuConstants.ITEMS_BACK:
        return ItemMenuOption.BACK;
      default:
        return ItemMenuOption.ERROR;
    }
  }

  /**
   * Shows edit item menu on console.
   *
   * @return user choice
   */
  public ItemEditMenuOption getItemEditMenuOption() {
    System.out.println(EnglishMenuConstants.ITEMS_EDIT_NAME + ". Edit name");
    System.out.println(EnglishMenuConstants.ITEMS_EDIT_DESC + ". Edit description");
    System.out.println(EnglishMenuConstants.ITEMS_EDIT_COST + ". Edit cost");
    System.out.println(EnglishMenuConstants.ITEMS_EDIT_CAT + ". Edit Category");
    System.out.println(EnglishMenuConstants.ITEMS_EDIT_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.ITEMS_EDIT_NAME:
        return ItemEditMenuOption.NAME;
      case EnglishMenuConstants.ITEMS_EDIT_DESC:
        return ItemEditMenuOption.DESC;
      case EnglishMenuConstants.ITEMS_EDIT_COST:
        return ItemEditMenuOption.COST;
      case EnglishMenuConstants.ITEMS_EDIT_CAT:
        return ItemEditMenuOption.CATEGORY;
      case EnglishMenuConstants.ITEMS_EDIT_BACK:
        return ItemEditMenuOption.BACK;
      default:
        return ItemEditMenuOption.ERROR;
    }
  }

  /**
   * Shows category choice menu on console.
   *
   * @return user choice
   */
  public Category getItemCategory() {
    System.out.println("Choose the category: \n");
    System.out.println(EnglishMenuConstants.ITEMS_CATEGORY_TOOL + ". Tool");
    System.out.println(EnglishMenuConstants.ITEMS_CATEGORY_VEHICLE + ". Vehicle");
    System.err.println(EnglishMenuConstants.ITEMS_CATEGORY_GAME + ". Game");
    System.err.println(EnglishMenuConstants.ITEMS_CATEGORY_TOY + ". Toy");
    System.err.println(EnglishMenuConstants.ITEMS_CATEGORY_SPORT + ". Sport");
    System.err.println(EnglishMenuConstants.ITEMS_CATEGORY_OTHER + ". Other");
    System.out.println(EnglishMenuConstants.ITEMS_CATEGORY_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.ITEMS_CATEGORY_TOOL:
        return Category.Tool;
      case EnglishMenuConstants.ITEMS_CATEGORY_VEHICLE:
        return Category.Vehicle;
      case EnglishMenuConstants.ITEMS_CATEGORY_GAME:
        return Category.Game;
      case EnglishMenuConstants.ITEMS_CATEGORY_TOY:
        return Category.Toy;
      case EnglishMenuConstants.ITEMS_CATEGORY_SPORT:
        return Category.Sport;
      case EnglishMenuConstants.ITEMS_CATEGORY_OTHER:
        return Category.Other;
      default:
        return null;
    }
  }

  /**
   * Shows contract menu on console.
   *
   * @return user choice
   */
  public ContractMenuOption getContractMenuOption() {
    System.out.println(EnglishMenuConstants.CONTRACT_CREATE + ". Create contract");
    System.out.println(EnglishMenuConstants.CONTRACT_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.CONTRACT_CREATE:
        return ContractMenuOption.CREATE;
      case EnglishMenuConstants.CONTRACT_BACK:
        return ContractMenuOption.BACK;
      default:
        return ContractMenuOption.ERROR;
    }
  }

  /**
   * Shows item menu on console.
   *
   * @return user choice
   */
  public TimeMenuOption getTimeMenuOption() {
    System.out.println(EnglishMenuConstants.TIME_SHOW_DAY + ". Show current day");
    System.out.println(EnglishMenuConstants.TIME_DAY_UP + ". Advance day");
    System.err.println(EnglishMenuConstants.TIME_BACK + ". Back");
    System.out.print("Enter your choice: ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.TIME_SHOW_DAY:
        return TimeMenuOption.SHOW;
      case EnglishMenuConstants.TIME_DAY_UP:
        return TimeMenuOption.ADVANCE;
      case EnglishMenuConstants.TIME_BACK:
        return TimeMenuOption.BACK;
      default:
        return TimeMenuOption.ERROR;
    }
  }

  /**
   * Prints member menu.
   *
   * @param page        current page
   * @param pageMembers current page members
   * @return user choice
   */
  public String printMemberMenu(Integer page, List<Member> pageMembers) {
    ArrayList<String> memberNames = new ArrayList<>();
    for (Member member : pageMembers) {
      memberNames.add(member.getName());
    }
    return printListMenu(page, memberNames);
  }

  /**
   * Prints item menu.
   *
   * @param page      current page
   * @param pageItems current page items
   * @return user choice
   */
  public String printItemMenu(Integer page, List<Item> pageItems) {
    ArrayList<String> itemNames = new ArrayList<>();
    for (Item item : pageItems) {
      itemNames.add(item.getName() + "(" + item.getOwner().getName() + ")");
    }
    return printListMenu(page, itemNames);
  }

  /**
   * Shows a list as a menu.
   *
   * @param page      current page
   * @param listNames list items
   * @return user choice
   */
  public String printListMenu(Integer page, ArrayList<String> listNames) {
    System.out.println("Page (" + (++page) + ")");
    System.out.println("1. Previous page");
    for (int i = 2; i < listNames.size() + 2; i++) {
      System.out.println(i + ". " + listNames.get(i - 2));
    }
    System.out.println((listNames.size() + 2) + ". Next page");
    System.out.println((listNames.size() + 3) + ". Back");
    System.out.print("=============\n");
    System.out.print("Enter number: ");
    return getStringInput();
  }

  public ConsoleUi switchUi() {
    return new SwedishUi();
  }

  // ----- INPUTS -----
  public String createMemberName() {
    System.out.print("Enter member name: ");
    return getStringInput();
  }

  public String createMemberEmail() {
    System.out.print("Enter member email: ");
    return getStringInput();
  }

  public String createMemberPhoneNum() {
    System.out.print("Enter member phone number: ");
    return getStringInput();
  }

  public String createItemName() {
    System.out.print("Enter Item name: ");
    return getStringInput();
  }

  public String createItemDescription() {
    System.out.print("Enter Item Description: ");
    return getStringInput();
  }

  public String createItemCost() {
    System.out.print("Enter Item cost: ");
    return getStringInput();
  }

  public void chooseMemberToLend() {
    System.out.print("Choose member to lend to:\n");
    System.out.println("=============");
  }

  public void chooseItemToLend() {
    System.out.print("Choose item to lend:\n");
    System.out.println("=============");
  }

  public String getStartDay() {
    System.out.print("Enter start day lend: ");
    return getStringInput();
  }

  /**
   * Get number of days.
   *
   * @return user input
   */
  public String getDays() {
    System.out.print("Enter how many days to lend: ");
    return getStringInput();
  }

  /**
   * Get user confirmation about a contract.
   *
   * @param member member to lend to
   * @param item   item to lend
   * @param days   total days
   * @return user choice
   */
  public ContractCreateOption getConractCreateOption(Member member, Item item, Integer days) {
    System.out.println("Item: " + item.getName());
    System.out.println("Days: " + days);
    Integer cost = item.getDailyCost() * days;
    System.out.println("Total: " + cost + "cr");
    System.out.println("You have: " + member.getCredits() + "cr");
    System.out.print("Are you sure? (" + EnglishMenuConstants.CONTRACT_CREATE_CONFIRM + "/"
        + EnglishMenuConstants.CONTRACT_CREATE_DENY + "): ");
    String option = getStringInput();

    switch (option) {
      case EnglishMenuConstants.CONTRACT_CREATE_CONFIRM:
        return ContractCreateOption.CONFIRM;
      case EnglishMenuConstants.CONTRACT_CREATE_DENY:
        return ContractCreateOption.DENY;
      default:
        return ContractCreateOption.ERROR;
    }
  }

  // ----- SUCCESS MESSAGES -----
  public void printMemberCreated() {
    System.out.println("Member added successfully!");
    waitForEnter();
  }

  /**
   * Print member creation cancelled.
   */
  public void printCreateMemberCancel() {
    System.out.println("Member creation cancelled!");
    waitForEnter();
  }

  public void printMemberDeleted() {
    System.out.println("Member deleted successfully.");
    waitForEnter();
  }

  public void printMemberEdited() {
    System.out.println("Member edited successfully.");
    waitForEnter();
  }

  public void printItemCreated() {
    System.out.println("Item created successfully.");
    waitForEnter();
  }

  public void printCreateItemCancel() {
    System.out.println("Item creation cancelled.");
    waitForEnter();
  }

  public void printItemDeleted() {
    System.out.println("Item deleted successfully!");
    waitForEnter();
  }

  public void printItemEdited() {
    System.out.println("Item edited successfully.");
    waitForEnter();
  }

  public void printEditItemCancel() {
    System.out.println("Item edit cancelled.");
    waitForEnter();
  }

  public void printContractCreated() {
    System.out.println("Contract created successfully!");
    waitForEnter();
  }

  public void printCreateContractCancel() {
    System.out.println("Contract creation cancelled!");
    waitForEnter();
  }

  public void printTimeAdvanced() {
    System.out.println("Time advanced successfully!");
    waitForEnter();
  }

  // ----- ERROR MESSAGES -----
  public void printMemberNotCreated() {
    System.out.println("Could not add member.");
    waitForEnter();
  }

  public void printMemberNotEdited() {
    System.out.println("Could not edit member.");
    waitForEnter();
  }

  /**
   * Prints a message when given member name is not unique or length not in range.
   */
  public void printInvalidMemberName() {
    System.out.println("Name must be unique and between "
        + Variables.MEMBER_MIN_NAME_LEN + " and "
        + Variables.MEMBER_MAX_NAME_LEN + " characters long. Try again.");
    waitForEnter();
  }

  public void printInvalidEmail() {
    System.out.println("Email must be valid and unique. Try again.");
    waitForEnter();
  }

  public void printInvalidPhoneNum() {
    System.out.println("Phone number must be unique. Try again.");
    waitForEnter();
  }

  public void printItemNotCreated() {
    System.out.println("Could not add item.");
    waitForEnter();
  }

  public void printItemNotEdited() {
    System.out.println("Could not edit item.");
    waitForEnter();
  }

  /**
   * Prints a message when given item name is not unique or length not in range.
   *
   */
  public void printInvalidItemName() {
    System.out.println("Name must be between " + Variables.ITEM_MIN_NAME_LEN + " and "
        + Variables.ITEM_MAX_NAME_LEN + " characters long and unique to the member. Try again.");
    waitForEnter();
  }

  /**
   * Prints a message when given item description length not in range.
   *
   */
  public void printInvalidItemDesc() {
    System.out.println("Description must be between " + Variables.ITEM_DESC_MIN_LEN + " and "
        + Variables.ITEM_DESC_MAX_LEN + " characters long. Try again.");
    waitForEnter();
  }

  public void printInvalidItemCostFormat() {
    System.out.println("Cost must be an integer.");
    waitForEnter();
  }

  /**
   * Prints a message when given item cost is not in range.
   *
   */
  public void printInvalidItemCost() {
    System.out.println("Cost must be between " + Variables.ITEM_MIN_COST
        + " and " + Variables.ITEM_MAX_COST + ".");
    waitForEnter();
  }

  public void printInvalidCategory() {
    System.out.println("Category does not exist. Try again.");
    waitForEnter();
  }

  public void printInvalidDays() {
    System.out.println("Day should be a positive integer. Try again.");
    waitForEnter();
  }

  public void printInvalidStartDay() {
    System.out.println("Day has already passed. Enter a day starting from today.");
    waitForEnter();
  }

  public void printInvalidDuration() {
    System.out.println("Duration must be " + Variables.CONTRACT_MIN_DURATION + " or more.");
    waitForEnter();
  }

  /**
   * Prints the item is already taken.
   *
   */
  public void printItemAlreadyTaken() {
    System.out.println("Item is already taken. Try another one.");
    waitForEnter();
  }

  public void printNotEnoughCredits() {
    System.out.println("You do not have enough credits.");
    waitForEnter();
  }

  // ----- VIEWS -----
  /**
   * Shows member details on console.
   *
   * @param memberToView member to view
   */
  public void viewMember(Member memberToView) {
    System.out.println("Id: " + memberToView.getId());
    System.out.println("Name: " + memberToView.getName());
    System.out.println("Email: " + memberToView.getEmail());
    System.out.println("Phone number: " + memberToView.getPhoneNum());
    System.out.println("Date added: " + memberToView.getDayAdded());
    System.out.println("Credits: " + memberToView.getCredits());
    System.out.println("Items: ");
    for (Item item : memberToView.getItems()) {
      viewItem(item);
    }
    waitForEnter();
  }

  private List<Member> sortMembersByName(ArrayList<Member> members) {
    Comparator<Member> nameComparator = Comparator.comparing(Member::getName);
    List<Member> sortedMembers = members.stream()
        .sorted(nameComparator)
        .collect(Collectors.toList());
    return sortedMembers;
  }

  /**
   * Shows simple member details on console.
   *
   * @param members member to list
   */
  public void listMembersSimple(ArrayList<Member> members) {
    List<Member> sortedMembers = sortMembersByName(members);

    for (Member member : sortedMembers) {
      System.out.println("Id: " + member.getId());
      System.out.println("Name: " + member.getName());
      System.out.println("Email: " + member.getEmail());
      System.out.println("Credits: " + member.getCredits());
      System.out.println("Items: " + member.getItems().size());
      waitForEnter();
    }
  }

  /**
   * Shows verbose member details on console.
   *
   * @param members member to list
   */
  public void listMembersVerbose(ArrayList<Member> members) {
    List<Member> sortedMembers = sortMembersByName(members);

    for (Member member : sortedMembers) {
      System.out.println("Id: " + member.getId());
      System.out.println("Name: " + member.getName());
      System.out.println("Email: " + member.getEmail());
      System.out.println("Items: ");
      for (Item item : member.getItems()) {
        System.out.println("\tName: " + item.getName());
        System.out.println("\tDescription: " + item.getDesc());
        System.out.println("\tCost: " + item.getDailyCost());
        if (item.getCurrUser() != null) {
          System.out.println("\tUsed by: " + item.getCurrUser().getName());
          System.out.println("\tContract: ");
          System.out.println(
              "\t\tStart date: "
                  + item.getContracts().get(item.getContracts().size() - 1).getStartDay());
          System.out.println(
              "\t\tEnd date: "
                  + item.getContracts().get(item.getContracts().size() - 1).getEndDay());
        }
      }
      waitForEnter();
    }
  }

  /**
   * Shows item details on console.
   *
   * @param item item to view
   */
  public void viewItem(Item item) {
    System.out.println("\tName: " + item.getName());
    System.out.println("\tDescription: " + item.getDesc());
    System.out.println("\tDateAdded: " + item.getDayAdded());
    System.out.println("\tDailyCost: " + item.getDailyCost());
    System.out.println("\tCategory: " + item.getCategory().name());
    if (item.getCurrUser() != null) {
      System.out.println("\tUsed by: " + item.getCurrUser().getName());
    }
    System.out.println("\tContracts: ");
    for (Contract contract : item.getContracts()) {
      viewContract(contract);
    }
    waitForEnter();
  }

  /**
   * Shows contract details on console.
   *
   * @param contract contract to view
   */
  public void viewContract(Contract contract) {
    System.out.println("=============");
    System.out.println("\t\tStart date: " + contract.getStartDay());
    System.out.println("\t\tEnd date: " + contract.getEndDay());
    System.out.println("\t\tLender: " + contract.getLender().getName());
  }

  public void printCurrDay(Time time) {
    System.out.println("Day (" + time.getDay() + ")");
    waitForEnter();
  }

  // ----- MISC -----
  private String getStringInput() {
    String input = scanner.nextLine();
    System.out.println("=============\n");
    return input;
  }

  /**
   * Wait for user to press enter.
   */
  private void waitForEnter() {
    System.out.println("Press Enter to continue.");
    System.out.println("=============\n");
    scanner.nextLine();
  }

  /**
   * Returns a member.
   */
  public Member getMember(ArrayList<Member> allMembers) {
    Member selected = null;
    Integer page = 0;
    String input = null;
    List<Member> pageMembers;

    while (true) {
      if (allMembers.size() < Variables.ITEMS_PER_PAGE) {
        pageMembers = allMembers.subList(page * Variables.MEMBERS_PER_PAGE, allMembers.size());
      } else {
        pageMembers = allMembers.subList(page * 10, page * 10 + Variables.ITEMS_PER_PAGE);
      }
      input = printMemberMenu(page, pageMembers);

      if (input.equals(Integer.toString(pageMembers.size() + 3))) { // Exit
        break;
      } else if (input.equals(EnglishMenuConstants.PAGE_PREV)) {
        page = prevPage(page);
      } else if (input.equals(Integer.toString(pageMembers.size() + 2))) {
        page = nextMemberPage(allMembers, page);
      } else {
        try {
          Integer intChoice = Integer.parseInt(input);
          return pageMembers.get(intChoice - 2);
        } catch (Exception e) {
          continue;
        }
      }
    }
    return selected;
  }

  /**
   * Returns an item.
   */
  public Item getItem(ArrayList<Item> allItems) {
    Item selected = null;
    Integer page = 0;
    String input = null;
    List<Item> pageItems;

    while (true) {
      if (allItems.size() < Variables.ITEMS_PER_PAGE) {
        pageItems = allItems.subList(page * Variables.MEMBERS_PER_PAGE, allItems.size());
      } else {
        pageItems = allItems.subList(page * 10, page * 10 + Variables.ITEMS_PER_PAGE);
      }
      input = printItemMenu(page, pageItems);

      if (input.equals(Integer.toString(pageItems.size() + 3))) { // Exit
        break;
      } else if (input.equals(EnglishMenuConstants.PAGE_PREV)) {
        page = prevPage(page);
      } else if (input.equals(Integer.toString(pageItems.size() + 2))) {
        page = nextItemPage(allItems, page);
      } else {
        try {
          Integer intChoice = Integer.parseInt(input);
          return pageItems.get(intChoice - 2);
        } catch (Exception e) {
          continue;
        }
      }
    }
    return selected;
  }

  private Integer nextItemPage(ArrayList<Item> items, Integer page) {
    if (page < (Math.floor((items.size() - 1) / (double) Variables.ITEMS_PER_PAGE))) {
      page++;
    }
    return page;
  }

  private Integer nextMemberPage(ArrayList<Member> members, Integer page) {
    if (page < (Math.floor((members.size() - 1)
        / (double) Variables.MEMBERS_PER_PAGE))) {
      page++;
    }
    return page;
  }

  private Integer prevPage(Integer page) {
    if (page > 0) {
      page--;
    }
    return page;
  }
}