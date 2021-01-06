package main;

import classmodel.Alle;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class StrukturUndBasistests {
        // besser in einzelne Tests aufspalten
    private String[] geforderteKlassen = {"Zugriffsverwaltung", "Nutzer"
            , "Projektadministrator", "Systemadministrator", "Entwickler"
    };
    
    private String[][][] geforderteMethoden ={
        {{"Zugriffsverwaltung"},{"boolean"},{"authentifizieren"},{"String","String"}} 
       ,{{"Zugriffsverwaltung"},{"boolean"},{"entwicklerHinzufuegen"},{"String","String"}}
       ,{{"Zugriffsverwaltung"},{"boolean"},{"loginAendern"},{"String","String"}}  
       ,{{"Zugriffsverwaltung"},{"void"},{"nutzerAnzeigen"},{}}    
       ,{{"Zugriffsverwaltung"},{"boolean"},{"passwortAendern"},{"String","String"}}
       ,{{"Zugriffsverwaltung"},{"boolean"},{"projektadministratorHinzufuegen"},{"String","String"}} 
       ,{{"Zugriffsverwaltung"},{"boolean"},{"systemadministratorHinzufuegen"},{"String","String"}}
       ,{{"Projektadministrator"},{"boolean"},{"darfDatenBearbeiten"},{}}  
       ,{{"Projektadministrator"},{"boolean"},{"darfTabellenBearbeiten"},{}}
       ,{{"Projektadministrator"},{"boolean"},{"darfNutzerAnlegen"},{}}
       ,{{"Systemadministrator"},{"boolean"},{"darfDatenBearbeiten"},{}}  
       ,{{"Systemadministrator"},{"boolean"},{"darfTabellenBearbeiten"},{}}
       ,{{"Systemadministrator"},{"boolean"},{"darfNutzerAnlegen"},{}}
       ,{{"Entwickler"},{"boolean"},{"darfDatenBearbeiten"},{}}  
       ,{{"Entwickler"},{"boolean"},{"darfTabellenBearbeiten"},{}}
       ,{{"Entwickler"},{"boolean"},{"darfNutzerAnlegen"},{}}      
    };
    
    private String[][][] geforderteKonstruktoren ={
        {{"Zugriffsverwaltung"},{}}
            ,{{"Projektadministrator"},{}}
            ,{{"Systemadministrator"},{}}
            ,{{"Entwickler"},{}}
            ,{{"Nutzer"},{}}
            ,{{"Nutzer"},{"String","String"}}
    }; 
    
    private String[][][] geforderteVariablen = {
        {{"Zugriffsverwaltung"},{"aktuellerNutzer"},{"Nutzer"}, {"private"}}
        , {{"Nutzer"},{"login"},{"String"}, {"private"}}    
        , {{"Nutzer"},{"passwort"},{"String"}, {"private"}}        
    };
    
    private String[][] geforderteVererbungen = {
        {"Projektadministrator", "Nutzer"}
       ,{"Systemadministrator", "Nutzer"}
       ,{"Entwickler", "Nutzer"}     
    };
    
    private Object[][][] geforderteErgebnisse ={
       {{"Projektadministrator"},{"boolean"},{"darfDatenBearbeiten"},{},{"true"},{}}  
       ,{{"Projektadministrator"},{"boolean"},{"darfTabellenBearbeiten"},{},{"true"},{}}
       ,{{"Projektadministrator"},{"boolean"},{"darfNutzerAnlegen"},{},{"false"},{}}
       ,{{"Systemadministrator"},{"boolean"},{"darfDatenBearbeiten"},{},{"true"},{}}  
       ,{{"Systemadministrator"},{"boolean"},{"darfTabellenBearbeiten"},{},{"true"},{}}
       ,{{"Systemadministrator"},{"boolean"},{"darfNutzerAnlegen"},{},{"true"},{}}
       ,{{"Entwickler"},{"boolean"},{"darfDatenBearbeiten"},{},{"true"},{}}  
       ,{{"Entwickler"},{"boolean"},{"darfTabellenBearbeiten"},{},{"false"},{}}
       ,{{"Entwickler"},{"boolean"},{"darfNutzerAnlegen"},{},{"false"},{}}      
    };
    
    @Test
    @Parameters(method = "klassen")
    public void testKlassenExistieren(String klasse){
//        for(String klasse: geforderteKlassen){
            Assert.assertTrue("Klasse " + klasse + " nach Aufgabe gefordert"
                , Alle.findeKlasse(klasse).size() > 0);
//        }
    }
    
    private Object klassen(){
        return this.geforderteKlassen;
    }
      
    @Test
    @Parameters(method = "methoden")
    public void testMethodenExistieren(String[][] methode){
//        for(String[][] methode: geforderteMethoden){
            String klasse = methode[0][0];
            String rueckgabetyp = methode[1][0];
            String name = methode[2][0];
            String[] parametertypen = methode[3];
            //System.out.println(klasse + ": " + name +"(" +Arrays.asList(parametertypen) +"): " + rueckgabetyp);
            Assert.assertTrue("geforderte Methode " + klasse + ": " + name 
                    + "(" +Arrays.asList(parametertypen) +"): " + rueckgabetyp + " nicht gefunden"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse, rueckgabetyp, name, parametertypen).size() > 0);           
//        }
    }
    
    private Object methoden(){
        return geforderteMethoden;
    }
    
    @Test
    @Parameters(method = "konstruktoren")
