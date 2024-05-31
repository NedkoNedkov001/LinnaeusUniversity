package club.data.models.boats;

import club.data.constants.SuccessMessages;
import club.data.constants.Variables;
import club.data.exceptions.boat.BoatHasNoDepthException;
import club.data.exceptions.boat.BoatHasNoPowerException;
import club.data.exceptions.boat.InvalidBoatDepthException;
import club.data.exceptions.boat.InvalidBoatLengthException;
import club.data.exceptions.boat.InvalidBoatNameException;
import club.data.exceptions.boat.InvalidBoatPowerException;
import club.data.exceptions.boat.InvalidBoatTypeException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Boat factory.
 */
public class BoatFactory {
  /**
   * Choose what boat to create.
   *
   * @param boatType   type
   * @param boatName   name
   * @param boatLength length
   * @param boatDepth  depth
   * @param boatPower  power
   * @return boat
   * @throws InvalidBoatTypeException   invalid boat type
   * @throws InvalidBoatNameException   invalid boat name
   * @throws InvalidBoatLengthException invalid boat length
   * @throws InvalidBoatPowerException  invalid boat power
   * @throws InvalidBoatDepthException  invalid boat depth
   */
  public Boat createBoat(int boatType, String boatName, int boatLength, int boatDepth,
      int boatPower) throws InvalidBoatNameException, InvalidBoatLengthException,
      InvalidBoatPowerException, InvalidBoatDepthException, InvalidBoatTypeException {
    if (!isValidType(boatType)) {
      throw new InvalidBoatTypeException();
    }
    if (!isValidName(boatName)) {
      throw new InvalidBoatNameException();
    } else if (!isValidLength(boatLength)) {
      throw new InvalidBoatLengthException();
    }

    if (boatType == 1) {
      return new Canoe(boatName, boatLength);
    } else if (boatType == 2) {
      if (!isValidPower(boatPower)) {
        throw new InvalidBoatPowerException();
      }
      return new Motorboat(boatName, boatLength, boatPower);
    } else if (boatType == 3) {
      if (!isValidDepth(boatDepth)) {
        throw new InvalidBoatDepthException();
      } else if (!isValidPower(boatPower)) {
        throw new InvalidBoatPowerException();
      }
      return new Motorsailer(boatName, boatLength, boatDepth, boatPower);
    } else if (boatType == 4) {
      if (!isValidDepth(boatDepth)) {
        throw new InvalidBoatDepthException();
      }
      return new Sailboat(boatName, boatLength, boatDepth);
    }

    return null;
  }

  /**
   * Change boat name.
   *
   * @param boat    boat
   * @param newName new name
   * @throws InvalidBoatNameException invalid name
   */
  public String changeName(Boat boat, String newName) throws InvalidBoatNameException {
    if (!isValidName(newName)) {
      throw new InvalidBoatNameException();
    }
    String oldName = boat.getName();
    boat.setName(newName);
    return String.format(SuccessMessages.BOAT_NAME_CHANGED, oldName, newName);
  }

  /**
   * Change boat length.
   *
   * @param boat      boat
   * @param newLength length
   * @throws InvalidBoatLengthException invalid boat length
   */
  public String changeLength(Boat boat, int newLength) throws InvalidBoatLengthException {
    if (!isValidLength(newLength)) {
      throw new InvalidBoatLengthException();
    }
    int oldLength = boat.getLength();
    boat.setLength(newLength);
    return String.format(SuccessMessages.BOAT_LENGTH_CHANGED, oldLength, newLength);
  }

  /**
   * Change boat power.
   *
   * @param boat     boat
   * @param newPower power
   * @throws InvalidBoatPowerException invalid boat power
   * @throws BoatHasNoPowerException   boat has no power
   */
  public String changePower(Boat boat, int newPower) throws InvalidBoatPowerException,
      BoatHasNoPowerException {
    if (!boat.getClass().getSimpleName().equals("Motorboat")
        && !boat.getClass().getSimpleName().equals("Motorsailer")) {
      throw new BoatHasNoPowerException(boat.getClass().getSimpleName());
    }

    if (!isValidPower(newPower)) {
      throw new InvalidBoatPowerException();
    }

    if (boat.getClass().getSimpleName().equals("Motorboat")) {
      Motorboat newBoat = (Motorboat) boat;
      int oldPower = newBoat.getPower();
      newBoat.setPower(newPower);
      return String.format(SuccessMessages.BOAT_POWER_CHANGED, oldPower, newPower);
    } else {
      Motorsailer newBoat = (Motorsailer) boat;
      int oldPower = newBoat.getPower();
      newBoat.setPower(newPower);
      return String.format(SuccessMessages.BOAT_POWER_CHANGED, oldPower, newPower);
    }
  }

  /**
   * Change boat depth.
   *
   * @param boat     boat
   * @param newDepth depth
   * @throws InvalidBoatDepthException invalid boat depth
   * @throws BoatHasNoDepthException   boat has no depth
   */
  public String changeDepth(Boat boat, int newDepth) throws InvalidBoatDepthException,
      BoatHasNoDepthException {
    if (!boat.getClass().getSimpleName().equals("Motorsailer")
        && !boat.getClass().getSimpleName().equals("Sailboat")) {
      throw new BoatHasNoDepthException(boat.getClass().getSimpleName());
    }

    if (!isValidDepth(newDepth)) {
      throw new InvalidBoatDepthException();
    }
    if (boat.getClass().getSimpleName().equals("Motorsailer")) {
      Motorsailer newBoat = (Motorsailer) boat;
      int oldDepth = newBoat.getDepth();
      newBoat.setDepth(newDepth);
      return String.format(SuccessMessages.BOAT_DEPTH_CHANGED, oldDepth, newDepth);
    } else {
      Sailboat newBoat = (Sailboat) boat;
      int oldDepth = newBoat.getDepth();
      newBoat.setDepth(newDepth);
      return String.format(SuccessMessages.BOAT_DEPTH_CHANGED, oldDepth, newDepth);
    }
  }

  private boolean isValidType(int boatType) {
    if (boatType == 1
        || boatType == 2
        || boatType == 3
        || boatType == 4) {
      return true;
    }
    return false;
  }

  private boolean isValidDepth(int boatDepth) {
    return boatDepth >= Variables.MIN_BOAT_DEPTH;
  }

  private boolean isValidPower(int boatPower) {
    return boatPower >= Variables.MIN_BOAT_POWER;
  }

  private boolean isValidName(String boatName) {
    Pattern pattern = Pattern.compile(Variables.BOAT_NAME_REGEX,
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(boatName);

    return boatName.length() >= Variables.MIN_BOAT_NAME_LENGTH && matcher.find();
  }

  private boolean isValidLength(int boatLength) {
    return boatLength >= Variables.MIN_BOAT_LENGTH;
  }
}
