package club.data.constants;

/**
 * Fail messages.
 */
public class FailMessages {
  ///////////////////////////////////////////
  // [FAIL] Messages
  //
  // [MEMBER]
  public static final String MEMBER_INVALID_NAME = "%n[FAIL] Member name [%s] must be "
      + "in the [Firstname Lastname] format.";
  public static final String MEMBER_INVALID_EMAIL = "%n[FAIL] Member email [%s] must be "
      + "in the [xxx@xxx.xxx] format.";
  public static final String MEMBER_INVALID_ID = "%n[FAIL] Member ID [%s] must be "
      + "[" + Variables.MEMBER_ID_LENGTH + "] characters long.";
  //
  //
  public static final String MEMBER_NAME_TAKEN = "%n[FAIL] Member name [%s] already taken.";
  public static final String MEMBER_EMAIL_TAKEN = "%n[FAIL] Member email [%s] already taken.";
  public static final String MEMBER_ID_TAKEN = "%n[FAIL] Member ID [%s] already taken";
  //
  //
  public static final String MEMBER_ADD_FAIL = "%n[FAIL] Could not add new member [%s].";
  public static final String MEMBER_SET_NAME_FAIL = "%n[FAIL] Could not set member name to [%s].";
  public static final String MEMBER_SET_EMAIL_FAIL = "%n[FAIL] Could not set member email to [%s].";
  public static final String MEMBER_ID_SET_FAIL = "%n[FAIL] Could not set member ID to [%s].";
  public static final String MEMBER_REMOVE_FAIL = "%n[FAIL] Could not remove member [%s].";
  public static final String MEMBER_REMOVE_BOAT_FAIL = "%n[FAIL] Could not remove boat [%s] "
      + "from member [%s].";
  //
  //
  public static final String MEMBER_INEXISTENT = "%n[FAIL] Member does not exist.";
  public static final String MEMBER_BOAT_INEXISTENT = "%n[FAIL] Member [%s] does not own a boat "
      + "[%s].";
  //
  //
  // [BOAT]
  public static final String BOAT_INVALID_NAME = "%n[FAIL] Boat name must be ["
      + Variables.MIN_BOAT_NAME_LENGTH + "] or more [letters].";
  public static final String BOAT_INVALID_LENGTH = "%n[FAIL] Boat length must be ["
      + Variables.MIN_BOAT_LENGTH + "m] or more.";
  public static final String BOAT_INVALID_POWER = "%n[FAIL] Boat power must be ["
      + Variables.MIN_BOAT_POWER + "hp] or more.";
  public static final String BOAT_INVALID_DEPTH = "%n[FAIL] Boat depth must be ["
      + Variables.MIN_BOAT_DEPTH + "m] or more.";
  public static final String BOAT_INVALID_TYPE = "%n[FAIL] Not a valid boat type.";
  public static final String BOAT_INEXISTENT = "%n[FAIL] Boat does not exist.";
  //
  //
  public static final String BOAT_HAS_NO_POWER = "%n[FAIL] Boat type [%s] does not have power.";
  public static final String BOAT_HAS_NO_DEPTH = "%n[FAIL] Boat type [%s] does not have depth.";
  //
  //
  public static final String BOAT_NAME_CHANGE_FAIL = "%n[FAIL] Could not change boat "
      + "name from [%s] to [%s].";
  public static final String BOAT_LENGTH_CHANGE_FAIL = "%n[FAIL] Could not change boat "
      + "length from [%sm] to [%sm].";
  public static final String BOAT_POWER_CHANGE_FAIL = "%n[FAIL] Could not change boat "
      + "power from [%shp] to [%shp].";
  public static final String BOAT_DEPTH_CHANGE_FAIL = "%n[FAIL] Could not change boat "
      + "depth from [%sm] to [%sm].";
  public static final String BOAT_ADD_FAIL = "%n[FAIL] Could not add boat [%s] to member[%s].";
  public static final String BOAT_CREATE_FAIL = "%n[FAIL] Could not create boat [%s].";
  //
  //
  // [CLUB]
  public static final String CLUB_CREATE_FAIL = "%n[FAIL] Could not create club [%s].";
  public static final String CLUB_INVALID_NAME = "%n[FAIL] Club name [%s] must be ["
      + Variables.MIN_CLUB_NAME_LENGTH + "] or more characters.";
  public static final String CLUB_RENAME_FAIL = "%n[FAIL] Could not rename club [%s] to [%s].";
  public static final String CLUB_MEMBER_ADD_FAIL = "%n[FAIL] Could not add new member [%s].";
  public static final String CLUB_REMOVE_MEMBER_FAIL = "%n[FAIL] Could not remove member [%s].";
  public static final String IMPORT_MEMBER_FAIL = "%n[FAIL] Could not import member [%s].";
  public static final String IMPORT_BOAT_FAIL = "%n[FAIL] Could not import boat [%s].";
  //
  //
  // [FILE]
  public static final String IMPORT_RECORDS_FAIL = "%n[FAIL] Import failed.";
  public static final String SAVE_CHANGES_FAIL = "%n[FAIL] Save failed.";
  public static final String FILE_NOT_FOUND = "%n[FAIL] File not found.";

}
