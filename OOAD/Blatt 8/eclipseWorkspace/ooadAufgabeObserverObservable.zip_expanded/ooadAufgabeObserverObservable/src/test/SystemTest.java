package test;

import java.util.regex.Pattern;
import main.Dialog;
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

    private void mitarbeiterAnlegen(String name, int nr) {
        String[] inputs = {name, "" + nr};
        this.systemInMock.provideLines(inputs);
        this.dialog.mitarbeiterHinzufuegen();
    }

    private void projektAnlegen(String name) {
        String[] inputs = {name};
        this.systemInMock.provideLines(inputs);
        this.dialog.projektwhiteboardHinzufuegen();
    }

    private void abteilungAnlegen(String name, int nr) {
        String[] inputs = {name, "" + nr};
        this.systemInMock.provideLines(inputs);
        this.dialog.abteilungswhiteboardHinzufuegen();
    }

    private void mitarbeiterZuProjekt(int posm, int posp) {
        String[] inputs = {"" + posm, "" + posp};
        this.systemInMock.provideLines(inputs);
        this.dialog.mitarbeiterAnmelden();
        System.out.println("***" + this.systemOutMock.getLog());
    }

    private void projektZuAbteilung(int posp, int posa) {
        String[] inputs = {"" + posp, "" + posa};
        this.systemInMock.provideLines(inputs);
        this.dialog.projektAnmelden();
    }

    private void infoInProjekt(int pos, String nachricht) {
        String[] inputs = {"" + pos, nachricht};
        this.systemInMock.provideLines(inputs);
        this.dialog.projektwhiteboardBeschreiben();
    }

    private void infoInAbteilung(int pos, String nachricht) {
        String[] inputs = {"" + pos, nachricht};
        this.systemInMock.provideLines(inputs);
        this.dialog.abteilungswhiteboardBeschreiben();
    }

    private void datenZeigen() {
        this.systemOutMock.clearLog();
//        String[] inputs={"8"};
//        this.systemInMock.provideLines(inputs);
        this.dialog.uebersichtAnzeigen();
    }

    @Test
    public void gesamtdialogTest() {
        String[] inputs = {"1", "Anna Nase", "1234", "0"};
        this.systemInMock.provideLines(inputs);
        this.dialog.schleife();
    }

    @Test
    public void mitarbeiterAnlegenTest() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.datenZeigen();
        Assert.assertTrue("Anna Nase(42) in Mitarbeiterliste nicht gefunden: "
                + this.systemOutMock.getLog(), this.systemOutMock.getLog().contains("Anna Nase")
                && this.systemOutMock.getLog().contains("42"));
    }

    @Test
    public void projektAnlegenTest() {
        this.projektAnlegen("Perpetuum");
        this.datenZeigen();
        Assert.assertTrue("Perpetuum in Projektliste nicht gefunden: "
                + this.systemOutMock.getLog(), this.systemOutMock.getLog().contains("Perpetuum"));
    }

    @Test
    public void abteilungAnlegenTest() {
        this.abteilungAnlegen("Shield", 4242);
        this.datenZeigen();
        Assert.assertTrue("Shield in Abteilungssliste nicht gefunden: "
                + this.systemOutMock.getLog(), this.systemOutMock.getLog().contains("Shield")
                && this.systemOutMock.getLog().contains("4242"));
    }

    @Test
    public void mitarbeiterInProjektTest() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.datenZeigen();

        Assert.assertTrue("Anna Nase(42) in Projekt Perpetuum nicht gefunden: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Perpetuum.*Anna.*Nase.*42.*"
                        , this.systemOutMock.getLog()));
    }

    @Test
    public void projektInAbteilungTest() {
        this.projektAnlegen("Perpetuum");
        this.abteilungAnlegen("Shield", 4242);
        this.projektZuAbteilung(1, 1);
        this.datenZeigen();

        Assert.assertTrue("Projekt Perpetuum nicht in Abteilung Shield gefunden: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Shield.*4242.*Perpetuum.*"
                        , this.systemOutMock.getLog()));
    }

    @Test
    public void nachrichtAnProjektTest() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.mitarbeiterZuProjekt(1, 2);
        this.mitarbeiterZuProjekt(2, 2);
        this.systemOutMock.clearLog();
        this.infoInProjekt(1, "Xulu");
        Assert.assertTrue("Nachricht Xulu nicht an Anna und Sergej geschickt: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Anna.*Xulu.*Sergej.*Xulu.*"
                        , this.systemOutMock.getLog())
                || Pattern.matches("(?s).*Sergej.*Xulu.*Anna.*Xulu.*"
                        , this.systemOutMock.getLog())
        );
    }

    @Test
    public void nachrichtAnProjekt2Test() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.mitarbeiterZuProjekt(1, 2);
        this.mitarbeiterZuProjekt(2, 2);
        this.systemOutMock.clearLog();
        this.infoInProjekt(2, "Xulu");Assert.assertTrue("Nachricht Xulu nicht an Anna und Edna geschickt: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Anna.*Xulu.*Edna.*Xulu.*"
                        , this.systemOutMock.getLog())
                || Pattern.matches("(?s).*Edna.*Xulu.*Anna.*Xulu.*"
                        , this.systemOutMock.getLog())
        );
    }

    @Test
    public void nachrichtAnProjekt3Test() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.systemOutMock.clearLog();
        this.infoInProjekt(2, "Xulu");
        Assert.assertTrue("Nachricht Xulu irrtuemlich verschickt: "
                + this.systemOutMock.getLog(), Pattern.compile("Xulu")
                .split(this.systemOutMock.getLog()).length == 1);
    }

    @Test
    public void nachrichtAnAbteilungTest() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.mitarbeiterZuProjekt(1, 2);
        this.mitarbeiterZuProjekt(2, 2);
        this.abteilungAnlegen("Shield", 4242);
        this.projektZuAbteilung(1, 1);
        this.systemOutMock.clearLog();
        this.infoInAbteilung(1, "Xulu");

        Assert.assertTrue("Nachricht Xulu nicht an Anna und Sergej geschickt: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Anna.*Xulu.*Sergej.*Xulu.*"
                        , this.systemOutMock.getLog())
                || Pattern.matches("(?s).*Sergej.*Xulu.*Anna.*Xulu.*"
                        , this.systemOutMock.getLog())
        );
        
    }

    @Test
    public void nachrichtAnAbteilung2Test() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.mitarbeiterZuProjekt(1, 2);
        this.mitarbeiterZuProjekt(2, 2);
        this.abteilungAnlegen("Shield", 4242);
        this.abteilungAnlegen("NCIS", 4243);
        this.projektZuAbteilung(1, 1);
        this.systemOutMock.clearLog();
        this.infoInAbteilung(2, "Xulu");

        Assert.assertTrue("Nachricht Xulu irrtuemlich verschickt: "
                + this.systemOutMock.getLog(), Pattern.compile("Xulu")
                .split(this.systemOutMock.getLog()).length == 1);
    }

    @Test
    public void nachrichtAnAbteilung3Test() {
        this.mitarbeiterAnlegen("Anna Nase", 42);
        this.projektAnlegen("Perpetuum");
        this.mitarbeiterZuProjekt(1, 1);
        this.mitarbeiterAnlegen("Edna Nase", 43);
        this.mitarbeiterAnlegen("Sergej Nase", 44);
        this.mitarbeiterZuProjekt(3, 1);
        this.projektAnlegen("Zero");
        this.mitarbeiterZuProjekt(1, 2);
        this.mitarbeiterZuProjekt(2, 2);
        this.abteilungAnlegen("Shield", 4242);
        this.projektZuAbteilung(1, 1);
        this.projektZuAbteilung(2, 1);
        this.systemOutMock.clearLog();
        this.infoInAbteilung(1, "Xulu");
        
        Assert.assertTrue("Nachricht Xulu nicht an Anna und Sergej geschickt: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Anna.*Xulu.*Sergej.*Xulu.*"
                        , this.systemOutMock.getLog())
                || Pattern.matches("(?s).*Sergej.*Xulu.*Anna.*Xulu.*"
                        , this.systemOutMock.getLog())
        );
        Assert.assertTrue("Nachricht Xulu nicht an Anna und Edna geschickt: "
                + this.systemOutMock.getLog()
                , Pattern.matches("(?s).*Anna.*Xulu.*Edna.*Xulu.*"
                        , this.systemOutMock.getLog())
                || Pattern.matches("(?s).*Edna.*Xulu.*Anna.*Xulu.*"
                        , this.systemOutMock.getLog())
        );
        Assert.assertTrue("Nachricht Xulu nicht doppelt an Anna geschickt: "
                + this.systemOutMock.getLog(), Pattern.matches("(?s).*Anna.*Xulu.*Anna.*Xulu.*", this.systemOutMock.getLog()));
    }
}
