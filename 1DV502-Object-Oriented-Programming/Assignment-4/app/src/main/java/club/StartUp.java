package club;

import club.data.constants.FailMessages;
import club.data.constants.SuccessMessages;
import club.data.exceptions.boat.BoatHasNoDepthException;
import club.data.exceptions.boat.BoatHasNoPowerException;
import club.data.exceptions.boat.InvalidBoatDepthException;
import club.data.exceptions.boat.InvalidBoatLengthException;
import club.data.exceptions.boat.InvalidBoatNameException;
import club.data.exceptions.boat.InvalidBoatPowerException;
import club.data.models.boats.Boat;
import club.data.models.boats.BoatFactory;
import club.data.models.boats.Motorboat;
import club.data.models.boats.Motorsailer;
import club.data.models.boats.Sailboat;
import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import club.strategies.Context;
import club.strategies.FindMemberStrategyBoatName;
import club.strategies.FindMemberStrategyEmail;
import club.strategies.FindMemberStrategyId;
import club.strategies.FindMemberStrategyName;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class.
 */
public class StartUp {

  private String changeBoatType(Member currMember, Boat currBoat,
      BoatFactory boatFactory, Scanner scanner) {
    int power = 0;
    int depth = 0;
    String boatType = currBoat.getClass().getSimpleName();
    if (boatType.equals("Motorboat")) {
      power = ((Motorboat) currBoat).getPower();
    } else if (boatType.equals("Sailboat")) {
      depth = ((Sailboat) currBoat).getDepth();
    } else if (boatType.equals("Motorsailer")) {
      power = ((Motorsailer) currBoat).getPower();
      depth = ((Motorsailer) currBoat).getDepth();
    }
    System.out.print(boatTypeMenu());
    int userChoice = intParse(scanner.nextLine());
    if (userChoice >= 5) {
      return "";
    }
    if ((userChoice == 2 || userChoice == 3)
        && !boatType.equals("Motorboat")
        && !boatType.equals("Motorsailer")) {
      System.out.print("Enter power: ");
      power = intParse(scanner.nextLine());
    }
    if ((userChoice == 3 || userChoice == 4)
        && !boatType.equals("Motorsailer")
        && !boatType.equals("Sailboat")) {
      System.out.print("Enter depth: ");
      depth = intParse(scanner.nextLine());
    }
    String name = currBoat.getName();
    int length = currBoat.getLength();
    currMember.addBoat(boatFactory, userChoice, name, length, depth, power);
    currMember.removeBoat(name);
    return String.format(SuccessMessages.BOAT_TYPE_CHANGED, name, boatType,
        currMember.getBoat(name).getClass().getSimpleName());
  }

  private String changeBoatDepth(Boat currBoat, BoatFactory boatFactory, Scanner scanner) {
    int newDepth = intParse(changeValueMenu("depth", scanner));
    try {
      return boatFactory.changeDepth(currBoat, newDepth);
    } catch (InvalidBoatDepthException e) {
      return String.format(FailMessages.BOAT_INVALID_DEPTH);
    } catch (BoatHasNoDepthException e) {
      return String.format(FailMessages.BOAT_HAS_NO_DEPTH, currBoat.getClass().getSimpleName());
    }
  }

  private String changeBoatPower(Boat currBoat, BoatFactory boatFactory, Scanner scanner) {
    int newPower = intParse(changeValueMenu("power", scanner));
    try {
      return boatFactory.changePower(currBoat, newPower);
    } catch (InvalidBoatPowerException e) {
      return String.format(FailMessages.BOAT_INVALID_POWER);
    } catch (BoatHasNoPowerException e) {
      return String.format(FailMessages.BOAT_HAS_NO_POWER, currBoat.getClass().getSimpleName());
    }
  }

  private String changeBoatLength(Boat currBoat, BoatFactory boatFactory, Scanner scanner) {
    int newLength = intParse(changeValueMenu("length", scanner));
    try {
      return boatFactory.changeLength(currBoat, newLength);
    } catch (InvalidBoatLengthException e) {
      return String.format(FailMessages.BOAT_INVALID_LENGTH);
    }
  }

  private String changeBoatName(Boat currBoat, BoatFactory boatFactory, Scanner scanner) {
    String newName = changeValueMenu("name", scanner);
    try {
      return boatFactory.changeName(currBoat, newName);
    } catch (InvalidBoatNameException e) {
      return String.format(FailMessages.BOAT_INVALID_NAME, newName);
    }
  }

  private String changeValueMenu(String valueName, Scanner scanner) {
    System.out.println("\n\n=== [Change " + valueName.toLowerCase() + "] ===");
    System.out.print("Enter " + valueName.toLowerCase() + ": ");
    String value = scanner.nextLine();
    return value;
  }

