package planetsWithMoons;

import java.util.*;

public class PlanetMain {

  /**
  * Printing the moons' names and diameters for each planet in a list of planets
  * @param planets
  */
  private static void printPlanetMoons(ArrayList<Planet> planets) {
    for (Planet planet : planets) {
      Moon[] moons = planet.getMoons();

      System.out.println("The planet is called " + planet.getName()
          + " and has the following moons:");

      for (int i = 0; i < planet.getNoOfMoons(); i++) {
        System.out.println("  Moon " + (i + 1) + " is called " + moons[i].getName()
            + " (" + moons[i].getSizeInKm() + "km)");
      }

      System.out.println();
    }
  }

  /**
   * Adding moons to a list of planets, then printing information about each planet's moons
   * @param args
   */
  public static void main(String[] args) {
    ArrayList<Planet> planets = new ArrayList<Planet>();

    /*
     * Notice I don't manually add number of moons, as I use the ArrayList's size
     * to avoid confusion with indexing
     */
    Planet mars = new Planet("Mars", 4, 249209300, 206669000);
    mars.addMoon(new Moon("Phobos", 22));
    mars.addMoon(new Moon("Deimos", 12));
    planets.add(mars);

    Planet earth = new Planet("Earth", 3, 152097701, 147098074);
    earth.addMoon(new Moon("The moon", 3474));
    planets.add(earth);

    printPlanetMoons(planets);
  }
}