package main;

import classmodel.Alle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class StrukturUndBasistests {
        // besser in einzelne Tests aufspalten
    private String[] geforderteKlassen = {"Ausruestung", "Waffe", "Ruestung"
            , "Conan", "Xenia", "Blobb", "SchwarzerRitter", "Kaempfer"
    };
    
    private String[][][] geforderteMethoden ={
        {{"Waffe"},{"int"},{"zuhauen"},{"int"}} 
            , {{"Ruestung"},{"int"},{"abwehr"},{"int"}}
            , {{"Conan"},{"int"},{"kaempfen"},{}}
            , {{"Conan"},{"int"},{"abwehren"},{"int"}}
            , {{"Conan"},{"void"},{"nimmWaffe"},{"Waffe"}}
            , {{"Conan"},{"void"},{"nimmRuestung"},{"Ruestung"}}
            , {{"Xenia"},{"int"},{"kaempfen"},{}}
            , {{"Xenia"},{"int"},{"abwehren"},{"int"}}
            , {{"Xenia"},{"void"},{"nimmWaffe"},{"Waffe"}}
            , {{"Xenia"},{"void"},{"nimmRuestung"},{"Ruestung"}}
            , {{"Blobb"},{"int"},{"kaempfen"},{}}
            , {{"Blobb"},{"int"},{"abwehren"},{"int"}}
            , {{"Blobb"},{"void"},{"nimmWaffe"},{"Waffe"}}
            , {{"Blobb"},{"void"},{"nimmRuestung"},{"Ruestung"}}
            , {{"Kaempfer"},{"int"},{"kaempfen"},{}}
            , {{"Kaempfer"},{"int"},{"abwehren"},{"int"}}
            , {{"Kaempfer"},{"void"},{"nimmWaffe"},{"Waffe"}}
            , {{"Kaempfer"},{"void"},{"nimmRuestung"},{"Ruestung"}}
            , {{"SchwarzerRitter"},{"int"},{"kaempfen"},{}}
            , {{"SchwarzerRitter"},{"int"},{"abwehren"},{"int"}}
            , {{"SchwarzerRitter"},{"void"},{"nimmWaffe"},{"Waffe"}}
            , {{"SchwarzerRitter"},{"void"},{"nimmRuestung"},{"Ruestung"}}
    };
    
    private String[][][] geforderteKonstruktoren ={
        {{"Ausruestung"},{"String","int"}}
            ,{{"Waffe"},{"String","int", "int"}}
            ,{{"Conan"},{"int", "int", "int"}}
            ,{{"Xenia"},{"int", "int", "int"}}
            ,{{"Blobb"},{"int", "int", "int"}}
            ,{{"Kaempfer"},{"int", "int", "int"}}
            ,{{"SchwarzerRitter"},{}}
    }; 
    
    private String[][][] geforderteVariablen = {
        {{"Ausruestung"},{"name"},{"String"}, {"protected"}}
            , {{"Ausruestung"},{"preis"},{"int"}, {"protected"}}    
            , {{"Waffe"},{"staerke"},{"int"}, {"private"}} 
            , {{"Ruestung"},{"schutz"},{"int"}, {"private"}} 
            , {{"Conan"},{"ruestung"},{"Ruestung"}, {"private"}}  
            , {{"Xenia"},{"ruestung"},{"Ruestung"}, {"private"}}
            , {{"Xenia"},{"waffe"},{"Waffe"}, {"private"}}
            , {{"Blobb"},{"waffe"},{"Waffe"}, {"private"}}
            , {{"Kaempfer"},{"gesundheit"},{"int"}, {"protected"}}
            , {{"Kaempfer"},{"geschick"},{"int"}, {"protected"}}
            , {{"Kaempfer"},{"sold"},{"int"}, {"protected"}}
            
    };
    
    private String[][] geforderteVererbungen = {
             {"Waffe", "Ausruestung"}
            ,{"Waffe", "Ausruestung"}
            ,{"Conan", "Kaempfer"} 
            ,{"Xenia", "Kaempfer"} 
            ,{"Blobb", "Kaempfer"}
            ,{"SchwarzerRitter", "Kaempfer"} 
    };
    
    private Object[][][] geforderteErgebnisse ={     
    };
    
    @Test
    @Parameters(method = "klassen")
    public void testKlassenExistieren(String klasse){
            Assert.assertTrue("Klasse " + klasse + " nach Aufgabe gefordert"
                , Alle.findeKlasse(klasse).size() > 0);
    }
    
    private Object klassen(){
        return this.geforderteKlassen;
    }
      
    @Test
    @Parameters(method = "methoden")
    public void testMethodenExistieren(String[][] methode){
            String klasse = methode[0][0];
            String rueckgabetyp = methode[1][0];
            String name = methode[2][0];
            String[] parametertypen = methode[3];
            
            Assert.assertTrue("geforderte Methode " + klasse + ": " + name 
                        + "(" +Arrays.asList(parametertypen) +"): " 
                        + rueckgabetyp + " nicht gefunden"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse
                            , rueckgabetyp, name, parametertypen).size() > 0);           
    }
        
    private Object methoden(){
        return geforderteMethoden;
    }
    
    @Test
    @Parameters(method = "konstruktoren")
    public void testKonstruktorenExistieren(String[][] methode){
            String klasse = methode[0][0];
            String[] parametertypen = methode[1];
            Assert.assertTrue("geforderter Konstruktor " + klasse +"(" 
                    +Arrays.asList(parametertypen) + " nicht gefunden"
                    , Alle.klasseHatKonstruktorParametertypenUnbekannt(klasse
                            , parametertypen).size() > 0);           
    }  
    
    private Object konstruktoren(){
        return geforderteKonstruktoren;
    }
    
    @Test
    @Parameters(method = "variablen")
    public void testVariablenExistieren(String[][] var){
            String klasse = var[0][0];
            String name = var[1][0];
            String typ = var[2][0];
            String[] sichtbar = var[3]; 
            Assert.assertTrue("Geforderte Variable " + name + " in Klasse"
                        + klasse + " mit Typ " + typ + " fehlt"
                    , Alle.klasseHatVariableVomTypMitSichtbarkeitUndArt(klasse
                            , name, typ, sichtbar));
    }
    
    private Object variablen(){
        return this.geforderteVariablen;
    }
    
    @Test
    @Parameters(method = "variablen")
    public void geforderteGetUndSetMethoden(String[][] var){
            String klasse = var[0][0];
            String name = var[1][0];
            String typ = var[2][0];
            Assert.assertTrue("Zur geforderten Variable " + name + " in Klasse "
                    + klasse + " mit Typ " + typ + " fehlt die get-Methode"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse, typ
                            , "get"+upper(name)).size() > 0);
            Assert.assertTrue("Zur geforderten Variable " + name + " in Klasse "
                    + klasse + " mit Typ " + typ + " fehlt die set-Methode"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse, "void"
                            , "set"+upper(name), typ).size() > 0);               
    }
    
    private String upper(String name){
        String tmp = name.toUpperCase();
        return tmp.charAt(0) + name.substring(1);
    }
    
    @Test
    @Parameters(method = "vererbungen")
    public void testGeforderteVererbungen(String[] erbe){
            Assert.assertTrue("Klasse " + erbe[0] + " sollte von " + erbe[1]
                    + " erben"
                    , Alle.erbtVon(erbe[0], erbe[1]));
    }
    
    private Object vererbungen(){
        return this.geforderteVererbungen;
    }
    
