package club;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import club.data.constants.FailMessages;
import club.data.constants.SuccessMessages;
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
import org.junit.jupiter.api.Test;

class Tests {
  @Test
  void clubCreateS() {
    String validName = "Club";
    BoatClub club = new BoatClub(validName);
    assertEquals(0, club.getMembers().size());
    assertTrue(club.getName() != null);
    assertTrue(club.getMembers() != null);
  }

  @Test
  void clubGetName() {
    BoatClub club = new BoatClub("Club");
    assertEquals("Club", club.getName());
  }

  @Test
  void clubChangeName() {
    BoatClub club = new BoatClub("Club");
    String oldName = club.getName();
    String invalidName = "";
    String validName = "Valid";
    assertEquals("Club", club.getName());

    assertEquals(String.format(FailMessages.CLUB_INVALID_NAME, invalidName),
        club.setName(invalidName)); // Set invalid name
    assertEquals(String.format(SuccessMessages.CLUB_RENAMED, oldName, validName),
        club.setName(validName)); // Set valid name

    assertEquals("Valid", club.getName());
  }

  @Test
  void clubAddMemberValid() {
    BoatClub club = new BoatClub("Club");

    assertEquals(String.format(SuccessMessages.MEMBER_ADDED, "John Doe"),
        club.addNewMember("John Doe", "john.doe@gmail.com"));
    assertEquals(0, club.getMember("John Doe").getBoats().size());

    assertEquals(String.format(SuccessMessages.MEMBER_ADDED, "Joe Doe"),
        club.addNewMember("Joe Doe", null));

    assertEquals(String.format(SuccessMessages.MEMBER_ADDED, "Jane Doe"),
        club.addNewMember("Jane Doe", "jane@gmail.com", "1a2b3c"));

    assertEquals(String.format(SuccessMessages.MEMBER_ADDED, "Jo Doe"),
        club.addNewMember("Jo Doe", "jo@gmail.com", "a1b2c3"));

    assertEquals(4, club.getMembers().size());
  }

  @Test
  void clubAddMemberInvalidName() {
    BoatClub club = new BoatClub("Club");

    assertEquals(String.format(FailMessages.MEMBER_INVALID_NAME, ""),
        club.addNewMember("", "john@gmail.com"));

    assertEquals(String.format(FailMessages.MEMBER_INVALID_NAME, ""),
        club.addNewMember("", "john@gmail.com", "a1b2c3"));

    club.addNewMember("John", "john@gmail.com");
    club.addNewMember("John", "john@gmail.com", "a1b2c3");

    club.addNewMember("John D", "john@gmail.com");
    club.addNewMember("John D", "john@gmail.com", "a1b2c3");

    club.addNewMember("John doe", "john@gmail.com");
    club.addNewMember("John doe", "john@gmail.com", "a1b2c3");

    club.addNewMember("john doe", "john@gmail.com");
    club.addNewMember("john doe", "john@gmail.com", "a1b2c3");

    assertEquals(0, club.getMembers().size());

  }

  @Test
  void clubAddMemberInvalidEmail() {
    BoatClub club = new BoatClub("Club");
    assertEquals(String.format(FailMessages.MEMBER_INVALID_EMAIL, "j"),
        club.addNewMember("John Doe", "j"));
    assertEquals(String.format(FailMessages.MEMBER_INVALID_EMAIL, "j"),
        club.addNewMember("John Doe", "j", "a1b2c3"));

    club.addNewMember("John Doe", "john");
    club.addNewMember("John Doe", "john", "a1b2c3");
    club.addNewMember("John Doe", "john@");
    club.addNewMember("John Doe", "john@", "a1b2c3");
    club.addNewMember("John Doe", "john@gmail");
    club.addNewMember("John Doe", "john@gmail", "a1b2c3");
    club.addNewMember("John Doe", "john@gmail.");
    club.addNewMember("John Doe", "john@gmail.");
    club.addNewMember("John Doe", "jo:hn@gmail.com");
    club.addNewMember("John Doe", "jo:hn@gmail.com", "a1b2c3");

    assertEquals(0, club.getMembers().size());
  }

  @Test
  void clubAddMemberInvalidId() {
    BoatClub club = new BoatClub("Club");

    assertEquals(String.format(FailMessages.MEMBER_INVALID_ID, ""),
        club.addNewMember("John Doe", "john@gmail.com", ""));

    assertEquals(String.format(FailMessages.MEMBER_INVALID_ID, "AAAAA"),
        club.addNewMember("John Doe", "john@gmail.com", "AAAAA"));

    assertEquals(String.format(FailMessages.MEMBER_INVALID_ID, "!@#$%^"),
        club.addNewMember("John Doe", "john@gmail.com", "!@#$%^"));

    assertEquals(0, club.getMembers().size());
  }

