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
import view.constants.SwedishMenuConstants;
import view.enums.ContractCreateOption;
import view.enums.ContractMenuOption;
import view.enums.ItemEditMenuOption;
import view.enums.ItemMenuOption;
import view.enums.MainMenuOption;
import view.enums.MemberMenuOption;
import view.enums.TimeMenuOption;

/**
 * Swedish view.
 */
public class SwedishUi implements ConsoleUi {
  private Scanner scanner = new Scanner(System.in, "UTF-8");

  // ----- MENUS -----

  /**
   * Shows main menu on console.
   *
   * @return user choice
   */
  public MainMenuOption getMainMenuOption() {
    System.out.println(SwedishMenuConstants.MAIN_TIME + ". Tid");
    System.out.println(SwedishMenuConstants.MAIN_CONTRACTS + ". Kontrakt");
    System.out.println(SwedishMenuConstants.MAIN_MEMBERS + ". Medlemmar");
    System.out.println(SwedishMenuConstants.MAIN_ITEMS + ". Föremål");
    System.out.println(SwedishMenuConstants.MAIN_SWITCH_UI + ". Byta UI");
    System.out.println(SwedishMenuConstants.MAIN_EXIT + ". Slut");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.MAIN_TIME:
        return MainMenuOption.TIME;
      case SwedishMenuConstants.MAIN_CONTRACTS:
        return MainMenuOption.CONTRACTS;
      case SwedishMenuConstants.MAIN_MEMBERS:
        return MainMenuOption.MEMBERS;
      case SwedishMenuConstants.MAIN_ITEMS:
        return MainMenuOption.ITEMS;
      case SwedishMenuConstants.MAIN_SWITCH_UI:
        return MainMenuOption.SWITCH_UI;
      case SwedishMenuConstants.MAIN_EXIT:
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
    System.out.println(SwedishMenuConstants.MEMBER_CREATE + ". Skapa medlem");
    System.out.println(SwedishMenuConstants.MEMBER_DELETE + ". Ta bort medlem");
    System.out.println(SwedishMenuConstants.MEMBER_EDIT + ". Redigera medlem");
    System.out.println(SwedishMenuConstants.MEMBER_VIEW + ". Visa medlem");
    System.out.println(SwedishMenuConstants.MEMBER_LIST_SIMPLE + ". (Enkla) Lista Medlemmar");
    System.out.println(SwedishMenuConstants.MEMBER_LIST_VERBOSE + ". (Komplexa) Lista Medlemmar");
    System.out.println(SwedishMenuConstants.MEMBER_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.MEMBER_CREATE:
        return MemberMenuOption.CREATE;
      case SwedishMenuConstants.MEMBER_DELETE:
        return MemberMenuOption.DELETE;
      case SwedishMenuConstants.MEMBER_EDIT:
        return MemberMenuOption.EDIT;
      case SwedishMenuConstants.MEMBER_VIEW:
        return MemberMenuOption.VIEW;
      case SwedishMenuConstants.MEMBER_LIST_SIMPLE:
        return MemberMenuOption.SIMPLE_LIST;
      case SwedishMenuConstants.MEMBER_LIST_VERBOSE:
        return MemberMenuOption.COMPLEX_LIST;
      case SwedishMenuConstants.MEMBER_BACK:
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
    System.out.println(SwedishMenuConstants.ITEMS_CREATE + ". Skapa föremål");
    System.out.println(SwedishMenuConstants.ITEMS_DELETE + ". Ta bort föremål");
    System.out.println(SwedishMenuConstants.ITEMS_EDIT + ". Redigera föremål");
    System.out.println(SwedishMenuConstants.ITEMS_VIEW + ". Visa föremål");
    System.out.println(SwedishMenuConstants.ITEMS_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.ITEMS_CREATE:
        return ItemMenuOption.CREATE;
      case SwedishMenuConstants.ITEMS_DELETE:
        return ItemMenuOption.DELETE;
      case SwedishMenuConstants.ITEMS_EDIT:
        return ItemMenuOption.EDIT;
      case SwedishMenuConstants.ITEMS_VIEW:
        return ItemMenuOption.VIEW;
      case SwedishMenuConstants.ITEMS_BACK:
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
    System.out.println(SwedishMenuConstants.ITEMS_EDIT_NAME + ". Redigera namn");
    System.out.println(SwedishMenuConstants.ITEMS_EDIT_DESC + ". Redigera beskrivning");
    System.out.println(SwedishMenuConstants.ITEMS_EDIT_COST + ". Redigera kostnad");
    System.out.println(SwedishMenuConstants.ITEMS_EDIT_CAT + ". Redigera kategori");
    System.out.println(SwedishMenuConstants.ITEMS_EDIT_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.ITEMS_EDIT_NAME:
        return ItemEditMenuOption.NAME;
      case SwedishMenuConstants.ITEMS_EDIT_DESC:
        return ItemEditMenuOption.DESC;
      case SwedishMenuConstants.ITEMS_EDIT_COST:
        return ItemEditMenuOption.COST;
      case SwedishMenuConstants.ITEMS_EDIT_CAT:
        return ItemEditMenuOption.CATEGORY;
      case SwedishMenuConstants.ITEMS_EDIT_BACK:
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
    System.out.println("Välj kategori: \n");
    System.out.println(SwedishMenuConstants.ITEMS_CATEGORY_TOOL + ". Verktyg");
    System.out.println(SwedishMenuConstants.ITEMS_CATEGORY_VEHICLE + ". Fordon");
    System.err.println(SwedishMenuConstants.ITEMS_CATEGORY_GAME + ". Spel");
    System.err.println(SwedishMenuConstants.ITEMS_CATEGORY_TOY + ". Leksak");
    System.err.println(SwedishMenuConstants.ITEMS_CATEGORY_SPORT + ". Sport");
    System.err.println(SwedishMenuConstants.ITEMS_CATEGORY_OTHER + ". Övrig");
    System.out.println(SwedishMenuConstants.ITEMS_CATEGORY_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.ITEMS_CATEGORY_TOOL:
        return Category.Tool;
      case SwedishMenuConstants.ITEMS_CATEGORY_VEHICLE:
        return Category.Vehicle;
      case SwedishMenuConstants.ITEMS_CATEGORY_GAME:
        return Category.Game;
      case SwedishMenuConstants.ITEMS_CATEGORY_TOY:
        return Category.Toy;
      case SwedishMenuConstants.ITEMS_CATEGORY_SPORT:
        return Category.Sport;
      case SwedishMenuConstants.ITEMS_CATEGORY_OTHER:
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
    System.out.println(SwedishMenuConstants.CONTRACT_CREATE + ". Skapa kontrakt");
    System.out.println(SwedishMenuConstants.CONTRACT_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.CONTRACT_CREATE:
        return ContractMenuOption.CREATE;
      case SwedishMenuConstants.CONTRACT_BACK:
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
    System.out.println(SwedishMenuConstants.TIME_SHOW_DAY + ". Visa nuvarande dag");
    System.out.println(SwedishMenuConstants.TIME_DAY_UP + ". Avancera dag");
    System.err.println(SwedishMenuConstants.TIME_BACK + ". Tillbaka");
    System.out.print("Ange ditt val: ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.TIME_SHOW_DAY:
        return TimeMenuOption.SHOW;
      case SwedishMenuConstants.TIME_DAY_UP:
        return TimeMenuOption.ADVANCE;
      case SwedishMenuConstants.TIME_BACK:
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
    System.out.println("Sida (" + (++page) + ")");
    System.out.println("1. Förra sida");
    for (int i = 2; i < listNames.size() + 2; i++) {
      System.out.println(i + ". " + listNames.get(i - 2));
    }
    System.out.println((listNames.size() + 2) + ". Nästa sida");
    System.out.println((listNames.size() + 3) + ". Tillbaka");
    System.out.print("=============\n");
    System.out.print("Välj nummer: ");
    return getStringInput();
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
      } else if (input.equals(SwedishMenuConstants.PAGE_PREV)) {
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
      } else if (input.equals(SwedishMenuConstants.PAGE_PREV)) {
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

  public ConsoleUi switchUi() {
    return new EnglishUi();
  }

  // ----- INPUTS -----
  public String createMemberName() {
    System.out.print("Välj medlem namn: ");
    return getStringInput();
  }

  public String createMemberEmail() {
    System.out.print("Välj medlem e-post: ");
    return getStringInput();
  }

  public String createMemberPhoneNum() {
    System.out.print("Välj medlem telefonnummer: ");
    return getStringInput();
  }

  public String createItemName() {
    System.out.print("Välj föremål namn: ");
    return getStringInput();
  }

  public String createItemDescription() {
    System.out.print("Välj föremål beskrivning: ");
    return getStringInput();
  }

  public String createItemCost() {
    System.out.print("Välj föremål kostnad: ");
    return getStringInput();
  }

  public void chooseMemberToLend() {
    System.out.print("Välj långivare:\n");
    System.out.println("=============");
  }

  public void chooseItemToLend() {
    System.out.print("Välj föremål:\n");
    System.out.println("=============");
  }

  public String getStartDay() {
    System.out.print("Välj startdag: ");
    return getStringInput();
  }

  /**
   * Get number of days.
   *
   * @return user input
   */
  public String getDays() {
    System.out.print("Välj hur många dagar: ");
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
    System.out.println("Föremål: " + item.getName());
    System.out.println("Dagar: " + days);
    Integer cost = item.getDailyCost() * days;
    System.out.println("Total: " + cost + "cr");
    System.out.println("Du har: " + member.getCredits() + "cr");
    System.out.print("Är du säker? (" + EnglishMenuConstants.CONTRACT_CREATE_CONFIRM + "/"
        + EnglishMenuConstants.CONTRACT_CREATE_DENY + "): ");
    String option = getStringInput();

    switch (option) {
      case SwedishMenuConstants.CONTRACT_CREATE_CONFIRM:
        return ContractCreateOption.CONFIRM;
      case SwedishMenuConstants.CONTRACT_CREATE_DENY:
        return ContractCreateOption.DENY;
      default:
        return ContractCreateOption.ERROR;
    }
  }

  // ----- SUCCESS MESSAGES -----
  public void printMemberCreated() {
    System.out.println("Medlem har lagts till!");
    waitForEnter();
  }

  /**
   * Print member creation cancelled.
   */
  public void printCreateMemberCancel() {
    System.out.println("Medlemsskapandet avbröts!");
    waitForEnter();
  }

  public void printMemberDeleted() {
    System.out.println("Medlem har raderats.");
    waitForEnter();
  }

  public void printMemberEdited() {
    System.out.println("Medlem har redigerats.");
    waitForEnter();
  }

  public void printItemCreated() {
    System.out.println("Föremålet har skapats.");
    waitForEnter();
  }

  public void printCreateItemCancel() {
    System.out.println("Skapandet av objekt avbröts.");
    waitForEnter();
  }

  public void printItemDeleted() {
    System.out.println("Föremålet har tagits bort!");
    waitForEnter();
  }

  public void printItemEdited() {
    System.out.println("Föremålet har redigerats.");
    waitForEnter();
  }

  public void printEditItemCancel() {
    System.out.println("Objektredigering avbröts.");
    waitForEnter();
  }

  public void printContractCreated() {
    System.out.println("Kontrakt skapat!");
    waitForEnter();
  }

  public void printCreateContractCancel() {
    System.out.println("Skapandet av kontrakt avbröts!");
    waitForEnter();
  }

  public void printTimeAdvanced() {
    System.out.println("Tiden har gått framåt!");
    waitForEnter();
  }

  // ----- ERROR MESSAGES -----
  public void printMemberNotCreated() {
    System.out.println("Kunde inte lägg till medlem.");
    waitForEnter();
  }

  public void printMemberNotEdited() {
    System.out.println("Kunde inte redigera medlem.");
    waitForEnter();
  }

  /**
   * Prints a message when given member name is not unique or length not in range.
   */
  public void printInvalidMemberName() {
    System.out.println("Namnet måste vara unikt och stå mellan "
        + Variables.MEMBER_MIN_NAME_LEN + " och "
        + Variables.MEMBER_MAX_NAME_LEN + " tecken långa. Försök igen.");
    waitForEnter();
  }

  public void printInvalidEmail() {
    System.out.println("E-postadressen måste vara giltig och unik. Försök igen.");
    waitForEnter();
  }

  public void printInvalidPhoneNum() {
    System.out.println("Telefonnumret måste vara unikt. Försök igen.");
    waitForEnter();
  }

  public void printItemNotCreated() {
    System.out.println("Kunde inte lägg till föremålet.");
    waitForEnter();
  }

  public void printItemNotEdited() {
    System.out.println("Kunde inte redigera föremålet.");
    waitForEnter();
  }

  /**
   * Prints a message when given item name is not unique or length not in range.
   *
   */
  public void printInvalidItemName() {
    System.out.println("Namnet måste stå mellan " + Variables.ITEM_MIN_NAME_LEN + " och "
        + Variables.ITEM_MAX_NAME_LEN + " tecken långa och unika för medlemmen. Försök igen.");
    waitForEnter();
  }

  /**
   * Prints a message when given item description length not in range.
   *
   */
  public void printInvalidItemDesc() {
    System.out.println("Beskrivning måste stå mellan " + Variables.ITEM_DESC_MIN_LEN + " och "
        + Variables.ITEM_DESC_MAX_LEN + " tecken långa. Försök igen.");
    waitForEnter();
  }

  public void printInvalidItemCostFormat() {
    System.out.println("Kostnaden måste vara ett heltal.");
    waitForEnter();
  }

  /**
   * Prints a message when given item cost is not in range.
   *
   */
  public void printInvalidItemCost() {
    System.out.println("Kostnaden måste stå mellan " + Variables.ITEM_MIN_COST
        + " och " + Variables.ITEM_MAX_COST + ".");
    waitForEnter();
  }

  public void printInvalidCategory() {
    System.out.println("Kategori finns inte. Försök igen.");
    waitForEnter();
  }

  public void printInvalidDays() {
    System.out.println("Dag ska vara ett positivt heltal. Försök igen.");
    waitForEnter();
  }

  public void printInvalidStartDay() {
    System.out.println("Dagen har redan passerat. Ange en dag från och med idag.");
    waitForEnter();
  }

  public void printInvalidDuration() {
    System.out.println("Varaktighet måste vara " + Variables.CONTRACT_MIN_DURATION + " eller mer.");
    waitForEnter();
  }

  /**
   * Prints the item is already taken.
   *
   */
  public void printItemAlreadyTaken() {
    System.out.println("Föremålet är redan tagit. Prova en annan.");
    waitForEnter();
  }

  public void printNotEnoughCredits() {
    System.out.println("Du har inte tillräckligt med krediter.");
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
    System.out.println("Namn: " + memberToView.getName());
    System.out.println("E-post: " + memberToView.getEmail());
    System.out.println("Telefonnummer: " + memberToView.getPhoneNum());
    System.out.println("Datum tillagt: " + memberToView.getDayAdded());
    System.out.println("Krediter: " + memberToView.getCredits());
    System.out.println("Föremål: ");
    for (Item item : memberToView.getItems()) {
      viewItem(item);
    }
    waitForEnter();
  }

  private List<Member> sortMembersById(ArrayList<Member> members) {
    Comparator<Member> idComparator = Comparator.comparing(Member::getId);
    List<Member> sortedMembers = members.stream()
        .sorted(idComparator)
        .collect(Collectors.toList());
    return sortedMembers;
  }

  /**
   * Shows simple member details on console.
   *
   * @param members members to list
   */
  public void listMembersSimple(ArrayList<Member> members) {
    List<Member> sortedMembers = sortMembersById(members);

    for (Member member : sortedMembers) {
      System.out.println("Id: " + member.getId());
      System.out.println("Namn: " + member.getName());
      System.out.println("E-post: " + member.getEmail());
      System.out.println("Krediter: " + member.getCredits());
      System.out.println("Föremål: " + member.getItems().size());
      waitForEnter();
    }
  }

  /**
   * Shows verbose member details on console.
   *
   * @param members members to list
   */
  public void listMembersVerbose(ArrayList<Member> members) {
    List<Member> sortedMembers = sortMembersById(members);

    for (Member member : sortedMembers) {
      System.out.println("Id: " + member.getId());
      System.out.println("Namn: " + member.getName());
      System.out.println("E-post: " + member.getEmail());
      System.out.println("Föremål: ");
      for (Item item : member.getItems()) {
        System.out.println("\tNamn: " + item.getName());
        System.out.println("\tBeskrivning: " + item.getDesc());
        System.out.println("\tKostnad: " + item.getDailyCost());
        if (item.getCurrUser() != null) {
          System.out.println("\tAnvänd av: " + item.getCurrUser().getName());
          System.out.println("\tKontrakt: ");
          System.out.println(
              "\t\tStartdag: "
                  + item.getContracts().get(item.getContracts().size() - 1).getStartDay());
          System.out.println(
              "\t\tSlutdag: "
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
    System.out.println("\tNamn: " + item.getName());
    System.out.println("\tBeskrivning: " + item.getDesc());
    System.out.println("\tDatum tillagt: " + item.getDayAdded());
    System.out.println("\tDaglig kostnad: " + item.getDailyCost());
    System.out.println("\tKetegori: " + item.getCategory().name());
    if (item.getCurrUser() != null) {
      System.out.println("\tAnvänds av: " + item.getCurrUser().getName());
    }
    System.out.println("\tKontrakt: ");
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
    System.out.println("\t\tStartdag: " + contract.getStartDay());
    System.out.println("\t\tSlutdag: " + contract.getEndDay());
    System.out.println("\t\tLångivare: " + contract.getLender().getName());
  }

  public void printCurrDay(Time time) {
    System.out.println("Dag (" + time.getDay() + ")");
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
    System.out.println("Tryck på Enter för att fortsätta.");
    System.out.println("=============\n");
    scanner.nextLine();
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