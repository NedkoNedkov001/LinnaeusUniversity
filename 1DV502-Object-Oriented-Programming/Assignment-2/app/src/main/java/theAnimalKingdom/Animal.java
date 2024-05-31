package theAnimalKingdom;

public abstract class Animal {
  private String name;
  private String latinName;
  private double weight;
  private String sound;

  public Animal(String name, String latinName, double weight, String sound) {
    this.name = name;
    this.latinName = latinName;
    this.weight = weight;
    this.sound = sound;
  }

  public String getName() {
    return name;
  }

  public String getLatinName() {
    return latinName;
  }

  public double getWeight() {
    return weight;
  }

  public String getSound() {
    return sound;
  }

  public abstract String makeSound();
}