  @Test
  void clubChangeMemberName() {
    BoatClub club = initializeClub();
    Member member = club.getMember("Member One");
    assertEquals(String.format(SuccessMessages.MEMBER_NAME_CHANGED, "Member One", "Dummy Member"),
        club.changeMemberName("Member One", "Dummy Member"));
    assertEquals("Dummy Member", member.getName());
  }

  @Test
  void clubChangeMemberNameInvalid() {
    BoatClub club = initializeClub();
    Member member = club.getMember("Member One");
    assertEquals(String.format(FailMessages.MEMBER_INVALID_NAME, "member one"),
        club.changeMemberName("Member One", "member one"));
    assertEquals("Member One", member.getName());
  }

  @Test
  void clubChangeMemberEmail() {
    BoatClub club = initializeClub();
    Member member = club.getMember("Member One");
    assertEquals(String.format(SuccessMessages.MEMBER_EMAIL_CHANGED, null, "new@gmail.com"),
        club.changeMemberEmail(member.getName(), "new@gmail.com"));
    assertEquals("new@gmail.com", member.getEmail());
  }

  @Test
  void clubChangeMemberEmailInvalid() {
    BoatClub club = initializeClub();
    Member member = club.getMember("Member One");
    assertEquals(String.format(FailMessages.MEMBER_INVALID_EMAIL, "invalid@email"),
        club.changeMemberEmail("Member One", "invalid@email"));
    assertEquals(null, member.getEmail());

  }

  @Test
  void clubRemoveMemberValidName() {
    BoatClub club = initializeClub();
    assertEquals(String.format(SuccessMessages.CLUB_MEMBER_REMOVED, "Member One"),
        club.removeMember("Member One"));
    assertEquals(2, club.getMembers().size());
  }

  @Test
  void clubRemoveMemberInexistentName() {
    BoatClub club = initializeClub();
    assertEquals(String.format(FailMessages.MEMBER_INEXISTENT, "Inexistent Member"),
        club.removeMember("Inexistent Member"));
    assertEquals(3, club.getMembers().size());
  }

  @Test
  void clubRemoveMemberValidMember() {
    BoatClub club = initializeClub();
    Member member = club.getMember("Member One");
    assertEquals(String.format(SuccessMessages.CLUB_MEMBER_REMOVED, "Member One"),
        club.removeMember(member.getName()));
    assertEquals(2, club.getMembers().size());
  }

  @Test
  void clubRemoveInexistentMember() {
    BoatClub club = initializeClub();
    Member member = new Member("Dummy One", "dummy@gmail.com", "a1b2c3"); // valid but not in club

    assertEquals(String.format(FailMessages.MEMBER_INEXISTENT, member.getName()),
        club.removeMember(member.getName()));
    assertEquals(3, club.getMembers().size());
  }

  @Test
  void clubGetMembers() {
    BoatClub club = new BoatClub("Club");
    club.addNewMember("Dummy One", "dummy@gmail.com");
    assertEquals(1, club.getMembers().size());

    club.addNewMember("Dummy Two", "dummytwo@gmail.com");
    assertEquals(2, club.getMembers().size());

    assertEquals("Dummy One", club.getMembers().get(0).getName());
    assertEquals("Dummy Two", club.getMembers().get(1).getName());
  }

  @Test
  void memberGetName() {
    Member member = initializeMember();
    assertEquals("Member One", member.getName());
  }

  @Test
  void memberGetEmail() {
    Member member = initializeMember();
    assertEquals(null, member.getEmail());
  }

  @Test
  void memberGetId() {
    Member member = initializeMember();
    assertEquals("a1b2c3", member.getId());
  }

