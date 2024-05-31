package club.data.models.boats;

/**
 * Motorboat class.
 */
public class Motorboat extends Boat {
  private int power;

  /**
   * Construct a new sailboat.
   *
   * @param newName   name
   * @param newLength length
   * @param newPower  power
   */
  public Motorboat(String newName, int newLength, int newPower) {
    super(newName, newLength);
    setPower(newPower);
  }

  public int getPower() {
    return this.power;
  }

  void setPower(int newPower) {
    power = newPower;
  }

  @Override
  public String toString() {
    return super.toString() + ":" + this.getPower();
  }
}
