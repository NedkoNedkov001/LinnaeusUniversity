package planetsWithMoons;

public class Moon {
  private String name;
  private int kmSize;

  public Moon(String name, int kmSize) {
    this.name = name;
    this.kmSize = kmSize;
  }

  public String getName() {
    return name;
  }

  public int getSizeInKm() {
    return kmSize;
  }
}