  @Test
  void memberAddBoatValid() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", null, "a1b2c3");
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_ADDED, "DummyCanoe",
        member.getName()), member.addBoat(boatFactory, 1, "DummyCanoe", 4, 0, 0));
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_ADDED, "DummyMotorboat",
        member.getName()), member.addBoat(boatFactory, 2, "DummyMotorboat", 4, 0, 50));
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_ADDED, "DummyMotorsailer",
        member.getName()),
        member.addBoat(boatFactory, 3,
            "DummyMotorsailer", 4, 1, 50));
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_ADDED, "DummySailboat",
        member.getName()), member.addBoat(boatFactory, 4, "DummySailboat", 4, 1, 0));
    assertEquals(4, member.getBoats().size());
  }

  @Test
  void memberAddBoatInvalidName() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", null, "a1b2c3");
    assertEquals(String.format(FailMessages.BOAT_INVALID_NAME),
        member.addBoat(boatFactory, 1, "", 3, 3, 100));
    assertEquals(String.format(FailMessages.BOAT_INVALID_NAME),
        member.addBoat(boatFactory, 1, "Hi", 3, 3, 100));
    assertEquals(String.format(FailMessages.BOAT_INVALID_NAME),
        member.addBoat(boatFactory, 1, "H3ll0", 3, 3, 100));
    assertEquals(0, member.getBoats().size());
  }

  @Test
  void memberAddBoatInvalidLength() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", "dummy@gmail.com", "a1b2c3");
    assertEquals(String.format(FailMessages.BOAT_INVALID_LENGTH),
        member.addBoat(boatFactory, 1, "Valid", 1, 0, 0));
    assertEquals(0, member.getBoats().size());
  }

  @Test
  void memberAddBoatInvalidPower() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", "dummy@gmail.com", "a1b2c3");
    assertEquals(String.format(FailMessages.BOAT_INVALID_POWER),
        member.addBoat(boatFactory, 3, "Motorsailer", 3, 3, 9));
    assertEquals(String.format(FailMessages.BOAT_INVALID_POWER),
        member.addBoat(boatFactory, 3, "Motorsailer", 3, 3, 0));
    assertEquals(0, member.getBoats().size());
  }

  @Test
  void memberAddBoatInvalidDepth() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = new Member("Dummy Member", "dummy@gmail.com", "a1b2c3");
    assertEquals(String.format(FailMessages.BOAT_INVALID_DEPTH),
        member.addBoat(boatFactory, 4, "Sailboat", 3, 0, 0));
    assertEquals(String.format(FailMessages.BOAT_INVALID_DEPTH),
        member.addBoat(boatFactory, 3, "Motorsailer", 3, 0, 50));
    assertEquals(0, member.getBoats().size());
  }

  @Test
  void memberGetBoat() {
    BoatFactory boatFactory = new BoatFactory();
    Member member = initializeMember();
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_ADDED, "DummyBoat", member.getName()),
        member.addBoat(boatFactory, 2, "DummyBoat", 3, 0, 50));
    assertEquals("DummyBoat", member.getBoat("DummyBoat").getName());
  }

  @Test
  void memberGetBoatInvalid() {
    Member member = initializeMember();
    assertEquals(null, member.getBoat("Invalid"));
  }

  @Test
  void memberGetBoats() {
    Member member = initializeMember();
    assertEquals(4, member.getBoats().size());
  }

  @Test
  void memberRemoveBoat() {
    Member member = initializeMember();
    assertEquals(4, member.getBoats().size());
    assertEquals(String.format(SuccessMessages.MEMBER_BOAT_REMOVED, "Canoe", "Member One"),
        member.removeBoat("Canoe"));
    assertEquals(3, member.getBoats().size());
  }

  @Test
  void boatGetName() {
    Member member = initializeMember();
    Boat boat = member.getBoat("Canoe");
    assertEquals("Canoe", boat.getName());
  }

  @Test
  void boatGetLength() {
    Member member = initializeMember();
    Boat boat = member.getBoat("Canoe");
    assertEquals(3, boat.getLength());
  }

  @Test
  void boatGetDepth() {
    Member member = initializeMember();
    Sailboat sailBoat = (Sailboat) member.getBoat("Sailboat");
    Motorsailer motorSailer = (Motorsailer) member.getBoat("Motorsailer");
    assertEquals(3, sailBoat.getDepth());
    assertEquals(3, motorSailer.getDepth());
  }

  @Test
  void boatGetPower() {
    Member member = initializeMember();
    Motorboat motorBoat = (Motorboat) member.getBoat("Motorboat");
    Motorsailer motorSailer = (Motorsailer) member.getBoat("Motorsailer");
    assertEquals(50, motorBoat.getPower());
    assertEquals(50, motorSailer.getPower());
  }

  @Test 
  void findMemberWithId() {
    BoatClub club = initializeClub();
    Context context = new Context();
    context.setStrategy(new FindMemberStrategyId());
    assertEquals("Member One", context.executeStrategy(club, "a1b2c3").getName());
  }

  @Test
  void findMemberWithEmail() {
    BoatClub club = initializeClub();
    Context context = new Context();
    context.setStrategy(new FindMemberStrategyEmail());
    assertEquals("Member Two", context.executeStrategy(club, "dummy@gmail.com").getName());
  }

  @Test
  void findMemberWithBoat() {
    BoatClub club = initializeClub();
    Context context = new Context();
    context.setStrategy(new FindMemberStrategyBoatName());
    assertEquals("Member One", context.executeStrategy(club, "Canoe").getName());
  }

  Member initializeMember() {
    BoatClub club = initializeClub();
    return club.getMember("Member One");
  }

  BoatClub initializeClub() {
    BoatClub club = new BoatClub("Club");
    club.addNewMember("Member One", null, "a1b2c3");
    club.addNewMember("Member Two", "dummy@gmail.com");
    club.addNewMember("Member Three", null);
    BoatFactory boatFactory = new BoatFactory();
    club.getMember("Member One").addBoat(boatFactory, 1, "Canoe", 3, 0, 0);
    club.getMember("Member One").addBoat(boatFactory, 2, "Motorboat", 3, 0, 50);
    club.getMember("Member One").addBoat(boatFactory, 3, "Motorsailer", 3, 3, 50);
    club.getMember("Member One").addBoat(boatFactory, 4, "Sailboat", 3, 3, 0);
    return club;
  }

}