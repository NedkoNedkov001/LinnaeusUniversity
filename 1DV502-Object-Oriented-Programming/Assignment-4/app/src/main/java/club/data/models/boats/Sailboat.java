package club.data.models.boats;

/**
 * Sailboat class.
 */
public class Sailboat extends Boat {
  private int depth;

  public Sailboat(String newName, int newLength, int newDepth) {
    super(newName, newLength);
    setDepth(newDepth);
  }

  public int getDepth() {
    return this.depth;
  }

  void setDepth(int newDepth) {
    depth = newDepth;
  }

  @Override
  public String toString() {
    return super.toString() + ":" + this.getDepth();
  }
}
