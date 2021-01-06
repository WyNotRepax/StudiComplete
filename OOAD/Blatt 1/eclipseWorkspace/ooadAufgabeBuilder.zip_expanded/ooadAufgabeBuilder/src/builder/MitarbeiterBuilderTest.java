package builder;


import entity.Fachgebiet;
import entity.Mitarbeiter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.Assert;

public class MitarbeiterBuilderTest {

    @Test
    public void testBasisobjekt() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .build();
        Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Eva"));
        Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Mustermann"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList());
        Assert.assertTrue("Erwartete Faehigkeiten: [], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    }

    @Test
    public void testNeuerVorname() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .vorname("Sergej")
                .build();
        Assert.assertTrue("Vorname erwartet 'Sergej', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Sergej"));
        Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Mustermann"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList());
        Assert.assertTrue("Erwartete Faehigkeiten: [], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    } 
    
    @Test
    public void testNeuerNachname() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .nachname("Eydin")
                .build();
        Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Eva"));
        Assert.assertTrue("Nachname erwartet 'Eydin', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Eydin"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList());
        Assert.assertTrue("Erwartete Faehigkeiten: [], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    }

    @Test
    public void testEineNeueFaehigkeit() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .mitFachgebiet(Fachgebiet.C)
                .build();
        Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Eva"));
        Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Mustermann"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList(Fachgebiet.C));
        Assert.assertTrue("Erwartete Faehigkeiten: [C], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    }
    
    @Test
    public void testMehrereFaehigkeiten() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .mitFachgebiet(Fachgebiet.C)
                .mitFachgebiet(Fachgebiet.JAVA)
                .build();
        Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Eva"));
        Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Mustermann"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList(Fachgebiet.JAVA, Fachgebiet.C));
        Assert.assertTrue("Erwartete Faehigkeiten: [Java, C], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    }   
    
    @Test
    public void testDoppelteFaehigkeiten() {
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .mitFachgebiet(Fachgebiet.ANALYSE)
                .mitFachgebiet(Fachgebiet.C)
                .mitFachgebiet(Fachgebiet.JAVA)
                .mitFachgebiet(Fachgebiet.ANALYSE)
                .mitFachgebiet(Fachgebiet.C)
                .mitFachgebiet(Fachgebiet.JAVA)
                .build();
        Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Eva"));
        Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Mustermann"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList(Fachgebiet.JAVA, Fachgebiet.C
                , Fachgebiet.ANALYSE));
        Assert.assertTrue("Erwartete Faehigkeiten: [Java, C, Analyse], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));
    }   
    
    @Test 
    public void nachtraeglicheAenderung(){
         Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .vorname(("Egon"))
                .nachname("Meier")
                .nachname("Eydin")
                .vorname("Anna")
                .build();
        Assert.assertTrue("Vorname erwartet 'Anna', gefunden:'"
                    + m.getVorname() + "'"
                , m.getVorname().equals("Anna"));
        Assert.assertTrue("Nachname erwartet 'Eydin', gefunden:'"
                    + m.getNachname() + "'"
                , m.getNachname().equals("Eydin"));
        Set<Fachgebiet> tmp = new HashSet<>();
        tmp.addAll(Arrays.asList());
        Assert.assertTrue("Erwartete Faehigkeiten: [], gefunden: "
                    + m.getFachgebiete()
                , m.getFachgebiete().equals(tmp));       
    }
    
    
    @Test
    public void testMehrfacheNutzung(){
        MitarbeiterBuilder mb = MitarbeiterBuilder
                .createBuilder()
                .vorname("Tyrion")
                .nachname("Lannister");
        Mitarbeiter m = MitarbeiterBuilder
                .createBuilder()
                .vorname("Daenerys")
                .nachname("Targaryen")
                .build();
        Mitarbeiter m2 = mb.build();
        Assert.assertTrue("geschachtelte Erzeugung  unterschiedlicher"
                    + "Objekte klappt nicht"
                , !m.getVorname().equals(m2.getVorname())
                  && !m.getNachname().equals(m2.getNachname()) );
    }
    
    @Test
    public void testNacheinandernutzung() {
      Mitarbeiter m0 = MitarbeiterBuilder
          .createBuilder()
          .mitFachgebiet(Fachgebiet.ANALYSE)
          .mitFachgebiet(Fachgebiet.C)
          .mitFachgebiet(Fachgebiet.DESIGN)
          .build();
      Mitarbeiter m = MitarbeiterBuilder
          .createBuilder()
          .mitFachgebiet(Fachgebiet.TEST)
          .mitFachgebiet(Fachgebiet.C)
          .mitFachgebiet(Fachgebiet.JAVA)
          .build();
      Assert.assertTrue("Vorname erwartet 'Eva', gefunden:'"
          + m.getVorname() + "'", m.getVorname().equals("Eva"));
      Assert.assertTrue("Nachname erwartet 'Mustermann', gefunden:'"
          + m.getNachname() + "'", m.getNachname().equals("Mustermann"));
      Set<Fachgebiet> tmp = new HashSet<>();
      tmp.addAll(
          Arrays.asList(Fachgebiet.JAVA, Fachgebiet.C, Fachgebiet.TEST));
      Assert.assertTrue("Erwartete Faehigkeiten: [Java, C, Test], gefunden: "
          + m.getFachgebiete(), m.getFachgebiete().equals(tmp));
    }
    
    @Test
    public void testErwarteteExcption() {
      try {
        MitarbeiterBuilder
            .createBuilder()
            .mitFachgebiet(Fachgebiet.ANALYSE)
            .mitFachgebiet(Fachgebiet.C)
            .mitFachgebiet(Fachgebiet.JAVA)
            .mitFachgebiet(Fachgebiet.TEST)
            .build();
        Assert.fail("Erwartete Exception bei 4 Fachgebieten fehlt.");
      } catch (IllegalArgumentException e) {
      }
    }
}
