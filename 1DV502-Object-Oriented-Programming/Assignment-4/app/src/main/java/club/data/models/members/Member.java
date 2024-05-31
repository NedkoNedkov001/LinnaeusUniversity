package club.data.models.members;

import club.data.constants.FailMessages;
import club.data.constants.SuccessMessages;
import club.data.exceptions.boat.InvalidBoatDepthException;
import club.data.exceptions.boat.InvalidBoatLengthException;
import club.data.exceptions.boat.InvalidBoatNameException;
import club.data.exceptions.boat.InvalidBoatPowerException;
import club.data.exceptions.boat.InvalidBoatTypeException;
import club.data.models.boats.Boat;
import club.data.models.boats.BoatFactory;
import java.util.ArrayList;

/**
 * Member class.
 */
public class Member {

  private String name;
  private String email;
  private String id;
  private ArrayList<Boat> boats = new ArrayList<Boat>();

  /**
   * Construct a new member.
   *
   * @param newName  name
   * @param newEmail email
   * @param newId    id
   */
  public Member(String newName, String newEmail, String newId) {
    setName(newName);
    setEmail(newEmail);
    setId(newId);
  }

  public String getName() {
    return name;
  }

  /**
   * Sets a new name for member.
   *
   * @param newName new name
   * @param members list of members to check if it's unique
   * @return [FAIL]/[SUCCESS] message
   */
  String setName(String newName) {
    String oldName = name;
    try {
      name = newName;
    } catch (Exception e) {
      return String.format(FailMessages.MEMBER_SET_NAME_FAIL, oldName, newName);
    }
    return String.format(SuccessMessages.MEMBER_NAME_CHANGED, oldName, newName);

  }

  public String getEmail() {
    return email;
  }

  /**
   * Sets a new email for member.
   *
   * @param newEmail new email
   * @param members  list of members to check if it's unique
   * @return [FAIL]/[SUCCESS] message
   */
  String setEmail(String newEmail) {
    String oldEmail = email;
    try {
      email = newEmail;
    } catch (Exception e) {
      return String.format(FailMessages.MEMBER_SET_EMAIL_FAIL, oldEmail, newEmail);
    }
    return String.format(SuccessMessages.MEMBER_EMAIL_CHANGED, oldEmail, newEmail);
  }

  public String getId() {
    return id;
  }

  /**
   * Adds a new boat to member.
   *
   * @return [FAIL]/[SUCCESS] message
   */
  public String addBoat(BoatFactory boatFactory, int boatType, String boatName, int boatLength,
      int boatDepth, int boatPower) {
    try {
      boats.add(boatFactory.createBoat(boatType, boatName, boatLength, boatDepth, boatPower));
    } catch (InvalidBoatNameException e) {
      return String.format(FailMessages.BOAT_INVALID_NAME);
    } catch (InvalidBoatLengthException e) {
      return String.format(FailMessages.BOAT_INVALID_LENGTH);
    } catch (InvalidBoatPowerException e) {
      return String.format(FailMessages.BOAT_INVALID_POWER);
    } catch (InvalidBoatDepthException e) {
      return String.format(FailMessages.BOAT_INVALID_DEPTH);
    } catch (InvalidBoatTypeException e) {
      return String.format(FailMessages.BOAT_INVALID_TYPE);
    }
    return String.format(SuccessMessages.MEMBER_BOAT_ADDED, boatName, getName());
  }

  /**
   * Removes a boat with given name from member.
   *
   * @param boatName boat name to remove
   * @return [FAIL]/[SUCCESS] message
   */
  public String removeBoat(String boatName) {
    for (Boat boat : boats) {
      if (boat.getName().equals(boatName)) {
        try {
          boats.remove(boat);
        } catch (Exception e) {
          return String.format(FailMessages.MEMBER_REMOVE_BOAT_FAIL, boatName, getName());
        }
        return String.format(SuccessMessages.MEMBER_BOAT_REMOVED, boatName, getName());
      }
    }
    return String.format(FailMessages.MEMBER_BOAT_INEXISTENT, getName(), boatName);
  }

  /**
   * Gets a boat with given name.
   *
   * @param name name
   * @return Boat
   */
  public Boat getBoat(String name) {
    for (Boat boat : boats) {
      if (boat.getName().equals(name)) {
        return boat;
      }
    }
    return null;
  }

  public ArrayList<Boat> getBoats() {
    return (ArrayList<Boat>) boats.clone();
  }

  private String setId(String newId) {
    String oldId = id;
    try {
      id = newId;
    } catch (Exception e) {
      return String.format(FailMessages.MEMBER_ID_SET_FAIL, oldId, newId);
    }
    return String.format(SuccessMessages.MEMBER_ID_CHANGED, oldId, newId);
  }

  @Override
  public String toString() {
    StringBuilder memberString = new StringBuilder();
    memberString
        .append("MEMBER:" + getName() + ":" + (getEmail() != null ? getEmail() + ":" : "")
            + getId() + "\n");
    for (Boat boat : boats) {
      memberString.append(boat + "\n");
    }
    return memberString.toString();
  }
}