package entity;

import interfaces.Visitor;
import interfaces.Zutat;

public class Fluessigkeit implements Zutat{
  private String name;
  private int ml; // in ml
  
  public Fluessigkeit() {}

  public Fluessigkeit(String name, int menge) {
    super();
    this.name = name;
    this.ml = menge;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMl() {
    return ml;
  }

  public void setMl(int menge) {
    this.ml = menge;
  }

  @Override
  public String accept(Visitor v) {
    return v.visit(this);
  }
  
}