//    @TestCaseName("{0}[0], {0}[1]")
    public void testKonstruktorenExistieren(String[][] methode){
//        for(String[][] methode: geforderteKonstruktoren){
            String klasse = methode[0][0];
            String[] parametertypen = methode[1];
            Assert.assertTrue("geforderter Konstruktor " + klasse +"(" 
                    +Arrays.asList(parametertypen) + " nicht gefunden"
                    , Alle.klasseHatKonstruktorParametertypenUnbekannt(klasse, parametertypen).size() > 0);           
//        }
    }  
    
    private Object konstruktoren(){
        return geforderteKonstruktoren;
    }
    
    @Test
    @Parameters(method = "variablen")
    public void testVariablenExistieren(String[][] var){
//        for(String[][] var: geforderteVariablen){
            String klasse = var[0][0];
            String name = var[1][0];
            String typ = var[2][0];
            String[] sichtbar = var[3]; 
            Assert.assertTrue("Geforderte Variable " + name + " in Klasse"
                    + klasse + " mit Typ " + typ + " fehlt"
                    , Alle.klasseHatVariableVomTypMitSichtbarkeitUndArt(klasse, name, typ, sichtbar));
//        }
    }
    
    private Object variablen(){
        return this.geforderteVariablen;
    }
    
    @Test
    @Parameters(method = "variablen")
    public void geforderteGetUndSetMethoden(String[][] var){
//         for(String[][] var: geforderteVariablen){
            String klasse = var[0][0];
            String name = var[1][0];
            String typ = var[2][0];
            Assert.assertTrue("Zur geforderten Variable " + name + " in Klasse"
                    + klasse + " mit Typ " + typ + " fehlt die get-Methode"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse, typ, "get"+upper(name)).size() > 0);
            Assert.assertTrue("Zur geforderten Variable " + name + " in Klasse"
                    + klasse + " mit Typ " + typ + " fehlt die set-Methode"
                    , Alle.klasseHatMethodeParametertypenUnbekannt(klasse, "void", "set"+upper(name), typ).size() > 0);            
//        }       
    }
    
    private String upper(String name){
        String tmp = name.toUpperCase();
        return tmp.charAt(0) + name.substring(1);
    }
    
    @Test
    @Parameters(method = "vererbungen")
    public void testGeforderteVererbungen(String[] erbe){
//        for(String[] erbe: geforderteVererbungen){
            Assert.assertTrue("Klasse " + erbe[0] + " sollte von " + erbe[1]
                    + "erben"
                    , Alle.erbtVon(erbe[0], erbe[1]));
//        }
    }
    
    private Object vererbungen(){
        return this.geforderteVererbungen;
    }
    
    @Test
    public void testGenutzteSammlung(){
        List<String> typen = Alle.klasseHatVariableMitName("Zugriffsverwaltung", "nutzer");
        boolean gefunden = false;
        for(String typ:typen){
            if (Alle.klasseImplementiertInterface(typ, "java.util.Collection")
                    || Alle.klasseImplementiertInterface(typ, "java.util.Map")
                    || typ.equals("java.util.Map")){
                gefunden = true;
            }
        }
        Assert.assertTrue("Variable nutzer in Zugriffsklasse sollte "
                + "Standard-Collection oder Map nutzen"
                , gefunden);
    }

    @Test
    @Parameters(method = "methodenergebnisse")
    public void testMethodenLiefernErgebnisse(Object[][] methode){
//        for(Object[][] methode: geforderteErgebnisse){
            String klasse = methode[0][0].toString();
            String rueckgabetyp = methode[1][0].toString();
            String name = methode[2][0].toString();
            String[] parametertypen = alsStrings(methode[3]);
            String ergebnis = methode[4][0].toString();
            Object[] aufrufwerte = methode[5]; 
            Method m = Alle.klasseHatMethodeParametertypenUnbekannt(klasse
                           , rueckgabetyp, name, parametertypen)
                      .get(0);
            Assert.assertTrue("Methode " + klasse + ": " + name 
                    + "(" +Arrays.asList(parametertypen) +"): " + rueckgabetyp 
                    + " liefert mit Parametern " + Arrays.asList(aufrufwerte) 
                    +" nicht geforderten Wert " + ergebnis
                    , Alle.methodeLiefertErgebnis(ergebnis, m));           
//        }
    }
    
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
}
