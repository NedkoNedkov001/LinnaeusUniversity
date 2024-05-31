package singlePlanet;

import java.util.*;

public class PlanetMain {

  /**
   * Printing the details of each planet in a list of planets
   * @param planets
   */
  private static void printPlanets(ArrayList<Planet> planets) {
    for (Planet planet : planets) {
      System.out.println(planet.getName());
      System.out.println("  Position: " + planet.getPosition());
      System.out.println("  Moons: " + planet.getNoOfMoons());
      System.out.println("  Aphelion: " + planet.getAphelion() + " km");
      System.out.println("  Perihelion: " + planet.getPerihelion() + " km");
    }
  }


  /**
   * Adding planets to a list of planets, then printing planets' details
   * @param args
   */
  public static void main(String[] args) {

    ArrayList<Planet> planets = new ArrayList<Planet>();
    planets.add(new Planet("Earth", 3, 1, 152097701, 147098074));
    planets.add(new Planet("Mars", 4, 2, 249209300, 206669000));

    printPlanets(planets);
  }
}