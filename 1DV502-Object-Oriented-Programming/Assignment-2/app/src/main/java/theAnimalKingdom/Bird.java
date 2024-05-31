package theAnimalKingdom;

public class Bird extends Animal {

  private boolean isMigrant;
  private boolean canFly;
  private String nestType;

  public Bird(String name, String latinName, double weight, String sound, boolean isMigrant, boolean canFly,
      String nestType) {
    super(name, latinName, weight, sound);
    this.isMigrant = isMigrant;
    this.canFly = canFly;
    this.nestType = nestType;
  }

  /*
   * Notice that there are only getters, as a bird species will
   * not change their behaviour, so setters are unnecessary
   */
  public boolean isMigrant() {
    return isMigrant;
  }

  public boolean canFly() {
    return canFly;
  }

  public String getNestType() {
    return nestType;
  }

  @Override
  public String makeSound() {
    return ("A " + super.getName() + " tweets: " + super.getSound());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("A " + super.getName() + " (" + super.getLatinName() + ") is a ");

    if (canFly()) {
      sb.append("flying");
    } else {
      sb.append("flightless");
    }

    sb.append(" bird, that ");

    if (isMigrant()) {
      sb.append("migrates");
    } else {
      sb.append("does not migrate");
    }

    sb.append(" to warmer countries in the winter and lives in a " + nestType + " nest.");

    return sb.toString();
  }
}
