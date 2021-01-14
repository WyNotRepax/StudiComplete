package main;

import analytics.Analyse;
import classmodel.Alle;
import entities.Pruefung;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class SystemTest {

    private Analyse analyse;
    private static Class impl;
    private Stream<Pruefung> beispieldaten;
    private Stream<Pruefung> leer;
    
    @Rule
    public final SystemOutRule systemOutMock 
            = new SystemOutRule().enableLog();

    @BeforeClass
    public static void setUpClass(){
        String implementierung = null;
        for(String k: Alle.alleKlassen()){
            if (Alle.klasseImplementiertInterface(k, "analytics.Analyse")){
                implementierung = k;
            }            
        }
        if(implementierung == null){
            throw new IllegalArgumentException("Keine Implementierung des"
                    + " Interfaces analytics.Analyse gefunden!");
        }
        impl = Alle.moeglicheClassObjekteFuer(implementierung).get(0);
        try {
            impl.newInstance();
        } catch (Exception e){
            throw new IllegalArgumentException("Implementierung hat keinen"
                    + " nutzbaren parameterlosen Konstruktor: " + e);
        }      
    }
    
    @Before
    public void setUp() throws Exception{
        this.analyse = (Analyse) impl.newInstance();
        this.beispieldaten = this.analyse.lesePruefungen();
        List<Pruefung> nix = new ArrayList<Pruefung>();
        this.leer = nix.stream();      
    }
    
    @Test
    public void testAnzahlPruefungen1(){
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, this.analyse.anzahlPruefungen(this.leer));
    }
    
    @Test
    public void testAnzahlPruefungen2(){
        Assert.assertEquals("Problem bei Beispieldaten"
                , 43, this.analyse.anzahlPruefungen(this.beispieldaten));
    }
    
    @Test
    public void testAnzahlBestandenerPruefungen1(){
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, this.analyse.anzahlBestandenerPruefungen(this.leer, 42));
    }
    
    @Test
    public void testAnzahlBestandenerPruefungen2(){
        Assert.assertEquals("Problem bei Beispieldaten"
                , 8, this.analyse.anzahlBestandenerPruefungen(
                        this.beispieldaten, 101));
    }
    
    @Test
    public void testAnzahlBestandenerPruefungen3(){
        Assert.assertEquals("Problem bei Beispieldaten"
                , 1, this.analyse.anzahlBestandenerPruefungen(
                        this.beispieldaten, 103));
    }
    
    @Test
    public void testAnzahlBestandenerPruefungen4(){
        Assert.assertEquals("Problem bei Beispieldaten"
                , 0, this.analyse.anzahlBestandenerPruefungen(
                        this.beispieldaten, 42));
    }
    
    @Test
    public void testDurchschnittsnote1(){
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, this.analyse.durchschnittsnote(this.leer,42),0);
    }
    
    @Test
    public void testDurchschnittsnote2(){
        Assert.assertEquals("Problem bei Schnitt von 102"
                , 271.6666, this.analyse.durchschnittsnote(
                        this.beispieldaten,102), 0.001);
    }
    
    @Test
    public void testDurchschnittsnote3(){
        Assert.assertEquals("Problem bei Schnittt von 103"
                , 400, this.analyse.durchschnittsnote(
                        this.beispieldaten,103), 0.001);
    }
    
    @Test
    public void testDurchschnittsnote4(){
        Assert.assertEquals("Problem bei Schnittt von 42"
                , 0, this.analyse.durchschnittsnote(
                        this.beispieldaten,42),0);
    }
    
    @Test
    public void testDurchschnittModul1(){
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, this.analyse.durchschnittModul(this.leer
                        ,"Programmierung 1"),0);
    }
    
    @Test
    public void testDurchschnittModule2(){
        Assert.assertEquals("Problem bei Schnitt von \"Programmierung 1\""
                , 347.1428, this.analyse.durchschnittModul(this.beispieldaten
                        ,"Programmierung 1"), 0.001);
    }
    
    @Test
    public void testDurchschnittModul3(){
        Assert.assertEquals("Problem bei Schnittt von \"SW-Projekt\""
                , 135, this.analyse.durchschnittModul(this.beispieldaten
                        ,"SW-Projekt"), 0.001);
    }
    
    @Test
    public void testDurchschnittModul4(){
        Assert.assertEquals("Problem bei Schnitt von \"SW-Projektus\""
                , 0, this.analyse.durchschnittModul(this.beispieldaten
                        ,"SW-Projektus"),0);
    }
    
    @Test
    public void testEndgueltigDurchgefallen1(){
        List<Integer> erg = this.analyse.endgueltigDurchgefallen(this.leer);
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, erg.size());
    }
    
    @Test
    public void testEndgueltigDurchgefallen2(){
        List<Integer> erg = this.analyse.endgueltigDurchgefallen(
                this.beispieldaten);
        Assert.assertEquals("Problem bei Beispieldaten"
                , 1, erg.size());
        Assert.assertTrue( "103 endgueltig durchgefallen"
                , erg.contains(103));
    } 
    
    @Test
    public void testHatNochOffenenDrittenVersuch1(){
        List<Integer> erg = this.analyse.hatNochOffenenDrittenVersuch(this.leer);
        Assert.assertEquals("Problem bei leerer Pruefungsliste"
                , 0, erg.size());
    }
    
    @Test
    public void testHatNochOffenenDrittenVersuch2(){
        List<Integer> erg = this.analyse.hatNochOffenenDrittenVersuch(
                this.beispieldaten);
        Assert.assertEquals("Muss drei offene Drittversuche geben"
                , 3, erg.size());
        Assert.assertTrue( "103 auch noch offenen Drittversuch"
                , erg.contains(103));
        Assert.assertEquals( "102 hat noch zwei offene Drittversuche"
                , 2, erg.stream().filter(p -> p == 102).count());
    }   
    
    @Test
    public void testZeigePruefungenVon1(){
        this.systemOutMock.clearLog();
        this.analyse.zeigePruefungenVon(this.leer, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                , "", this.systemOutMock.getLog());
    }
    
    @Test
    public void testZeigePruefungenVon2(){
        this.systemOutMock.clearLog();
        this.analyse.zeigePruefungenVon(this.beispieldaten, 100);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von 11 Pruefungen bei 100 erwartet: "
                + this.systemOutMock.getLog()
                , 11, tmp.length);
    }
    
    @Test
    public void testZeigePruefungenVon3(){
        this.systemOutMock.clearLog();
        this.analyse.zeigePruefungenVon(this.beispieldaten, 101);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        //System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von 8 Pruefungen bei 101 erwartet: "
                + this.systemOutMock.getLogWithNormalizedLineSeparator()
                , 8, tmp.length);
    }
    
    @Test
    public void testZeigePruefungenVon4(){
        this.systemOutMock.clearLog();
        this.analyse.zeigePruefungenVon(this.beispieldaten, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                + this.systemOutMock.getLog()
                , "", this.systemOutMock.getLog());
    }
    
    @Test
    public void testZeigeBestandenePruefungenVon1(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeBestandenePruefungenVon(this.leer, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                , "", this.systemOutMock.getLog());
    }
    
    @Test
    public void testZeigeBestandenePruefungenVon2(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeBestandenePruefungenVon(this.beispieldaten, 100);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von 8 bestandenen Pruefungen bei "
                + "100 erwartet: "
                + this.systemOutMock.getLog()
                , 8, tmp.length);
    }
    
    @Test
    public void testZeigeBestandenePruefungenVon3(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeBestandenePruefungenVon(this.beispieldaten, 103);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        //System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von einer bestandenen Pruefung bei "
                + "103 erwartet: "
                + this.systemOutMock.getLogWithNormalizedLineSeparator()
                , 1, tmp.length);
    }
    
    @Test
    public void testZeigeBestandenePruefungenVon4(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeBestandenePruefungenVon(this.beispieldaten, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                + this.systemOutMock.getLog()
                , "", this.systemOutMock.getLog());
    }

    @Test
    public void testZeigeGeordnetPruefungenVon1(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeGeordnetPruefungenVon(this.leer, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                , "", this.systemOutMock.getLog());
    }
    
    @Test
    public void testZeigeGeordnetPruefungenVon2(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeGeordnetPruefungenVon(this.beispieldaten, 100);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von acht geprueften Faechern bei "
                + "100 erwartet: "
                + this.systemOutMock.getLog()
                , 8, tmp.length);
    }
    
    @Test
    public void testZeigeGeordnetPruefungenVon3(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeGeordnetPruefungenVon(this.beispieldaten, 103);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        //System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von drei geprueften Faechern bei "
                + "103 erwartet: "
                + this.systemOutMock.getLogWithNormalizedLineSeparator()
                , 3, tmp.length);
    }
    
    @Test
    public void testZeigeGeordnetPruefungenVon4(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeGeordnetPruefungenVon(this.beispieldaten, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                + this.systemOutMock.getLog()
                , "", this.systemOutMock.getLog());
    }   
    
    private void ausgegebeneWertepruefen(String out){
        Pattern pat = Pattern.compile("\\d\\d\\d");
        Matcher mat = pat.matcher(out);
        List<String> werte = new ArrayList<>();
        while (mat.find()) {
            werte.add(out.substring(mat.start(), mat.end()));
        }
        //System.out.println("werte: "+ werte);
        for(int i=0; i < werte.size()-1; i++){
            if(werte.get(i).compareTo(werte.get(i+1)) > 0){
                throw new IllegalArgumentException("Notenliste " + werte 
                        + " nicht aufsteigend geordnet");
            }
        }
    }  
    
    @Test
    public void testZeigeDetailgeordnetPruefungenVon1(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeDetailgeordnetPruefungenVon(this.leer, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                , "", this.systemOutMock.getLog());
    }
    
    @Test
    public void testZeigeDetailgeordnetPruefungenVon2(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeDetailgeordnetPruefungenVon(this.beispieldaten, 100);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        for(String s: tmp){
            this.ausgegebeneWertepruefen(s);
        }
        //System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von acht geprueften Faechernn bei "
                + "100 erwartet: "
                + this.systemOutMock.getLog()
                , 8, tmp.length);
    }
    
    @Test
    public void testZeigeDetailgeordnetPruefungenVon3(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeDetailgeordnetPruefungenVon(this.beispieldaten, 103);
        String[] tmp = this.systemOutMock.getLogWithNormalizedLineSeparator()
                .split("(?sm)\n|\r");
        for(String s: tmp){
            this.ausgegebeneWertepruefen(s);
        }
        //System.out.println(Arrays.asList(tmp));
        Assert.assertEquals("Ausgabe von drei geprueften Faechern bei "
                + "103 erwartet: "
                + this.systemOutMock.getLogWithNormalizedLineSeparator()
                , 3, tmp.length);
    }
    
    @Test
    public void testZeigeDetailgeordnetPruefungenVon4(){
        this.systemOutMock.clearLog();
        this.analyse.zeigeGeordnetPruefungenVon(this.beispieldaten, 42);
        Assert.assertEquals("Keine Ausgabe von Pruefungen erwartet"
                + this.systemOutMock.getLog()
                , "", this.systemOutMock.getLog());
    }   
}