//    @Test
//    //@Ignore // enthaelt hier keine Tests
//    @Parameters(method = "methodenergebnisse")
//    public void testMethodenLiefernErgebnisse(Object[][] methode){
//            String klasse = methode[0][0].toString();
//            String rueckgabetyp = methode[1][0].toString();
//            String name = methode[2][0].toString();
//            String[] parametertypen = alsStrings(methode[3]);
//            String ergebnis = methode[4][0].toString();
//            Object[] aufrufwerte = methode[5]; 
//            Method m = Alle.klasseHatMethodeParameterUnbekannt(klasse
//                           , rueckgabetyp, name, parametertypen)
//                      .get(0);
//            Assert.assertTrue("Methode " + klasse + ": " + name 
//                    + "(" +Arrays.asList(parametertypen) +"): " + rueckgabetyp 
//                    + " liefert mit Parametern " + Arrays.asList(aufrufwerte) 
//                    +" nicht geforderten Wert " + ergebnis
//                    , Alle.methodeLiefertErgebnis(ergebnis, m));           
//    }
    
    private Object methodenergebnisse(){
        return this.geforderteErgebnisse;
    }
    
    private String[] alsStrings(Object[] arr){
        String[] ergebnis = new String[arr.length];
        for(int i=0; i < arr.length; i++){
            ergebnis[i] = arr[i].toString();
        }
        return ergebnis;
    }
    
    @Test
    // Conan muss Variable mit Namen waffen haben, die zwei Waffen-Objekte
    // enthalten kann; dies kann Collection oder Array sein
    public void testGenutzteSammlung(){
        List<String> typen = Alle.klasseHatVariableMitName("Conan", "waffen");
        String waffe = Alle.findeKlasse("Waffe").get(0);
        boolean gefunden = false;
        for(String typ:typen){
            if (Alle.klasseImplementiertInterface(typ, "java.util.Collection")
                    || typ.equals("[L" + waffe+";")){
                gefunden = true;
            }
        }
        Assert.assertTrue("Variable waffe in Conan sollte "
                + "Array oder Standard-Collection nutzen"
                , gefunden);
    }
    
    private List<Object> waffen(){
        List<Object> ergebnis = new ArrayList<>();
        Constructor con = Alle.klasseHatKonstruktorParametertypenBekannt("Waffe"
                , String.class, int.class, int.class).get(0);
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Waffe0", 0, 20));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Waffe10", 10, 30));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Waffe6", 6, 20));
        return ergebnis;
    }

    private List<Object> ruestung(){
        List<Object> ergebnis = new ArrayList<>();
        Constructor con = Alle.klasseHatKonstruktorParametertypenBekannt("Ruestung"
                , String.class, int.class, int.class).get(0);
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Ruestung0", 0, 20));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Ruestung10", 10, 30));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, "Ruestung6", 6, 20));
        return ergebnis;
    } 
    
     private List<Object> kaempfer(){
        List<Object> ergebnis = new ArrayList<>();
        Constructor con = Alle.klasseHatKonstruktorParametertypenBekannt("Conan"
                , int.class, int.class, int.class).get(0);
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 0, 20));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 10, 30));
        con = Alle.klasseHatKonstruktorParametertypenBekannt("Xenia", int.class
                , int.class, int.class).get(0);
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 0, 20));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 10, 30));
        con = Alle.klasseHatKonstruktorParametertypenBekannt("Blobb", int.class
                , int.class, int.class).get(0);
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 0, 20));
        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con, 100, 10, 30));
