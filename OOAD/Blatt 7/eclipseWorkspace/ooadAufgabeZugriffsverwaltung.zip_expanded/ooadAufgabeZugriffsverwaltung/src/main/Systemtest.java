package main;

import nutzerverwaltung.interaktiv.Messages;
import nutzerverwaltung.interaktiv.Zugriffsdialog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class Systemtest {
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
    
    private void anmelden(String login, String passwort){
        String[] inputs = {login, passwort};
        this.systemInMock.provideLines(inputs);
        this.dialog.nutzerWechseln();        
    }
    
    private void sysadminHinzu(String login, String passwort){
        String[] inputs = {login, passwort, "1"};
        this.systemInMock.provideLines(inputs);
        this.dialog.nutzerHinzufuegen();        
    }
    
    private void projektadminHinzu(String login, String passwort){
        String[] inputs = {login, passwort, "2"};
        this.systemInMock.provideLines(inputs);
        this.dialog.nutzerHinzufuegen();        
    } 
    
    private void entwicklerHinzu(String login, String passwort){
        String[] inputs = {login, passwort, "3"};
        this.systemInMock.provideLines(inputs);
        this.dialog.nutzerHinzufuegen();        
    } 
    
    private void passwortAendern(String alt, String neu){
        String[] inputs = {alt, neu};
        this.systemInMock.provideLines(inputs);
        this.dialog.passwortAendern();        
    }

    private void loginAendern(String alt, String neu){
        String[] inputs = {alt, neu};
        this.systemInMock.provideLines(inputs);
        this.dialog.loginVeraendern();        
    }  
    
    @Test
    public void testStartOhneNutzer1(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("x", "x");
        Assert.assertTrue("Nicht vorhandener Nutzer x kann sich anmelden",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.27")));
    }
    
    @Test
    public void testStartOhneNutzer2(){
        this.dialog = new Zugriffsdialog();
        this.entwicklerHinzu("x", "x");
        Assert.assertTrue("Ohne erste Anmeldung kein neuer Entwickler",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }
    
    @Test
    public void testStartOhneNutzer3(){
        this.dialog = new Zugriffsdialog();
        this.projektadminHinzu("x", "x");
        Assert.assertTrue("Ohne erste Anmeldung kein neuer Projektadmin",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }

    @Test
    public void testStartOhneNutzer4(){
        this.dialog = new Zugriffsdialog();
        this.sysadminHinzu("x", "x");
        Assert.assertTrue("Ohne erste Anmeldung kein neuer Sysadmin",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    } 
    
    @Test
    public void testStartOhneNutzer5(){
        this.dialog = new Zugriffsdialog();
        this.passwortAendern("admin", "x");
        Assert.assertTrue("Ohne erste Anmeldung kein Passwort aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.23")));
    } 
    
    @Test
    public void testStartOhneNutzer6(){
        this.dialog = new Zugriffsdialog();
        this.loginAendern("admin", "x");
        Assert.assertTrue("Ohne erste Anmeldung kein Login aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.11")));
    }

    @Test 
    public void testAdminNachAnforderungEingerichtet(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        Assert.assertTrue("Nutzer (admin,admin) muss vorhanden sein",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }
    
    @Test 
    public void testAdminKannAdminEinrichten(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ad", "ad2");
        Assert.assertTrue("Sysadmin muss neuen Sysadmin einrichten koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.18")));
    }  
    
    @Test 
    public void testNeuerAdminKannSichAnmelden(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ad", "ad2");
        this.systemOutMock.clearLog();
        this.anmelden("ad", "ad2");
        Assert.assertTrue("neuer Sysadmin muss sich anmelden koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }
    
    @Test 
    public void testAdminKannProjektAdminEinrichten(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("pro", "pro2");
        Assert.assertTrue("Sysadmin muss neuen Projektadmin einrichten koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.18")));
    }  
    
    @Test 
    public void testNeuerProjektAdminKannSichAnmelden(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("pro", "pro2");
        this.systemOutMock.clearLog();
        this.anmelden("pro", "pro2");
        Assert.assertTrue("neuer Projektadmin muss sich anmelden koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }

    @Test 
    public void testAdminKannEntwicklerEinrichten(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        Assert.assertTrue("Sysadmin muss neuen Entwickler einrichten koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.18")));
    }  
    
    @Test 
    public void testNeuerEntwicklerKannSichAnmelden(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ent2");
        Assert.assertTrue("neuer Entwickler muss sich anmelden koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    } 
    
    @Test 
    public void testProjektAdminKannKeinenSysadminErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("pro", "pro2");
        this.anmelden("pro", "pro2");
        this.systemOutMock.clearLog();
        this.sysadminHinzu("a", "b");
        Assert.assertTrue("Projektadmin darf keinen Sysadmin erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }  
    
    @Test 
    public void testProjektAdminKannKeinProjektadminErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("pro", "pro2");
        this.anmelden("pro", "pro2");
        this.systemOutMock.clearLog();
        this.projektadminHinzu("a", "b");
        Assert.assertTrue("Projektadmin darf keinen Projektadmin erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }

    @Test 
    public void testProjektAdminKannKeinenEntwicklerErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("pro", "pro2");
        this.anmelden("pro", "pro2");
        this.systemOutMock.clearLog();
        this.entwicklerHinzu("a", "b");
        Assert.assertTrue("Projektadmin darf keinen Entwickler erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    } 
    
    @Test 
    public void testEntwicklerKannKeinenSysadminErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.sysadminHinzu("a", "b");
        Assert.assertTrue("Entwickler darf keinen Sysadmin erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }  
    
    @Test 
    public void testEntwicklerKannKeinProjektadminErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.projektadminHinzu("a", "b");
        Assert.assertTrue("Entwickler darf keinen Projektadmin erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }

    @Test 
    public void testEntwicklerKannKeinenEntwicklerErzeugen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.entwicklerHinzu("a", "b");
        Assert.assertTrue("Entwickler darf keinen Entwickler erzeugen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.19")));
    }

    @Test 
    public void testEntwicklerKannKorrektesPasswortAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("ent2", "ente");
        Assert.assertTrue("Entwickler darf eigenes Passwort aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.22")));
    } 
    
     @Test 
    public void testEntwicklerDarfFalschesPasswortNichtZumAendernNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("enton", "ente");
        Assert.assertTrue("Entwickler darf falsches Passwort nicht zur "
                + "Passwortaenderung nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.23")));
    }   
    
    @Test 
    public void testEntwicklerKannNeuesPasswortNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ente");
        Assert.assertTrue("Entwickler kann neues Passwort nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }   
    
    @Test 
    public void testEntwicklerDarfFalschesPasswortNichtNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ent2");
        Assert.assertTrue("Entwickler darf falsches Passwort nicht nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.27")));
    }  
    
    @Test 
    public void testSysadminKannKorrektesPasswortAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("ent2", "ente");
        Assert.assertTrue("Sysadmin darf eigenes Passwort aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.22")));
    } 
    
    @Test 
    public void testSysadminKannFalschesPasswortNichtZumAendernNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("enton", "ente");
        Assert.assertTrue("Sysadmin darf falsches Passwort nicht zur "
                + "Passwortaenderung nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.23")));
    }    
    
    @Test 
    public void testSysadminKannNeuesPasswortNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ente");
        Assert.assertTrue("Sysadmin kann neues Passwort nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }   
    
    @Test 
    public void testSysadminDarfFalschesPasswortNichtNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ent2");
        Assert.assertTrue("Sysadmin darf falsches Passwort nicht nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.27")));
    }     

    
    @Test 
    public void testProjektadminKannKorrektesPasswortAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("ent2", "ente");
        Assert.assertTrue("Projektadmin darf eigenes Passwort aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.22")));
    } 

    @Test 
    public void testProjektadminKannFalschesPasswortNichtZumAendernNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.passwortAendern("enton", "ente");
        Assert.assertTrue("Projektadmin darf falsches Passwort nicht zur "
                + "Passwortaenderung nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.23")));
    }    
    
    @Test 
    public void testProjektadminKannNeuesPasswortNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ente");
        Assert.assertTrue("Projektadmin kann neues Passwort nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }   
    
    @Test 
    public void testProjektadminDarfFalschesPasswortNichtNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.passwortAendern("ent2", "ente");
        this.anmelden("admin", "admin");
        this.systemOutMock.clearLog();
        this.anmelden("ent", "ent2");
        Assert.assertTrue("Projektadmin darf falsches Passwort nicht nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.27")));
    }   
    
    @Test 
    public void testEntwicklerKannLoginNichtAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.entwicklerHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.loginAendern("ent", "ente");
        Assert.assertTrue("Entwickler darf Login nicht aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.11")));
    } 
    
    @Test 
    public void testSysadminKannKorrektesLoginAendern1(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.loginAendern("admin", "ente");
        Assert.assertTrue("Sysadmin darf Logins aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.10")));
    } 
    
    @Test 
    public void testSysadminKannKorrektesLoginAendern2(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.systemOutMock.clearLog();
        this.loginAendern("ent", "ente");
        Assert.assertTrue("Sysadmin darf Logins aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.10")));
    }  
    
    @Test 
    public void testSysadminKannNichtExistentesLoginNichtAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.systemOutMock.clearLog();
        this.loginAendern("administer", "ente");
        Assert.assertTrue("Sysadmin darf nicht existentes Login nicht aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.11")));
    }    
    
    @Test 
    public void testNutzerKannNeuesLoginNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.loginAendern("admin", "ente");
        this.systemOutMock.clearLog();
        this.anmelden("ente", "admin");
        Assert.assertTrue("Nutzer muss veraendertes Login nutzen koennen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.26")));
    }   
    
    @Test 
    public void testNutzerDarfFalschesLoginNichtNutzen(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.sysadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.loginAendern("admin", "ente");
        this.systemOutMock.clearLog();
        this.anmelden("admin", "admin");
        Assert.assertTrue("Nutzer darf veraendertes/altes Login nicht nutzen",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.27")));
    }     
    
    @Test 
    public void testProjektadminKannLoginNichtAendern(){
        this.dialog = new Zugriffsdialog();
        this.anmelden("admin", "admin");
        this.projektadminHinzu("ent", "ent2");
        this.anmelden("ent", "ent2");
        this.systemOutMock.clearLog();
        this.loginAendern("ent", "ente");
        Assert.assertTrue("Projektadmin darf Logins nicht aendern",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Zugriffsdialog.11")));
    } 
}
