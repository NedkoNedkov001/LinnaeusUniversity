package club.data.constants;

/**
 * All constant variables and messages to run the program.
 */
public class Variables {
  // Constant Variables
  public static final int MIN_CLUB_NAME_LENGTH = 2;
  public static final int MIN_BOAT_NAME_LENGTH = 3;

  public static final int MIN_BOAT_LENGTH = 2;
  public static final int MIN_BOAT_POWER = 10;
  public static final int MIN_BOAT_DEPTH = 1;
  public static final int MEMBER_ID_LENGTH = 6;
  public static final String MEMBER_NAME_REGEX = "^[A-Z][a-z]+\\s[A-Z][a-z]+$";
  public static final String MEMBER_EMAIL_REGEX = "^[A-Z0-9.]+@[A-Z0-9.]+\\.[A-Z]+$";
  public static final String MEMBER_ID_REGEX = "^[a-z0-9]{" + MEMBER_ID_LENGTH + ",}$";
  public static final String BOAT_NAME_REGEX = "^[A-Za-z]{" + MIN_BOAT_NAME_LENGTH + ",}$";
}
