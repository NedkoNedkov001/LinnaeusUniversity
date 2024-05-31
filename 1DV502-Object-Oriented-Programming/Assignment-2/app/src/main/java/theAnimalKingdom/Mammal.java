package theAnimalKingdom;

public class Mammal extends Animal {
  private String furColor;
  private boolean hasWinterFur;

  public Mammal(String name, String latinName, double weight, String sound, String furColor, boolean hasWinterFur) {
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
    StringBuilder sb = new StringBuilder();

    sb.append("A " + super.getName() + " (" + super.getLatinName() + ") has " + getFurColor() + " fur and ");

    if (hasWinterFur()) {
      sb.append("changes");
    } else {
      sb.append("does not change");
    }

    sb.append(" it during the winter.");

    return sb.toString();
  }
}
