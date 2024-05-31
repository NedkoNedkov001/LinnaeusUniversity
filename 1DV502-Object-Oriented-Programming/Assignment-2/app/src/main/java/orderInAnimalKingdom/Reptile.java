package orderInAnimalKingdom;

public class Reptile extends Animal {
  private String habitat;
  private boolean isPoisonous;

  public Reptile(String name, String latinName, double weight, 
                 String sound, String habitat, boolean isPoisonous) {
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
    return (getLatinName() + " lives in the " + getHabitat());
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
