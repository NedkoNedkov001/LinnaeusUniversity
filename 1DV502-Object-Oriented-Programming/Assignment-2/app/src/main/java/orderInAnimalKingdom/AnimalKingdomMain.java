package orderInAnimalKingdom;

import java.util.ArrayList;
import java.util.Collections;

public class AnimalKingdomMain {

  /**
   * Sorting a list of animals by their latin name, then printing our information about each.
   * @param args
   */
  public static void main(String[] args) {
    ArrayList<Animal> animals = new ArrayList<Animal>();
    animals.add(new Reptile("green sea turtle", "Chelonia mydas", 100.50, "", "sea", false));
    animals.add(new Mammal("tiger", "Panthera tigris", 400, "rawr", "orange", false));
    animals.add(new Mammal("cattle", "Bos taurus", 500, "moo", "red and white", false));
    animals.add(new Reptile("nile crocodile", "Crocodylus niloticus", 320, "growl", "fresh water", false));
    animals.add(new Bird("indian peafowl", "Pavo cristatus", 4.9, "rattle", false, true, "ground"));
    animals.add(new Bird("north Island brown kiwi", "Apteryx mantelli", 2.8, "kee-wee", false, false, "ground"));

    Collections.sort(animals);

    for (Animal animal : animals) {
      System.out.println(animal);
    }
  }
}
