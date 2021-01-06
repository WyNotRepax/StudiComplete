package entity;

import interfaces.Visitor;
import interfaces.Zutat;

public class Feststoff implements Zutat {

  private String name;
  private int gramm;
  
  public Feststoff() {}

  public Feststoff(String name, int menge) {
    super();
    this.name = name;
    this.gramm = menge;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGramm() {
    return gramm;
  }

  public void setGramm(int menge) {
    this.gramm = menge;
  }

  @Override
  public String accept(Visitor v) {
    return v.visit(this);
  }
}
