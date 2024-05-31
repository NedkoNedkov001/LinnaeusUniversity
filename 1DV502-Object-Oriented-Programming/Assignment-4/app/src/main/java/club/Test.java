package club;

import club.data.exceptions.boat.BoatHasNoDepthException;
import club.data.exceptions.boat.BoatHasNoPowerException;
import club.data.exceptions.boat.InvalidBoatDepthException;
import club.data.exceptions.boat.InvalidBoatLengthException;
import club.data.exceptions.boat.InvalidBoatNameException;
import club.data.exceptions.boat.InvalidBoatPowerException;
import club.data.models.boats.Boat;
import club.data.models.boats.BoatFactory;
import club.data.models.members.BoatClub;
import club.data.models.members.Member;
import club.strategies.Context;
import club.strategies.FindMemberStrategyId;

/**
 * Test class.
 */
public class Test {
  /**
   * Main method to test.
   *
   * @param args args
   */
  public static void main(String[] args) {
    BoatClub club = new BoatClub("Club");

    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", null, "a1b2c3");
    member.addBoat(boatFactory, 1, "", 3, 3, 100);

    club.addNewMember("John Doe", null, "1a2b3c");
    System.out.println(member.addBoat(boatFactory, 1, "Boat", 4, 1, 10));
    Boat boat = member.getBoat("Boat");
    System.out.println(boat);

    // BEGIN TESTS //
    try {
      System.out.print(boatFactory.changeName(boat, "NewName"));
    } catch (InvalidBoatNameException e) {
      e.printStackTrace();
    }

    try {
      System.out.print(boatFactory.changeLength(boat, 5));
    } catch (InvalidBoatLengthException e) {
      e.printStackTrace();
    }

    try {
      System.out.print(boatFactory.changePower(boat, 3));
    } catch (InvalidBoatPowerException e) {
      e.printStackTrace();
    } catch (BoatHasNoPowerException e) {
      e.printStackTrace();
    }

    try {
      System.out.print(boatFactory.changeDepth(boat, 3));
    } catch (InvalidBoatDepthException e) {
      e.printStackTrace();
    } catch (BoatHasNoDepthException e) {
      e.printStackTrace();
    }

    System.out.println(boat);

    System.out.println(club);
    member = null;
    System.out.println(member);
    Context context = new Context();
    context.setStrategy(new FindMemberStrategyId());
    member = context.executeStrategy(club, "1a2b3c");
    System.out.println(member);
  }
}
