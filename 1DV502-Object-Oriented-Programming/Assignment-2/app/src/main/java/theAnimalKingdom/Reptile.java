package theAnimalKingdom;

public class Reptile extends Animal {
  private String habitat;
  private boolean isPoisonous;

  public Reptile(String name, String latinName, double weight, String sound, String habitat, boolean isPoisonous) {
    super(name, latinName, weight, sound);
    this.habitat = habitat;
    this.isPoisonous = isPoisonous;
  }

  /*
   * Notice that there are only getters, as a reptile species will
   * not change their behaviour, so setters are unnecessary
   */
  public String getHabitat() {
    return habitat;
  }

  public boolean isPoisonous() {
    return isPoisonous;
  }

  @Override
  public String makeSound() {
    return ("A " + super.getName() + " hizzes: " + super.getSound());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(
        "A " + super.getName() + " (" + super.getLatinName() + ") lives in a " + getHabitat() + " habitat and is ");

    if (!isPoisonous()) {
      sb.append("not");
    }

    sb.append(" poisonous.");

    return sb.toString();
  }

}
