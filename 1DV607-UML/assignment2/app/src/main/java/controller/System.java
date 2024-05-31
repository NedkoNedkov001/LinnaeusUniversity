package controller;

import model.Item;
import model.Member;
import model.Register;
import model.enums.Category;
import view.ConsoleUi;
import view.enums.ContractCreateOption;
import view.enums.ContractMenuOption;
import view.enums.ItemEditMenuOption;
import view.enums.ItemMenuOption;
import view.enums.MainMenuOption;
import view.enums.MemberMenuOption;
import view.enums.TimeMenuOption;

/**
 * Main controller for the app.
 * 
 */
public class System {
  private Register register;
  private ConsoleUi ui;

  /**
   * Constructor for system.
   */
  public System(ConsoleUi ui) {
    register = new Register();
    this.ui = ui;
  }

  /**
   * Starting the app.
   */
  public void run() {
    runMainMenu();
  }

  // ----------------- Menu methods
  private void runMainMenu() {
    Boolean run = true;
    while (run) {
      MainMenuOption option = ui.getMainMenuOption();
      switch (option) {
        case MEMBERS:
          runMemberMenu();
          continue;
        case ITEMS:
          runItemsMenu();
          continue;
        case CONTRACTS:
          runContractsMenu();
          continue;
        case TIME:
          runTimeMenu();
          continue;
        case EXIT:
          run = false;
          continue;
        case SWITCH_UI:
          switchUi();
          continue;
        default:
          break;
      }
    }
  }

  private void runMemberMenu() {
    while (true) {
      MemberMenuOption option = ui.getMemberMenuOption();
      switch (option) {
        case CREATE:
          createMember();
          continue;
        case DELETE:
          deleteMember();
          continue;
        case EDIT:
          editMember();
          continue;
        case VIEW:
          viewMember();
          continue;
        case SIMPLE_LIST:
          simpleListMembers();
          continue;
        case COMPLEX_LIST:
          complexListMembers();
          continue;
        case BACK:
          return;
        default:
          continue;
      }
    }
  }

  private void runItemsMenu() {
    while (true) {
      ItemMenuOption option = ui.getItemMenuOption();
      switch (option) {
        case CREATE:
          createItem();
          continue;
        case DELETE:
          deleteItem();
          continue;
        case EDIT:
          runEditItemMenu();
          continue;
        case VIEW:
          viewItem();
          continue;
        case BACK:
          return;
        default:
          continue;
      }
    }
  }

  private void runEditItemMenu() {
    Item itemToEdit = getItem();
    if (itemToEdit == null) {
      return;
    }
    while (true) {
      ItemEditMenuOption option = ui.getItemEditMenuOption();
      switch (option) {
        case NAME:
          editItemName(itemToEdit);
          continue;
        case DESC:
          editItemDesc(itemToEdit);
          continue;
        case COST:
          editItemCost(itemToEdit);
          continue;
        case CATEGORY:
          editItemCategory(itemToEdit);
          continue;
        case BACK:
          return;
        default:
          continue;
      }
    }
  }

  private void runContractsMenu() {
    while (true) {
      ContractMenuOption option = ui.getContractMenuOption();
      switch (option) {
        case CREATE:
          createContract();
          continue;
        case BACK:
          return;
        default:
          continue;
      }
    }
  }

  private void runTimeMenu() {
    while (true) {
      TimeMenuOption option = ui.getTimeMenuOption();
      switch (option) {
        case SHOW:
          showTime();
          continue;
        case ADVANCE:
          advanceTime();
          continue;
        case BACK:
          return;
        default:
          continue;
      }
    }
  }

  // ----------------- Operations
  private void createMember() {
    String[] memberDetails = getMemberDetails();
    String memberName = memberDetails[0];
    String memberEmail = memberDetails[1];
    String memberPhoneNum = memberDetails[2];
    if (memberName.equals("") || memberEmail.equals("")) {
      ui.printCreateMemberCancel();
      return;
    }
    if (register.addMember(memberName, memberEmail, memberPhoneNum)) {
      ui.printMemberCreated();
    } else {
      ui.printMemberNotCreated();
    }
  }

  private void deleteMember() {
    Member memberToDelete = getMember();
    if (register.removeMember(memberToDelete)) {
      ui.printMemberDeleted();
    }
  }

  private void editMember() {
    Member memberToEdit = getMember();
    if (memberToEdit == null) {
      return;
    }
    String[] memberDetails = getMemberDetails();
    String memberName = memberDetails[0];
    String memberEmail = memberDetails[1];
    String memberPhoneNum = memberDetails[2];
    if (register.editMember(memberToEdit, memberName, memberEmail, memberPhoneNum)) {
      ui.printMemberEdited();
    } else {
      ui.printMemberNotEdited();
    }
  }

  private void viewMember() {
    Member memberToView = getMember();
    if (memberToView == null) {
      return;
    }
    ui.viewMember(memberToView);
  }

  private void simpleListMembers() {
    ui.listMembersSimple(register.getMembers());
  }

  private void complexListMembers() {
    ui.listMembersVerbose(register.getMembers());
  }

  private void createItem() {
    Member owner = getMember();
    if (owner == null) {
      ui.printCreateItemCancel();
      return;
    }

    String itemName = getItemName(owner);
    if (itemName.equals("")) {
      ui.printCreateItemCancel();
      return;
    }

    String itemDescr = getItemDesc();
    Integer itemCost = getItemCost();
    Category itemCategory = ui.getItemCategory();

    if (itemCategory == null) {
      ui.printCreateItemCancel();
      return;
    }
    if (register.addItemToMember(owner, itemName, itemDescr, itemCost, itemCategory)) {
      ui.printItemCreated();
    } else {
      ui.printItemNotCreated();
    }
  }

