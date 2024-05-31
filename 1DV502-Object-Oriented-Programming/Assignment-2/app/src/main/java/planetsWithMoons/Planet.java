package planetsWithMoons;

import java.util.ArrayList;

public class Planet {
  private String name;
  private int position;
  // private int noOfMoons; => using moons.size() now
  private ArrayList<Moon> moons = new ArrayList<Moon>();
  private int aphelion;
  private int perihelion;

  public Planet() {
    setName("Unknown");
    setPosition(0);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName) {
    setName(newName);
    setPosition(0);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos) {
    setName(newName);
    setPosition(newPos);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos, int newAphelion) {
    setName(newName);
    setPosition(newPos);
    setAphelion(newAphelion);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos, int newAphelion, int newPerihelion) {
    setName(newName);
    setPosition(newPos);
    setAphelion(newAphelion);
    setPerihelion(newPerihelion);
  }

  public String getName() {
    return name;
  }

  public void setName(String newName) {
    if (newName.length() > 2) {
      name = newName;
    }
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int newPos) {
    if (newPos > 0 && newPos < 8) {
      position = newPos;
    }
  }

  public int getNoOfMoons() {
    return moons.size();
  }

  public void addMoon(Moon aNewMoon) {
    moons.add(aNewMoon);
  }

  public Moon[] getMoons() {
    Moon[] moonsArr = new Moon[getNoOfMoons()];

    for (int i = 0; i < getNoOfMoons(); i++) {
      Moon currMoon = moons.get(i);
      moonsArr[i] = new Moon(currMoon.getName(), currMoon.getSizeInKm());
    }

    return moonsArr;
  }

  public int getAphelion() {
    return aphelion;
  }

  public void setAphelion(int newAphelion) {
    if (newAphelion >= 0) {
      aphelion = newAphelion;
    }
  }

  public int getPerihelion() {
    return perihelion;
  }

  public void setPerihelion(int newPerihelion) {
    if (newPerihelion >= 0) {
      perihelion = newPerihelion;
    }
  }
}
