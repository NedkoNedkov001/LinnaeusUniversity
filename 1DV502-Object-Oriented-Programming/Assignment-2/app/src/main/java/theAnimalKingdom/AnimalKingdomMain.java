package theAnimalKingdom;

import java.util.ArrayList;

public class AnimalKingdomMain {
  /**
   * Printing information about each animal in a list.
   * @param animals an ArrayList of type Animal
   */
  private static void printAnimalsInfo(ArrayList<Animal> animals) {
    for (Animal animal : animals) {
      System.out.println(animal);
      System.out.println(animal.makeSound() + "\n");
    }
  }

  /**
   * Adding animals to a list and then printing information about them.
   * @param args /
   */
  public static void main(String[] args) {
    ArrayList<Animal> animals = new ArrayList<>();
    animals.add(new Bird("tawny owl", "Strix aluco", 0.54, "hooo", false, true, "tree"));
    animals.add(new Mammal("tiger", "Panthera tigris", 400, "rawr", "orange", false));
    animals.add(new Reptile("green anaconda", "Eunectes murinus", 50, "hisss", "swamp", false));

    printAnimalsInfo(animals);
  }
}
