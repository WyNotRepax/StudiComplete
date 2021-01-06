package entity;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DoppellisteTest {

  Doppelliste dleer = new Doppelliste();

  @Test
  void nullTest() {
    Doppelliste d1 = new Doppelliste(null,null);
    Doppelliste d2 = d1.clone();
    Assertions.assertNotSame(d2, d1);
    Assertions.assertEquals(d1, d2);   
  }
  
  @Test
  void leerTest() {
    Doppelliste d1 = new Doppelliste();
    Doppelliste d2 = d1.clone();
    Assertions.assertNotSame(d2, d1);
    Assertions.assertNotSame(d2.getListe1(), d1.getListe1());
    Assertions.assertNotSame(d2.getListe2(), d1.getListe2());
    Assertions.assertEquals(d1, d2);   
  }
  
  @Test
  void gefuelltTest() {
    List<Punkt> l1 = new ArrayList<Punkt>();
    l1.addAll(List.of(new Punkt(1,1), new Punkt(2,1), new Punkt(3,1)));
    l1.add(null);
    List<Punkt> l2 = new ArrayList<Punkt>();
    l2.addAll(List.of(new Punkt(1,1), new Punkt(1,2), new Punkt(1,3)));
    l2.add(1, null);
    Doppelliste d1 = new Doppelliste(l1,l2);
    Doppelliste d2 = d1.clone();
    Assertions.assertNotSame(d2, d1);
    Assertions.assertNotSame(d2.getListe1(), d1.getListe1());
    Assertions.assertNotSame(d2.getListe2(), d1.getListe2());
    Assertions.assertNotSame(d2.getListe2().get(0), d1.getListe2().get(0));
    Assertions.assertEquals(d1, d2);   
  }
  
  @Test
  void identischeListenGefuelltTest() {
    List<Punkt> l1 = new ArrayList<Punkt>();
    l1.addAll(List.of(new Punkt(1,1), new Punkt(2,1), new Punkt(3,1)));
    Doppelliste d1 = new Doppelliste(l1,l1);
    Assertions.assertSame(d1.getListe1(), d1.getListe2());
    Doppelliste d2 = d1.clone();
    Assertions.assertNotSame(d2, d1);
    Assertions.assertNotSame(d2.getListe1(), d1.getListe1());
    Assertions.assertNotSame(d2.getListe2(), d1.getListe2());
    Assertions.assertEquals(d1, d2);   
    Assertions.assertSame(d2.getListe1(), d2.getListe2());
  }

  @Test
  void identischeObjekteGefuelltTest() {
    List<Punkt> l1 = new ArrayList<Punkt>();
    Punkt id = new Punkt(42,42);
    l1.addAll(List.of(id, new Punkt(2,1), id));
    List<Punkt> l2 = new ArrayList<Punkt>();
    l2.addAll(List.of(new Punkt(1,1), new Punkt(1,2), new Punkt(1,3)));
    Doppelliste d1 = new Doppelliste(l1,l2);
    Assertions.assertSame(d1.getListe1().get(0), d1.getListe1().get(2));
    Doppelliste d2 = d1.clone();
    Assertions.assertNotSame(d2, d1);
    Assertions.assertNotSame(d2.getListe1(), d1.getListe1());
    Assertions.assertNotSame(d2.getListe2(), d1.getListe2());
    Assertions.assertEquals(d1, d2);   
    Assertions.assertSame(d2.getListe1().get(0), d2.getListe1().get(2));
  }
}
