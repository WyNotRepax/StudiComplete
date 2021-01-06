package main;

import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class SystemTest {

    private Zugriffsdialog dialog;
    
    @Rule
    public final SystemOutRule systemOutMock = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = TextFromStandardInputStream.emptyStandardInputStream();

    @Before
    public void setUp(){
        this.dialog = new Zugriffsdialog();
    }
    
    private void neuesBild(String serie, int nr){
        String[] inputs = {serie, ""+nr};
        this.systemInMock.provideLines(inputs);
        this.dialog.sammelbildHinzufuegen();        
    }
    
    private void bildHerausgeben(String serie, int nr, int anzahl){
        String[] inputs = {serie, ""+nr, ""+anzahl};
        this.systemInMock.provideLines(inputs);
        this.dialog.sammelbilderHerausgeben();
    }   
    
    private void serieZeigen(String serie){
        String[] inputs = {serie};
        this.systemInMock.provideLines(inputs);
        this.dialog.serienbilderZeigen();
    }
    
    private void basisdaten(){
        Object[][] basis = {{"WM 2014", 123, 2}
                , {"EM 2016", 42, 3}
                , {"EM 2016", 71, 1}
        };
        for(Object[] eingabe:basis){
            for(int i=0; i< (int)eingabe[2]; i++){
                this.neuesBild(eingabe[0].toString(), (int)eingabe[1]);
            }
        }
    }
    
    @Test
    public void testHinzu1(){
        this.neuesBild("XYZ", 100);
        this.systemOutMock.clearLog();
        this.dialog.gesamtbestand();
        Assert.assertTrue("Bild (XYZ,100), XYZ nicht befunden "
                , this.systemOutMock.getLog().contains("XYZ"));
        Assert.assertTrue("Bild (XYZ,100), 100 nicht befunden "
                , this.systemOutMock.getLog().contains("100"));
    }
    
    @Test
    public void testHinzu2(){
        this.neuesBild("XYZ", 100);
        this.neuesBild("XYZ", 100);
        this.systemOutMock.clearLog();
        this.dialog.gesamtbestand();
        Assert.assertFalse("doppeltes Bild (XYZ,100) nicht zweimal ausgeben"
                , Pattern.matches("(?s).*100.*100.*", this.systemOutMock.getLog()));
    }
    
    @Test
    public void testStandardausgabeVonMultisetGenutzt(){
        this.basisdaten();
        this.systemOutMock.clearLog();
        this.dialog.gesamtbestand();
        Assert.assertTrue("Standardausgabe 'x 2' fuer Doppelte nutzen "
                , this.systemOutMock.getLog().contains("x 2"));
        Assert.assertTrue("Standardausgabe 'x 3' fuer Dreifache nutzen "
                , this.systemOutMock.getLog().contains("x 3"));   
    }
    
    @Test
    public void testHinzuMehrfach(){
        this.basisdaten();
        this.neuesBild("EM 2016", 42);
        this.systemOutMock.clearLog();
        this.dialog.gesamtbestand();
        Assert.assertTrue("'x 4' fuer vierfaches Bild nicht gefunden "
                , this.systemOutMock.getLog().contains("x 4"));   
    }
    
    @Test
    public void testSerieZeigen1(){
        this.serieZeigen("ABC");
        Assert.assertFalse("Bildnummer in nicht existenter Serie gefunden"
                , Pattern.matches("(?s).*[0-9].*", this.systemOutMock.getLog()));
    }
 
    @Test
    public void testserieZeigen2(){
        this.basisdaten();
        this.systemOutMock.clearLog();  
        this.serieZeigen("EM 2016");
        Assert.assertFalse("keine doppelte Nummer fuer EM 16 in Basisdaten"
                , this.systemOutMock.getLog().contains("x 2"));
        Assert.assertTrue("Standardausgabe 'x 3' fuer dreifache Nummern nutzen "
                , this.systemOutMock.getLog().contains("x 3")); 
    }
    
    @Test
    public void testHerausgeben1(){
        this.bildHerausgeben("XYZ", 1, 1);
        Assert.assertTrue("nicht existentes Bild herausgegeben"                , this.systemOutMock.getLog().contains("nicht erfolgreich"));
    }
    
    @Test
    public void testHerausgeben2(){
        this.basisdaten();
        this.systemOutMock.clearLog();
        this.bildHerausgeben("EM 2016", 42, 3);
        Assert.assertFalse("existierende Bilder nicht herausgegeben: "
                        + this.systemOutMock.getLog()
                , this.systemOutMock.getLog().contains("nicht erfolgreich"));
    }

    @Test
    public void testHerausgeben3(){
        this.basisdaten();
        this.systemOutMock.clearLog();
        this.bildHerausgeben("EM 2016", 42, 1);
        this.dialog.gesamtbestand();
        Assert.assertFalse("existierende Bilder nicht herausgegeben"
                , this.systemOutMock.getLog().contains("nicht erfolgreich"));
        Assert.assertTrue("Bildanzahl von (EM 2016, 42) nicht korrekt reduziert; "
                    + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*x 2.*x 2.*", this.systemOutMock.getLog()));        
    }  

    @Test
    public void testHerausgeben4(){
        this.basisdaten();
        this.neuesBild("EM 2016", 42);
        this.systemOutMock.clearLog();
        this.bildHerausgeben("EM 2016", 42, 2);
        this.dialog.gesamtbestand();
        Assert.assertFalse("existierende Bilder nicht herausgegeben"
                , this.systemOutMock.getLog().contains("nicht erfolgreich"));
        Assert.assertTrue("Bildanzahl von (EM 2016, 42) nicht korrekt reduziert; "
                    + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*x 2.*x 2.*", this.systemOutMock.getLog()));        
    }    
}