//        con = Alle.klasseHatKonstruktorParameterBekannt("SchwarzerRitter").get(0);
//        ergebnis.add(Alle.erzeugeObjektMitKonstruktor(con));
        return ergebnis;
    }  
     
    @Test
    @Parameters(method = "waffen")
    public void testWaffen1(Object waffe){
        Method m = Alle.klasseHatMethodeParametertypenBekannt("Waffe", int.class
                , "zuhauen", int.class).get(0);
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(waffe, m, 0);
        Assert.assertTrue("Methode zuhauen muss immer nicht negativen "
                + "Wert liefern (geschick=0)"
            , ((int)erg) >= 0);
    } 

    @Test
    @Parameters(method = "waffen")
    public void testWaffen2(Object waffe){
        Method m = Alle.klasseHatMethodeParametertypenBekannt("Waffe", int.class
                , "zuhauen", int.class).get(0);
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(waffe, m, 10);
        Assert.assertTrue("Methode zuhauen muss immer nicht negativen Wert "
                + "liefern (geschick=10)"
            , ((int)erg) >= 0);
    }  
    
    @Test
    @Parameters(method = "ruestung")
    public void testRuestung1(Object ruestung){
        Method m = Alle.klasseHatMethodeParametertypenBekannt("Ruestung", int.class
                , "abwehr", int.class).get(0);
        int angriff = 0;
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(ruestung, m
                , angriff);
        Assert.assertTrue("Methode abwehr muss immer nicht negativen Wert "
                + "liefern (angriff=0)"
            , ((int)erg) >= 0);
        Assert.assertTrue("Methode abwehr darf Angriffswert nicht vergroessern,"
                + " vorher 0, jetzt " + erg
            , ((int)erg) <= angriff);        
    } 

    @Test
    @Parameters(method = "ruestung")
    public void testRuestung2(Object ruestung){
        Method m = Alle.klasseHatMethodeParametertypenBekannt("Ruestung", int.class
                , "abwehr", int.class).get(0);
        int angriff = 10;
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(ruestung, m
                , angriff);
        Assert.assertTrue("Methode abwehr muss immer nicht negativen Wert "
                + "liefern (angriff=10)"
            , ((int)erg) >= 0);
        Assert.assertTrue("Methode abwehr darf Angriffswert nicht vergroessern,"
                + " vorher 10, jetzt " + erg
            , ((int)erg) <= angriff);        
    }

    public Object szenarien(){
        List<Object> ergebnis = new ArrayList<>();
        for(Object k:kaempfer()){
            for(Object w:waffen()){
                for(Object r:ruestung()){
                    ergebnis.add(new Object[]{k,w,r});
                }
            }
        }
        return ergebnis;
    }
    
    @Test
    @Parameters(method = "szenarien")
    // Tests evtl. aufteilen
    public void testKaempfer1(Object kaempfer, Object waffe, Object ruestung){
        Method m = Alle.klasseHatMethodeParametertypenUnbekannt(
                kaempfer.getClass().getSimpleName(), "void"
                , "nimmWaffe", "Waffe").get(0);
        Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, waffe);
        m = Alle.klasseHatMethodeParametertypenUnbekannt(
                kaempfer.getClass().getSimpleName(), "void"
                , "nimmRuestung", "Ruestung").get(0);
        Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, ruestung);
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "kaempfen").get(0);
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m);
        Assert.assertTrue("Methode kaempfen muss immer nicht negativen "
                + "Wert liefern"
            , ((int)erg) >= 0);
       
        System.out.println("kaempfer: " + kaempfer);
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "getGesundheit").get(0);
        int gesundheit = (int)Alle.fuehreAufObjektMethodeMitParameternAus(
                kaempfer, m);
        
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class, "abwehren"
                , int.class).get(0);
        int angriff = 0;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);
        gesundheit = (int)erg;
        angriff = 100;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);
    }     
    
    @Test
    @Parameters(method = "kaempfer")
    public void testKaempfer2(Object kaempfer){
        Method  m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "kaempfen").get(0);
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m);
        Assert.assertTrue("Methode kaempfen muss immer nicht negativen"
                + " Wert liefern"
            , ((int)erg) >= 0);
       
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "getGesundheit").get(0);
        int gesundheit = (int)Alle.fuehreAufObjektMethodeMitParameternAus(
                kaempfer, m);
        
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "abwehren", int.class).get(0);
        int angriff = 0;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);
        gesundheit = (int)erg;
        angriff = 100;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);
    } 
    
    @Test
    public void testSchwarzerRitter(){
        Constructor con = Alle
                .klasseHatKonstruktorParametertypenBekannt("SchwarzerRitter").get(0);
        Object kaempfer = Alle.erzeugeObjektMitKonstruktor(con);
        Method  m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "kaempfen").get(0);
        Object erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m);
        Assert.assertTrue("Methode kaempfen muss immer nicht negativen"
                + " Wert liefern"
            , ((int)erg) >= 0);
       
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "getGesundheit").get(0);
        int gesundheit = (int)Alle.fuehreAufObjektMethodeMitParameternAus(
                kaempfer, m);
        
        m = Alle.klasseHatMethodeParametertypenBekannt(
                kaempfer.getClass().getSimpleName(), int.class
                , "abwehren", int.class).get(0);
        int angriff = 0;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);
        gesundheit = (int)erg;
        angriff = 100;
        erg = Alle.fuehreAufObjektMethodeMitParameternAus(kaempfer, m, angriff);
        Assert.assertTrue("Ein Angriff darf die Gesundheit nicht erhoehen"
            , ((int)erg) <= gesundheit);       
    }
}