  private void deleteItem() {
    Item itemToRemove = getItem();
    if (itemToRemove == null) {
      return;
    }
    if (register.removeItem(itemToRemove)) {
      ui.printItemDeleted();
    }
  }

  private void editItemName(Item itemToEdit) {
    String itemName = getItemName(itemToEdit.getOwner());
    if (itemName.equals("")) {
      ui.printEditItemCancel();
      return;
    }
    if (register.editItemName(itemToEdit, itemName)) {
      ui.printItemEdited();
    } else {
      ui.printItemNotEdited();
    }
  }

  private void editItemDesc(Item itemToEdit) {
    String itemDesc = getItemDesc();
    if (register.editItemDesc(itemToEdit, itemDesc)) {
      ui.printItemEdited();
    } else {
      ui.printItemNotEdited();
    }
  }

  private void editItemCost(Item itemToEdit) {
    Integer itemCost = getItemCost();
    if (register.editItemCost(itemToEdit, itemCost)) {
      ui.printItemEdited();
    } else {
      ui.printItemNotEdited();
    }
  }

  private void editItemCategory(Item itemToEdit) {
    Category itemCategory = ui.getItemCategory();
    if (itemCategory == null) {
      ui.printEditItemCancel();
      return;
    }
    if (register.editItemCategory(itemToEdit, itemCategory)) {
      ui.printItemEdited();
    } else {
      ui.printItemNotEdited();
    }
  }

  private void viewItem() {
    Item itemToView = getItem();
    if (itemToView == null) {
      return;
    }
    ui.viewItem(itemToView);
  }

  private void createContract() {
    ui.chooseMemberToLend();
    Member memberToLend = getMember();
    if (memberToLend == null) {
      ui.printCreateContractCancel();
      return;
    }

    ui.chooseItemToLend();
    Item selectedItem = getItem();
    if (selectedItem == null) {
      ui.printCreateContractCancel();
      return;
    }

    Integer startDay = getContractStartDay();
    Integer days = getContractDuration();

    if (!register.isAvailableItemAtTime(selectedItem, startDay, days)) {
      ui.printItemAlreadyTaken();
      return;
    }

    ContractCreateOption option = ui.getConractCreateOption(
        memberToLend, selectedItem, days);
    switch (option) {
      case CONFIRM:
        if (!register.isEnoughCredits(memberToLend, selectedItem, days)) {
          ui.printNotEnoughCredits();
          return;
        }
        register.addContract(selectedItem, memberToLend, startDay, days);
        ui.printContractCreated();
        return;
      case DENY:
        return;
      default:
        return;
    }
  }

  private void showTime() {
    ui.printCurrDay(register.getTime());
  }

  private void advanceTime() {
    register.dayUp();
    ui.printTimeAdvanced();
  }

  private void switchUi() {
    ui = ui.switchUi();
  }

  // ----------------- Supporting methods
  private String[] getMemberDetails() {
    String memberName = null;
    String memberEmail = null;
    String memberPhoneNum = null;
    
    while (true) {
      memberName = ui.createMemberName();
      if (!register.isValidName(memberName)) {
        ui.printInvalidMemberName();
      } else {
        break;
      }
    }
    while (true) {
      memberEmail = ui.createMemberEmail();
      if (!register.isValidEmail(memberEmail)) {
        ui.printInvalidEmail();
      } else {
        break;
      }
    }
    while (true) {
      memberPhoneNum = ui.createMemberPhoneNum();
      if (!register.isValidPhoneNum(memberPhoneNum)) {
        ui.printInvalidPhoneNum();
      } else {
        break;
      }
    }
    String[] details = new String[3];
    details[0] = memberName;
    details[1] = memberEmail;
    details[2] = memberPhoneNum;
    return details;
  }

  private String getItemName(Member owner) {
    String itemName = null;
    while (true) {
      itemName = ui.createItemName();
      if (!register.isValidItemName(owner, itemName)) {
        ui.printInvalidItemName();
      } else {
        break;
      }
    }
    return itemName;
  }

  private String getItemDesc() {
    String itemDesc = null;
    while (true) {
      itemDesc = ui.createItemDescription();
      if (!register.isValidDescription(itemDesc)) {
        ui.printInvalidItemDesc();
      } else {
        break;
      }
    }
    return itemDesc;
  }

  private Integer getItemCost() {
    Integer itemCost = null;
    while (true) {
      try {
        itemCost = Integer.parseInt(ui.createItemCost());
      } catch (Exception e) {
        ui.printInvalidItemCostFormat();
      }
      if (!register.isValidCost(itemCost)) {
        ui.printInvalidItemCost();
      } else {
        break;
      }
    }
    return itemCost;
  }

  private Integer getContractStartDay() {
    Integer startDay = null;
    while (true) {
      try {
        startDay = Integer.parseInt(ui.getStartDay());
      } catch (Exception e) {
        ui.printInvalidDays();
        continue;
      }
      if (!register.isValidStartDay(startDay)) {
        ui.printInvalidStartDay();
      } else {
        break;
      }
    }
    return startDay;
  }

  private Integer getContractDuration() {
    Integer days = null;
    while (true) {
      try {
        days = Integer.parseInt(ui.getDays());
      } catch (Exception e) {
        ui.printInvalidDays();
        continue;
      }
      if (!register.isValidDuration(days)) {
        ui.printInvalidDuration();
      } else {
        break;
      }
    }
    return days;
  }

  // ----------------- Selecting menu methods

  private Member getMember() {
    return ui.getMember(register.getMembers());
  }

  private Item getItem() {
    return ui.getItem(register.getAllItems());
  }
}