package main;

import entity.Feststoff;
import entity.Fluessigkeit;
import interfaces.Visitor;
import interfaces.Zutat;
import visitor.VisitorNA;
import visitor.VisitorSI;

public class Main {

  public static void main(String[] args) {
    Zutat[] tmp = {new Feststoff("Mehl", 400), new Feststoff("Salz", 200)
        , new Fluessigkeit("Wasser", 500), new Fluessigkeit("Oel", 45)
        , new Feststoff("Zitronensaeure", 40)};
    /*
    // Ausgabe mit einem Visitor
    Visitor visitor = new VisitorSI();
    for(Zutat z: tmp) {
    	System.out.println(visitor.visit(z));
    }
    
    // Ausgabe mit anderem Visitor
    visitor = new VisitorImperial();
    for(Zutat z: tmp) {
    	System.out.println(visitor.visit(z));
    }*/
    for(Visitor visitor : new Visitor[]{new VisitorSI(),new VisitorNA()}) {
    	for(Zutat z: tmp) {
        	System.out.println(visitor.visit(z));
        }
    }
  }

}
