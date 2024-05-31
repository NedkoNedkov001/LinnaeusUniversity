package club.data.models.boats;

/**
 * Motorsailer class.
 */
public class Motorsailer extends Boat {
  private int depth;
  private int power;

  /**
   * Construct a new motorsailer.
   *
   * @param newName   name
   * @param newLength length
   * @param newDepth  depth
   * @param newPower  power
   */
  public Motorsailer(String newName, int newLength, int newDepth, int newPower) {
    super(newName, newLength);
    setPower(newPower);
    setDepth(newDepth);
  }

  public int getDepth() {
    return this.depth;
  }

  void setDepth(int newDepth) {
    depth = newDepth;
  }

  public int getPower() {
    return this.power;
  }

  void setPower(int newPower) {
    power = newPower;
  }

  @Override
  public String toString() {
    return super.toString() + ":" + this.getDepth() + ":" + this.getPower();
  }
}
