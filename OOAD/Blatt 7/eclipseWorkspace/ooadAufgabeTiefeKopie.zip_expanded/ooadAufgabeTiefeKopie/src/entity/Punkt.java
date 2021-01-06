package entity;

import java.util.Objects;

public class Punkt implements Cloneable{
  private int x;
  private int y;
  
  public Punkt() {
  }
  
  public Punkt(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  @Override
  public Punkt clone() {
    return new Punkt(this.x, this.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Punkt other = (Punkt) obj;
    return this.x == other.x && this.y == other.y;
  }

  @Override
  public String toString() {
    return "Punkt [x=" + this.x + ", y=" + this.y + "]";
  }

  public int getX() {
    return this.x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return this.y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
