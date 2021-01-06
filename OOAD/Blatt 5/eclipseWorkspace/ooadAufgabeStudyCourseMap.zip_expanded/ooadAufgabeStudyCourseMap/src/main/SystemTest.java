package main;

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
    public void setUp(){
        this.dialog = new Dialog();
    }
    
    private void studentHinzu(int mat, String name){
        String[] inputs = {""+mat, name};
        this.systemInMock.provideLines(inputs);
        this.dialog.newStudent();
    }
    
    private void studentLoeschen(int mat){
        String[] inputs = {""+mat};
        this.systemInMock.provideLines(inputs);
        this.dialog.removeStudent();       
    }
    
    private void studentSuchen(int mat){
        String[] inputs = {""+mat};
        this.systemInMock.provideLines(inputs);
        this.dialog.searchStudent();       
    }
    
    @Test
    public void testErsterStudent(){
        this.studentHinzu(42, "Ute Utemeier");
        Assert.assertTrue("Student sollte hinzugefuegt sein: 42 Ute Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*student added.*"
                        , this.systemOutMock.getLog()));
    }
    
    @Test
    public void testKeineDoppeltenStudenten(){
        this.studentHinzu(42, "Ute Utemeier");
        this.systemOutMock.clearLog();
        this.studentHinzu(42, "Ulf Utemeier");
        Assert.assertTrue("Student sollte nicht hinzugefuegt sein: 42 Ulf Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*student not added.*"
                        , this.systemOutMock.getLog()));
    }  
    
    @Test
    public void testStudentErfolgreichZeigen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(43, "Ulf Schmidt");
        this.systemOutMock.clearLog();
        this.dialog.showCourse();
        Assert.assertTrue("Student sollte vorhanden sein: 42 Ute Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*42.*Ute.*Utemeier.*"
                        , this.systemOutMock.getLog()));
        Assert.assertTrue("Student sollte vorhanden sein: 43 Ulf Schmidt: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*43.*Ulf.*Schmidt.*"
                        , this.systemOutMock.getLog()));        
    }
    
    @Test
    public void testExistierendenStudentLoeschen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(43, "Ulf Schmidt");
        this.systemOutMock.clearLog();
        this.studentLoeschen(42);
        Assert.assertTrue("Student sollte geloescht sein: 42 Ute Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*student removed.*"
                        , this.systemOutMock.getLog()));     
    } 

    @Test
    public void testNichtExistierendenStudentLoeschen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(43, "Ulf Schmidt");
        this.studentLoeschen(42);
        this.systemOutMock.clearLog();
        this.studentLoeschen(42);
        Assert.assertTrue("Student sollte nicht mehr loeschbar sein: 42 Ute Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*student not removed.*"
                        , this.systemOutMock.getLog()));     
    }    
    
    
    @Test
    public void testExistierendenStudentLoeschenUndAnzeigen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(43, "Ulf Schmidt");
        this.studentLoeschen(42);
        this.systemOutMock.clearLog();
        this.dialog.showCourse();
        Assert.assertFalse("Student sollte nicht vorhanden sein: 42 Ute Utemeier: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*42.*Ute.*Utemeier.*"
                        , this.systemOutMock.getLog()));
        Assert.assertTrue("Student sollte vorhanden sein: 43 Ulf Schmidt: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*43.*Ulf.*Schmidt.*"
                        , this.systemOutMock.getLog()));        
    }    
    
    @Test
    public void testExistierendenStudentSuchen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(43, "Ulf Schmidt");   
        this.systemOutMock.clearLog();
        this.studentSuchen(43);
        Assert.assertTrue("Student sollte gefunden werden: 43 Ulf Schmidt: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*43.*Ulf.*Schmidt.*"
                        , this.systemOutMock.getLog()));
    }
    
    @Test
    public void testNichtExistierendenStudentSuchen(){
        this.studentHinzu(42, "Ute Utemeier");
        this.studentHinzu(44, "Ulf Schmidt");   
        this.systemOutMock.clearLog();
        this.studentSuchen(43);
        Assert.assertTrue("Student sollte nicht gefunden werden: 43 Ulf Schmidt: "
                     + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*student not found.*"
                        , this.systemOutMock.getLog()));
    }
    
    @Test
    public void testDialogVollstaendigDurchlaufen(){
        String[] inputs = {"1", "73", "Sergej Ob"
                , "1", "74", "Anna Zusic"
                , "1", "73", "Heinz Meier"
                , "3", "74"
                , "3", "42"
                , "4"
                , "2", "73"
                , "2", "73"
                , "0"};
        this.systemInMock.provideLines(inputs);
        this.dialog.control();
        String ausgabe = this.systemOutMock.getLog();
        Assert.assertTrue("Zwei Studenten sollten erfolgreich hinzugefuegt werden: "
                     + ausgabe
                , Pattern.matches("(?s)(.*student added.*){2}", ausgabe));
        Assert.assertTrue("Ein Student sollte nicht erfolgreich hinzugefuegt werden: "
                     + ausgabe
                , Pattern.matches("(?s).*student not added.*", ausgabe));
        Assert.assertTrue("Ein Student sollte erfolgreich gefunden und ausgegeben werden: "
                     + ausgabe
                , Pattern.matches("(?s)(.*74.*Anna.*Zusic.*){2}", ausgabe));
        Assert.assertTrue("Ein Student sollte erfolgreich ausgegeben werden: "
                     + ausgabe
                , Pattern.matches("(?s).*73.*Sergej.*Ob.*", ausgabe));        
        Assert.assertTrue("Ein Student nicht gefunden werden: "
                     + ausgabe
                , Pattern.matches("(?s).*student not found.*", ausgabe));
        Assert.assertTrue("Ein Student sollte erfolgreich geloescht werden: "
                     + ausgabe
                , Pattern.matches("(?s).*student removed.*", ausgabe));
        Assert.assertTrue("Ein Student konnte nicht geloescht werden: "
                     + ausgabe
                , Pattern.matches("(?s).*student not removed.*", ausgabe));
    }    
}
