package main;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class SystemTest {

    @Rule
    public final SystemOutRule systemOutMock = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = TextFromStandardInputStream.emptyStandardInputStream();

    private Dialog dialog;

    @Before
    public void setUp() {
        this.dialog = new Dialog();
    }

    private void reset() {
        this.ausfuehren(new String[]{"1"});
    }

    private void add(int wert) {
        this.ausfuehren(new String[]{"2", "" + wert});
    }

    private void subtract(int wert) {
        this.ausfuehren(new String[]{"3", "" + wert});
    }

    private void inMemory() {
        this.ausfuehren(new String[]{"4"});
    }

    private void addMemory() {
        this.ausfuehren(new String[]{"5"});
    }

    private void subtractMemory() {
        this.ausfuehren(new String[]{"6"});
    }

    private void undo() {
        this.ausfuehren(new String[]{"98"});
    }

    private void redo() {
        this.ausfuehren(new String[]{"99"});
    }

    private void ausfuehren(String[] inputs) {
        this.systemInMock.provideLines(inputs);
        this.systemOutMock.clearLog();
        //System.out.println("inputs: " + Arrays.asList(inputs));
        this.dialog.einSchritt();
    }

    private Stack<String> ausgegebeneWerte(){
        String out = this.systemOutMock.getLog();
        Pattern pat = Pattern.compile("\\d+");
        Matcher mat = pat.matcher(out);
        Stack<String> werte = new Stack<>();
        while (mat.find()) {
            werte.add(out.substring(mat.start(), mat.end()));
        }
        //System.out.println("werte: " + werte);
        if (werte.size() < 2) {
            throw new IllegalArgumentException("Kann Werte fuer Speicher und "
                    + "Rechner nicht finden, gefordertes Ausgabeformat "
                    + "eingehalten?");
        }
        return werte;
    }
    
    private void erwartet(Integer speicher, Integer rechner) {
        Stack<String> werte = ausgegebeneWerte();
        Assert.assertEquals("Wert des Rechners stimmt nicht mit Erwartung "
                + "ueberein", rechner, Integer.decode(werte.pop()));
        Assert.assertEquals("Wert des Speichers stimmt nicht mit Erwartung "
                + "ueberein", speicher, Integer.decode(werte.pop()));
    }
    
    private boolean wertGefunden(int wert){
        return this.ausgegebeneWerte().contains(""+wert);
    }

    @Test
    public void testUndoOhneVorherigenSchritt() {
        try {
            this.undo();
            erwartet(0, 0);
        } catch (Exception e) {
            Assert.fail("Undo ohne vorherigem Schritt darf nicht zu "
                    + "Abbruch fuehren: " + e);
            e.printStackTrace();
        }
    }

    @Test
    public void testRedoOhneVorherigenSchritt() {
        try {
            this.redo();
            erwartet(0, 0);
        } catch (Exception e) {
            Assert.fail("Redo ohne vorherigem Schritt darf nicht zu "
                    + "Abbruch fuehren: " + e);
        }
    }

    @Test
    public void testResetOhneVorherigenSchritt() {
        try {
            this.reset();
            erwartet(0, 0);
        } catch (Exception e) {
            Assert.fail("Reset ohne vorherigem Schritt darf nicht zu "
                    + "Abbruch fuehren: " + e);
        }
    }

    @Test
    public void testAdd() {
        this.add(21);
        this.add(21);
        erwartet(0, 42);
    }
    
    @Test
    public void testSubstract() {
        this.add(42);
        this.add(42);
        this.subtract(42);
        erwartet(0, 42);
    }
    
    @Test
    public void testToMemory() {
        this.add(21);
        this.inMemory();
        this.add(21);
        erwartet(21, 42);
    }
    
    @Test
    public void testAddMemory() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        erwartet(21, 63);
    }
    
    @Test
    public void testSubtractMemory() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.subtractMemory();
        erwartet(21, 21);
    }
    
    @Test
    public void testReset() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        this.reset();
        erwartet(0, 0);
    }
    
    
    // in der letzten Ausgabe soll kein Undo angeboten werden
    private void keinUndo(){
        this.systemOutMock.clearLog();
        this.dialog.ausgabe();
        Assert.assertFalse("Wenn kein Undo moeglich, soll auch kein "
                + "Undo angeboten werden"
                , this.wertGefunden(98));        
    }
    
    private void keinRedo(){
        this.systemOutMock.clearLog();
        this.dialog.ausgabe();
        Assert.assertFalse("Wenn kein Redo moeglich, soll auch kein "
                + "redo angeboten werden"
                , this.wertGefunden(99));        
    }    
    
    @Test
    public void testKeinUndoAmAnfang() {
        this.keinUndo();
    } 
    
    @Test
    public void testKeinRedoAmAnfang() {
        this.add(21);
        Assert.assertFalse("Wenn kein Redo moeglich, soll auch kein "
                + "Redo am Anfang angeboten werden"
                , this.wertGefunden(99));
    }  
    
    @Test
    public void testKeinRedoOhneUndo() {
        this.add(21);
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        this.reset();
        Assert.assertFalse("Wenn kein Redo moeglich, soll auch kein "
                + "Redo im Verlauf angeboten werden"
                , this.wertGefunden(99));
    }   
    
    @Test
    public void testResetOhneVorherigenSchrittDannUndo() {
        this.reset();
        this.reset(); // erst bei/vor zweiter Aktion Reset sichtbar
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.undo();
        this.keinUndo();
    }

    @Test
    public void testAddDannUndo() {
        this.add(21);
        this.add(21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }
    
    @Test
    public void testSubstractDannUndo() {
        this.add(42);
        this.add(42);
        this.subtract(42);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,84);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,42);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }
    
    @Test
    public void testToMemoryDannUndo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }
    
    @Test
    public void testAddMemoryDannUndo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }
    
    @Test
    public void testSubtractMemoryDannUndo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.subtractMemory();
        Assert.assertTrue("Undo fuer fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fuer fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }
    
    @Test
    public void testResetDannUndo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        this.reset();
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,63);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
    }   
 
    @Test
    public void testResetOhneVorherigenSchrittDannUndoDannRedo() {
        this.reset();
        this.reset(); // erst bei/vor zweiter Aktion Reset sichtbar
        Assert.assertFalse("Kein Redo erwartet"
                , this.wertGefunden(99));
        this.undo();
        erwartet(0,0);
        Assert.assertFalse("Kein Redo erwartet"
                , this.wertGefunden(99));
        this.undo();
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,0);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,0);
        this.keinRedo();
    }

    @Test
    public void testAddDannUndoDannRedo() {
        this.add(21);
        this.add(21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,42);
        this.keinRedo();
    }
    
    @Test
    public void testSubstractDannUndoDannRedo() {
        this.add(42);
        this.add(42);
        this.subtract(42);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,84);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,42);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,42);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,84);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,42);
        this.keinRedo();
    }
    
    @Test
    public void testToMemoryDannUndoDannRedo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,42);
        this.keinRedo();
    }
    
    @Test
    public void testAddMemoryDannUndoDannRedo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,42);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,63);
        this.keinRedo();
    }
    
    @Test
    public void testSubtractMemoryDannUndoDannRedo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.subtractMemory();
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fuer Rest fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,42);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,21);
        this.keinRedo();
    }
    
    @Test
    public void testResetDannUndoDannRedo() {
        this.add(21);
        this.inMemory();
        this.add(21);
        this.addMemory();
        this.reset();
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,63);
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,42);
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(21,21);
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,21);
        Assert.assertTrue("Undo fuer Reset fehlt"
                , this.wertGefunden(98));
        this.undo();
        erwartet(0,0);
        this.keinUndo();
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,21);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,42);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(21,63);
        Assert.assertTrue("Redo erwartet"
                , this.wertGefunden(99));
        this.redo();
        erwartet(0,0);
        this.keinRedo();
        Assert.assertTrue("Undo fuer Redo fehlt"
            , this.wertGefunden(98));
        this.undo();
        erwartet(21,63);
    }     
    
    @Test
    public void testResetDannUndoRedoUndoRedo() {
        this.add(21);
        this.add(21);
        erwartet(0,42);
        this.undo();
        erwartet(0,21);
        this.redo();
        erwartet(0,42);
        this.undo();
        erwartet(0,21);
        this.redo();
        erwartet(0,42);
    }
}
