package club.data.models.boats;

/**
 * Abstract class boat. Base for all boat types.
 */
public abstract class Boat {
  private String name;
  private int length;

  /**
   * Constructs a new boat.
   *
   * @param newName   name
   * @param newLength length
   */
  public Boat(String newName, int newLength) {
    setName(newName);
    setLength(newLength);
  }

  public int getLength() {
    return length;
  }

  void setLength(int newLength) {
    length = newLength;
  }

  public String getName() {
    return name;
  }

  void setName(String newName) {
    name = newName;
  }

  @Override
  public String toString() {
    return "BOAT:" + getName() + ":" + getClass().getSimpleName() + ":" + getLength();
  }
}
