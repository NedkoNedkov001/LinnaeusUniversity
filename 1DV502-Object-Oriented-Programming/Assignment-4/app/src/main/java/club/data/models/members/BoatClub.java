package club.data.models.members;

import club.data.constants.FailMessages;
import club.data.constants.SuccessMessages;
import club.data.constants.Variables;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BoatClub class. This is where all members are stored.
 */
public class BoatClub {
  private String name;
  private ArrayList<Member> members;

  /**
   * Constructs a new boat club.
   *
   * @param newName name
   */
  public BoatClub(String newName) {
    String nameMessage = setName(newName);
    try {
      if (nameMessage.equals(String.format(FailMessages.CLUB_INVALID_NAME, newName))) {
        throw new IllegalArgumentException();
      }
      members = new ArrayList<Member>();
    } catch (Exception e) {
      System.out.print(String.format(FailMessages.CLUB_CREATE_FAIL, newName));
    }
  }

  public String getName() {
    return name;
  }

  /**
   * Rename the boat club.
   *
   * @param newName name
   * @return [FAIL]/[SUCCESS] message
   */
  public String setName(String newName) {
    String oldName = name;
    try {
      if (newName.length() < Variables.MIN_CLUB_NAME_LENGTH) {
        throw new IllegalArgumentException();
      }
      name = newName;
    } catch (IllegalArgumentException ae) {
      return String.format(FailMessages.CLUB_INVALID_NAME, newName);
    } catch (Exception e) {
      return String.format(FailMessages.CLUB_RENAME_FAIL, oldName, newName);
    }
    return String.format(SuccessMessages.CLUB_RENAMED, oldName, newName);
  }

  public String addNewMember(String name, String email) {
    return tryAddMember(name, email, generateId());
  }

  public String addNewMember(String name, String email, String id) {
    return tryAddMember(name, email, id);
  }

  /**
   * Removes a member from club.
   *
   * @param memberName name
   * @return [FAIL]/[SUCCESS] message
   */
  public String removeMember(String memberName) {
    return tryRemoveMember(memberName);
  }

  /**
   * Change member name.
   *
   * @param oldName name
   * @param newName name
   * @return Fail/Success message
   */
  public String changeMemberName(String oldName, String newName) {
    Member member = getMember(oldName);
    String nameMessage = validateName(newName);
    if (nameMessage != null) {
      return nameMessage;
    }
    return member.setName(newName);
  }

  /**
   * Change member email.
   *
   * @param memberName name
   * @param newEmail email
   * @return Fail/Success message
   */
  public String changeMemberEmail(String memberName, String newEmail) {
    Member member = getMember(memberName);
    String emailMessage = validateEmail(newEmail);
    if (emailMessage != null) {
      return emailMessage;
    }
    return member.setEmail(newEmail);
  }

  /**
   * Gets a member with given name.
   *
   * @param name name
   * @return member.
   */
  public Member getMember(String name) {
    for (Member member : members) {
      if (member.getName().equals(name)) {
        return member;
      }
    }
    return null;
  }

  public ArrayList<Member> getMembers() {
    return (ArrayList<Member>) members.clone();
  }

  private String validateName(String name) {
    if (!isName(name)) {
      return String.format(FailMessages.MEMBER_INVALID_NAME, name);
    }
    if (!isUniqueName(name)) {
      return String.format(FailMessages.MEMBER_NAME_TAKEN, name);
    }
    return null;
  }

  private String validateEmail(String email) {
    if (email != null) {
      if (!isEmail(email)) {
        return String.format(FailMessages.MEMBER_INVALID_EMAIL, email);
      }
      if (!isUniqueEmail(email)) {
        return String.format(FailMessages.MEMBER_EMAIL_TAKEN, email);
      }
    }
    return null;
  }

  private String validateId(String id) {
    if (!isId(id)) {
      return String.format(FailMessages.MEMBER_INVALID_ID, id);
    }
    if (!isUniqueId(id)) {
      return String.format(FailMessages.MEMBER_ID_TAKEN, id);
    }
    return null;
  }

  private boolean isName(String name) {
    if (name == null) {
      return false;
    }
    Pattern pattern = Pattern.compile(Variables.MEMBER_NAME_REGEX);
    Matcher matcher = pattern.matcher(name);
    return (matcher.find());
  }

  private boolean isUniqueName(String name) {
    for (Member member : members) {
      if (member.getName().equals(name)) {
        return false;
      }
    }
    return true;
  }

  private boolean isEmail(String email) {
    Pattern pattern = Pattern.compile(Variables.MEMBER_EMAIL_REGEX,
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return (matcher.find() || email == null);
  }

  private boolean isUniqueEmail(String email) {
    for (Member member : members) {
      if (member.getEmail() == null) {
        continue;
      }
      if (member.getEmail().equals(email)) {
        return false;
      }
    }
    return true;
  }

  private boolean isId(String id) {

    Pattern pattern = Pattern.compile(Variables.MEMBER_ID_REGEX,
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(id);

    return id.length() == Variables.MEMBER_ID_LENGTH && matcher.find();
  }

  private boolean isUniqueId(String id) {
    for (Member member : members) {
      if (member.getId().equals(id)) {
        return false;
      }
    }
    return true;
  }

  private String tryAddMember(String newName, String newEail, String newId) {
    String nameMessage = validateName(newName);
    String emailMessage = validateEmail(newEail);
    String idMessage = validateId(newId);
    if (nameMessage != null) {
      return nameMessage;
    } else if (emailMessage != null) {
      return emailMessage;
    } else if (idMessage != null) {
      return idMessage;
    }
    try {
      members.add(new Member(newName, newEail, newId));
    } catch (Exception e) {
      return String.format(FailMessages.MEMBER_ADD_FAIL, newName);
    }
    return String.format(SuccessMessages.MEMBER_ADDED, newName);
  }

  private String tryRemoveMember(String name) {
    Member member = getMember(name);
    if (member == null) {
      return String.format(FailMessages.MEMBER_INEXISTENT);
    }
    try {
      members.remove(member);
    } catch (Exception e) {
      return String.format(FailMessages.CLUB_REMOVE_MEMBER_FAIL, member.getName());
    }
    return String.format(SuccessMessages.CLUB_MEMBER_REMOVED, member.getName());

  }

  private String generateId() {
    String id = java.util.UUID.randomUUID().toString().substring(0, 6);
    while (true) {
      for (Member member : members) {
        if (member.getId().equals(id)) {
          continue;
        }
      }
      return id;
    }
  }

  @Override
  public String toString() {
    StringBuilder clubString = new StringBuilder();

    for (Member member : members) {
      clubString.append(member);
    }

    return clubString.toString();
  }
}
