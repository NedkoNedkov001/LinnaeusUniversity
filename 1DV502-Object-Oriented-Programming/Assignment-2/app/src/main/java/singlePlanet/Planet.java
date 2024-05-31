package singlePlanet;

public class Planet {
  private String name;
  private int position;
  private int noOfMoons;
  private int aphelion;
  private int perihelion;

  public Planet() {
    setName("Unknown");
    setPosition(0);
    setNoMoons(0);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName) {
    setName(newName);
    setPosition(0);
    setNoMoons(0);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos) {
    setName(newName);
    setPosition(newPos);
    setNoMoons(0);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos, int newCount) {
    setName(newName);
    setPosition(newPos);
    setNoMoons(newCount);
    setAphelion(0);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos, int newCount, int newAphelion) {
    setName(newName);
    setPosition(newPos);
    setNoMoons(newCount);
    setAphelion(newAphelion);
    setPerihelion(0);
  }

  public Planet(String newName, int newPos, int newCount, int newAphelion, int newPerihelion) {
    setName(newName);
    setPosition(newPos);
    setNoMoons(newCount);
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
    return noOfMoons;
  }

  public void setNoMoons(int newCount) {
    if (newCount >= 0) {
      noOfMoons = newCount;
    }
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