  private String boatMenu(Boat currBoat, String boatType) {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [" + currBoat.toString().replace("BOAT:", "")
        .replace("\n", "") + "] ===\n");
    menu.append(++items + ". Change name\n");
    menu.append(++items + ". Change length\n");
    if (boatType.equals("Motorboat") || boatType.equals("Motorsailer")) {
      menu.append(++items + ". Change power\n");
    }
    if (boatType.equals("Motorsailer") || boatType.equals("Sailboat")) {
      menu.append(++items + ". Change depth\n");
    }
    menu.append(++items + ". Change Type\n");
    menu.append(++items + ". Remove\n");
    menu.append(++items + ". Back\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private String boatTypeMenu() {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [ Choose Boat Type ] ===\n");
    menu.append(++items + ". Canoe\n");
    menu.append(++items + ". MotorBoat\n");
    menu.append(++items + ". MotorSailer\n");
    menu.append(++items + ". SailBoat\n");
    menu.append(++items + ". Back\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private String addNewBoat(Member member, BoatFactory boatFactory, Scanner scanner) {
    System.out.print(boatTypeMenu());
    int boatType;
    boatType = intParse(scanner.nextLine());
    if (boatType == 5) {
      return "";
    }
    System.out.println("\n\n=== [Add new Boat] ===");
    String name = null;
    System.out.print("Enter name: ");
    name = scanner.nextLine().strip();
    int length = 0;
    System.out.print("Enter length: ");
    length = intParse(scanner.nextLine());
    int power = 0;
    int depth = 0;
    if (boatType == 2 || boatType == 3) {
      System.out.print("Enter power: ");
      power = intParse(scanner.nextLine());
    }
    if (boatType == 3 || boatType == 4) {
      System.out.print("Enter depth: ");
      depth = intParse(scanner.nextLine());
    }
    return member.addBoat(boatFactory, boatType, name, length, depth, power);
  }

  private String boatsMenu(Member currMember) {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [" + getMemberInfo(currMember) + "] ===\n");
    menu.append(++items + ". Add boat\n");
    for (Boat boat : currMember.getBoats()) {
      menu.append(++items + ". " + boat.getName() + "\n");
    }
    menu.append(++items + ". Back\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private String removeMember(BoatClub club, Member currMember) {
    return club.removeMember(currMember.getName());
  }

  private String changeMemberEmail(BoatClub club, Member member, Scanner scanner) {
    System.out.println("\n\n=== [Change Email] ===");
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    return club.changeMemberEmail(member.getName(), email);
  }

  private String changeMemberName(BoatClub club, Member member, Scanner scanner) {
    System.out.println("\n\n=== [Change Name] ===");
    System.out.print("Enter name: ");
    String newName = scanner.nextLine();
    return club.changeMemberName(member.getName(), newName);
  }

  private String getMemberInfo(Member member) {
    ArrayList<String> memberInfo = new ArrayList<String>();
    String[] allInfo = member.toString().split(":");
    for (String infoPiece : allInfo) {
      if (infoPiece.endsWith("\nBOAT")) {
        memberInfo.add(infoPiece.replace("\nBOAT", ""));
        break;
      } else if (infoPiece.equals("MEMBER")) {
        continue;
      }
      memberInfo.add(infoPiece);
    }
    return String.join(":", memberInfo).replace("\n", "");
  }

  private String findMemberMenu(Context context, BoatClub club, Scanner scanner) {
    System.out.print(findStrategyMenu());
    int userChoice = intParse(scanner.nextLine());
    if (userChoice == 1) {
      context.setStrategy(new FindMemberStrategyName());
    } else if (userChoice == 2) {
      context.setStrategy(new FindMemberStrategyEmail());
    } else if (userChoice == 3) {
      context.setStrategy(new FindMemberStrategyId());
    } else if (userChoice == 4) {
      context.setStrategy(new FindMemberStrategyBoatName());
    } else {
      return "";
    }
    System.out.print("Condition: ");
    String condition = scanner.nextLine();
    String result = null;
    try {
      result = context.executeStrategy(club, condition).toString();
    } catch (Exception e) {
      return String.format(FailMessages.MEMBER_INEXISTENT);
    }
    return result;
  }

  private String findStrategyMenu() {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [Search by] ===\n");
    menu.append(++items + ". Member name\n");
    menu.append(++items + ". Member email\n");
    menu.append(++items + ". Member id\n");
    menu.append(++items + ". Boat name\n");
    menu.append(++items + ". Back\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private String memberMenu(Member currMember) {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [" + getMemberInfo(currMember) + "] ===\n");
    menu.append(++items + ". Change name\n");
    menu.append(++items + ". Change email\n");
    menu.append(++items + ". Boats\n");
    menu.append(++items + ". Remove\n");
    menu.append(++items + ". Back\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private String addNewMember(BoatClub club, Scanner scanner) {
    System.out.println("\n\n=== [Add new Member] ===");
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email (or leave empty): ");
    String email = scanner.nextLine();
    return club.addNewMember(name, !email.equals("") ? email : null);
  }

  private String clubMenu(BoatClub club) {
    StringBuilder menu = new StringBuilder();
    int items = 0;
    menu.append("\n\n=== [" + club.getName() + "] ===\n");
    menu.append(++items + ". Add member\n");
    for (Member member : club.getMembers()) {
      menu.append(++items + ". " + member.getName() + "\n");
    }
    menu.append(++items + ". Find member\n");
    menu.append(++items + ". Print\n");
    menu.append(++items + ". Save and Exit\n");
    menu.append("User choice: ");
    return menu.toString();
  }

  private int intParse(String string) {
    try {
      return Integer.parseInt(string);
    } catch (Exception e) {
      return 0;
    }
  }

  private String printInfo(BoatClub club) {
    StringBuilder info = new StringBuilder();
    info.append("\n\n=== [ INFO ] ===\n");
    info.append(club);
    return info.toString();
  }

  private void promptKeyPress(Scanner scanner) {
    System.out.println("\nPress [ENTER] to continue");
    try {
      scanner.nextLine();
    } catch (Exception e) {
      throw e;
    }
  }

  private String saveChanges(BoatClub club) {
    try {
      FileWriter fstream = new FileWriter("app/src/main/java/club/registry.data",
          Charset.forName("UTF-8"));
      BufferedWriter out = new BufferedWriter(fstream);
      out.write(club.toString());
      out.close();
    } catch (Exception e) {
      System.out.println(String.format(FailMessages.SAVE_CHANGES_FAIL));
    }
    return club.toString();
  }

  private BoatClub importData(BoatFactory boatFactory) {
    BoatClub club = new BoatClub("BoatLovers");
    try {
      File registry = new File("app/src/main/java/club/registry.data");
      Scanner lineReader = new Scanner(registry, Charset.forName("UTF-8"));
      String currMemberName = null;
      Member currMember = null;
      while (lineReader.hasNextLine()) {
        String[] currLine = lineReader.nextLine().split(":");
        if (currLine[0].equals("MEMBER")) {
          currMemberName = currLine[1];
          if (currLine.length == 4) {
            System.out.print(club.addNewMember(currLine[1], currLine[2], currLine[3]));
          } else if (currLine.length == 3) {
            System.out.print(club.addNewMember(currLine[1], null, currLine[2]));
          } else {
            System.out.print(String.format(FailMessages.IMPORT_MEMBER_FAIL, currLine[1]));
          }
        } else if (currLine[0].equals("BOAT")) {
          currMember = club.getMember(currMemberName);
          if (currLine[2].equals("Canoe")) {
            System.out.print(currMember.addBoat(
                boatFactory, 1, currLine[1], intParse(currLine[3]), 0, 0));
          } else if (currLine[2].equals("Motorboat")) {
            System.out.print(currMember
                .addBoat(boatFactory, 2, currLine[1],
                    intParse(currLine[3]), 0, intParse(currLine[4])));
          } else if (currLine[2].equals("Motorsailer")) {
            System.out.print(currMember.addBoat(boatFactory, 3, currLine[1],
                intParse(currLine[3]), intParse(currLine[4]), intParse(currLine[5])));
          } else if (currLine[2].equals("Sailboat")) {
            System.out.print(currMember.addBoat(boatFactory, 4, currLine[1],
                intParse(currLine[3]), intParse(currLine[4]), 0));
          } else {
            System.out.print(String.format(FailMessages.IMPORT_BOAT_FAIL, currLine[1]));
          }
        }
      }
      lineReader.close();
    } catch (FileNotFoundException e) {
      System.out.println(String.format(FailMessages.IMPORT_RECORDS_FAIL));
    } catch (IOException e) {
      System.out.println(String.format(FailMessages.IMPORT_RECORDS_FAIL));
    }
    return club;
  }

  /**
   * Main functionality.
   *
   * @param args arguments.
   */
  public static void main(String[] args) {
    StartUp app = new StartUp();
    Context context = new Context();
    Scanner scanner = new Scanner(System.in, Charset.forName("UTF-8"));
    BoatFactory boatFactory = new BoatFactory();
    BoatClub club = app.importData(boatFactory);
    Member currMember = null;
    Boat currBoat = null;
    String boatType = null;
    int currMenuNum = 1;
    int userChoice = 0;
    while (currMenuNum > 0) {
      if (currMenuNum == 1) {
        System.out.print(app.clubMenu(club));
        userChoice = app.intParse(scanner.nextLine());
        if (userChoice == 1) {
          System.out.println(app.addNewMember(club, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice == club.getMembers().size() + 4) {
          currMenuNum--;
          break;
        } else if (userChoice == club.getMembers().size() + 3) {
          System.out.println(app.printInfo(club));
          app.promptKeyPress(scanner);
        } else if (userChoice == club.getMembers().size() + 2) {
          System.out.print(app.findMemberMenu(context, club, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice > 1 && userChoice < club.getMembers().size() + 2) {
          currMenuNum++;
          currMember = club.getMembers().get(userChoice - 2);
          continue;
        }
      } else if (currMenuNum == 2) {
        System.out.print(app.memberMenu(currMember));
        userChoice = app.intParse(scanner.nextLine());
        if (userChoice == 1) {
          System.out.println(app.changeMemberName(club, currMember, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice == 2) {
          System.out.println(app.changeMemberEmail(club, currMember, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice == 3) {
          currMenuNum++;
          continue;
        } else if (userChoice == 4) {
          System.out.println(app.removeMember(club, currMember));
          app.promptKeyPress(scanner);
          currMenuNum--;
          continue;
        } else if (userChoice == 5) {
          currMenuNum--;
          continue;
        }
      } else if (currMenuNum == 3) {
        System.out.print(app.boatsMenu(currMember));
        userChoice = app.intParse(scanner.nextLine());
        if (userChoice == 1) {
          System.out.println(app.addNewBoat(currMember, boatFactory, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice == currMember.getBoats().size() + 2) {
          currMenuNum--;
          continue;
        } else if (userChoice > 1
            && userChoice < currMember.getBoats().size() + 2) {
          currBoat = currMember.getBoats().get(userChoice - 2);
          boatType = currBoat.getClass().getSimpleName();
          currMenuNum++;
          continue;
        }
      } else if (currMenuNum == 4) {
        System.out.print(app.boatMenu(currBoat, boatType));
        userChoice = app.intParse(scanner.nextLine());
        if (userChoice == 1) {
          System.out.println(app.changeBoatName(currBoat, boatFactory, scanner));
          app.promptKeyPress(scanner);
        } else if (userChoice == 2) {
          System.out.println(app.changeBoatLength(currBoat, boatFactory, scanner));
          app.promptKeyPress(scanner);
        }
        String operation = "None";
        if (boatType.equals("Canoe")) {
          if (userChoice == 3) {
            operation = "Type";
          } else if (userChoice == 4) {
            operation = "Remove";
          } else if (userChoice == 5) {
            operation = "Back";
          }
        } else if (boatType.equals("Motorboat")) {
          if (userChoice == 3) {
            System.out.println(app.changeBoatPower(currBoat, boatFactory, scanner));
            app.promptKeyPress(scanner);
          } else if (userChoice == 4) {
            operation = "Type";
          } else if (userChoice == 5) {
            operation = "Remove";
          } else if (userChoice == 6) {
            operation = "Back";
          }
        } else if (boatType.equals("Motorsailer")) {
          if (userChoice == 3) {
            System.out.println(app.changeBoatPower(currBoat, boatFactory, scanner));
            app.promptKeyPress(scanner);
          } else if (userChoice == 4) {
            System.out.println(app.changeBoatDepth(currBoat, boatFactory, scanner));
            app.promptKeyPress(scanner);
          } else if (userChoice == 5) {
            operation = "Type";
          } else if (userChoice == 6) {
            operation = "Remove";
          } else if (userChoice == 7) {
            operation = "Back";
          }
        } else if (boatType.equals("Sailboat")) {
          if (userChoice == 3) {
            System.out.println(app.changeBoatDepth(currBoat, boatFactory, scanner));
          } else if (userChoice == 4) {
            operation = "Type";
          } else if (userChoice == 5) {
            operation = "Remove";
          } else if (userChoice == 6) {
            operation = "Back";
          }
        }
        if (operation.equals("Type")) {
          System.out.println(app.changeBoatType(currMember, currBoat, boatFactory, scanner));
          currBoat = currMember.getBoats().get(currMember.getBoats().size() - 1);
          boatType = currBoat.getClass().getSimpleName();
          app.promptKeyPress(scanner);
        } else if (operation.equals("Remove")) {
          System.out.println(currMember.removeBoat(currBoat.getName()));
          app.promptKeyPress(scanner);
          currMenuNum--;
          continue;
        } else if (operation.equals("Back")) {
          currMenuNum--;
          continue;
        }
      }
    }
    System.out.println(app.saveChanges(club));
    
  }
}
