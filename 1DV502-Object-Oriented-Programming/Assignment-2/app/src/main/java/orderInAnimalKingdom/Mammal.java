package orderInAnimalKingdom;

public class Mammal extends Animal {
  private String furColor;
  private boolean hasWinterFur;

  public Mammal(String name, String latinName, double weight, String sound,
                String furColor, boolean hasWinterFur) {
    super(name, latinName, weight, sound);
    this.furColor = furColor;
    this.hasWinterFur = hasWinterFur;
  }

  /*
   * Notice that there are only getters, as a mammal species will
   * not change their behaviour, so setters are unnecessary
   */
  public String getFurColor() {
    return furColor;
  }

  public boolean hasWinterFur() {
    return hasWinterFur;
  }

  @Override
  public String makeSound() {
    return ("A " + super.getName() + " says: " + super.getSound());
  }

  @Override
  public String toString() {
    return (getLatinName() + " has a fur that is " + getFurColor());
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
