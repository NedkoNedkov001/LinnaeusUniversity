package orderInAnimalKingdom;

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
    return (getLatinName() + " puts its egg in/on a hole in the " + getNestType());
  }

  @Override
  public int compareTo(Animal o) {
    String thisName = this.getLatinName();
    String otherName = o.getLatinName();
    int minNameSize = Math.min(thisName.length(), otherName.length());

    for (int i = 0; i < minNameSize; i++) {
      if (Character.compare(thisName.charAt(i), otherName.charAt(i)) > 0){
        // if (thisName.charAt(i) > otherName.charAt(i)) {
          return 1;
        } else if (Character.compare(thisName.charAt(i), otherName.charAt(i)) < 0) {
          return -1;
        }
    }

    if (thisName.length() < otherName.length()) {
      return -1;
    } else {
      return 1;
    }
  }
}
